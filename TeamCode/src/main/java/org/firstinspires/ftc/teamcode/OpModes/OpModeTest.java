package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.components.BratOutTake;
import org.firstinspires.ftc.teamcode.components.Hanger;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

@TeleOp(name = "test hang")
@Config
public class OpModeTest extends LinearOpMode {
    public static Hanger hanger;
    public int pos = 1;
    private StickyGamepads gp2;
    private BratOutTake arm;
    @Override
    public void runOpMode() throws InterruptedException{
        FtcDashboard dash = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dash.getTelemetry());
        arm = new BratOutTake(hardwareMap, telemetry);
        gp2 = new StickyGamepads(gamepad2);


        waitForStart();
//        hanger.STOP = true;
        while(opModeIsActive() && !isStopRequested()){
            if(gp2.x){
                arm.activate();
            }
            if(gp2.y){
                arm.deactivate();
            }
            if(gp2.a){
                arm.setFeedPos();
            }
            arm.update();
            gp2.update();
            telemetry.update();
        }
    }
}
