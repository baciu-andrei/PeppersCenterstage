package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.CoolServo;

@Config
public class BratOutTake {
    public enum ArmStates{
        READY_FOR_PIXELS,
        TO_BACKDROP,
        FEED
    };
    ArmStates STATE;
    private final Servo rot1, rot2, al_b, rotp;
    private final CoolServo rotate1, rotate2, align_backdrop, rotatePixels;

    public static double parallel_backdrop = 0.2999, parallel_ground = 0;
    public static double activate_angle1 = 0.95, activate_angle2 = 0.95,
                            deactivate_angle1 = 0.23, deactivate_angle2 = 0.23,
                            feed1 = 0, feed2 = 0;
    public static double rotate_unit = 0.33, rotation_to_feed = 0.3;
    public int rotate = 0;
    private Telemetry telemetry;


    public BratOutTake(HardwareMap hm, Telemetry tele){
        rot1 = hm.get(Servo.class, "virtual1");
        rot2 = hm.get(Servo.class, "virtual2");
        al_b = hm.get(Servo.class, "angle");
        rotp = hm.get(Servo.class, "pivot");

        rotate1 = new CoolServo(rot1, false, 15, 8, deactivate_angle1);
        rotate2 = new CoolServo(rot2, true, 15, 8, deactivate_angle2);

        align_backdrop = new CoolServo(al_b, true,16, 15, 0);
        rotatePixels = new CoolServo(rotp, true, 20, 12,0);

        STATE = ArmStates.READY_FOR_PIXELS;

        rotate1.setPosition(deactivate_angle1);
        rotate2.setPosition(deactivate_angle2);

        align_backdrop.setPosition(0);
        rotatePixels.setPosition(0);

        telemetry = tele;
    }
    public boolean isActive = false, isRotated = false, feedIsOn = false;
    public void update(){

        switch (STATE){
            case FEED:
                rotate1.setPosition(feed1);
                rotate2.setPosition(feed2);

                align_backdrop.setPosition(parallel_ground);
                rotatePixels.setPosition(rotation_to_feed);

                break;
            case READY_FOR_PIXELS:
                rotate1.setPosition(deactivate_angle1);
                rotate2.setPosition(deactivate_angle2);

                align_backdrop.setPosition(parallel_ground);
                rotatePixels.setPosition(rotate_unit * 0);

                break;
            case TO_BACKDROP:
                rotate1.setPosition(activate_angle1);
                rotate2.setPosition(activate_angle2);

                align_backdrop.setPosition(parallel_backdrop);
                rotatePixels.setPosition(rotate_unit * rotate);
                break;
        }

        rotate1.update();
        rotate2.update();

        rotatePixels.update();
        align_backdrop.update();
    }

    public void activate(){
        STATE = ArmStates.TO_BACKDROP;
    }
    public void deactivate(){
        STATE = ArmStates.READY_FOR_PIXELS;
        rotate = 0;
    }

    public void rotate90(){ rotate = 1;}
    public void antiRotate90(){ rotate = 0;}


    public boolean isAtRest(){
        return rotate1.getTimeToMotionEnd() == 0 && rotate2.getTimeToMotionEnd() == 0 && rotatePixels.getTimeToMotionEnd() == 0 &&
                align_backdrop.getTimeToMotionEnd() == 0;
    }

    public void setFeedPos(){
        STATE = ArmStates.FEED;
    }
    public boolean getFeedPos(){
        return feedIsOn;
    }

}


// 260 -- level 3
