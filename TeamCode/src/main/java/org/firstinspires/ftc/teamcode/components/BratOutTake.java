package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

@Config
public class BratOutTake {
    public static Servo rotate_all, rotate1, rotate2, swap_pixels;

    public static double activate_angle = 60;
    private Telemetry telemetry;

    private StickyGamepads gp1, gp2;
    public BratOutTake(HardwareMap hm, Gamepad g1, Gamepad g2, Telemetry tele){
        rotate1 = hm.get(Servo.class, "backdrop_servo1");
        rotate2 = hm.get(Servo.class, "backdrop_servo2");

        rotate_all = hm.get(Servo.class, "align_backdrop");
        swap_pixels = hm.get(Servo.class, "swap_pixels");

        gp1 = new StickyGamepads(g1);
        gp2 = new StickyGamepads(g2);
        telemetry = tele;
    }

    public void loop(boolean shouldActivate){
        if(shouldActivate) {
            rotate1.setPosition(activate_angle);
            rotate2.setPosition(activate_angle);
        } else {
            rotate1.setPosition(0);
            rotate2.setPosition(0);
        }
    }
}
