package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class OpMode extends LinearOpMode {

    DriveTrain driveTrain;

    public void initialize(){
        driveTrain = new DriveTrain(hardwareMap , gamepad1 , gamepad2);
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
