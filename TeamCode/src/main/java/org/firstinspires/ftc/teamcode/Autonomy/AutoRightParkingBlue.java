package org.firstinspires.ftc.teamcode.Autonomy;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Config
@Autonomous(name = "AutoRightParkingBlue")
public class AutoRightParkingBlue extends LinearOpMode {

    DcMotorEx mfl;
    DcMotorEx mfr;
    DcMotorEx mbl;
    DcMotorEx mbr;

    public static int milisecondsToParkRightBlue = 6500;
    public static int goFrontMiliseconds = 100;

    public static double sadMotor = -0.0415;

    boolean crashed = false;

    public void runOpMode() throws InterruptedException {
        mfl = hardwareMap.get(DcMotorEx.class, "mfl");
        mfr = hardwareMap.get(DcMotorEx.class, "mfr");
        mbl = hardwareMap.get(DcMotorEx.class, "mbl");
        mbr = hardwareMap.get(DcMotorEx.class, "mbr");

//        mfl.setDirection(DcMotorSimple.Direction.REVERSE);
        mfr.setDirection(DcMotorSimple.Direction.REVERSE);
//        mbl.setDirection(DcMotorSimple.Direction.REVERSE);
        mbr.setDirection(DcMotorSimple.Direction.REVERSE);

        ArrayList<DcMotorEx> motorListDrive = new ArrayList<>();
        motorListDrive.add(mfl);
        motorListDrive.add(mfr);
        motorListDrive.add(mbl);
        motorListDrive.add(mbr);

        for(DcMotorEx motor: motorListDrive) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        waitForStart();
        timer.reset();
        while(opModeIsActive())
        {
            if(timer.time(TimeUnit.MILLISECONDS)<goFrontMiliseconds)
            {
                mfl.setPower(0.5);
                mfr.setPower(0.5);
                mbl.setPower(0.5-sadMotor);
                mbr.setPower(0.5);
            }
            else{
            if(timer.time(TimeUnit.MILLISECONDS)<milisecondsToParkRightBlue)
            {

                mfl.setPower(-0.5);
                mfr.setPower(0.5);
                mbl.setPower(0.5-sadMotor);
                mbr.setPower(-0.5);
            }
            else
            {
                mfl.setPower(0);
                mfr.setPower(0);
                mbl.setPower(0);
                mbr.setPower(0);

            }}
        }
    }
}