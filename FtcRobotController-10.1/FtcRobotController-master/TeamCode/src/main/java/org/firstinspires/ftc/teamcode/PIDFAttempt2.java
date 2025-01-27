package org.firstinspires.ftc.teamcode;

import android.graphics.PixelFormat;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
@Config
public class PIDFAttempt2 extends OpMode{
    private PIDController controller;
    public static double p=0.007,i=0,d=0.0005;
    public static double f=0.12;
    public static int target =300;
    private final double ticks_in_degree = 2100;
    private DcMotorEx arm_motor;

    public PIDFAttempt2(DcMotorEx arm_motor) {
        this.arm_motor = arm_motor;
        controller = new PIDController(p,i,d);

    }

    @Override
    public void init(){
        //controller = new PIDController(p,i,d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        //arm_motor = hardwareMap.get(DcMotorEx.class,"ArmRotator");
        //arm_motor.setDirection(DcMotorEx.Direction.REVERSE);
    }
    @Override
    public void loop(){
        controller.setPID(p,i,d);
        int armPos = arm_motor.getCurrentPosition();
        double pid = controller.calculate(armPos, target);
        double ff = Math.cos(Math.toRadians(target/ticks_in_degree))*f;

        double power = pid +ff;

        arm_motor.setPower(power);

        //telemetry.addData("pos",armPos);
        //telemetry.addData("target", target);
        //telemetry.update();
    }

    public void setSetpoint(int newTarget) {
        target = newTarget;
    }

    public int getSetpoint() {
        return target;
    }

    public int getCurrentPosition() {
        return arm_motor.getCurrentPosition();
    }
}