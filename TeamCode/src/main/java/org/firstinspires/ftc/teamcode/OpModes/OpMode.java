package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.components.Controls;
import org.firstinspires.ftc.teamcode.components.Elevator;
import org.firstinspires.ftc.teamcode.parts.Avion;
import org.firstinspires.ftc.teamcode.parts.DriveTrain;
import org.firstinspires.ftc.teamcode.parts.Intake;
import org.firstinspires.ftc.teamcode.parts.OutTake;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

import java.util.List;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "dionijos")
public class OpMode extends LinearOpMode {

    DriveTrain driveTrain;
    OutTake outTake;
    Intake intake;
    Controls controls;
    Avion avion;


    FtcDashboard dash;
    public void initialize(){
        dash = FtcDashboard.getInstance();
        controls = new Controls(gamepad1, gamepad2);
        telemetry = new MultipleTelemetry(telemetry, dash.getTelemetry());
        driveTrain = new DriveTrain(hardwareMap, gamepad1, gamepad2, telemetry);
        outTake = new OutTake(hardwareMap, controls, telemetry);
        intake = new Intake(hardwareMap, controls, telemetry);
        avion = new Avion(hardwareMap, telemetry, controls);

    }

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        List<LynxModule> Hubs = hardwareMap.getAll(LynxModule.class);

        for(LynxModule module : Hubs){
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        ElapsedTime freq = new ElapsedTime();
        boolean b = false;
        waitForStart();


        while(opModeIsActive() && !isStopRequested()) {
            freq.reset();

            if(!b && gamepad1.b){
                if(!gamepad1.isRumbling()){
                    gamepad1.rumbleBlips(1);
                }
            }

            controls.update();

            driveTrain.run();
            outTake.update();
            intake.loop();
            avion.loop();

            b = gamepad1.b;
            telemetry.addData("freq", (double)1/freq.seconds());
            for(LynxModule module : Hubs){
                module.clearBulkCache();
            }
        }
    }
}
