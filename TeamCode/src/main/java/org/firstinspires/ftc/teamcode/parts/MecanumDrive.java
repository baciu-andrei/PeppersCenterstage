package org.firstinspires.ftc.teamcode.parts;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MecanumDrive {
    private DcMotor leftF, rightF,
                    leftB, rightB;

    private int Seconds;
    private boolean doNothing = true, reatched = false;
    public void setTimer(int seconds){
        Seconds = seconds;
        doNothing = false;
        reatched = false;
        time.reset();
    }
    public boolean isAtDestination(){
        return reatched;
    }
    private ElapsedTime time;
    public void run(){
        if(doNothing) return;
        if(time.seconds() >= Seconds){
            doNothing = true;

            time.reset();
        }



    }
}
