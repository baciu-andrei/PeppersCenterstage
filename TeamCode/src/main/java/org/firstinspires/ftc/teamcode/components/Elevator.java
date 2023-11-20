package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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

    final private CoolMotor left, right;
	StickyGamepads gamepad1, gamepad2;
	public static PIDCoefficients pidCoefficients = new PIDCoefficients(0.03,0.13,0.00035);

	public static double ff1 = 0.07, ff2 = 0.00035;
	final private double fullExtend = (int)(972);
	private int lift_level;
	public int gotoPos;
	final private double CPR = 103.8, diametruSpool = 32, oneStep = fullExtend/11;

	final public AMP profile = new AMP(8000, 5000, 3500);

	Telemetry telemetry;

	public Elevator(HardwareMap hardwareMap, Gamepad g1, Gamepad g2, Telemetry tel){
		left  = new CoolMotor(hardwareMap.get(DcMotorEx.class, "ll"), CoolMotor.RunMode.PID, false);
		right = new CoolMotor(hardwareMap.get(DcMotorEx.class, "lr"), CoolMotor.RunMode.PID, false);

		telemetry = tel;
		gamepad1 = new StickyGamepads(g1);
		gamepad2 = new StickyGamepads(g2);

		STATE = LiftStates.STATIC;

		profile.setMotion(0,0,0);

	}

	private void controls(){
		if(gamepad2.dpad_up){
			lift_level ++;
			if(lift_level > 11) lift_level = 11;
			else{
				gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
				STATE = LiftStates.GO_UP;
				profile.setMotion(profile.getPosition(), gotoPos, profile.getVelocity());
			}
		}

		if(gamepad2.dpad_down){
			lift_level --;
			if(lift_level < 0) lift_level = 0;
			else{
				gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
				STATE = LiftStates.GO_DOWN;
				profile.setMotion(profile.getPosition(), gotoPos, profile.getVelocity());
			}
		}

		if(gamepad2.dpad_left){
			lift_level = 0;
			gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
			STATE = LiftStates.GO_DOWN;
			profile.setMotion(profile.getPosition(), gotoPos, profile.getVelocity());
		}

		if(gamepad2.dpad_right){
			lift_level = 11;
			gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
			STATE = LiftStates.GO_UP;
			profile.setMotion(profile.getPosition(), gotoPos, profile.getVelocity());
		}


		gamepad1.update();
		gamepad2.update();
	}

	public void loop(){
		controls();

		int target = (int)profile.getPosition();


//		if(Math.abs(target - left.motor.getCurrentPosition()) == 0) STATE.state = LiftStates.STATES.STATIC;

		left.setPIDF(pidCoefficients, ff1 + ff2 * target);
		right.setPIDF(pidCoefficients, ff1 + ff2 * target);

		left.calculatePower(left.motor.getCurrentPosition(), target);
		right.calculatePower(right.motor.getCurrentPosition(), target);

		if(lift_level == 0){
			left.motor.setTargetPosition(0);
			right.motor.setTargetPosition(0);
		}

		left.update();
		right.update();
		profile.update();


		telemetry.addData("current position", left.motor.getCurrentPosition());
		telemetry.addData("level", lift_level);
		telemetry.addData("target position", gotoPos);
		telemetry.addData("step lift", oneStep);

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
		profile.setMotion(profile.getPosition(), gotoPos, profile.getSignedVelocity());

	}

	public int getLevel(){ return lift_level; }
	public LiftStates getLiftState(){ return STATE; }

	public int getLevelNow(){ return (int) Math.floor(left.motor.getCurrentPosition()/oneStep); }
}
