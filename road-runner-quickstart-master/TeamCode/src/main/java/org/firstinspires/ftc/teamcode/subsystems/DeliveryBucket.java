package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class DeliveryBucket {
    private static final double FORWARD_POSITION = 1;
    private static final double BACK_POSITION = 0.0;
    private static Servo bucketRotator;
    private static Gamepad driver1;
    private static Gamepad driver2;
    public static boolean isBucketForward = true;
    private int debounceCounter = 0;
    private static final int DEBOUNCE_THRESHOLD = 30;

    public DeliveryBucket(OpMode opMode) {
        driver1 = opMode.gamepad1;
        driver2 = opMode.gamepad2;
        bucketRotator = opMode.hardwareMap.get(Servo.class, "bucketRotator");
        bucketRotator.setDirection(Servo.Direction.FORWARD);
        bucketRotator.setPosition(BACK_POSITION); // Start with the claw closed
    }

    public void teleOp() {
        handleToggle();
    }
    private void handleToggle() {
        // Toggle claw when trigger is pressed
        if (driver1.b) {
            if (debounceCounter > DEBOUNCE_THRESHOLD) {
                toggleBucket();
                debounceCounter = 0;
            }
        } else {
            debounceCounter++;
        }
    }
    private void toggleBucket() {
        if (isBucketForward) {
            setBucketBack();
        } else {
            setBucketForward();
        }
    }
    public void setBucketBack() {
        bucketRotator.setPosition(BACK_POSITION);
        isBucketForward = false;
    }
    public void setBucketForward() {
        bucketRotator.setPosition(FORWARD_POSITION);
        isBucketForward = true;
    }
    public boolean isIsBucketForward() {
        return isBucketForward;
    }
    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Delivery Bucket State", isBucketForward ? "Open" : "Closed");
        opMode.telemetry.addData("Delivery Bucket Position", bucketRotator.getPosition());
        opMode.telemetry.update();
    }
}