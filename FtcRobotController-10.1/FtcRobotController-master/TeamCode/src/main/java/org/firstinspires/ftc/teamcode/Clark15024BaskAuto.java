package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Clark15024BaskAuto")
public class Clark15024BaskAuto extends LinearOpMode{
    Clark15024HWMap robot = new Clark15024HWMap();

    @Override
    public void runOpMode(){
        robot.Map(hardwareMap);
        //telemetry.addData("Time", time.time());
        waitForStart();

        //turnRight(500);
        moveLeft(1, 1250);
        moveRight(1,500);
        moveForward(1,1200);
        moveRight(1,950);
        moveBack(1,100);
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
        robot.driveLeftBack.setPower(p);
        robot.driveRightFront.setPower(p);
        robot.driveLeftFront.setPower(-p);
        robot.driveRightBack.setPower(-p);
        sleep(t);

        robot.driveLeftBack.setPower(0);
        robot.driveRightFront.setPower(0);
        robot.driveLeftFront.setPower(0);
        robot.driveRightBack.setPower(0);
    }

    public void turnRight(int t) {
        {
            robot.driveLeftBack.setPower(-1);
            robot.driveRightFront.setPower(1);
            robot.driveLeftFront.setPower(-1);
            robot.driveRightBack.setPower(1);
            sleep(t);
        }
    }

    }
