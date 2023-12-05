package org.firstinspires.ftc.teamcode.Autonomy;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

@Config
@Autonomous(name = "AutoRightParkingRed")
public class AutoRightParkingRed extends LinearOpMode {

    DcMotorEx mfl;
    DcMotorEx mfr;
    DcMotorEx mbl;
    DcMotorEx mbr;

    public static int milisecondsToParkRightRed = 10000;
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
            if(timer.milliseconds()<milisecondsToParkRightRed)
            {
                mfl.setPower(0.5);
                mfr.setPower(-0.5);
                mbl.setPower(-0.5);
                mbr.setPower(0.5);
            }
        }
    }
}