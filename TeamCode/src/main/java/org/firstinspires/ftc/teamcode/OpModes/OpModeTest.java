package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.components.Grippers;
import org.firstinspires.ftc.teamcode.components.Hanger;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

@TeleOp(name = "test hang")
@Config
public class OpModeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException{
        FtcDashboard dash = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dash.getTelemetry());
        StickyGamepads gp = new StickyGamepads(gamepad2);

        DcMotorEx dcMotorEx = hardwareMap.get(DcMotorEx.class, "Hang Motor");
        dcMotorEx.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcMotorEx.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        boolean var = true;
        while(opModeIsActive() && !isStopRequested()){
//            if(gp.x){
//                grippers.Drop1();
//                grippers.Drop2();
//            }
//            if(gp.a){
//                grippers.activate();
//            }
            dcMotorEx.setPower(0);
            if(gamepad2.a){
                dcMotorEx.setPower(1);
            }
            if(gamepad2.b){
                dcMotorEx.setPower(-1);
            }

            telemetry.addData("pos", dcMotorEx.getCurrentPosition());
            telemetry.update();
        }
    }
}
