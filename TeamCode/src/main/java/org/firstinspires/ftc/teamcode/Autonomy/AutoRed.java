package org.firstinspires.ftc.teamcode.Autonomy;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.components.Controls;
import org.firstinspires.ftc.teamcode.components.Elevator;
import org.firstinspires.ftc.teamcode.parts.OutTake;

import java.util.ArrayList;

@Autonomous(name = "don't run this")
@Config
public class AutoRed extends LinearOpMode {
    DcMotorEx mfl;
    DcMotorEx mfr;
    DcMotorEx mbl;
    DcMotorEx mbr;

    Elevator ele;
    Servo claw;
    Servo v1;
    Servo v2;
    Servo angle;
    Servo rotate;
    private Controls c;

    public static int die = 30000;
    public static int purplePixel = 1000;
    public static int rotation = 2000;

    public int goALittleBack = 400;
    public static int goToBackDrop = 1000;
    public static int putPixel = 10000;
    public static int right = 1000;
    public static int park = 2000;
    public double sadMotor = 0;

    public AutoRed() {
    }

    public void runOpMode() throws InterruptedException {
        mfl = hardwareMap.get(DcMotorEx.class, "mfl");
        mfr = hardwareMap.get(DcMotorEx.class, "mfr");
        mbl = hardwareMap.get(DcMotorEx.class, "mbl");
        mbr = hardwareMap.get(DcMotorEx.class, "mbr");
        ele = new Elevator(hardwareMap , telemetry);
        claw = hardwareMap.get(Servo.class, "claw1");
        v1 = hardwareMap.get(Servo.class, "virtual1");
        v2 = hardwareMap.get(Servo.class, "virtual2");
        angle = hardwareMap.get(Servo.class, "angle");
        rotate = hardwareMap.get(Servo.class, "pivot");

        double voltage;
        voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
        v2.setDirection(Servo.Direction.REVERSE);
        v1.setPosition(0.23);
        v2.setPosition(0.23);
        claw.setPosition(0.8);
        angle.setPosition(0);
        rotate.setPosition(0);

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
            voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
            double powerCoeff = 1.0/(voltage/12);

            mfl.setPower(powerCoeff/2);
            mfr.setPower(powerCoeff/2);
            mbl.setPower((powerCoeff)/2-sadMotor);
            mbr.setPower(powerCoeff/2);
            sleep(purplePixel);

            mfl.setPower(-powerCoeff/2);
            mfr.setPower(-powerCoeff/2);
            mbl.setPower(-((powerCoeff)/2-sadMotor));
            mbr.setPower(-powerCoeff/2);
            sleep(goALittleBack);

            mfl.setPower(powerCoeff/2);
            mfr.setPower(-powerCoeff/2);
            mbl.setPower((powerCoeff)/2-sadMotor);
            mbr.setPower(-powerCoeff/2);
            sleep(rotation);

            mfl.setPower(powerCoeff/2);
            mfr.setPower(powerCoeff/2);
            mbl.setPower((powerCoeff)/2-sadMotor);
            mbr.setPower(powerCoeff/2);
            sleep(goToBackDrop);

            ele.setLevel(3);
            while(ele.getLevelNow()<2.9){ele.update_values();
                ele.loop();}
            v1.setPosition(1);
            v2.setPosition(1);
            angle.setPosition(0.3);
            rotate.setPosition(0.33);
            sleep(1000);
            claw.setPosition(0.07);
            v1.setPosition(0.23);
            v2.setPosition(0.23);
            sleep(1000);
            ele.setLevel(0);
            while(ele.getLevelNow()>0){ele.update_values();
            ele.loop();}


            mfl.setPower(powerCoeff/2);
            mfr.setPower(-powerCoeff/2);
            mbl.setPower((-powerCoeff)/2-sadMotor);
            mbr.setPower(powerCoeff/2);
            sleep(right);

            mfl.setPower(powerCoeff/2);
            mfr.setPower(powerCoeff/2);
            mbl.setPower((powerCoeff)/2-sadMotor);
            mbr.setPower(powerCoeff/2);
            sleep(park);

            mfl.setPower(0);
            mfr.setPower(0);
            mbl.setPower(0);
            mbr.setPower(0);
            sleep(die);
        }
    }
}
