package org.firstinspires.ftc.teamcode.opmodes;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.*;


@TeleOp (name = "DriverCentricTeleOp - BLUE")
public class DriverCentricTeleopBlue extends OpMode {
    private Claw claw;
    private Lift lift;
    private DriverCentricDrive driverCentricDrive;
    private ClawRotator clawRotator;
    private Wrist wrist;
    private Color_Sensor colorSensor;

    private final String ALLIANCE_COLOR = "Blue";

    @Override
    public void init() {
        driverCentricDrive = new DriverCentricDrive(this);
        claw = new Claw(this);
        lift = new Lift(this);
        clawRotator = new ClawRotator(this);
        wrist = new Wrist(this);
        colorSensor = new Color_Sensor(this);

    }

    @Override
    public void loop() {
        //TODO: reduce speed if lift if up or right

        driverCentricDrive.driverCentric();
        claw.teleOp();
        claw.addTelemetry(this);
        lift.teleop();
        clawRotator.teleOp();
        wrist.teleOp();
        colorSensor.addTelemetry(this);
        detectColorAndOpen();
        detectColorAndClose();

    }

    public void detectColorAndOpen() {
        if (gamepad2.b && colorSensor.getColorDetected().equals(ALLIANCE_COLOR)) {// && !frontClaw.isOpen()) {
            //sleep(2000);
            claw.setClawOpen();
        }
    }

    public void detectColorAndClose() {
        if (!gamepad2.b && colorSensor.getColorDetected().equals(ALLIANCE_COLOR)) {
            claw.setClawClosed();
        }
    }

}
