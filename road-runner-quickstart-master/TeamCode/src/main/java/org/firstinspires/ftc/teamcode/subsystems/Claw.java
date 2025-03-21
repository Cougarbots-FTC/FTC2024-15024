package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.CRServo;

@Config
public class Claw {
    private static final double OPEN_POSITION = 0.0;
    private static final double CLOSED_POSITION = 1;
    private static CRServo clawServo;
    private static Gamepad driver1;
    private static Gamepad driver2;
    public static boolean isClawOpen = false;
    private int debounceCounter = 0;
    private static final int DEBOUNCE_THRESHOLD = 30;

    public Claw(OpMode opMode) {
        driver1 = opMode.gamepad1;
        driver2 = opMode.gamepad2;
        clawServo = opMode.hardwareMap.get(CRServo.class, "claw");
        clawServo.setDirection(CRServo.Direction.FORWARD);
        //clawServo.setPosition(OPEN_POSITION); // Start with the claw open

    }

    public void teleOp() {

        if (driver2.y){
            clawServo.setPower(1);
        }
        else if(driver2.b){
            clawServo.setPower(-1);
        } else {
            clawServo.setPower(0);
        }
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
        //clawServo.setPosition(CLOSED_POSITION);
        isClawOpen = false;

    }
    public void setClawOpen() {
        //clawServo.setPosition(OPEN_POSITION);

    }
    public boolean isOpen() {
        return isClawOpen;
    }
    public void addTelemetry(OpMode opMode) {
        opMode.telemetry.addData("Claw State", isClawOpen ? "Open" : "Closed");
        //opMode.telemetry.addData("Claw Position", clawServo.getPosition());
        opMode.telemetry.update();
    }
}