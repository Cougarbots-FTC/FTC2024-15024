package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.subsystems.*;


@TeleOp (name = "DriverCentricTeleOp - NEW")
public class DriverCentricTeleop extends OpMode {
    private BackClaw backClaw;
    private DeliveryBucket deliveryBucket;
    private FrontClaw frontClaw;
    private Lift lift;
    private DriverCentricDrive driverCentricDrive;
    private Rotator rotator;
    private Wrist wrist;
    private Color_Sensor colorSensor;

    private final String ALLIANCE_COLOR = "Blue";

    @Override
    public void init() {
        driverCentricDrive = new DriverCentricDrive(this);
        backClaw = new BackClaw(this);
        deliveryBucket = new DeliveryBucket(this);
        frontClaw = new FrontClaw(this);
        lift = new Lift(this);
        rotator = new Rotator(this);
        wrist = new Wrist(this);
        colorSensor = new Color_Sensor(this);

    }

    @Override
    public void loop() {
        //TODO: reduce speed if lift if up or right

        driverCentricDrive.driverCentric();
        backClaw.teleOp();
        deliveryBucket.teleOp();
        frontClaw.teleOp();
        frontClaw.addTelemetry(this);
        lift.teleop();
        rotator.teleOp();
        wrist.teleOp();
        colorSensor.teleOp(ALLIANCE_COLOR);
        colorSensor.addTelemetry(this);


    }
}
