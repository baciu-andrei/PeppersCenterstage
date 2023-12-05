package org.firstinspires.ftc.teamcode.parts;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.Controls;

@Config
public class Avion {
    private Telemetry telemetry;
    private Servo servo;
    private Controls controls;
    public static double launchPos = 0, initPos = 1;
    public Avion(HardwareMap hm, Telemetry tel, Controls cntrl){
        servo = hm.get(Servo.class, "Avion_servo");
        servo.setPosition(initPos);
        controls = cntrl;
    }

    public void loop(){
        if(controls.launchPlane) {
            servo.setPosition(launchPos);
        }
    }
}