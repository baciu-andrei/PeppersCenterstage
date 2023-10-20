package org.firstinspires.ftc.teamcode.Fastpixel;

import com.qualcomm.ftccommon.FtcWifiDirectChannelSelectorActivity;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DriveTrain;

import java.util.concurrent.TimeUnit;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "trashTeleOp")

public class mergi extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DriveTrain driveTrain;
        driveTrain = new DriveTrain(hardwareMap , gamepad1 , gamepad2);

        OpClaw claw;
        double clawClosingPos = 0.6, clawOpeningPos = 0.5;
        double clawRotation0DegreesOuttake = 0.3, clawRotation45DegreesOuttake = 0.45, clawRotation90DegreesOuttake = 0.6;
        double clawVirtualStorage = 0.4, clawVirtualScoring = 0.6;
        boolean isLeftClosed = false, isRightClosed = false, isVirtualInScoringPos = false;
        claw = new OpClaw(hardwareMap, clawOpeningPos, clawRotation0DegreesOuttake, clawVirtualStorage);
        boolean wasSharePreviouslyPressed = false, wasOptionsPreviouslyPressed = false, wasYPrevoiuslyPressed = false, wasLeftBumperPreviouslyPressed = false, wasRightBumperPreviouslyPressed = false, wasDpadRightPreviouslyPressed = false, wasDpadLeftPreviouslyPressed = false, wasBPreviouslyPressed = false;
        boolean isReadyToScore = false, isPasstroughUp = false;

        lift elevator = new lift(hardwareMap);
        int level = 0, increment = 40, firstpos = 70, ground = 0, passthroughPos = 100;
        boolean goingToTargetPos = false, scoringPosition = false;

        DcMotorEx intake = hardwareMap.get(DcMotorEx.class, "intake");
        boolean enabledIntake = false;
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setPower(0);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        ElapsedTime timer = new ElapsedTime();

        boolean wasDpadUpPreviouslyPressed = false, wasDpadDownPreviouslyPressed = false, wasXPreviouslyPressed = false, wasAPreviouslyPressed = false;

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
                else if(goingToTargetPos == true && timer.milliseconds() >= 1000)
                {
                    if(scoringPosition == true)
                    {
                        elevator.setTarget(ground + firstpos + level*increment);
                        goingToTargetPos = false;
                        scoringPosition = false;
                    }
                    if(scoringPosition == false)
                    {
                        elevator.setTarget(ground);
                        goingToTargetPos = false;
                    }

                }
            }
            wasXPreviouslyPressed = gamepad1.x;

            if(gamepad1.options && !wasOptionsPreviouslyPressed)
            {
                if(isPasstroughUp)
                {
                    elevator.setTarget(ground);
                    isPasstroughUp = false;
                }
                else {
                    elevator.setTarget(passthroughPos);
                    isPasstroughUp = true;
                }
            }
            wasOptionsPreviouslyPressed = gamepad1.options;

            if(gamepad1.share && !wasSharePreviouslyPressed)
            {
                if(isReadyToScore)
                {
                    claw.virtual(clawVirtualStorage);
                    isReadyToScore = false;
                    scoringPosition = true;
                }
                else
                {
                    claw.virtual(clawVirtualScoring);
                    isReadyToScore = true;
                }

            }
            wasSharePreviouslyPressed = gamepad1.share;
            //intake
            if(gamepad1.a && !wasAPreviouslyPressed)
            {
                if(enabledIntake == false)
                {
                    intake.setPower(1);
                    enabledIntake = true;
                }
                else
                {
                    intake.setPower(0);
                    enabledIntake = false;
                }
            }
            wasAPreviouslyPressed = gamepad1.a;

            //claw

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