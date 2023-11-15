package org.firstinspires.ftc.teamcode.Fastpixel;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.teamcode.DriveTrain;

import java.util.concurrent.TimeUnit;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "trashTeleOp")
@Config

public class Mergi extends LinearOpMode {

    FtcDashboard dash = FtcDashboard.getInstance();
    Outtake outtake;
    public static double intakeServoDown = 0.2, intakeServoUp = 0.8;
    public static double clawAngleStorage = 0, clawAngleScoring = 0.25;
    public static double clawClosingPos = 0.5, clawOpeningPos = 0;
    public static double intakeSpeed = 0.6;
    public static double clawRotation0DegreesOuttake = 0.5, clawRotation45DegreesOuttake = 0, clawRotation90DegreesOuttake = 0;
    public static double clawVirtualStorage = 0, clawVirtualScoring = 0.63;
    public static boolean isLeftClosed = false, isRightClosed = false , isClawAngleAtStorage = true;
    public static boolean wasG2YPreviouslyPressed = false, wasSharePreviouslyPressed = false, wasOptionsPreviouslyPressed = false, wasYPrevoiuslyPressed = false, wasLeftBumperPreviouslyPressed = false, wasLeftBumper2PreviouslyPressed = false, wasRightBumper2PreviouslyPressed = false, wasRightBumperPreviouslyPressed = false, wasDpadRightPreviouslyPressed = false, wasDpadLeftPreviouslyPressed = false, wasBPreviouslyPressed = false;
    public static boolean isReadyToScore = false, isPasstroughUp = false;
    public static int level = 0, increment = 40, firstpos = 600, ground = 0, passthroughPos = 100;
    public static boolean goingToTargetPos = false, scoringPosition = false;
    public static boolean enabledIntake = false;
    public static int liftConfig = 0;
    public static boolean wasRightStickButtonPressed = false;
    public static boolean wasDpadUpPreviouslyPressed = false, wasDpadDownPreviouslyPressed = false, wasXPreviouslyPressed = false, wasAPreviouslyPressed = false;
    @Override
    public void runOpMode() throws InterruptedException {

        telemetry = new MultipleTelemetry(telemetry, dash.getTelemetry());
        DriveTrain driveTrain;
        driveTrain = new DriveTrain(hardwareMap , gamepad1 , gamepad2 , telemetry);

        IntakeServo intakeServo = new IntakeServo(hardwareMap , intakeServoUp);

        OpClaw claw;

        claw = new OpClaw(hardwareMap, clawOpeningPos, clawRotation0DegreesOuttake, clawVirtualStorage, clawAngleStorage);

        outtake = new Outtake(hardwareMap, gamepad1, gamepad2, telemetry);

        DcMotorEx intake = hardwareMap.get(DcMotorEx.class, "intake");

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setPower(0);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        ElapsedTime timer = new ElapsedTime();


        timer.time(TimeUnit.MILLISECONDS);

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
            driveTrain.loop();

            //lift lmao
            if(gamepad1.dpad_up && !wasDpadUpPreviouslyPressed)
                level++;
            wasDpadUpPreviouslyPressed = gamepad1.dpad_up;

            if(gamepad1.dpad_down && !wasDpadDownPreviouslyPressed)
                level--;
            wasDpadDownPreviouslyPressed = gamepad1.dpad_down;

            if(gamepad1.x && !wasXPreviouslyPressed)
            {
                if(goingToTargetPos == false)
                {
                    goingToTargetPos = true;
                    timer.reset();
                }
            }
            wasXPreviouslyPressed = gamepad1.x;




            wasOptionsPreviouslyPressed = gamepad1.options;

            if(gamepad2.a && !wasSharePreviouslyPressed)
            {
                if(isReadyToScore)
                {
                    claw.virtual(clawVirtualStorage);
                    isReadyToScore = false;

                }
                else
                {
                    claw.virtual(clawVirtualScoring);
                    isReadyToScore = true;
                }

            }
            wasSharePreviouslyPressed = gamepad2.a;
            //intake
            if(gamepad1.a && !wasAPreviouslyPressed)
            {
                if(enabledIntake == false)
                {
                    intake.setPower(intakeSpeed);
                    enabledIntake = true;
                }
                else
                {
                    intake.setPower(0);
                    enabledIntake = false;
                }
            }
            wasAPreviouslyPressed = gamepad1.a;

            if(gamepad2.left_bumper && wasLeftBumper2PreviouslyPressed)
            {
                intakeServo.setIntakeServo(intakeServoDown);
            }
            wasLeftBumper2PreviouslyPressed = gamepad2.left_bumper;

            if(gamepad2.right_bumper && wasRightBumper2PreviouslyPressed)
            {
                intakeServo.setIntakeServo(intakeServoUp);
            }
            wasRightBumper2PreviouslyPressed = gamepad2.right_bumper;

            //claw

            if(gamepad2.b && !wasRightStickButtonPressed)
            {
                if(isClawAngleAtStorage == true)
                {
                    claw.setAng(clawAngleScoring);
                    isClawAngleAtStorage = false;
                }
                else
                {
                    claw.setAng(clawAngleStorage);
                    isClawAngleAtStorage = true;
                }
            }
            wasRightStickButtonPressed = gamepad2.b;

            //rotate

            if(gamepad1.y && !wasYPrevoiuslyPressed)
            {
                claw.rotateOuttake(clawRotation0DegreesOuttake);
            }
            wasYPrevoiuslyPressed = gamepad1.y;
            if(gamepad1.dpad_right && !wasDpadRightPreviouslyPressed)
            {
                claw.rotateOuttake(clawRotation45DegreesOuttake);
            }
            wasDpadRightPreviouslyPressed = gamepad1.dpad_right;
            if(gamepad1.dpad_left && !wasDpadLeftPreviouslyPressed)
            {
                claw.rotateOuttake(clawRotation90DegreesOuttake);
            }
            wasDpadLeftPreviouslyPressed = gamepad1.dpad_left;

            //gripper
            if(gamepad1.left_bumper && !wasLeftBumperPreviouslyPressed)
            {
                if(isLeftClosed)
                {
                    claw.gripperBack(clawOpeningPos);
                    isLeftClosed = false;
                }
                else
                {
                    claw.gripperBack(clawClosingPos);
                    isLeftClosed = true;
                }
            }
            wasLeftBumperPreviouslyPressed = gamepad1.left_bumper;
            if(gamepad1.right_bumper && !wasRightBumperPreviouslyPressed)
            {
                if(isRightClosed)
                {
                    claw.gripperFront(clawOpeningPos);
                    isRightClosed = false;
                }
                else
                {
                    claw.gripperFront(clawClosingPos);
                    isRightClosed = true;
                }
            }
            wasRightBumperPreviouslyPressed = gamepad1.right_bumper;

            outtake.loop();


            wasRightBumperPreviouslyPressed = gamepad1.right_bumper;


            telemetry.addData("intakeSpeed", intakeSpeed);
            telemetry.addData("groundT",ground);
            telemetry.addData("passthroughPosT",passthroughPos);
            telemetry.addData("levelT",level);
            telemetry.addData("clesteSt/spateT",isLeftClosed);
            telemetry.addData("clesteDr/fataT",isRightClosed);
            telemetry.addData("isReadyToScoreT",isReadyToScore);
            telemetry.addData("isPasstroughUpT",isPasstroughUp);
            telemetry.addData("goingToTargetPosT",goingToTargetPos);
            telemetry.addData("scoringPositionT",scoringPosition);
            telemetry.addData("isClawAngleAtStorage", isClawAngleAtStorage);
            telemetry.update();
        }
    }
}
/*
dpad_up - level , dpad_down - level , dpad_right - rotate 45, dpad_left - rotate 0
x - lift
y -  rotate 90
a - intake
b - virtual
rt,lt
lb,rb - grippers
options
 */