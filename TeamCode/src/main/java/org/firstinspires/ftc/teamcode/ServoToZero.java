package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "servoTo0")
public class ServoToZero extends LinearOpMode {
    Servo se1;
    Servo se2;
    Servo se3;
    Servo se4;
    Servo se5;
    Servo se0;
    Servo sc2;
    Servo sc4;
    @Override
    public void runOpMode() throws InterruptedException {
        se1 = hardwareMap.get(Servo.class, "se1");
        se2 = hardwareMap.get(Servo.class, "se2");
        se3 = hardwareMap.get(Servo.class, "se3");
        se4 = hardwareMap.get(Servo.class, "se4");
        se5 = hardwareMap.get(Servo.class, "se5");
        se0 = hardwareMap.get(Servo.class, "se0");
        sc2 = hardwareMap.get(Servo.class, "sc2");
        sc4 = hardwareMap.get(Servo.class, "sc4");
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                se1.setPosition(0);
                if (gamepad1.options)
                    se1.setPosition(1);
            }
            if (gamepad1.b) {
                se2.setPosition(0);
                if (gamepad1.options)
                    se2.setPosition(1);
            }
            if (gamepad1.y) {
                se3.setPosition(0);
                if (gamepad1.options)
                    se3.setPosition(1);
            }
            if (gamepad1.x) {
                se4.setPosition(0);
                if (gamepad1.options)
                    se4.setPosition(1);
            }
            if (gamepad1.dpad_up) {
                se5.setPosition(0);
                if (gamepad1.options)
                    se5.setPosition(1);
            }
            if (gamepad1.dpad_down) {
                se0.setPosition(0);
                if (gamepad1.options)
                    se0.setPosition(1);
            }
            if (gamepad1.left_bumper) {
                sc2.setPosition(0);
                if (gamepad1.options)
                    sc2.setPosition(1);
            }
            if (gamepad1.right_bumper) {
                sc4.setPosition(0);
                if (gamepad1.options)
                    sc4.setPosition(1);
            }
        }
    }
}
