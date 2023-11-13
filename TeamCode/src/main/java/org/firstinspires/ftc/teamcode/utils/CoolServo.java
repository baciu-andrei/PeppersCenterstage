package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.AMP;

public class CoolServo {

    private final Servo servo;
    public AMP profile;
    private boolean isProfiled = false;

    public CoolServo(Servo servo, boolean reversed, double initialPosition){
        this.servo = servo;
        if(reversed) servo.setDirection(Servo.Direction.REVERSE);
        setInitialPosition(initialPosition);
    }

    public CoolServo(Servo servo, boolean reversed, double profileMaxVelocity, double profileAcceleration, double profileDeceleration, double initialPosition){
        this.servo = servo;
        if(reversed) servo.setDirection(Servo.Direction.REVERSE);
        profile = new AMP(profileMaxVelocity, profileAcceleration, profileDeceleration);
        isProfiled = true;
        setInitialPosition(initialPosition);
    }

    public CoolServo(Servo servo, boolean reversed, double profileMaxVelocity, double profileAcceleration, double initialPosition){
        this(servo, reversed, profileMaxVelocity, profileAcceleration, profileAcceleration, initialPosition);
    }

    public double cachedPosition, targetPosition;

    private void setInitialPosition(double pos){
        cachedPosition = pos;
        targetPosition = pos;
        if(isProfiled)profile.setMotion(pos, pos, 0);
        servo.setPosition(pos);
    }

    public void setPosition(double position){
        if(position == targetPosition) return;
        targetPosition = position;
        if(isProfiled) profile.setMotion(cachedPosition, targetPosition, profile.getSignedVelocity());
    }

    public void update(){
        if(isProfiled) profile.update();
        if(isProfiled && cachedPosition != profile.getPosition()) {
            cachedPosition = profile.getPosition();
            servo.setPosition(cachedPosition);
        }
        if(!isProfiled && cachedPosition != targetPosition) {
            cachedPosition = targetPosition;
            servo.setPosition(targetPosition);
        }
    }

    public boolean isProfiled() {
        return isProfiled;
    }

    public double getTimeToMotionEnd(){
        if(!isProfiled) return 0;
        return profile.getTimeToMotionEnd();
    }
}