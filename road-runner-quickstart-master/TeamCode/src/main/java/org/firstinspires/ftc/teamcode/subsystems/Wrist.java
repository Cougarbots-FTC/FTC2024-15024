package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Wrist {
    private static final double OPEN_POSITION = 1;
    private static final double CLOSED_POSITION = 0.0;
    private static CRServo wristServo;
    private static Gamepad driver1;
    private static Gamepad driver2;
    public static boolean isClawForward = false;
    private int debounceCounter = 0;
    private static final int DEBOUNCE_THRESHOLD = 30;

    public Wrist(OpMode opMode) {
        driver1 = opMode.gamepad1;
        driver2 = opMode.gamepad2;
        wristServo = opMode.hardwareMap.get(CRServo.class, "wrist");
        wristServo.setDirection(CRServo.Direction.FORWARD);
        //wristServo.setPosition(CLOSED_POSITION); // Start with the claw closed
    }

    public void teleOp() {
        double power = driver2.left_stick_x;
        wristServo.setPower(power);
        /*
        if (driver2.dpad_left) {
            wristServo.setPower(1);
        } else if (driver2.dpad_right) {
            wristServo.setPower(-1);
        } else {
            wristServo.setPower(0);
        }
        */
        //handleToggle();
    }
    private void handleToggle() {
        // Toggle claw when trigger is pressed
        if (driver2.left_bumper) {
            if (debounceCounter > DEBOUNCE_THRESHOLD) {
                toggleClaw();
                debounceCounter = 0;
            }
        } else {
            debounceCounter++;
        }
    }
    private void toggleClaw() {
        if (isClawForward) {
            setClawClosed();
        } else {
            setClawOpen();
        }
    }
    public void setClawClosed() {
        //wristServo.setPosition(CLOSED_POSITION);
        isClawForward = false;
    }
    public void setClawOpen() {
        //wristServo.setPosition(OPEN_POSITION);
        isClawForward = true;
    }
    public boolean isOpen() {
        return isClawForward;
    }
    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Wrist State", isClawForward ? "Forward" : "Back");
        //opMode.telemetry.addData("Wrist Position", wristServo.getPosition());
        opMode.telemetry.update();
    }
}