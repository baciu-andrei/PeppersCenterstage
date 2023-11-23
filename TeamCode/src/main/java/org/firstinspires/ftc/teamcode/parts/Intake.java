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


    public boolean acivateNormalIntake, activateReveredIntake;

    public Intake(HardwareMap hm, Gamepad gp1, Gamepad gp2, Telemetry tele){
        intakeMotor = hm.get(DcMotor.class, "Intake");
        intake_servo = hm.get(Servo.class, "intake_retract");

        intake_servo.setDirection(Servo.Direction.FORWARD);


        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        intake_level = MAX_LEVELS;

        go_up = false;
        intake_servo.setPosition(intake_level * step_pos);

        tel = tele;

    }
    private boolean IntakeOn = false, go_up, intake_reverse;
    public void loop(){
        if(acivateNormalIntake) {
            IntakeOn = !IntakeOn;
            intake_reverse = false;
            intake_level = 5;
        }
        if(activateReveredIntake){
            intake_reverse = true;
            IntakeOn = !IntakeOn;
        }

        intake_servo.setPosition(intake_level * step_pos);

        if(intake_reverse){
            intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        } else {
            intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        }

        if(IntakeOn) intakeMotor.setPower(1);
        else intakeMotor.setPower(0);

    }
}
