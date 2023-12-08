package org.firstinspires.ftc.teamcode.Autonomy;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Autonomy.utils.BlueCloseTrajectory;
import org.firstinspires.ftc.teamcode.components.Controls;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.parts.OutTake;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "autoBlueClose")
public class AutonomyBlueClose extends LinearOpMode {

    private Controls controls;
    private OutTake outTake;
    public BlueCloseTrajectory bt;


    private SampleMecanumDrive mecanumDrive;
    OpenCvCamera camera;

    public void runOpMode() throws InterruptedException{


        // ------------------ OpenCv initialisation code
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier
                ("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        ObjectDetectionPipeline detector = new ObjectDetectionPipeline(telemetry);

        camera.setPipeline(detector);
        // ------------------ OpenCv code
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                camera.startStreaming(432, 240, OpenCvCameraRotation.SENSOR_NATIVE);
            }

            @Override
            public void onError(int errorCode) {
                // ------------------ Tzeapa frate
            }

        });
        FtcDashboard.getInstance().startCameraStream(camera, 0);
        mecanumDrive = new SampleMecanumDrive(hardwareMap);
        controls = new Controls(gamepad1, gamepad2);
        outTake = new OutTake(hardwareMap, controls, telemetry);
        int i = 0;
        bt = new BlueCloseTrajectory(mecanumDrive, outTake, controls);
        outTake.update();
        mecanumDrive.setPoseEstimate(bt.initRobotPos);

        outTake.grippers.activate();

        waitForStart();

        outTake.grippers.activate();
            switch (detector.getLocation()) {
                case LEFT:
                    telemetry.addData("location:" , "left");
                    mecanumDrive.followTrajectorySequenceAsync(bt.getLeft());
                    break;
                case MIDDLE:
                    telemetry.addData("location:" , "middle");
                    mecanumDrive.followTrajectorySequenceAsync(bt.getMiddle());
                    break;
                case RIGHT:
                    telemetry.addData("location:" , "right");
                    mecanumDrive.followTrajectorySequenceAsync(bt.getRight());
                    break;
                default:
                    telemetry.addData("location: " , "nu stiu boss");
                    mecanumDrive.followTrajectorySequenceAsync(bt.getLeft());
                    break;
            }



        while(opModeIsActive()) {
            camera.stopStreaming();
            if(i==0)
                outTake.grippers.activate();
            if(i<2)
                i++;

            mecanumDrive.update();
            outTake.update();
            controls.update();

        }

    }

}
