package org.firstinspires.ftc.teamcode.parts;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

@Config
public class Intake {
    private static DcMotor intakeMotor;
    private static Servo intake_servo;
    private final Telemetry tel;

    public int intake_level = 0;
    public static final int MAX_LEVELS = 5;
    public static double step_pos = 0.83/MAX_LEVELS;

    private StickyGamepads gamepad1, gamepad2;
    public boolean acivateNormalIntake, activateReveredIntake;

    public Intake(HardwareMap hm, Gamepad gp1, Gamepad gp2, Telemetry tele){
        intakeMotor = hm.get(DcMotor.class, "Intake");
        intake_servo = hm.get(Servo.class, "intake_retract");

        intake_servo.setDirection(Servo.Direction.FORWARD);


        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        MotorConfigurationType mct = intakeMotor.getMotorType().clone();
        mct.setAchieveableMaxRPMFraction(1.0);
        intakeMotor.setMotorType(mct);

        intake_level = MAX_LEVELS;

        intake_servo.setPosition(intake_level * step_pos);

        tel = tele;
        gamepad1 = new StickyGamepads(gp1);
        gamepad2 = new StickyGamepads(gp2);

    }
    private boolean IntakeOn = false;
    public void loop(){
        if(gamepad2.gamepad.right_trigger != 0){
            intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            IntakeOn = true;
        } else IntakeOn = false;

        if(gamepad2.gamepad.left_trigger != 0){
            intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            IntakeOn = true;
        } else if(gamepad2.gamepad.right_trigger == 0) IntakeOn = false;

        if(IntakeOn){
            intake_servo.setPosition(0 * step_pos);
        } else {
            intake_servo.setPosition(intake_level * step_pos);
        }

        if(IntakeOn) intakeMotor.setPower(0.8);
        else intakeMotor.setPower(0);

        gamepad1.update();
        gamepad2.update();

        tel.addData("Motor Direction", intakeMotor.getDirection().toString());

    }
}
