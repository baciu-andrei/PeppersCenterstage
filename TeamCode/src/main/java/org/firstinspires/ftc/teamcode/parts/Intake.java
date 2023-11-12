package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

public class Intake {
    private static DcMotor intakeMotor;
    private static StickyGamepads gamepad1, gamepad2;

    public Intake(HardwareMap hm, Gamepad gp1, Gamepad gp2){
        intakeMotor = hm.get(DcMotor.class, "Intake");

        gamepad1 = new StickyGamepads(gp1);
        gamepad2 = new StickyGamepads(gp2);

    }
    private boolean IntakeOn = false;
    public void loop(){
        if(gamepad1.a) {
            IntakeOn = !IntakeOn;

            if(IntakeOn) intakeMotor.setPower(1);
            else intakeMotor.setPower(0);
        }
    }
}
