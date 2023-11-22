package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Grippers {
    Servo claw1, claw2;

    public Grippers(HardwareMap hm){
        claw1 = hm.get(Servo.class, "claw1");
        claw2 = hm.get(Servo.class, "claw2");



    }
}
