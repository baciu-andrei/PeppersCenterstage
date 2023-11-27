package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.AMP;
import org.firstinspires.ftc.teamcode.utils.CoolMotor;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;
@Config
public class Elevator{

	public LiftStates STATE;

	final private DcMotorEx left, right;

	private static final double CPR = 145.1, diametruSpool = 32;
	public static final double fullExtend = 950;
	public static final double oneStep = fullExtend/11;
	private int level = 0;
	private double gotoPos = 0, rightMotorPos = 0, leftMotorPos = 0;

	Telemetry telemetry;

	public Elevator(HardwareMap hardwareMap, Telemetry tel){
		left = hardwareMap.get(DcMotorEx.class, "ll");
		right = hardwareMap.get(DcMotorEx.class, "lr");

		left.setDirection(DcMotorEx.Direction.REVERSE);

		left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		left.setTargetPosition(0);
		right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		right.setTargetPosition(0);

		left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		left.setPower(1);
		right.setPower(1);

		telemetry = tel;

	}

	public void loop(){

		rightMotorPos = right.getCurrentPosition();
		leftMotorPos = left.getCurrentPosition();

		if(gotoPos == 0){
			gotoPos = -2;
		}

		left.setTargetPosition((int)gotoPos);
		right.setTargetPosition((int)gotoPos);

		left.setPower(1);
		right.setPower(1);


		telemetry.addData("right current position", rightMotorPos);
		telemetry.addData("left current position", leftMotorPos);
		telemetry.addData("level", level);
		telemetry.addData("target position", gotoPos);


		telemetry.update();
	}

	public int getLevel(){
		return level;
	}

	public void setLevel(int lvl){
		int correctedLevel;
		if(lvl > 11) correctedLevel = 11;
		else if(lvl < 0) correctedLevel = 0;
		else correctedLevel = lvl;

		if(correctedLevel > level) STATE = LiftStates.GO_UP;
		else if(correctedLevel < level) STATE = LiftStates.GO_DOWN;
		else STATE = LiftStates.STATIC;

		gotoPos = (double)(correctedLevel * oneStep);

		level = correctedLevel;

	}

	public double getLevelNow(){
		return (double)rightMotorPos/oneStep;
	}


}
