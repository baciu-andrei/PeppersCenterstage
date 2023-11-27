package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.components.Controls;
import org.firstinspires.ftc.teamcode.components.Elevator;

@TeleOp(name = "lift tuning")
public class pidLiftTest extends LinearOpMode {
    Elevator lift;
    Controls controls;
    @Override
    public void runOpMode() throws InterruptedException{
        lift = new Elevator(hardwareMap, telemetry);
        controls = new Controls(gamepad1, gamepad2);

        waitForStart();

        while(opModeIsActive()){
            controls.update();

            if(controls.stepElevtorUp){
                lift.setLevel(lift.getLevel() + 1);
            }
            if(controls.stepElevatorDown){
                lift.setLevel(lift.getLevel() - 1);
            }
            lift.loop();
        }
    }
}
