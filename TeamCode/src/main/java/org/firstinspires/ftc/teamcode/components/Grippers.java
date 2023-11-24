package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.utils.MultiTickUpdater;

@Config
public class Grippers {
    private static class STATES{
        public boolean claw1Active;
        public boolean claw2Active;
    }
    private STATES STATE;
    private MultiTickUpdater backSensorUpdater, frontSensorUpdater;
    private Telemetry telemetry;
    private final RevColorSensorV3 back_sensor, front_sensor;
    private final Servo claw1, claw2;

    public static double open_claw1 = 0, close_claw1 = 0.8,
                        open_claw2 = 0.07, close_claw2 = 0.8;
    public static double trashhold_back = 15, trashhold_front = 15;
    public static boolean ActivateAutomatic = true;

    public void update(){
        double backDistance = back_sensor.getDistance(DistanceUnit.MM);
        double frontDistance = front_sensor.getDistance(DistanceUnit.MM);

        if(ActivateAutomatic){
            if(!STATE.claw1Active &&
               backDistance >= trashhold_back){

                backSensorUpdater.update();

            } else {
                backSensorUpdater.reset();
            }

            if(STATE.claw1Active &&
               !STATE.claw2Active &&
               frontDistance >= trashhold_front){

                frontSensorUpdater.update();

            } else {
                frontSensorUpdater.reset();
            }

            STATE.claw1Active = backSensorUpdater.getState();
            STATE.claw2Active = frontSensorUpdater.getState();
        }

        if(STATE.claw1Active) claw1.setPosition(close_claw1);
        else claw1.setPosition(open_claw1);

        if(STATE.claw2Active) claw2.setPosition(close_claw2);
        else claw2.setPosition(open_claw2);


        telemetry.addData("sensor read1", backDistance);
        telemetry.addData("sensor read2", frontDistance);

    }
    public Grippers(HardwareMap hm, Telemetry tele){
        claw1 = hm.get(Servo.class, "claw1");
        claw2 = hm.get(Servo.class, "claw2");

        back_sensor = hm.get(RevColorSensorV3.class, "back_sensor");
        front_sensor = hm.get(RevColorSensorV3.class, "front_sensor");

        frontSensorUpdater = new MultiTickUpdater(15);
        backSensorUpdater = new MultiTickUpdater(15);

        STATE = new STATES();

        claw1.setDirection(Servo.Direction.REVERSE);
        claw2.setDirection(Servo.Direction.FORWARD);

        STATE.claw1Active = false;
        STATE.claw2Active = false;

        telemetry = tele;

        update();
    }

    public void Drop1(){
        STATE.claw1Active = false;
    }
    public void Drop2(){
        STATE.claw2Active = false;
    }
    public void reset(){
        STATE.claw1Active = false;
        STATE.claw2Active = false;
        ActivateAutomatic = true;
    }
    public void dezactivateAuto(){
        ActivateAutomatic = false;
    }

}
