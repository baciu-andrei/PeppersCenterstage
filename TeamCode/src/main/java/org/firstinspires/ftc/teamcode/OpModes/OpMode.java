package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.components.Controls;
import org.firstinspires.ftc.teamcode.components.Elevator;
import org.firstinspires.ftc.teamcode.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.parts.Intake;
import org.firstinspires.ftc.teamcode.parts.OutTake;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "dionijos")
public class OpMode extends LinearOpMode {

    DriveTrain driveTrain;
    OutTake outTake;
    Intake intake;
    Controls controls;


    FtcDashboard dash;
    public void initialize(){
        dash = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dash.getTelemetry());
        driveTrain = new DriveTrain(hardwareMap, gamepad1, gamepad2, telemetry);
        outTake = new OutTake(hardwareMap, gamepad1, gamepad2, telemetry);
        intake = new Intake(hardwareMap, gamepad1, gamepad2, telemetry);
        controls = new Controls(intake, outTake, gamepad1, gamepad2);

    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();
        ElapsedTime freq = new ElapsedTime();
        while(opModeIsActive() && !isStopRequested()) {
            freq.reset();

            controls.TwoPlayers();

            driveTrain.run();
            outTake.update();
            intake.loop();

            telemetry.addData("freq", (double)1/freq.seconds());
        }
    }
}
