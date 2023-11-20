package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.ByteController;

@TeleOp(name = "controller test")
public class ControllerTestOpMode extends OpMode{
    public static ByteController controller;
    public void runOpMode() throws InterruptedException{

        controller = new ByteController(gamepad1, telemetry);
        waitForStart();
        while(!isStopRequested() && opModeIsActive()){
            controller.loop(gamepad1);
        }
    }
}
