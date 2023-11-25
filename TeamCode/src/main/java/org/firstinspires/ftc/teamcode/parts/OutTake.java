package org.firstinspires.ftc.teamcode.parts;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.BratOutTake;
import org.firstinspires.ftc.teamcode.components.Controls;
import org.firstinspires.ftc.teamcode.components.Elevator;
import org.firstinspires.ftc.teamcode.components.Grippers;
import org.firstinspires.ftc.teamcode.components.LiftStates;

@Config
public class OutTake {
    private enum STATES_OOUTTAKE {
        RESET_ELEVATOR,
        SET_FEED_FORWARD,
        SHOUD_RETRACT,
        WAIT_FOR_ARM_REST,
        WAIT_FOR_ELEVATOR_REST,
        READY_FOR_PIXELS;

        public static int pos = 0;
        public static int saved_level = 0;
    }
    private STATES_OOUTTAKE STATE;
    private final Elevator elevator;
    private final BratOutTake arm;
    private final Grippers grippers;
    private Telemetry telemetry;

    public int RetractLevel = 4;

    private Controls controls;
    public OutTake(HardwareMap hm, Controls c, Telemetry tele){
        elevator = new Elevator(hm, tele);
        arm = new BratOutTake(hm, tele);
        grippers = new Grippers(hm, tele);

        telemetry = tele;
        controls = c;
        STATE = STATES_OOUTTAKE.READY_FOR_PIXELS;
        grippers.reset();
    }

    private void handleControls(){

        ///////// ELEVATOR /////////
        if(controls.retractElevtor && elevator.getLevelNow() > 0){
            STATE = STATES_OOUTTAKE.SHOUD_RETRACT;
            grippers.reset();
        }
        if(controls.extendElevator){
            elevator.setLevel(STATES_OOUTTAKE.saved_level);
        }
        if(controls.stepElevtorUp){
            elevator.setLevel(elevator.getLevel() + 1);
        }
        if(controls.stepElevatorDown){
            elevator.setLevel(elevator.getLevel() - 1);
        }

        ///////// ARM ///////////
        if(controls.rotatePixels){
            if(arm.isRotated) arm.antiRotate90();
            else arm.rotate90();
        }

        //////// CLAW //////////
        if(controls.dropPixel1){
            grippers.Drop1();
        }
        if(controls.dropPilex2){
            grippers.Drop2();
        }

    }

    public void update(){
        switch (STATE){
            case SHOUD_RETRACT:

                arm.deactivate();
                arm.update();
                STATES_OOUTTAKE.saved_level = elevator.getLevel();
                elevator.setLevel(RetractLevel);

                STATE = STATES_OOUTTAKE.WAIT_FOR_ELEVATOR_REST;
                STATES_OOUTTAKE.pos = RetractLevel;

                break;

            case RESET_ELEVATOR:

                STATE = STATES_OOUTTAKE.WAIT_FOR_ELEVATOR_REST;
                STATES_OOUTTAKE.pos = 0;
                elevator.setLevel(0);

                break;
            case SET_FEED_FORWARD:
                // TODO:

                break;

            case WAIT_FOR_ARM_REST:
                if(arm.isAtRest()){
                    STATE = STATES_OOUTTAKE.RESET_ELEVATOR;
                }
                break;
            case WAIT_FOR_ELEVATOR_REST:
                if(elevator.getLevelNow() == STATES_OOUTTAKE.pos){
                    if(STATES_OOUTTAKE.pos == RetractLevel) {
                        STATE = STATES_OOUTTAKE.WAIT_FOR_ARM_REST;
                    } else if(STATES_OOUTTAKE.pos == 0){
                        STATE = STATES_OOUTTAKE.READY_FOR_PIXELS;
                    }
                }
                break;
            case READY_FOR_PIXELS:
                if(elevator.getLevelNow() > 3.3){
                    arm.activate();
                    arm.rotate90();
                }
                break;
        }

        if(elevator.getLevelNow() != 0){
            grippers.dezactivateAuto();
        } else grippers.reset();

        handleControls();

        arm.update();
        grippers.update();
        elevator.loop();

    }
}
