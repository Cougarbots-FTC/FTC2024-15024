package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;

@Config
public class ClawRotator {
    private static final double FORWARD_POSITION = 1;
    private static final double BACK_POSITION = -1;
    private static Servo rotator1;
    private static Gamepad driver1;
    private static Gamepad driver2;
    public static boolean isRotatorForward = false;
    private int debounceCounter = 0;
    private static final int DEBOUNCE_THRESHOLD = 30;

    public ClawRotator(OpMode opMode) {
        driver1 = opMode.gamepad1;
        driver2 = opMode.gamepad2;

        rotator1 = opMode.hardwareMap.get(Servo.class, "rightArmRotate");
        rotator1.setDirection(Servo.Direction.FORWARD);
    }

    public void teleOp() {
        handleRotate();
    }
    private void handleRotate() {
        // rotate claw forward and back on a
        if (driver2.a) {
            if (debounceCounter > DEBOUNCE_THRESHOLD) {
                toggleRotator();
                debounceCounter = 0;
            }
        } else {
            debounceCounter++;
        }
    }
    private void toggleRotator() {
        if (isIsRotatorForward()) {
            setRotatorBack();
        } else {
            setRotatorForward();
        }
    }
    public void setRotatorBack() {
        rotator1.setPosition(BACK_POSITION);
        isRotatorForward = false;
    }
    public void setRotatorForward() {
        rotator1.setPosition(FORWARD_POSITION);
        isRotatorForward = true;
    }
    public boolean isIsRotatorForward() {
        return isRotatorForward;
    }
    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Claw Rotator State: ", isRotatorForward ? "Forward" : "Back");
        opMode.telemetry.update();
    }
}