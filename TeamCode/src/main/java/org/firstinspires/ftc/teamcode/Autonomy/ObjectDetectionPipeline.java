package org.firstinspires.ftc.teamcode.Autonomy;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Config
public class ObjectDetectionPipeline extends OpenCvPipeline {
    Mat mat = new Mat();
    Telemetry telemetry;
    public enum Location{
        LEFT,
        MIDDLE,
        RIGHT
    }
    private Location location;
    public boolean isBlue = false;

    public static int leftRectTopX = 0 ,leftRectTopY = 80;
    public static int leftRectBottomX = 50 ,leftRectBottomY = 180;


    public static int rightRectTopX = 380 ,rightRectTopY = 80;
    public static int rightRectBottomX = 430 ,rightRectBottomY = 200;

    public static int middleRectTopX = 190 ,middleRectTopY = 80;
    public static int middleRectBottomX = 260 ,middleRectBottomY = 180;

    public static int lowH = 100 ,lowS = 80, lowV = 50;
    public static int highH = 140, highS = 255, highV = 255;



    public ObjectDetectionPipeline(Telemetry telemetry) {this.telemetry = telemetry;}

    @Override
    public Mat processFrame(Mat input) {
          Rect LEFT_ROI = new Rect(
                new Point(leftRectTopX, leftRectTopY),
                new Point(leftRectBottomX, leftRectBottomY));
          Rect MIDDLE_ROI = new Rect(
                new Point(middleRectTopX, middleRectTopY),
                new Point(middleRectBottomX, middleRectBottomY));
          Rect RIGHT_ROI = new Rect(
                  new Point(rightRectTopX, rightRectTopY),
                  new Point(rightRectBottomX, rightRectBottomY)
          );
        //thresholding
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_BGR2HSV);
        Scalar lowHSV = new Scalar(lowH ,lowS, lowV);
        Scalar highHSV = new Scalar(highH ,highS, highV);
        Core.inRange(mat, lowHSV, highHSV ,mat);

        Mat left = mat.submat(LEFT_ROI);
        Mat middle = mat.submat(MIDDLE_ROI);
        Mat right = mat.submat(RIGHT_ROI);

        double leftValue = Core.sumElems(left).val[0] /LEFT_ROI.area() /255;
        double middleValue = Core.sumElems(middle).val[0] /MIDDLE_ROI.area() /255;
        double rightValue = Core.sumElems(right).val[0]/ RIGHT_ROI.area() / 255;

        left.release();
        middle.release();

        telemetry.addData("Left raw value", (int) Core.sumElems(left).val[0]);
        telemetry.addData("Right raw value", (int) Core.sumElems(middle).val[0]);
        telemetry.addData("Left percentage", Math.round(leftValue * 100) + "%");
        telemetry.addData("Right percentage", Math.round(middleValue * 100) + "%");

        if(leftValue>rightValue&&leftValue>middleValue) {
            location = Location.LEFT;
            telemetry.addData("pixel_location: ", "left");
        }
        else if(middleValue>rightValue&&middleValue>leftValue){
            location = Location.MIDDLE;
            telemetry.addData("pixel_location: ", "middle");
        }
        else{
            location = Location.RIGHT;
            telemetry.addData("pixel_location: ", "right");
        }
        telemetry.update();
        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_GRAY2RGB);

        Scalar colorFound = new Scalar(0,255,0);
        Scalar colorNotFound = new Scalar(255,0,0);

        Imgproc.rectangle(mat,LEFT_ROI,location == Location.LEFT? colorFound:colorNotFound);
        Imgproc.rectangle(mat,MIDDLE_ROI,location == Location.MIDDLE? colorFound:colorNotFound);
        Imgproc.rectangle(mat,RIGHT_ROI,location == Location.RIGHT? colorFound:colorNotFound);

        return mat;
    }
    public Location getLocation(){
        return location;
    }
}
