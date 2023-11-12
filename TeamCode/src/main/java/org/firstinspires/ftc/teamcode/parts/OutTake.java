package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.BratOutTake;
import org.firstinspires.ftc.teamcode.components.Elevator;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

public class OutTake {
    private Elevator elevator;

    private BratOutTake brat;

    private Telemetry telemetry;

    private static boolean activateDropper;
    private StickyGamepads gamepad1, gamepad2;
    public OutTake(HardwareMap hm, Gamepad gp1, Gamepad gp2, Telemetry tele){
        elevator = new Elevator(hm, gp1, gp2, tele);
        brat = new BratOutTake(hm, gp1, gp2, tele);

        telemetry = tele;

        gamepad1 = new StickyGamepads(gp1);
        gamepad2 = new StickyGamepads(gp2);

        activateDropper = false;
    }

    public void loop(){
        if(gamepad1.a){ activateDropper = !activateDropper; }
        elevator.loop();
        brat.loop(activateDropper);

        gamepad1.update();
    }
}
