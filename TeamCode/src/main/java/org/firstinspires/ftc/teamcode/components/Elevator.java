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
	StickyGamepads gamepad1, gamepad2;

	final private double fullExtend = (int)(950);
	private int lift_level;
	public int gotoPos;
	final private double CPR = 103.8, diametruSpool = 32, oneStep = fullExtend/11;

	Telemetry telemetry;

	public Elevator(HardwareMap hardwareMap, Gamepad g1, Gamepad g2, Telemetry tel){
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
		gamepad1 = new StickyGamepads(g1);
		gamepad2 = new StickyGamepads(g2);

		STATE = LiftStates.STATIC;

	}

	private void controls(){
		if(gamepad2.dpad_up){
			lift_level ++;
			if(lift_level > 11) lift_level = 11;
			else{
				gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
				STATE = LiftStates.GO_UP;
			}
		}

		if(gamepad2.dpad_down){
			lift_level --;
			if(lift_level < 0) lift_level = 0;
			else{
				gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
				STATE = LiftStates.GO_DOWN;
			}
		}

		if(gamepad2.dpad_left){
			lift_level = 0;
			gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
			STATE = LiftStates.GO_DOWN;
		}

		if(gamepad2.dpad_right){
			lift_level = 11;
			gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
			STATE = LiftStates.GO_UP;
		}


		gamepad1.update();
		gamepad2.update();
	}

	public void loop(){
		controls();

		if(right.getCurrentPosition() == gotoPos) STATE = LiftStates.STATIC;

		if(lift_level == 0){
			gotoPos = -5;
		}

		left.setTargetPosition(gotoPos);
		right.setTargetPosition(gotoPos);
		left.setPower(1);
		right.setPower(1);

		telemetry.addData("right current position", right.getCurrentPosition());
		telemetry.addData("level", lift_level);
		telemetry.addData("target position", gotoPos);

		telemetry.update();
	}

	public void setLevel(int level){
		int level_correct;
		if(level < 0) level_correct = 0;
		else if(level > 11) level_correct = 11;
		else level_correct = level;

		if(level_correct > lift_level) STATE= LiftStates.GO_UP;
		else if(level_correct < lift_level) STATE = LiftStates.GO_DOWN;
		else STATE= LiftStates.STATIC;
		lift_level = level_correct;

		gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));

	}

	public int getLevel(){ return lift_level; }
	public int getLevelNow(){ return (int) Math.floor(right.getCurrentPosition()/oneStep); }
}
