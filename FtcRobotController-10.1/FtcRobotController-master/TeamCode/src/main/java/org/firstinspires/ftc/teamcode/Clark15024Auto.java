package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous (name = "Clark2024Auto")
public class Clark15024Auto extends LinearOpMode {
    Clark15024HWMap robot = new Clark15024HWMap();
    ElapsedTime time = new ElapsedTime();


    @Override
    public void runOpMode(){
        telemetry.addData("Time", time.time());
        moveForward(10, 100);
    }

    public void moveForward(double p, int t){
        robot.driveLeftBack.setPower(-p);
        robot.driveRightFront.setPower(-p);
        robot.driveLeftFront.setPower(-p);
        robot.driveRightBack.setPower(-p);
        sleep(t);

        robot.driveLeftBack.setPower(0);
        robot.driveRightFront.setPower(0);
        robot.driveLeftFront.setPower(0);
        robot.driveRightBack.setPower(0);
    }

    public void moveBack(double p, int t){
        robot.driveLeftBack.setPower(p);
        robot.driveRightFront.setPower(p);
        robot.driveLeftFront.setPower(p);
        robot.driveRightBack.setPower(p);
        sleep(t);

        robot.driveLeftBack.setPower(0);
        robot.driveLeftFront.setPower(0);
        robot.driveRightBack.setPower(0);
        robot.driveRightFront.setPower(0);
    }

    public void moveLeft(double p, int t){
        robot.driveLeftBack.setPower(-p);
        robot.driveRightFront.setPower(-p);
        robot.driveLeftFront.setPower(p);
        robot.driveRightBack.setPower(p);
        sleep(t);

        robot.driveLeftBack.setPower(0);
        robot.driveRightFront.setPower(0);
        robot.driveLeftFront.setPower(0);
        robot.driveRightBack.setPower(0);
    }

    public void moveRight(double p, int t){
        robot.driveLeftBack.setPower(-p);
        robot.driveRightFront.setPower(-p);
        robot.driveLeftFront.setPower(p);
        robot.driveRightBack.setPower(p);
        sleep(t);

        robot.driveLeftBack.setPower(0);
        robot.driveRightFront.setPower(0);
        robot.driveLeftFront.setPower(0);
        robot.driveRightBack.setPower(0);
    }
}
