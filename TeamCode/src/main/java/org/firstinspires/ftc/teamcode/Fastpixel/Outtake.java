package org.firstinspires.ftc.teamcode.Fastpixel;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utils.StickyGamepads;

public class Outtake {
    private Elevator elevator;



    private Telemetry telemetry;

    private static boolean activateDropper;
    private StickyGamepads gamepad1, gamepad2;
    public Outtake(HardwareMap hm, Gamepad gp1, Gamepad gp2, Telemetry tele){
        elevator = new Elevator(hm, gp1, gp2, tele);

        telemetry = tele;

        gamepad1 = new StickyGamepads(gp1);
        gamepad2 = new StickyGamepads(gp2);

        activateDropper = false;
    }

    public void loop(){
        if(gamepad2.a){ activateDropper = !activateDropper; }
        elevator.loop();

        gamepad2.update();
        gamepad1.update();
    }
}
