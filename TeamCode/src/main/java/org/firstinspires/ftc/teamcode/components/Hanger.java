package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Hanger {
    LiftStates STATE = LiftStates.STATIC;
    public static boolean STOP = false;
    private Telemetry telemetry;
    private DcMotorEx HangMotor;
    public double speed, position, gotoPosition, MAX_HIGHT = 1876;
    private boolean Full_extend;

    public Hanger(HardwareMap hm, Telemetry tel){
        telemetry = tel;
        HangMotor = hm.get(DcMotorEx.class, "Hang Motor");
        HangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        HangMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        MotorConfigurationType mct = HangMotor.getMotorType().clone();
        mct.setAchieveableMaxRPMFraction(1.0);
        HangMotor.setMotorType(mct);

        HangMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        HangMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        position = 0;
        speed = 0;
        STATE = LiftStates.GO_HARD_DOWN;
        gotoPosition = 0;
    }
    private void hard_loop(){
        HangMotor.setPower(1.0);
    }
    public void loop(){

        if(Full_extend){
            HangMotor.setPower(1);
        } else if (HangMotor.getCurrentPosition() > 0){
            HangMotor.setPower(-1);
        }

//        if(STOP){
//            telemetry.addData("position", HangMotor.getCurrentPosition());
//            return;
//        }
//        if(Full_extend) gotoPosition = MAX_HIGHT;
//        else gotoPosition = 0;
//        double d = HangMotor.getCurrentPosition();
//        speed = Math.abs(position - d);
//        position = d;
//        if(STATE == LiftStates.GO_HARD_DOWN){
//            hard_loop();
//            if(speed == 0){
//                STATE = LiftStates.STATIC;
//                HangMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//                HangMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//
//                HangMotor.setTargetPosition(0);
//                HangMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//                STATE = LiftStates.STATIC;
//            }
//        } else {
//            HangMotor.setTargetPosition((int)gotoPosition);
//            HangMotor.setPower(1);
//        }
        telemetry.addData("position", HangMotor.getCurrentPosition());
        telemetry.addData("STATE", STATE.toString());
    }

    public void setPosition(boolean full_extend){
        Full_extend = full_extend;
    }
    public boolean getPosition(){
        return Full_extend;
    }
}
