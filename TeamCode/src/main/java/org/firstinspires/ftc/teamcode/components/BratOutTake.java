package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.CoolServo;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

@Config
public class BratOutTake {
    private Servo rotate1, rotate2, align_backdrop;
    private Servo claw1, claw2;

    public static double activate_angle = 0.63, deactivate_angle = 0, align_position = 0.25, close_claw = 0.5;
    private Telemetry telemetry;

    private StickyGamepads gp1, gp2;
    private boolean lastActivate = false, up_down = false;
    private static double set = 0;
    public BratOutTake(HardwareMap hm, Gamepad g1, Gamepad g2, Telemetry tele){
        rotate1 = hm.get(Servo.class, "virtual1");
        rotate2 = hm.get(Servo.class, "virtual2");
        align_backdrop = hm.get(Servo.class, "align_backdrop");

        claw1 = hm.get(Servo.class, "claw1");
//        claw2 = hm.get(Servo.class, "claw2");

        rotate2.setDirection(Servo.Direction.FORWARD);
        rotate1.setDirection(Servo.Direction.REVERSE);
        align_backdrop.setDirection(Servo.Direction.FORWARD);

        claw1.setDirection(Servo.Direction.REVERSE);
//        claw2.setDirection(Servo.Direction.REVERSE);

        rotate2.setPosition(0);
        rotate1.setPosition(0);

        align_backdrop.setPosition(0);

        claw1.setPosition(0);
//        claw2.setPosition(0);
//        rotate_all = hm.get(Servo.class, "align_backdrop");
//        swap_pixels = hm.get(Servo.class, "swap_pixels");



        gp1 = new StickyGamepads(g1);
        gp2 = new StickyGamepads(g2);
        telemetry = tele;
    }
    public void loop(boolean shouldActivate){
        if(shouldActivate && !lastActivate){

            rotate1.setPosition(activate_angle);
            rotate2.setPosition(activate_angle);
            align_backdrop.setPosition(align_position);

        } else if(!shouldActivate && lastActivate){

            rotate1.setPosition(deactivate_angle);
            rotate2.setPosition(deactivate_angle);
            align_backdrop.setPosition(0);

        }

        if(gp1.x){
            set = close_claw - set;
            claw1.setPosition(set);
//            claw2.setPosition(set);
        }


        telemetry.addData("v1 pos", rotate1.getPosition());
        telemetry.addData("v2 pos", rotate2.getPosition());
        telemetry.addData("align pos", align_backdrop.getPosition());
        lastActivate = shouldActivate;

        gp1.update();
    }
}
