package org.firstinspires.ftc.teamcode.Autonomy;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.components.Controls;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@Autonomous(name = "auto-blue")
public class AutoBlue extends LinearOpMode {
    public Controls controls;
    SampleMecanumDrive mecanumDrive;
    @Override
    public void runOpMode() throws InterruptedException{
        controls = new Controls(gamepad1, gamepad2);
        mecanumDrive = new SampleMecanumDrive(hardwareMap);

        waitForStart();

        while(!isStopRequested() && opModeIsActive()){

        }
    }

}
