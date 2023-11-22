package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.components.Elevator;
import org.firstinspires.ftc.teamcode.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.parts.Intake;
import org.firstinspires.ftc.teamcode.parts.OutTake;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "dionijos")
public class OpMode extends LinearOpMode {

    DriveTrain driveTrain;
    OutTake outTake;
    Intake intake;

    FtcDashboard dash;
    public void initialize(){
        dash = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dash.getTelemetry());
        driveTrain = new DriveTrain(hardwareMap, gamepad1, gamepad2, telemetry);
        outTake = new OutTake(hardwareMap, gamepad1, gamepad2, telemetry);
        intake = new Intake(hardwareMap, gamepad1, gamepad2, telemetry);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();

        while(opModeIsActive() && !isStopRequested()) {
            driveTrain.run();
            outTake.loop();
            intake.loop();
        }
    }
}
