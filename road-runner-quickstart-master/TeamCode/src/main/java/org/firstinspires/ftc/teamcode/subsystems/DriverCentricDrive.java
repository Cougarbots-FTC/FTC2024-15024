package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class DriverCentricDrive {
    private final DcMotor leftFront,leftBack,rightFront,rightBack;
    private final Gamepad Driver1;
    double speed = 1;
    public DriverCentricDrive(OpMode opMode) {
        Driver1 = opMode.gamepad1;
        HardwareMap hardwareMap = opMode.hardwareMap;
        leftFront = hardwareMap.get(DcMotor.class, "driveLeftFront");       // e1
        leftBack = hardwareMap.get(DcMotor.class, "driveLeftBack");         // e0
        rightFront = hardwareMap.get(DcMotor.class, "driveRightFront");     // e3
        rightBack = hardwareMap.get(DcMotor.class, "driveRightBack");       // e2

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);

        
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void driverCentric() {
        if(Driver1.right_bumper){
            speed=.5;
        } else {
            speed = 1;
        }
        double y = -Driver1.left_stick_y;
        double x = Driver1.left_stick_x * 1.1;
        double rx = Driver1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator * speed;
        double backLeftPower = (y - x + rx) / denominator* speed;
        double frontRightPower = (y - x - rx)  / denominator* speed;
        double backRightPower = (y + x - rx) / denominator* speed;

        leftFront.setPower(frontLeftPower);
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);
    }
}