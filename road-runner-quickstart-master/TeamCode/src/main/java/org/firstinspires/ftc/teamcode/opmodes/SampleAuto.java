package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.ClawRotator;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.LiftRotator;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;

@Config
@Autonomous(name = "Sample Auto", group = "auto")
public class SampleAuto extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-12, 61.25, Math.toRadians(90));
        Pose2d WallIntake = new Pose2d(-24, 58.5, Math.toRadians(90)); //TODO: check this
        Pose2d Score1 = new Pose2d(0, 33, Math.toRadians(90));
        Pose2d Score2 = new Pose2d(0.5, 36, Math.toRadians(90));
        Pose2d Score3 = new Pose2d(-1, 27.75, Math.toRadians(90));
        Pose2d Score4 = new Pose2d(-4.5, 27.75, Math.toRadians(90));
        Pose2d Score5 = new Pose2d(-6.25, 27.75, Math.toRadians(90));
        Pose2d targetPosition = new Pose2d(5, 36, Math.toRadians(90)); // Target position (5, 36)

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Claw claw = new Claw(this);
        ClawRotator clawRotator = new ClawRotator(this);
        LiftRotator liftRotator = new LiftRotator(this);
        Lift lift = new Lift(this);

        Action deliverPreload = drive.actionBuilder(new Pose2d(-12,61.25,Math.toRadians(90)))
                .strafeTo(new Vector2d(0, 35.5))
                //.turn(Math.toRadians(270))
                //.splineTo(new Vector2d(0,35.5), Math.toRadians(90))
                .build();

        Action pushSamplesToObservation = drive.actionBuilder(Score1)
                .splineToLinearHeading(new Pose2d(-34.5, 40, Math.toRadians(90)), Math.toRadians(270))
                .strafeTo(new Vector2d(-34.5, 8))
                .strafeTo(new Vector2d(-44, 8)) //move behind first sample to push
                .strafeTo(new Vector2d(-44, 52.5))
                .strafeTo(new Vector2d(-44, 10))
                .strafeTo(new Vector2d(-55, 10)) // move behind the second sample to push
                .strafeTo(new Vector2d(-55, 52.5))
                .strafeTo(new Vector2d(-36, 52.5))
                .turn(Math.toRadians(-180))
                .strafeTo(new Vector2d(-36, 58.75)) // move to pick up first specimen
                .build();

        while (!opModeIsActive() && !isStopRequested()) {
            claw.setClawClosed(this);
            lift.resetLift();
            liftRotator.setLiftBack();
        }

        waitForStart();

        //////////////////////////// START HERE ////////////////////////////////////////////////
        if (opModeIsActive()) {
            // Preload
            Actions.runBlocking(
                    new SequentialAction(
                            deliverPreload
                    )
            );

            lift.moveHighRung();

            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(0, 35.5, Math.toRadians(90)))
                            .strafeTo(new Vector2d(0, 33))
                            .build()
            );
            lift.setPower(-1);
            lift.resetLift();
            sleep(600);
            claw.setClawOpen(this);
            Actions.runBlocking(
                    new SequentialAction(
                            pushSamplesToObservation
                    )
            );

            lift.moveToWall();
            claw.setClawClosed(this);
            lift.setPower(1);
            sleep(200); // maybe ??? small lift
            lift.Stop();

            // Move to the target position (5, 36)
            Actions.runBlocking(
                    drive.actionBuilder(Score2) // Starting from Score2 position
                            .splineToLinearHeading(targetPosition, Math.toRadians(90)) // Move to (5, 36)
                            .build()
            );

            // Additional code continues here...
            lift.moveHighRung();
            Actions.runBlocking(
                    drive.actionBuilder(Score2)
                            .strafeTo(new Vector2d(0.5, 32)) //second score
                            .build()
            );
            lift.resetLift();
            sleep(50);
            claw.setClawOpen(this);

            //TODO: keep going
        }
    }

}
