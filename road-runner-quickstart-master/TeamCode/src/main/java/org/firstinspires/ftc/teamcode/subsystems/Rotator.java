package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Rotator {
    private static final double FORWARD_POSITION = 0.0;
    private static final double BACK_POSITION = 0.5;
    private static Servo leftRotator;
    private static Servo rightRotator;
    private static Gamepad driver1;
    private static Gamepad driver2;
    public static boolean isRotatorForward = false;
    private int debounceCounter = 0;
    private static final int DEBOUNCE_THRESHOLD = 30;

    public Rotator(OpMode opMode) {
        driver1 = opMode.gamepad1;
        driver2 = opMode.gamepad2;
        leftRotator = opMode.hardwareMap.get(Servo.class, "leftArmRotator");
        rightRotator = opMode.hardwareMap.get(Servo.class, "rightArmRotator");
        leftRotator.setDirection(Servo.Direction.REVERSE);
        rightRotator.setDirection(Servo.Direction.REVERSE);
        leftRotator.setPosition(BACK_POSITION); // Start with the arm back
        rightRotator.setPosition(BACK_POSITION);
    }

    public void teleOp() {
        handleToggle();
    }
    private void handleToggle() {
        // Toggle claw when trigger is pressed
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
        if (isRotatorForward) {
            setRotatorBack();
        } else {
            setRotatorForward();
        }
    }
    public void setRotatorBack() {
        leftRotator.setPosition(FORWARD_POSITION);
        rightRotator.setPosition(FORWARD_POSITION);
        isRotatorForward = false;
    }
    public void setRotatorForward() {
        leftRotator.setPosition(BACK_POSITION);
        rightRotator.setPosition(BACK_POSITION);
        isRotatorForward = true;
    }
    public boolean isIsRotatorForward() {
        return isRotatorForward;
    }
    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Arm Rotator State", isRotatorForward ? "Open" : "Closed");
        opMode.telemetry.addData("Arm Rotator Position", leftRotator.getPosition() + ", " + rightRotator.getPosition());
        opMode.telemetry.update();
    }
}