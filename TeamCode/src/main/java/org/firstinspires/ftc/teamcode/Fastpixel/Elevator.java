package org.firstinspires.ftc.teamcode.Fastpixel;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utils.CoolMotor;
import org.firstinspires.ftc.teamcode.Utils.StickyGamepads;
@Config
public class Elevator{

//    final private CoolMotor left, right;
    final private DcMotorEx left, right;
    StickyGamepads gamepad1, gamepad2;
//    public static PIDCoefficients pidCoefficients = new PIDCoefficients(0.01,0.1,0.001);
//
//    public static double ff1 = 0.1, ff2 = 0.00009;
    final private double fullExtend = 950;
    public static int offset = 5;
    private int lift_level ;
    public int gotoPos;
    final private double CPR = 103.8, diametruSpool = 32, oneStep = fullExtend/11;

    Telemetry telemetry;

    public Elevator(HardwareMap hardwareMap, Gamepad g1, Gamepad g2, Telemetry tel){
//        left  = new CoolMotor(hardwareMap.get(DcMotorEx.class, "ll"), CoolMotor.RunMode.PID, false);
//        right = new CoolMotor(hardwareMap.get(DcMotorEx.class, "lr"), CoolMotor.RunMode.PID, false);

        left = hardwareMap.get(DcMotorEx.class, "ll");
        right = hardwareMap.get(DcMotorEx.class, "lr");

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        left.setTargetPosition(0);
        right.setTargetPosition(0);

        left.setPower(1);
        right.setPower(1);

        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry = tel;
        gamepad1 = new StickyGamepads(g1);
        gamepad2 = new StickyGamepads(g2);
    }

    private void controls(){
        if(gamepad2.dpad_up){
            lift_level ++;
            if(lift_level > 11) lift_level = 11;
            else{
                gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
            }
        }
        if(gamepad2.dpad_down){
            lift_level --;
            if(lift_level < 0) lift_level = 0;
            else{
                gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
            }
        }
        if(gamepad2.dpad_left){
            lift_level = 0;
            gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
        }
        if(gamepad2.dpad_right){
            lift_level = 11;
            gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
        }

        gotoPos += gamepad2.gamepad.right_stick_y*4;

        gamepad1.update();
        gamepad2.update();
    }

    public void loop(){   
        controls();

        if(gotoPos == 0) gotoPos = -5;

        left.setTargetPosition(gotoPos + offset);
        right.setTargetPosition(gotoPos);

        telemetry.addData("current position", left.getCurrentPosition());
        telemetry.addData("level", lift_level);
        telemetry.addData("target position", gotoPos);

        telemetry.update();
    }
}
