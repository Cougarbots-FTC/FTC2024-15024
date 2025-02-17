package org.firstinspires.ftc.teamcode.opmodes;

// RR-specific imports
import android.transition.Slide;

import com.acmerobotics.dashboard.config.Config;

// Non-RR imports
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.*;

@Config
@Autonomous(name = "Specimen Auto", group = "auto")
public class SpecimenAuto extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {
        //TODO should start be all 0s?
        //Pose2d beginPose = new Pose2d(0, 0, Math.toRadians(90));
        Pose2d beginPose = new Pose2d(-12, 62.25, Math.toRadians(90));
        Pose2d WallIntake = new Pose2d(-37, 48, Math.toRadians(90));
        Pose2d Score1 = new Pose2d(1.5, 27.75, Math.toRadians(90));
        //Pose2d Score2 = new Pose2d(.5, 27.75, Math.toRadians(90));
        //Pose2d Score3 = new Pose2d(-1, 27.75, Math.toRadians(90));
        //Pose2d Score4 = new Pose2d(-4.5, 27.75, Math.toRadians(90));
        //Pose2d Score5 = new Pose2d(-6.25, 27.75, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        BackClaw claw = new BackClaw(this);
        Lift lift = new Lift(this);
        Rotator rotator = new Rotator(this);

        while (!opModeIsActive() && !isStopRequested()) {
            claw.setClawClosed();
            rotator.setRotatorBack();
        }

        waitForStart();

        if (opModeIsActive()) {
            // Preload
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .strafeTo(new Vector2d(1.5,27.75))
                            .build()
            );
            lift.HighRung();
            sleep(300);
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(1.5, -20, Math.toRadians(90)))
                            .lineToY(-25)
                            .build()
            );
            lift.Reset();
            claw.setClawOpen();
            sleep(250);

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
            rotator.moveToHorizontal();
            sleep(200);
            arm.ArmIntake();
            lift.moveToWall();
            sleep(750);
            claw.setClawClosed();
            sleep(200);
            arm.ArmRest();

            Actions.runBlocking(
                    drive.actionBuilder(WallIntake)
                            .strafeTo(new Vector2d(.5,27.75))
                            .build());
            rotator.FullRotate();
            lift.HighRung();
            sleep(200);
            arm.ArmScore();
            sleep(200);
            claw.setClawOpen();

            Actions.runBlocking(drive.actionBuilder(Score2)
                    .strafeTo(new Vector2d(-37,48))
                    .build());
            rotator.moveToHorizontal();
            sleep(200);
            arm.ArmIntake();
            lift.moveToWall();
            sleep(750);
            claw.setClawClosed();
            sleep(200);
            arm.ArmRest();

            Actions.runBlocking(
                    drive.actionBuilder(WallIntake)
                            .strafeTo(new Vector2d(-1,27.75))
                            .build());
            rotator.FullRotate();
            lift.HighRung();
            sleep(200);
            arm.ArmScore();
            sleep(200);
            claw.setClawOpen();
            sleep(200);

            Actions.runBlocking(drive.actionBuilder(Score3)
                    .strafeTo(new Vector2d(-37,48.25))
                    .build());
            rotator.moveToHorizontal();
            sleep(200);
            arm.ArmIntake();
            lift.moveToWall();
            sleep(750);
            claw.setClawClosed();
            sleep(200);
            arm.ArmRest();

            Actions.runBlocking(
                    drive.actionBuilder(WallIntake)
                            .strafeTo(new Vector2d(-4.5,27.75))
                            .build());
            rotator.FullRotate();
            lift.HighRung();
            sleep(200);
            arm.ArmScore();
            sleep(200);
            claw.setClawOpen();

            Actions.runBlocking(drive.actionBuilder(Score4)
                    .strafeTo(new Vector2d(-37,48))
                    .build());
            rotator.moveToHorizontal();
            sleep(200);
            arm.ArmIntake();
            lift.moveToWall();
            sleep(750);
            claw.setClawClosed();
            sleep(200);
            arm.ArmRest();

            Actions.runBlocking(
                    drive.actionBuilder(WallIntake)
                            .strafeTo(new Vector2d(-6.25,28))
                            .build());
            rotator.FullRotate();
            lift.HighRung();
            sleep(200);
            arm.ArmScore();
            sleep(200);
            claw.setClawOpen();
            sleep(200);
            rotator.moveToHorizontal();
            lift.Reset();

            Actions.runBlocking(drive.actionBuilder(Score5)
                    .strafeTo(new Vector2d(-37,48))
                    .build());

             */
        }
    }
}
