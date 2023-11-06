package org.firstinspires.ftc.teamcode.Fastpixel;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OpClaw {

    private Servo v1;
    private Servo v2;
    private Servo r;
    private Servo g1;
    private Servo g2;
    private Servo angle;

    OpClaw(HardwareMap hm, double gripperInit, double rotationInit, double virtualInit, double angleInit)
    {
        v1 = hm.get(Servo.class, "virtual1");
        v2 = hm.get(Servo.class, "virtual2");
        r = hm.get(Servo.class, "rotation");
        g1 = hm.get(Servo.class, "gripper1");
        g2 = hm.get(Servo.class, "gripper2");
        angle = hm.get(Servo.class, "angle");

        v1.setDirection(Servo.Direction.REVERSE);
        g2.setDirection(Servo.Direction.REVERSE);
        gripperBack(gripperInit);
        gripperFront(gripperInit);
        rotateOuttake(rotationInit);
        virtual(virtualInit);
    }
    public void gripperBack(double pos)
    {
        g1.setPosition(pos);
    }
    public void gripperFront(double pos)
    {
        g2.setPosition(pos);
    }
    public void rotateOuttake(double pos)
    {
        r.setPosition(pos);
    }
    public void virtual(double pos)
    {
        v1.setPosition(pos);
        v2.setPosition(pos);
    }
    public void setAng(double pos){angle.setPosition(pos);}
}
