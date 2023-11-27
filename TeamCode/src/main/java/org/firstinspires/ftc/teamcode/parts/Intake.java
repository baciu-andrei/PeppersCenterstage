package org.firstinspires.ftc.teamcode.parts;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.Controls;

@Config
public class Intake {
    private static DcMotor intakeMotor;
    private static Servo intake_servo;
    private final Telemetry telemetry;
    private static Controls controls;

    public int intake_level = 0;
    public static final int MAX_LEVELS = 5;
    private Telemetry tel;
    public static double step_pos = 0.83/MAX_LEVELS, down_adding = 0;


    public Intake(HardwareMap hm, Controls c, Telemetry tele){
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

        telemetry = tele;
        controls = c;

    }
    private boolean IntakeOn = false;
    public void loop(){
        if(controls.Intake){
            intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            IntakeOn = true;
        } else IntakeOn = false;

        if(controls.reverseIntake){
            intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            IntakeOn = true;
        } else if(!controls.Intake) IntakeOn = false;

        if(IntakeOn){
            intake_servo.setPosition(0 * step_pos + down_adding);
        } else {
            intake_servo.setPosition(intake_level * step_pos);
        }

        if(IntakeOn) intakeMotor.setPower(0.8);
        else intakeMotor.setPower(0);


    }
}
