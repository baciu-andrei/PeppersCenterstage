package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.CoolServo;

@Config
public class BratOutTake {
    private final Servo rot1, rot2, al_b, rotp;
    private final CoolServo rotate1, rotate2, align_backdrop, rotatePixels;

    public static double parallel_backdrop = 0.2999, parallel_ground = 0;
    public static double activate_angle1 = 0.75, activate_angle2 = 0.748,
                            deactivate_angle1 = 0, deactivate_angle2 = 0.05;
    public static double rotate_unit = 0.3, rotation_adaos = 0.03, rotation_adaos_inactive = 0;
    private Telemetry telemetry;


    public BratOutTake(HardwareMap hm, Telemetry tele){
        rot1 = hm.get(Servo.class, "virtual1");
        rot2 = hm.get(Servo.class, "virtual2");
        al_b = hm.get(Servo.class, "angle");
        rotp = hm.get(Servo.class, "pivot");

        rotate1 = new CoolServo(rot1, false, 15, 8, 0);
        rotate2 = new CoolServo(rot2, true, 15, 8, 0);

        align_backdrop = new CoolServo(al_b, true,16, 15, 0);
        rotatePixels = new CoolServo(rotp, true, 20, 12,0);

        rotate1.setPosition(0);
        rotate2.setPosition(0);

        align_backdrop.setPosition(0);
        rotatePixels.setPosition(0);

        telemetry = tele;
    }
    public boolean isActive, isRotated;
    public void update(){
        if(isActive){
            rotate1.setPosition(activate_angle1);
            rotate2.setPosition(activate_angle2);

            align_backdrop.setPosition(parallel_backdrop);
        } else {
            rotate1.setPosition(deactivate_angle1);
            rotate2.setPosition(deactivate_angle2);

            align_backdrop.setPosition(parallel_ground);
        }

        if(isRotated){
            rotatePixels.setPosition(rotate_unit * 1 + rotation_adaos);
        } else {
            rotatePixels.setPosition(rotate_unit * 0 + rotation_adaos_inactive);
        }

        rotate1.update();
        rotate2.update();

        rotatePixels.update();
        align_backdrop.update();
    }

    public void activate(){
        isActive = true;
    }
    public void deactivate(){
        isActive = false; isRotated = false;
    }

    public void rotate90(){ isRotated = true; }
    public void antiRotate90(){ isRotated = false; }


    public boolean isAtRest(){
        return rotate1.getTimeToMotionEnd() == 0 && rotate2.getTimeToMotionEnd() == 0 && rotatePixels.getTimeToMotionEnd() == 0 &&
                align_backdrop.getTimeToMotionEnd() == 0;
    }

}


// 260 -- level 3
