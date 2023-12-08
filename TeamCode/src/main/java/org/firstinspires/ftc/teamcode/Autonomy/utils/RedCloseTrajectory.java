package org.firstinspires.ftc.teamcode.Autonomy.utils;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.components.Controls;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.parts.OutTake;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
public class RedCloseTrajectory {
    boolean mirrored, longer;
    public static double offsetX = -10, offsetY = 10;
    public static double backDropOffsetX = 10;
    public static double leftX = 12, leftY = -35.5, leftAngle = 152.51;
    public static double rightX = 6, rightY = -34, rightAngle = 49.25;
    public static double middleX = 12, middleY = -32, middleAngle = 269.33-180;
    public static double leftRetreatX = -10, leftRetreatY = -47.35, leftRetreatAngle = 360;
    public static double lbackdropX = 53, lbackdropY = -39;
    public static double mbackdropX = 54, mbackdropY = -31.5;
    public static double rbackdropX = 53, rbackdropY = -23;

    public double offsetlbackdropX = 53 - backDropOffsetX;
    public double offsetmbackdropX = 53 - backDropOffsetX;
    public double offsetrbackdropX = 53 - backDropOffsetX;


    public static double parkX = 65, parkY = -60, parkAngle = 280;


    public TrajectorySequence Left, Right, Middle;

    public Pose2d left = new Pose2d(leftX, leftY, Math.toRadians(leftAngle)),
            right = new Pose2d(rightX, rightY, Math.toRadians(rightAngle)),
            middle = new Pose2d(middleX, middleY, Math.toRadians(middleAngle));
    public Pose2d leftRetreat = new Pose2d(leftRetreatX, leftRetreatY, Math.toRadians(leftRetreatAngle));
    public Pose2d rightRetreat = new Pose2d(leftRetreatX , leftRetreatY, Math.toRadians(rightAngle));
    public Pose2d lbackdrop = new Pose2d(lbackdropX, lbackdropY, Math.toRadians(leftRetreatAngle));
    public Pose2d mbackdrop = new Pose2d(mbackdropX, mbackdropY, Math.toRadians(leftRetreatAngle));
    public Pose2d rbackdrop = new Pose2d(rbackdropX, rbackdropY, Math.toRadians(leftRetreatAngle));


    public Pose2d offlbackdrop = new Pose2d(offsetlbackdropX, lbackdropY, Math.toRadians(leftRetreatAngle));
    public Pose2d offmbackdrop = new Pose2d(offsetmbackdropX, mbackdropY, Math.toRadians(leftRetreatAngle));
    public Pose2d offrbackdrop = new Pose2d(offsetrbackdropX, rbackdropY, Math.toRadians(leftRetreatAngle));

    public Pose2d initRobotPos = new Pose2d(12.54, -61.90, Math.toRadians(267.43-180));
    public Pose2d parkPos = new Pose2d(parkX, parkY, Math.toRadians(leftRetreatAngle)),goinToPark = new Pose2d(parkX-40, (parkY-10), Math.toRadians(leftRetreatAngle));


    public RedCloseTrajectory(SampleMecanumDrive drive, OutTake outTake, Controls c){

        Left = drive.trajectorySequenceBuilder(initRobotPos)
                .lineToLinearHeading(left)
                .lineToLinearHeading(leftRetreat)
                .lineToLinearHeading(lbackdrop)
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    outTake.elevator.setLevel(5);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    outTake.elevator.setLevel(1);
                })
                .waitSeconds(0.8)
                .addTemporalMarker(() -> {
                    c.dropPixel1 = true;
                    c.dropPilex2 = true;
                })
                .waitSeconds(0.2)
                .lineToLinearHeading(offlbackdrop)
                .waitSeconds(0.3)
                .addTemporalMarker(() -> {
                    c.retractElevtor = true;
                })

                .lineToLinearHeading(goinToPark)

                .lineToLinearHeading(parkPos)
                .build();

        Right = drive.trajectorySequenceBuilder(initRobotPos)
                .lineToLinearHeading(right)
                .lineToLinearHeading(rightRetreat)
                .lineToLinearHeading(rbackdrop)
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    outTake.elevator.setLevel(5);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    outTake.elevator.setLevel(1);
                })
                .waitSeconds(0.8)
                .addTemporalMarker(() -> {
                    c.dropPixel1 = true;
                    c.dropPilex2 = true;
                })
                .waitSeconds(0.2)
                .lineToLinearHeading(offrbackdrop)
                .waitSeconds(0.3)
                .addTemporalMarker(() -> {
                    c.retractElevtor = true;
                })

                .lineToLinearHeading(goinToPark)

                .lineToLinearHeading(parkPos)
                .build();

        Middle = drive.trajectorySequenceBuilder(initRobotPos)
                .lineToLinearHeading(middle)
                .lineToLinearHeading(leftRetreat)
                .lineToLinearHeading(mbackdrop)
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    outTake.elevator.setLevel(5);
                })
                .waitSeconds(0.5)
                .addTemporalMarker(() -> {
                    outTake.elevator.setLevel(1);
                })
                .waitSeconds(0.8)
                .addTemporalMarker(() -> {
                    c.dropPixel1 = true;
                    c.dropPilex2 = true;
                })
                .waitSeconds(0.2)
                .lineToLinearHeading(offmbackdrop)
                .waitSeconds(0.3)
                .addTemporalMarker(() -> {
                    c.retractElevtor = true;
                })

                .lineToLinearHeading(goinToPark)

                .lineToLinearHeading(parkPos)
                .build();
    }

    public TrajectorySequence getLeft(){
        return Left;
    }
    public TrajectorySequence getRight(){
        return Right;
    }

    public TrajectorySequence getMiddle(){
        return Middle;
    }

}
