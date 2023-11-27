package org.firstinspires.ftc.teamcode.Autonomy;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.parts.DriveTrain;

@Autonomous(name = "auto1")
public class Autonomy1 extends LinearOpMode {


    private SampleMecanumDrive mecanumDrive;

    private void iniT(){
        mecanumDrive = new SampleMecanumDrive(hardwareMap);
    }
    @Override
    public void runOpMode() throws InterruptedException{

        Trajectory traj = mecanumDrive.trajectoryBuilder(new Pose2d())
                .forward(5)
                .build();

        waitForStart();

        mecanumDrive.followTrajectory(traj);

        while(opModeIsActive() && !isStopRequested()){

        }
    }

}
