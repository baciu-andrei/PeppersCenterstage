package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.parts.Intake;
import org.firstinspires.ftc.teamcode.parts.OutTake;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;

public class Controls {
    private Intake intake;
    private OutTake outTake;

    private StickyGamepads gamepad1, gamepad2;

    public Controls(Intake i, OutTake o, Gamepad g1, Gamepad g2){
        intake = i;
        outTake = o;

        gamepad1 = new StickyGamepads(g1);
        gamepad2 = new StickyGamepads(g2);
    }

    public void TwoPlayers(){
        ////// OUT TALKE //////

        if(gamepad1.right_bumper){
            outTake.claw1Reset      = true;
        }
        if(gamepad1.left_bumper){
            outTake.claw2Reset      = true;
        }
        if(gamepad2.dpad_up){
            outTake.oneStepUp       = true;
        }
        if(gamepad2.dpad_down){
            outTake.oneStepDown     = true;
        }
        if(gamepad2.dpad_left){
            outTake.retractElevator = true;
        }
        if(gamepad2.dpad_right){
            outTake.fullExtend      = true;
        }

        ////// INTAKE //////
        if(gamepad1.gamepad.right_stick_y > 0){
            intake.acivateNormalIntake   = true;
            intake.activateReveredIntake = false;
        } else if (gamepad1.gamepad.right_stick_x < 0){
            intake.acivateNormalIntake   = false;
            intake.activateReveredIntake = true;
        }
    }


}
