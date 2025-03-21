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
    private static CRServo rotator1;
    private static CRServo rotator2;
    private static Gamepad driver1;
    private static Gamepad driver2;
    public static boolean isRotatorForward = false;
    private int debounceCounter = 0;
    private static final int DEBOUNCE_THRESHOLD = 30;

    public ClawRotator(OpMode opMode) {
        driver1 = opMode.gamepad1;
        driver2 = opMode.gamepad2;

        rotator1 = opMode.hardwareMap.get(CRServo.class, "clawRotator1");
        rotator1.setDirection(CRServo.Direction.FORWARD);

        rotator2 = opMode.hardwareMap.get(CRServo.class, "clawRotator2");
        rotator2.setDirection(CRServo.Direction.REVERSE);
    }

    public void teleOp() {

        //handleRotate();
        double rotatorPower = driver2.right_stick_x;
        rotator1.setPower(rotatorPower);
        rotator2.setPower(rotatorPower);
    }
    private void handleRotate() {
        // rotate claw left/right on driver2 right stick
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
        //rotator.setPosition(0);
        rotator1.setPower(1);
        isRotatorForward = false;
    }
    public void setRotatorForward() {
        rotator1.setPower(-1);
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