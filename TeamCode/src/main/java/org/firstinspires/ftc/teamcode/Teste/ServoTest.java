package org.firstinspires.ftc.teamcode.Teste;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="ServoTest")

public class ServoTest extends LinearOpMode {

    public static double position = 0;
    public boolean wasDpadUpPressed = false, wasDpadDownPressed = false, isServo1Reversed = false, wasOptionsPressed = false;

    public void runOpMode() throws InterruptedException {

        Servo servo = hardwareMap.get(Servo.class, "servo");
        servo.setDirection(Servo.Direction.FORWARD);
        servo.setPosition(position);

        waitForStart();
        while(opModeIsActive())
        {
            if(gamepad1.dpad_up && !wasDpadUpPressed) {
                position = position + 0.01;
            }
            wasDpadUpPressed = gamepad1.dpad_up;

            if(gamepad1.dpad_down && !wasDpadDownPressed){
                position = position - 0.01;
            }
            wasDpadDownPressed = gamepad1.dpad_down;

            if(gamepad1.a){
                position = 0;
            }
            if(gamepad1.y){
                position = 1;
            }

            if(gamepad1.options && !wasOptionsPressed)
            {
                if(isServo1Reversed)
                {
                    servo.setDirection(Servo.Direction.FORWARD);
                    isServo1Reversed = false;
                }
                else {
                    servo.setDirection(Servo.Direction.REVERSE);
                    isServo1Reversed = true;
                }
            }
            wasOptionsPressed = gamepad1.options;

            servo.setPosition(position);

            telemetry.addData("servo position", servo.getPosition());
            telemetry.addData("desired position", position);
            telemetry.addData("servo reversed", isServo1Reversed);
            telemetry.update();

        }
    }
}