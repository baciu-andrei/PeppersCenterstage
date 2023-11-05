package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.DriveTrain;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "mifu lol")
public class OpMode extends LinearOpMode {

    DriveTrain driveTrain;

    public void initialize(){
        driveTrain = new DriveTrain(hardwareMap , gamepad1 , gamepad2 , telemetry);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
            driveTrain.loop();

        }
    }
}
