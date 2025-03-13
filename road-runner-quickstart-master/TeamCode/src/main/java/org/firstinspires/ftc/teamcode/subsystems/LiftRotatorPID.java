package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

public class LiftRotatorPID {
    private PIDController controller;
    public static double p = 0.0001;
    public static double i = 0;
    public static double d = 0.0005;
    public static double f = 0;
    public static int target = 100;
    private double ticks_in_degrees = 2100;//t * (360.0 / 28);
            // https://www.revrobotics.com/rev-41-1600/
            // 28 counts / rev at the motor
            //(ticks) * (360 / encoder counts per rev);
    private final DcMotor liftRotator;
    private final Gamepad driver1;
    private final Gamepad driver2;


    public LiftRotatorPID(OpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        driver1 = opMode.gamepad1;
        driver2 = opMode.gamepad2;

        liftRotator = hardwareMap.get(DcMotor.class, "liftRotator");
        liftRotator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        controller = new PIDController(p, i, d);
    }

    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }
    public void loop() {
        controller.setPID(p,i,d);
        int rotatorPosition = liftRotator.getCurrentPosition();
        double pid = controller.calculate(rotatorPosition, target);
        double ff = Math.cos(Math.toRadians(target/ticks_in_degrees)) * f;

        double power = pid + ff;
        setPower(power);
    }
    public void setSetpoint(int newTarget) {
        target = newTarget;
    }
    public int getSetpoint () {
        return target;
    }
    public int getCurrentPosition() {
        return liftRotator.getCurrentPosition();
    }
    public void setPower (double power) {
        liftRotator.setPower(power);
    }
    public void addTelemetry (OpMode opMode) {
        opMode.telemetry.addData("Rotator Position: ", liftRotator.getCurrentPosition());
        opMode.telemetry.addData("Rotator Target: ", getSetpoint());
        opMode.telemetry.update();
    }
}
