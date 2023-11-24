package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.AMP;
import org.firstinspires.ftc.teamcode.utils.CoolMotor;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;
@Config
public class Elevator{

	public LiftStates STATE;

	final private DcMotor left, right;
	final private double fullExtend = (int)(950);
	private int lift_level;
	public int gotoPos;
	final private double CPR = 103.8, diametruSpool = 32, oneStep = fullExtend/11;

	Telemetry telemetry;

	public Elevator(HardwareMap hardwareMap, Telemetry tel){
		left = hardwareMap.get(DcMotor.class, "ll");
		right = hardwareMap.get(DcMotor.class, "lr");

		left.setDirection(DcMotorSimple.Direction.REVERSE);

		left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

		left.setTargetPosition(0);
		right.setTargetPosition(0);

		left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		left.setPower(1);
		right.setPower(1);


		telemetry = tel;

		STATE = LiftStates.STATIC;

	}

	public void loop(){

		if(lift_level == 0){
			gotoPos = -5;
		}

		if(lift_level == 0){
			gotoPos = -5;
		}

		left.setTargetPosition(gotoPos);
		right.setTargetPosition(gotoPos);

		left.setPower(1);
		right.setPower(1);

		if(right.getCurrentPosition() == gotoPos) STATE = LiftStates.STATIC;


		telemetry.addData("right current position", right.getCurrentPosition());
		telemetry.addData("level", lift_level);
		telemetry.addData("target position", gotoPos);

		telemetry.update();
	}

	public void setLevel(int level){
		int level_corrected;

		if(level < 0) level_corrected = 0;
		else if(level > 11) level_corrected = 11;
		else level_corrected = level;

		if(level_corrected > lift_level) STATE= LiftStates.GO_UP;
		else if(level_corrected < lift_level) STATE = LiftStates.GO_DOWN;
		else STATE= LiftStates.STATIC;
		lift_level = level_corrected;

		gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));

	}

	public int getLevel(){ return lift_level; }
	public double getLevelNow(){ return right.getCurrentPosition()/oneStep; }
}
