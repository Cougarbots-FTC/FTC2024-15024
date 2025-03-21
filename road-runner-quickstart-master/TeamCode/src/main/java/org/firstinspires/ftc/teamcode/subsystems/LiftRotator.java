package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class LiftRotator {
    private final DcMotor liftRotator;
    private final Gamepad Driver1;
    private final Gamepad Driver2;

    public LiftRotator(OpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Driver1 = opMode.gamepad1;
        Driver2 = opMode.gamepad2;

        liftRotator = hardwareMap.get(DcMotor.class, "liftRotator");

        liftRotator.setDirection(DcMotorSimple.Direction.FORWARD);

        liftRotator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftRotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void teleop() {
        if (Driver2.left_trigger > 0.1) {
            moveByTicks(150);
        } else if (Driver2.right_trigger > 0.1) {
            moveByTicks(-150);
        } /*else if (Driver2.a) {
            liftRotator.setTargetPosition(0);
            liftRotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftRotator.setPower(1);
        } else if (Driver2.b) {
            liftRotator.setTargetPosition(300);
            liftRotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftRotator.setPower(1);
        } */else {
            Stop();
        }

    }

    public void  moveByTicks(int ticks) {
        // Calculate new target position by adding ticks
        int newTarget = liftRotatorPosition() + ticks;
        liftRotator.setTargetPosition(newTarget);
        liftRotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRotator.setPower(0.25);
    }

    public void Stop() {
        liftRotator.setPower(0);
    }

    public int liftRotatorPosition () { return liftRotator.getCurrentPosition(); }

    public void addTelemetry (OpMode opMode) {
        opMode.telemetry.addData("Rotator Position: ", liftRotatorPosition());
        opMode.telemetry.update();
    }
}
