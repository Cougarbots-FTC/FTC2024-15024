package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    @Override
    public void init() {
        driverCentricDrive = new DriverCentricDrive(this);
        backClaw = new BackClaw(this);
        deliveryBucket = new DeliveryBucket(this);
        frontClaw = new FrontClaw(this);
        lift = new Lift(this);
        rotator = new Rotator(this);
        wrist = new Wrist(this);

    }

    @Override
    public void loop() {
        //TODO: reduce speed if lift if up or right

        driverCentricDrive.driverCentric();
        backClaw.teleOp();
        deliveryBucket.teleOp();
        frontClaw.teleOp();
        lift.teleop();
        lift.addTelemetry(this);
        rotator.teleOp();
        wrist.teleOp();
    }
}
