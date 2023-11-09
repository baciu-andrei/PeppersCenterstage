package org.firstinspires.ftc.teamcode.components;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.IStateBasedModule;

import java.util.ArrayList;
import java.util.*;

public class Elevator{

    final private DcMotorEx left, right;
	private Gamepad last1, last2;

	private boolean getter = false;
	final private double msToChangeLevels = 7.6;

	private int lift_level;

	public Elevator(@NonNull HardwareMap hardwareMap){
		left  = hardwareMap.get(DcMotorEx.class, "ll");
		right = hardwareMap.get(DcMotorEx.class, "lr");

		ArrayList<DcMotorEx> motorList = new ArrayList<>();

		motorList.add(left);
		motorList.add(right);

		for(DcMotorEx motor: motorList){
			motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
			motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
			motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

			MotorConfigurationType mct = motor.getMotorType().clone();
			mct.setAchieveableMaxRPMFraction(1.0);

			motor.setMotorType(mct);
		}

	}
	private void moveElevator(){
		ElapsedTime moveElevator = new ElapsedTime();

		left.setPower(1);
		right.setPower(1);

		moveElevator.reset();
		while(moveElevator.milliseconds() < msToChangeLevels);

		left.setPower(0);
		right.setPower(0);

	}
	private void controls(@NonNull Gamepad gp1, Gamepad gp2){
		if(gp1.a && !last1.a){
			getter = !getter;
		}
		if(gp1.dpad_up && !last1.dpad_up){
			lift_level ++;
			if(lift_level > 11) lift_level = 11;
			else{
				left.setDirection(DcMotorSimple.Direction.FORWARD);
				right.setDirection(DcMotorSimple.Direction.FORWARD);
				moveElevator();
			}
		}
		if(gp1.dpad_down && !last1.dpad_up){
			lift_level --;
			if(lift_level < 0) lift_level = 0;
			else{
				left.setDirection(DcMotorSimple.Direction.REVERSE);
				right.setDirection(DcMotorSimple.Direction.REVERSE);
				moveElevator();
			}
		}

		// TODO: implement the joystick movement



		last1 = gp1;
		last2 = gp2;
	}

	public void loop(Gamepad gamepad1, Gamepad gamepad2){
		controls(gamepad1, gamepad2);

	}
}
