package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import java.util.ArrayList;

public class BElevator implements IStateBasedModule {

    DcMotorEx ll;
    DcMotorEx lr;
    static int groundPos = 0, level = 0, incrementPos = 50, firstLevelPos = 20, transferPos = 40;

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
        State.RESETING.position = groundPos;
    }
    public BElevator(HardwareMap hm, Gamepad gamepad1, Gamepad gamepad2, State initialState)
    {
        ll = hm.get(DcMotorEx.class, "ll");
        lr = hm.get(DcMotorEx.class, "lr");

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


        this.state = initialState;
    }
    public void update(){
        updateStateValues();
        updateState();
        updateHardware();
    }
    @Override
    public void updateState() {
        if(this.state == State.RESETING&&Math.abs(ll.getVelocity())<=0)
        {
            groundPos = ll.getCurrentPosition();
            state = state.nextState;
        }
        else
        {
            if(state.nextState.position==ll.getCurrentPosition())
            {
                state = state.nextState;
            }
        }
    }

    @Override
    public void updateHardware() {
        if(state == State.RESETING)
        {
            ll.setPower(-0.5);
            lr.setPower(-0.5);
        }
        else {
            ll.setTargetPosition(state.position);
            lr.setTargetPosition(state.position);
        }
    }

}
