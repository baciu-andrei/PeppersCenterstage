package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.IStateBasedModule;

import java.util.ArrayList;

public class Elevator implements IStateBasedModule {

    DcMotorEx ll;
    DcMotorEx lr;

    Gamepad gamepad1, gamepad2;
    static int groundPos = 0, level = 0, incrementPos = 50, firstLevelPos = 20, transferPos = 40;



    /*TODO
     *  updateState() reseting,next State
     *  updateHardware() , reset motor power , pid motoare
     *  update la final
     *  write in gamepad
     *  */
    enum State{
        DOWN(groundPos), GOING_DOWN(groundPos, DOWN),
        UP(groundPos + firstLevelPos +level* incrementPos), GOING_UP(groundPos + firstLevelPos +level* incrementPos, UP),
        TRANSFER(transferPos), GOING_TRANSFER(transferPos, TRANSFER),
        RESETING(groundPos, DOWN);

        public int position;
        public final State nextState;
        State(int position){
            this.position = position;
            this.nextState = this;
        }
        State(int position, State nextState)
        {
            this.position = position;
            this.nextState = nextState;
        }
    }
    State state;

    public void setState(State newState){
        if(state == newState)
            return;
        else
            this.state = newState;

    }
    public void updateStateValues(){
        State.DOWN.position = groundPos;
        State.GOING_DOWN.position = groundPos;
        State.UP.position = groundPos + firstLevelPos + incrementPos*level;
        State.GOING_UP.position = groundPos + firstLevelPos + incrementPos*level;
        State.TRANSFER.position = transferPos + groundPos;
        State.GOING_TRANSFER.position = transferPos + groundPos;
    }
    public Elevator(HardwareMap hm, Gamepad gamepad1, Gamepad gamepad2, State initialState)
    {
        ll = hm.get(DcMotorEx.class, "ll");
        lr = hm.get(DcMotorEx.class, "lr");

        ll.setDirection(DcMotorSimple.Direction.REVERSE);
        lr.setDirection(DcMotorSimple.Direction.REVERSE);

        ArrayList<DcMotorEx> motorListElevator = new ArrayList<>(ll, lr);

        for(DcMotorEx motorElevator : motorListElevator){
            motorElevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorElevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorElevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            MotorConfigurationType motorConfigurationType = motorElevator.getMotorType().clone();
            motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
            motorElevator.setMotorType(motorConfigurationType);
        }

        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.state = initialState;
 
    }

    public void update(){
        updateStateValues();
        updateState();
        updateHardware();
    }

    @Override
    public void updateState() {
        if(this.state == State.RESETING)
        {
            //RESET CODE IDK
        }
        else
        {

        }
    }

    @Override
    public void updateHardware() {

    }

}
