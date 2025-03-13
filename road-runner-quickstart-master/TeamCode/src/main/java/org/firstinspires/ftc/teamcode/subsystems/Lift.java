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
    public Lift(OpMode opMode) {

        Driver2 = opMode.gamepad2;
        Driver1 = opMode.gamepad1;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;

        LeftLift = hardwareMap.get(DcMotor.class, "LiftA");
        LeftLift.setDirection(DcMotorSimple.Direction.REVERSE);

        RightLift = hardwareMap.get(DcMotor.class, "LiftB");
        RightLift.setDirection(DcMotorSimple.Direction.FORWARD);

        // Reset encoders to start at 0
/*
        RightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LeftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LeftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LeftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
*/
    }

    public void teleop() {

        /*double slow = (Driver1.right_bumper ? 0.5 : 1.0);
        double liftPowerUp = Driver1.left_trigger * slow;
        double liftPowerDown = Driver1.right_trigger * slow;
        */
        if (Driver1.left_trigger > 0.1) {
            //LeftLift.setPower(liftPowerUp);
            //RightLift.setPower(liftPowerUp);
            moveByTicks(150);
        } else if (Driver1.right_trigger > 0.1) {
            //LeftLift.setPower(-1 * liftPowerDown);
            //RightLift.setPower(-1 * liftPowerDown);
            moveByTicks(-150);
        } else {
            Stop();
        }

        if (Driver1.dpad_up) {
            LeftLift.setTargetPosition(300);
            RightLift.setTargetPosition(300);
        }
        else if (Driver1.dpad_down) {
            LeftLift.setTargetPosition(0);
            RightLift.setTargetPosition(0);
        }
    }
   public void moveByTicks(int ticks) {
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

    
    public void Stop() {
        LeftLift.setPower(0);
        RightLift.setPower(0);
    }

    public double leftLiftPosition() {
        return LeftLift.getCurrentPosition();
    }
    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Left Lift Position", leftLiftPosition());
        opMode.telemetry.addData("Right Lift Position: ", RightLift.getCurrentPosition());
        opMode.telemetry.update();
    }




}
