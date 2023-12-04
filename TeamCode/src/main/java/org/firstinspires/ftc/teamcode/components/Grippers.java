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
    public static class STATES{
        public boolean claw1Active;
        public boolean claw2Active;
    }
    private STATES STATE;
    private Controls controls;
    public boolean latch = false;
    private MultiTickUpdater backSensorUpdater, frontSensorUpdater;
    private Telemetry telemetry;
    private final RevColorSensorV3 back_sensor, front_sensor;
    private final Servo claw1, claw2;
    public static double open_claw1 = 0.07, close_claw1 = 0.6,
                        open_claw2 = 0.08, close_claw2 = 0.6;
    public static double trashhold_back = 19, trashhold_front = 19;
    public static boolean ActivateAutomatic = true;
    public static double backDistance, frontDistance;

    public void update_values(){
        backDistance = back_sensor.getDistance(DistanceUnit.MM);
        frontDistance = front_sensor.getDistance(DistanceUnit.MM);
    }

    public void update(){

        if(ActivateAutomatic){
            if(backDistance <= trashhold_back){
                STATE.claw1Active = true;
            }
            if(frontDistance <= trashhold_front && STATE.claw1Active){
                STATE.claw2Active = true;
            }

        }

        if(STATE.claw1Active) {
            claw1.setPosition(close_claw1);
        }
        else claw1.setPosition(open_claw1);

        if(STATE.claw2Active) {
            claw2.setPosition(close_claw2);
        }
        else claw2.setPosition(open_claw2);

        if(STATE.claw1Active && STATE.claw2Active && !latch){
            controls.vibrate1 = true;
            controls.vibrate2 = true;
            latch = true;
        }


        telemetry.addData("sensor read1", backDistance);
        telemetry.addData("sensor read2", frontDistance);
        telemetry.addData("claw1", backSensorUpdater.getTicks());
        telemetry.addData("claw2", frontSensorUpdater.getTicks());
        telemetry.addData("boolc1", STATE.claw1Active);
        telemetry.addData("boolc2", STATE.claw2Active);

    }
    public Grippers(HardwareMap hm, Telemetry tele, Controls c){
        controls = c;
        claw1 = hm.get(Servo.class, "claw1");
        claw2 = hm.get(Servo.class, "claw2");

        back_sensor = hm.get(RevColorSensorV3.class, "back_sensor");
        front_sensor = hm.get(RevColorSensorV3.class, "front_sensor");

        frontSensorUpdater = new MultiTickUpdater(75);
        backSensorUpdater = new MultiTickUpdater(75);

        STATE = new STATES();

        claw1.setDirection(Servo.Direction.REVERSE);
        claw2.setDirection(Servo.Direction.FORWARD);

        STATE.claw1Active = false;
        STATE.claw2Active = false;

        telemetry = tele;

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
        latch = false;
    }
    public void activate(){
        STATE.claw1Active = true;
        STATE.claw2Active = true;
    }
    public void ActivateAuto(){ ActivateAutomatic = true; }
    public boolean bothDropped(){
        return !STATE.claw1Active && !STATE.claw2Active;
    }
    public void dezactivateAuto(){
        ActivateAutomatic = false;
    }

}
