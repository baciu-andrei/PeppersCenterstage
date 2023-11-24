package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;

public class RestrictedMotor {
    DcMotor motor;
    LynxModule lynxModule;
    public RestrictedMotor(DcMotor m){
        motor = m;
    }
}
