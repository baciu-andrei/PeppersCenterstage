package org.firstinspires.ftc.teamcode.components;

import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

@Config
public class Grippers {
    private final Servo claw1, claw2;
    public static int ticks_trashhold = 5, ticks_for_sensor1 = 0, ticks_for_sensor2 = 0;
    public static double open_claw1 = 0, close_claw1 = 0.8,
                        open_claw2 = 0.07, close_claw2 = 0.8;

    private boolean active_claw_1, active_claw_2;
    private boolean auto_claw1, auto_claw2;

    private final RevColorSensorV3 back_sensor, front_sensor;
    public static double trashhold_back = 15, trashhold_front = 15;
    public static boolean ActivateAutoSensors = true, ActivateAutomatic = true;

    public void update(){
        double back_dist = back_sensor.getDistance(DistanceUnit.MM);
        double front_dist = front_sensor.getDistance(DistanceUnit.MM);

        if(ActivateAutomatic && !auto_claw1 && back_dist <= trashhold_back){
            ++ ticks_for_sensor1;
        } else ticks_for_sensor1 = 0;

        if(ActivateAutomatic && auto_claw1 && !auto_claw2 && front_dist <= trashhold_front) {
            ++ ticks_for_sensor2;
        } else ticks_for_sensor2 = 0;

        if(ticks_for_sensor1 >= ticks_trashhold){
            auto_claw1 = true;
        }
        if(ticks_for_sensor2 >= ticks_trashhold){
            auto_claw2 = true;
        }

        if(ActivateAutoSensors && ActivateAutomatic) {
            active_claw_1 = auto_claw1;
            active_claw_2 = auto_claw2;
        }


        if(active_claw_1) claw1.setPosition(close_claw1);
        else claw1.setPosition(open_claw1);

        if(active_claw_2) claw2.setPosition(close_claw2);
        else claw2.setPosition(open_claw2);


        telemetry.addData("sensor read1", back_dist);
        telemetry.addData("sensor read2", front_dist);

    }
    private Telemetry telemetry;
    public Grippers(HardwareMap hm, Telemetry tele){
        claw1 = hm.get(Servo.class, "claw1");
        claw2 = hm.get(Servo.class, "claw2");

        back_sensor = hm.get(RevColorSensorV3.class, "back_sensor");
        front_sensor = hm.get(RevColorSensorV3.class, "front_sensor");

        claw1.setDirection(Servo.Direction.REVERSE);
        claw2.setDirection(Servo.Direction.FORWARD);

        active_claw_1 = false;
        active_claw_2 = false;

        telemetry = tele;

        update();
    }

    public void srl1(){
        active_claw_1 = !active_claw_1;
    }
    public void srl2(){
        active_claw_2 = !active_claw_2;
    }

    public void reset_claw_1(){
        active_claw_1 = false;
    }
    public void reset_claw_2(){
        active_claw_2 = false;
    }
    public void reset(){
        active_claw_1 = false;
        active_claw_2 = false;
        auto_claw1 = false;
        auto_claw2 = false;
        ActivateAutoSensors = true;
    }
    public void dezactivateAuto(){
        ActivateAutoSensors = false;
    }
    public void activateAuto(){
        ActivateAutoSensors = true;
    }

}
