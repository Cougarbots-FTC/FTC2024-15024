package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.R;

public class Lift {
    ElapsedTime runtime = new ElapsedTime();
    public final DcMotor LeftLift;
    public final DcMotor RightLift;
    private final HardwareMap hardwareMap;
    private final Gamepad Driver2;
    private final Gamepad Driver1;
    public final Telemetry telemetry;
    private final LinearOpMode myOpMode;
    private final BackClaw backClaw;
    public Lift(OpMode opMode) {
        if (opMode instanceof LinearOpMode) {
            myOpMode = (LinearOpMode) opMode;
        } else {
            myOpMode = null;
        }
        Driver2 = opMode.gamepad2;
        Driver1 = opMode.gamepad1;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        backClaw = new BackClaw(opMode);

        LeftLift = hardwareMap.get(DcMotor.class, "LiftA");
        LeftLift.setDirection(DcMotorSimple.Direction.REVERSE);
        LeftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RightLift = hardwareMap.get(DcMotor.class, "LiftB");
        RightLift.setDirection(DcMotorSimple.Direction.FORWARD);
        RightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reset encoders to start at 0
        //LeftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // RightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void teleop() {

        double liftPowerUp = Driver1.left_trigger * (Driver1.right_bumper ? 0.5 : 1.0);
        double liftPowerDown = Driver1.right_trigger * (Driver1.right_bumper ? 0.5 : 1.0);

        if (Driver1.left_trigger > 0.1) {
            LeftLift.setPower(liftPowerUp);
            RightLift.setPower(liftPowerUp);
        } else if (Driver1.right_trigger > 0.1) {
            LeftLift.setPower(-1 * liftPowerDown);
            RightLift.setPower(-1 * liftPowerDown);
        } else {
            Stop();
        }
        //if (Driver1.right_trigger > 0.1) moveByTicks(150);
        //else if (Driver1.left_trigger > 0.1) moveByTicks(-150);
    }
   /* public void moveByTicks(int ticks) {
        // Calculate new target positions by adding ticks
        int newLeftTarget = LeftLift.getCurrentPosition() + ticks;
        int newRightTarget = RightLift.getCurrentPosition() + ticks;

        LeftLift.setTargetPosition(newLeftTarget);
        RightLift.setTargetPosition(newRightTarget);

        LeftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LeftLift.setPower(1);
        RightLift.setPower(1);
    }
    */
    
    public void Stop() {
        LeftLift.setPower(0);
        RightLift.setPower(0);
    }

    public double liftPosition() {
        return LeftLift.getCurrentPosition();
    }
    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Lift Position", liftPosition());
        opMode.telemetry.update();
    }

    public void HighRung() {
        LeftLift.setPower(1);
        RightLift.setPower(1);
        myOpMode.sleep(700);
        LeftLift.setPower(0);
        RightLift.setPower(0);
    }
    //TODO: check to make sure the claw open functionality is working

    /**
     * This function will drop the lift half way then open the claw
     */
    public void ResetAndOpenClaw() {
        LeftLift.setPower(-1);
        RightLift.setPower(-1);
        myOpMode.sleep(200);
        backClaw.setClawOpen();
        //myOpMode.sleep(600);
        myOpMode.sleep(400);
        LeftLift.setPower(0);
        RightLift.setPower(0);
    }

    public void liftSpecimen() {
        LeftLift.setPower(1);
        RightLift.setPower(1);
        myOpMode.sleep(50);
        LeftLift.setPower(0);
        RightLift.setPower(0);
    }
    public void smallReset() {
        LeftLift.setPower(-1);
        RightLift.setPower(-1);
        myOpMode.sleep(200);
        LeftLift.setPower(0);
        RightLift.setPower(0);
    }
}
