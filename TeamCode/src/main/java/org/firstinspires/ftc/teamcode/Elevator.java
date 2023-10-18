package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import java.util.ArrayList;

public class Elevator implements IRobotModule{

    DcMotorEx ll;
    DcMotorEx lr;
    Gamepad gamepad1, gamepad2;
    static int low = 100, mid = 200, high = 300, ground = 0, target;

    public enum State{
        LOW(low),
        MID(mid),
        HIGH(high),
        GROUND(ground),
        GOING_LOW(low),
        GOING_MID(mid),
        GOING_HIGH(high),
        GOING_GROUND(ground);

        public int pos;

        State(int pos) {
            this.pos = pos;
        }

        public static void update(){
            LOW.pos = low;
            MID.pos = mid;
            HIGH.pos = high;
            GROUND.pos = ground;
            GOING_GROUND.pos = ground;
        }
    }
    public State state, previousState;
    public Elevator(HardwareMap hm, Gamepad gamepad1, Gamepad gamepad2)
    {
        init(hm,gamepad1,gamepad2);
    }

    public void init(HardwareMap hm, Gamepad gamepad1, Gamepad gamepad2)
    {
        ll = hm.get(DcMotorEx.class, "ll");
        lr = hm.get(DcMotorEx.class, "lr");

        ll.setDirection(DcMotorSimple.Direction.REVERSE);
        lr.setDirection(DcMotorSimple.Direction.REVERSE);

        ArrayList<DcMotorEx> motorListElevator = new ArrayList<>();
        motorListElevator.add(ll);
        motorListElevator.add(lr);

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
    }

    @Override
    public void atStart() {

    }

    @Override
    public void loop() {
        State.update();
    }
}
