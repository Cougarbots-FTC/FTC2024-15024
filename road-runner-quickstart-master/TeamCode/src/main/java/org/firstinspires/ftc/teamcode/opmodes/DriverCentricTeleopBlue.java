package org.firstinspires.ftc.teamcode.opmodes;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.*;


@TeleOp (name = "DriverCentricTeleOp - BLUE")
public class DriverCentricTeleopBlue extends OpMode {

    private DriverCentricDrive driverCentricDrive;
    private Lift lift;
    private LiftRotator liftRotator;
    //private LiftRotatorPID liftRotator;
    private Claw claw;
    private ClawRotator clawRotator;
    private Wrist wrist;
    private Color_Sensor colorSensor;
    private boolean liftForward = false;

    private final String ALLIANCE_COLOR = "Blue";

    @Override
    public void init() {
        driverCentricDrive = new DriverCentricDrive(this);
        claw = new Claw(this);
        lift = new Lift(this);
        liftRotator = new LiftRotator(this);
        //liftRotator = new LiftRotatorPID(this);
        clawRotator = new ClawRotator(this);
        wrist = new Wrist(this);
        colorSensor = new Color_Sensor(this);

    }

    @Override
    public void loop() {
        driverCentricDrive.driverCentric();
        claw.teleOp();
        lift.teleop(liftForward);
        liftRotator.teleop();
        clawRotator.teleOp();
        wrist.teleOp();

        lift.addTelemetry(this);

        liftForward = liftRotator.isLiftForward();

        //detectColorAndOpen();
        //detectColorAndClose();

    }

    public void armRotatorFunctionality() {

    }

    public void detectColorAndOpen() {
        if (gamepad2.b && colorSensor.getColorDetected().equals(ALLIANCE_COLOR)) {// && !frontClaw.isOpen()) {
            //sleep(2000);
            //claw.setClawOpen(this);
        }
    }

    public void detectColorAndClose() {
        if (!gamepad2.b && colorSensor.getColorDetected().equals(ALLIANCE_COLOR)) {
            //claw.setClawClosed(this);
        }
    }

}
