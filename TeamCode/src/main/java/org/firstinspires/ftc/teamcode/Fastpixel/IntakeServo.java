package org.firstinspires.ftc.teamcode.Fastpixel;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeServo {
    private Servo servo;
    public IntakeServo(HardwareMap hm, double latchInit)
    {
        servo = hm.get(Servo.class,"servoIntake");
        setIntakeServo(latchInit);
    }
    public void setIntakeServo(double pos)
    {
        servo.setPosition(pos);
    }
}
