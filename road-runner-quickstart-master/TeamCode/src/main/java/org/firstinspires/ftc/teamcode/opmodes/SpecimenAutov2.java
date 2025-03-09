package org.firstinspires.ftc.teamcode.opmodes;

// RR-specific imports

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.*;

import java.util.Vector;

@Config
@Autonomous(name = "Specimen Auto V2", group = "auto")
public class SpecimenAutov2 extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-12, 62.25, Math.toRadians(90));
        Pose2d WallIntake = new Pose2d(-37, 48, Math.toRadians(90));
        Pose2d Score1 = new Pose2d(1.5, 27.75, Math.toRadians(90));
        Pose2d Score2 = new Pose2d(.5, 27.75, Math.toRadians(90));
        Pose2d Score3 = new Pose2d(-1, 27.75, Math.toRadians(90));
        Pose2d Score4 = new Pose2d(-4.5, 27.75, Math.toRadians(90));
        Pose2d Score5 = new Pose2d(-6.25, 27.75, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        BackClaw claw = new BackClaw(this);

        while (!opModeIsActive() && !isStopRequested()) {

        }

        waitForStart();

        if (opModeIsActive()) {
            // Preload
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .strafeTo(new Vector2d(1.5,27.75))
                            .build()
            );


            Actions.runBlocking(
                    drive.actionBuilder(Score1)
                            .splineToLinearHeading(new Pose2d(-38,40,Math.toRadians(90)),Math.toRadians(270))
                            .strafeTo(new Vector2d(-38,10))
                            .strafeTo(new Vector2d(-52,10))
                            .strafeTo(new Vector2d(-52,56))
                            .strafeTo(new Vector2d(-52,10))
                            .strafeTo(new Vector2d(-58,10))
                            .strafeTo(new Vector2d(-58,56))
                            .strafeTo(new Vector2d(-58,10))
                            .strafeTo(new Vector2d(-66,10))
                            .strafeTo(new Vector2d(-66,56))
                            .strafeTo(new Vector2d(-37,47.75))
                            .build());

/*
        Pose2d beginPose = new Pose2d(-12, 58, Math.toRadians(90));
        Pose2d WallIntake = new Pose2d(-47, 55.5, Math.toRadians(90));
        Vector2d WallIntakeV = new Vector2d(-47, 55.5);
        Pose2d Score = new Pose2d(-1.5, 33, Math.toRadians(90));
        Vector2d ScoreV = new Vector2d(-1.5, 33);

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        BackClaw claw = new BackClaw(this);
        Lift lift = new Lift(this);
        Rotator rotator = new Rotator(this);

        while (!opModeIsActive() && !isStopRequested()) {
            claw.setClawClosed();
            //rotator.setRotatorBack();
        }

        waitForStart();

        if (opModeIsActive()) {
            /// Preload & move to submersible to score 1st specimen

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            //.splineTo(ScoreV, Math.toRadians(90))
                            .strafeTo(new Vector2d(-1.5, 33))
                            .build()
            );
            lift.HighRung();
            sleep(600);

            ///move up to score
            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .lineToY(29.75)
                            .build()
            );
            lift.ResetAndOpenClaw();
            sleep(250); // can remove if action is moving smoothly

            ///move back
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(-1.5, 29.75, Math.toRadians(90)))
                            //TODO: can this value be smaller to speed up?
                            .lineToY(33)
                            .build()
            );
            lift.smallReset();
            //claw.setClawOpen();
            /// Push two of the samples into the observation zone
           Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(-1.5, 33, Math.toRadians(90)))
                            .splineToSplineHeading(new Pose2d(-37,30,Math.toRadians(270)),Math.toRadians(270))
                            .lineToY(10)
                            .strafeTo(new Vector2d(-48.5,10))
                            .strafeTo(new Vector2d(-48.5, 45))
                            .strafeTo(new Vector2d(-48.5,5))
                            .strafeTo(new Vector2d(-57,5))
                            .strafeTo(new Vector2d(-57,45))
                            //.strafeTo(new Vector2d(-60.5,10))
                            //.strafeTo(new Vector2d(-60.5,56))
                            //.strafeTo(new Vector2d(-47, 50))
                            //.strafeTo(WallIntakeV)
                            .build()
            );

/*
            ///Pick up specimen to score
/*            claw.setClawClosed();
            sleep(600);
            lift.liftSpecimen();

            Actions.runBlocking(
                    drive.actionBuilder(WallIntake) //new Pose2d(-47, 56.5, Math.toRadians(270)))
                            .splineToSplineHeading(Score, Math.toRadians(90))
                            .build()
            );
            lift.HighRung();
            //sleep(200);

            ///move up to score
            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .splineToSplineHeading(new Pose2d(1.5, 29.5, Math.toRadians(90)), Math.toRadians(0))
                            //.strafeTo(new Vector2d(1.5,29.5))
                            .build()
            );
            lift.ResetAndOpenClaw();


            Actions.runBlocking(
                    drive.actionBuilder(Score)
                            .splineToSplineHeading(WallIntake, Math.toRadians(270))
                            .build()
            );


*/
        }
    }
}
