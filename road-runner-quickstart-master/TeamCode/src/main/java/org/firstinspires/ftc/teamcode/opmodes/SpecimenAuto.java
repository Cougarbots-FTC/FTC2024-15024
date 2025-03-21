package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.*;

@Config
@Autonomous(name = "Specimen Auto", group = "auto")
public class SpecimenAuto extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-12, 61.25, Math.toRadians(90));
        Pose2d WallIntake = new Pose2d(-35, 44.5, Math.toRadians(90)); //TODO: check this
        Pose2d Score1 = new Pose2d(0, 34, Math.toRadians(90));
        Pose2d Score2 = new Pose2d(.5, 27.75, Math.toRadians(90));
        Pose2d Score3 = new Pose2d(-1, 27.75, Math.toRadians(90));
        Pose2d Score4 = new Pose2d(-4.5, 27.75, Math.toRadians(90));
        Pose2d Score5 = new Pose2d(-6.25, 27.75, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Claw claw = new Claw(this);
        //ClawRotator clawRotator = new ClawRotator(this);
        //LiftRotator liftRotator = new LiftRotator(this);
        Lift lift = new Lift(this);


        while (!opModeIsActive() && !isStopRequested()) {
            claw.setClawClosed(this);
            lift.resetLift();
        }

        waitForStart();

        if (opModeIsActive()) {
            // Preload
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .strafeTo(new Vector2d(0, 35.5))
                            .build()
            );

            lift.moveHighRung();

            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(0, 35.5, Math.toRadians(90)))
                            .strafeTo(new Vector2d(0, 34)) //TODO: Check the y value
                            .build()
            );
            lift.resetLift();
            claw.setClawOpen(this);

            Actions.runBlocking(
                    drive.actionBuilder(Score1)
                            .splineToLinearHeading(new Pose2d(-34.5, 40, Math.toRadians(90)), Math.toRadians(270))
                            .strafeTo(new Vector2d(-34.5, 8))
                            .strafeTo(new Vector2d(-43, 8)) //move behind first sample to push
                            .strafeTo(new Vector2d(-43, 52.5))
                            .strafeTo(new Vector2d(-43, 10))
                            .strafeTo(new Vector2d(-55, 10)) // move behind the second sample to push
                            .strafeTo(new Vector2d(-55, 52.5))
                            //.strafeTo(new Vector2d(-55, 10))
                            //.strafeTo(new Vector2d(-60, 10))
                            //.strafeTo(new Vector2d(-60, 52.5))
                            .strafeTo(new Vector2d(-45, 44.5)) // move to pick up first specimen
                            .splineToSplineHeading(new Pose2d(-35, 44.5, Math.toRadians(270)), Math.toRadians(90)) //maybe???
                            .build());

            lift.moveToWall();
            claw.setClawClosed(this);
            lift.setPower(1);
            sleep(200); // maybe ???
            lift.Stop();

            //move back to submersible
            Actions.runBlocking(
                    drive.actionBuilder(WallIntake)
                            .splineToLinearHeading(Score2, Math.toRadians(270)) //I have no idea...😕
                            .build()
            );
            lift.moveHighRung();
            lift.resetLift();
            claw.setClawOpen(this);

            //TODO: keep going

        }
    }
}
