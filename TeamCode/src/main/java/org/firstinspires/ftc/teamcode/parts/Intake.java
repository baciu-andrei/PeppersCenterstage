package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

public class Intake {
    private static DcMotor intakeMotor;
    private static StickyGamepads gamepad1, gamepad2;

    public Intake(HardwareMap hm, Gamepad gp1, Gamepad gp2){
        intakeMotor = hm.get(DcMotor.class, "Intake");

        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        MotorConfigurationType mct = intakeMotor.getMotorType().clone();
        mct.setAchieveableMaxRPMFraction(1.0);

        intakeMotor.setMotorType(mct);

        gamepad1 = new StickyGamepads(gp1);
        gamepad2 = new StickyGamepads(gp2);

    }
    private boolean IntakeOn = false;
    public void loop(){
        if(gamepad2.x) {
            IntakeOn = !IntakeOn;

            if(IntakeOn) intakeMotor.setPower(1);
            else intakeMotor.setPower(0);
        }
        gamepad1.update();
        gamepad2.update();
    }
}
