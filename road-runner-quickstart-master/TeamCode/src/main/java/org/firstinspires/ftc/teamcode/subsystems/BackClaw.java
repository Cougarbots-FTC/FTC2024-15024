package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class BackClaw {
    private static final double OPEN_POSITION = 0.5;
    private static final double CLOSED_POSITION = 0.0;
    private static Servo clawServo;
    private static Gamepad driver1;
    private static Gamepad driver2;
    public static boolean isClawOpen = false;
    private int debounceCounter = 0;
    private static final int DEBOUNCE_THRESHOLD = 30;

    public BackClaw(OpMode opMode) {
        driver1 = opMode.gamepad1;
        driver2 = opMode.gamepad2;
        clawServo = opMode.hardwareMap.get(Servo.class, "backClaw");
        clawServo.setDirection(Servo.Direction.FORWARD);
        clawServo.setPosition(CLOSED_POSITION); // Start with the claw closed
    }

    public void teleOp() {
        handleToggle();
    }
    private void handleToggle() {
        // Toggle claw when trigger is pressed
        if (driver2.y) {
            if (debounceCounter > DEBOUNCE_THRESHOLD) {
                toggleClaw();
                debounceCounter = 0;
            }
        } else {
            debounceCounter++;
        }
    }
    private void toggleClaw() {
        if (isClawOpen) {
            setClawClosed();
        } else {
            setClawOpen();
        }
    }
    public void setClawClosed() {
        clawServo.setPosition(CLOSED_POSITION);
        isClawOpen = false;
    }
    public void setClawOpen() {
        clawServo.setPosition(OPEN_POSITION);
        isClawOpen = true;
    }
    public boolean isOpen() {
        return isClawOpen;
    }
    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Back Claw State", isClawOpen ? "Open" : "Closed");
        opMode.telemetry.addData("Back Claw Position", clawServo.getPosition());
        opMode.telemetry.update();
    }
}