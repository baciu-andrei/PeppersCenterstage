package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.nio.ByteBuffer;

public class ByteController {
    private static byte[] byteArray, lastByteArray, tmparray1, tmparray2;
    public Gamepad gamepadPressed, gamepadReleased;
    private static Telemetry tele;
    public ByteController(Gamepad gamepad1, Telemetry tel){
        byteArray = gamepad1.toByteArray();
        tele = tel;
    }

    public void loop(Gamepad gamepad1){
        tele.addData("size", byteArray.length);
        byteArray = gamepad1.toByteArray();
        for(int i = 0; i < byteArray.length; i++){
            tmparray1[i] = (byte) (lastByteArray[i] ^ byteArray[i]);
            tmparray2[i] = (byte) (byteArray[i] ^ lastByteArray[i]);

        }
        gamepadPressed.fromByteArray(lastByteArray);

        lastByteArray = byteArray.clone();
    }
}
