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
    //TODO: Check this position
    private final int FORWARD_POSITION = 300;
    private final int BACK_POSITION = 0;

    public LiftRotator(OpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        Driver1 = opMode.gamepad1;
        Driver2 = opMode.gamepad2;

        liftRotator = hardwareMap.get(DcMotor.class, "liftRotator");
        liftRotator.setDirection(DcMotorSimple.Direction.FORWARD);
        liftRotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //liftRotator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }

    public void teleop() {
        double slow = (Driver2.right_bumper ? 0.5 : 1.0);
        double rotatorPowerUp = Driver2.left_trigger * slow;
        double rotatorPowerDown = -1 * Driver2.right_trigger * slow;
        if (Driver2.left_trigger > 0.1) {
            liftRotator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            liftRotator.setPower(rotatorPowerUp);
            //moveByTicks(150);
        } else if (Driver2.right_trigger > 0.1) {
            liftRotator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            liftRotator.setPower(rotatorPowerDown);
            //moveByTicks(-150);
        } else if (Driver2.a) {
            setLiftBack();
        } else if (Driver2.x) {
            setLiftForward();
        } else {
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

    public void setLiftBack() {
        liftRotator.setTargetPosition(BACK_POSITION);
        liftRotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRotator.setPower(0.5);
        //TODO: Check value
        while (liftRotatorPosition() > 50) {}
        liftRotator.setPower(0);
    }
    public void setLiftForward() {
        liftRotator.setTargetPosition(FORWARD_POSITION);
        liftRotator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRotator.setPower(0.25);
        //TODO: Check value
        while (liftRotatorPosition() < FORWARD_POSITION) {}
        liftRotator.setPower(0);
    }
    public int liftRotatorPosition () { return liftRotator.getCurrentPosition(); }

    public boolean liftForward() {
        return liftRotatorPosition() >= FORWARD_POSITION; //TODO: check this
    }
    public void addTelemetry (OpMode opMode) {
        opMode.telemetry.addData("Rotator Position: ", liftRotatorPosition());
        opMode.telemetry.addData("Rotator Forward", liftForward());
        opMode.telemetry.update();
    }
}
