package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.parts.Intake;
import org.firstinspires.ftc.teamcode.parts.OutTake;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

public class Controls {

    public boolean retractElevtor, extendElevator,
                    stepElevtorUp, stepElevatorDown,
                    dropPixel1, dropPilex2,
                    rotatePixels,
                    Intake, reverseIntake,
                    feedForward;

    private StickyGamepads gamepad1, gamepad2;

    public Controls(Gamepad g1, Gamepad g2){
        gamepad1 = new StickyGamepads(g1);
        gamepad2 = new StickyGamepads(g2);
    }

    private void reset(){
        retractElevtor      = false;
        extendElevator      = false;
        stepElevtorUp       = false;
        stepElevatorDown    = false;
        dropPixel1          = false;
        dropPilex2          = false;
        feedForward         = false;
        rotatePixels        = false;
    }

    public void update(){ // deocamdata hard coded

        reset();

        if(gamepad1.right_bumper){
            dropPixel1 = true;
        }
        if(gamepad1.left_bumper) {
            dropPilex2 = true;
        }

        if(gamepad2.dpad_up){
            stepElevtorUp = true;
        }
        if(gamepad2.dpad_down){
            stepElevatorDown = true;
        }
        if(gamepad2.dpad_right) {
            extendElevator = true;
        }
        if(gamepad2.dpad_left){
            retractElevtor = true;
        }

        if(gamepad2.gamepad.left_trigger != 0){
            Intake = true;
        }
        if(gamepad2.gamepad.right_trigger != 0){
            reverseIntake = true;
        }

        if(gamepad2.b){
            rotatePixels = true;
        }

        if(gamepad2.x){
            feedForward = true;
        }


        gamepad1.update();
        gamepad2.update();
    }

}
