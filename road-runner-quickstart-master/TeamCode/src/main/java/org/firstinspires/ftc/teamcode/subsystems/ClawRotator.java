package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.CRServo;

@Config
public class ClawRotator {
    private static final double FORWARD_POSITION = 1;
    private static final double BACK_POSITION = 0.0;
    private static CRServo rotator;
    private static Gamepad driver1;
    private static Gamepad driver2;
    public static boolean isRotatorForward = false;
    private int debounceCounter = 0;
    private static final int DEBOUNCE_THRESHOLD = 30;

    public ClawRotator(OpMode opMode) {
        driver1 = opMode.gamepad1;
        driver2 = opMode.gamepad2;
        rotator = opMode.hardwareMap.get(CRServo.class, "clawRotator");
        rotator.setDirection(CRServo.Direction.FORWARD);
    }

    public void teleOp() {
        handleRotate();
    }
    private void handleRotate() {
        // rotate claw left/right on driver2 right stick
       double power = driver2.right_stick_x;
       rotator.setPower(power);
    }
    public void setRotatorBack() {
        rotator.setPower(-1);
        isRotatorForward = false;
    }
    public void setRotatorForward() {
        rotator.setPower(1);
        isRotatorForward = true;
    }
    public boolean isIsRotatorForward() {
        return isRotatorForward;
    }
    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Claw Rotator State: ", isRotatorForward ? "Left" : "Right");
        opMode.telemetry.addData("Claw Rotator Power: ", rotator.getPower());
        opMode.telemetry.update();
    }
}