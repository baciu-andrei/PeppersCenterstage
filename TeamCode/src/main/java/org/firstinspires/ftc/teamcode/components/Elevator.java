package org.firstinspires.ftc.teamcode.components;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.CoolMotor;
import org.firstinspires.ftc.teamcode.utils.StickyGamepads;
@Config
public class Elevator{

    final private CoolMotor left, right;
	StickyGamepads gamepad1, gamepad2;
	public static PIDCoefficients pidCoefficients = new PIDCoefficients(0.03,0.1,0.001);

	public static double ff1 = 0.1, ff2 = 0.00009;
	final private int fullExtend = (int)(180*4/Math.sin(Math.PI/3));
	private int lift_level;
	final private double CPR = 103.8, diametruSpool = 32, oneStep = fullExtend/11;

	public boolean activate;

	Telemetry telemetry;

	public Elevator(HardwareMap hardwareMap, Gamepad g1, Gamepad g2, Telemetry tel){
		left  = new CoolMotor(hardwareMap.get(DcMotorEx.class, "ll"), CoolMotor.RunMode.PID, true);
		right = new CoolMotor(hardwareMap.get(DcMotorEx.class, "lr"), CoolMotor.RunMode.PID, false);

		telemetry = tel;
		gamepad1 = new StickyGamepads(g1);
		gamepad2 = new StickyGamepads(g2);
	}
	public int gotoPos;

	private void controls(){
		if(gamepad1.dpad_up){
			lift_level ++;
			if(lift_level > 11) lift_level = 11;
			else{
				gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
			}
		}
		if(gamepad1.dpad_down){
			lift_level --;
			if(lift_level < 0) lift_level = 0;
			else{
				gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
			}
		}
		if(gamepad1.dpad_left){
			lift_level = 0;
			gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
		}
		if(gamepad1.dpad_right){
			lift_level = 11;
			gotoPos = (int) (lift_level*oneStep * CPR / (Math.PI * diametruSpool));
		}
		if(gamepad1.a){
			if(activate) {
				lift_level = 9;
				activate = true;
			} else {
				activate = false;
			}
		}

		gotoPos += gamepad1.gamepad.right_stick_y;

		gamepad1.update();
		gamepad2.update();
	}

	public void loop(){
		controls();

		left.setPIDF(pidCoefficients, ff1 + ff2 * left.motor.getCurrentPosition());
		right.setPIDF(pidCoefficients, ff1 + ff2 * left.motor.getCurrentPosition());

		left.calculatePower(left.motor.getCurrentPosition(), gotoPos);
		right.calculatePower(left.motor.getCurrentPosition(), gotoPos);

		left.update();
		right.update();

		telemetry.addData("current position", left.motor.getCurrentPosition());
		telemetry.addData("level", lift_level);
		telemetry.addData("target position", gotoPos);

		telemetry.update();
	}
}
