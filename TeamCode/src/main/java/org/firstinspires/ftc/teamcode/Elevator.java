package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.IStateBasedModule;

import java.util.ArrayList;

public class Elevator implements IStateBasedModule {

    DcMotorEx left, right;
	Gamepad gamepad1, gamepad2;

	public Elevator(HardwareMap hardwareMap, Gamepad g1, Gamepad g2){
		gamepad1 = g1;	gamepad2 = g2;
		left  = hardwareMap.get(DcMotorEx.class, "ll");
		right = hardwareMap.get(DcMotorEx.class, "lr");

		left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}
}
