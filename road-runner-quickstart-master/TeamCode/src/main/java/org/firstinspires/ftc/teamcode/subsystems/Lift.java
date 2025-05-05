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
    public final Integer HIGH_CHAMBER = 2080;
    public final Integer HIGH_BASKET = 3500;
    public final Integer WALL_POSITION = 550;
    public final Integer MAX_EXTEND = 650; // can go up to 1500 if arm is not out
    public Lift(OpMode opMode) {

        Driver2 = opMode.gamepad2;
        Driver1 = opMode.gamepad1;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;

        LeftLift = hardwareMap.get(DcMotor.class, "LiftA");
        LeftLift.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RightLift = hardwareMap.get(DcMotor.class, "LiftB");
        RightLift.setDirection(DcMotorSimple.Direction.REVERSE);
        RightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reset encoders to start at 0
        //RightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    //    RightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //LeftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    //    LeftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
/*
        LeftLift.setTargetPosition(0);
        RightLift.setTargetPosition(0);

        LeftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
*/
    }

    public void teleop(boolean liftForward) {

        double slow = (Driver1.right_bumper ? 0.5 : 1.0);
        double liftPowerUp = Driver1.left_trigger * slow;
        double liftPowerDown = Driver1.right_trigger * slow;

        //lift can only extend if the trigger is pressed
        //if the lift is forward, the lift must be lest than the horizontal max

        if (Driver1.left_trigger > 0.1 &&
                (!liftForward || leftLiftPosition() < MAX_EXTEND) ) {
            LeftLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RightLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            LeftLift.setPower(liftPowerUp);
            RightLift.setPower(liftPowerUp);
            //moveByTicks(150);
        } else if (Driver1.right_trigger > 0.1) {
            LeftLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RightLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            LeftLift.setPower(-1 * liftPowerDown);
            RightLift.setPower(-1 * liftPowerDown);
            //moveByTicks(-150);
        }  else if (Driver1.y) {
            maxExtend();
        }/*else if (Driver1.b) {
            moveHighRung();
        } else if (Driver1.a) {
            moveToWall();
        } else if (Driver1.x) {
            resetLift();
        }

        if (Driver1.right_trigger > 0.1) {

        } else if (Driver1.left_trigger > 0.1) {

        }
         */
        else {
            Stop();
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

    public void moveHighRung() {
        LeftLift.setTargetPosition(HIGH_CHAMBER);
        RightLift.setTargetPosition(HIGH_CHAMBER);

        LeftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        setPower(1.0);

        while (leftLiftPosition() < HIGH_CHAMBER) { }
        setPower(0.4);
    }
    public void moveToWall() {
        LeftLift.setTargetPosition(WALL_POSITION);
        RightLift.setTargetPosition(WALL_POSITION);

        LeftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        setPower(1);

        while (leftLiftPosition() < WALL_POSITION) {}
        setPower(0.4);
    }
    public void resetLift() {
        LeftLift.setTargetPosition(0);
        RightLift.setTargetPosition(0);

        LeftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        setPower(1);

        while (leftLiftPosition() > 50) {}
        Stop();
    }

    public void maxExtend() {
        LeftLift.setTargetPosition(MAX_EXTEND);
        RightLift.setTargetPosition(MAX_EXTEND);

        LeftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        setPower(1.0);

        while (leftLiftPosition() <= MAX_EXTEND) {}
        Stop();
    }

    public void setPower(double power) {
        LeftLift.setPower(power);
        RightLift.setPower(power);
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
        opMode.telemetry.addData("Slide Power", LeftLift.getPower());
        opMode.telemetry.update();
    }
}
