package org.firstinspires.ftc.teamcode.opmodes;

// RR-specific imports

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
        Pose2d WallIntake = new Pose2d(-37, 48, Math.toRadians(90));
        Pose2d Score1 = new Pose2d(1.5, 35.5, Math.toRadians(90));
        Pose2d Score2 = new Pose2d(.5, 27.75, Math.toRadians(90));
        Pose2d Score3 = new Pose2d(-1, 27.75, Math.toRadians(90));
        Pose2d Score4 = new Pose2d(-4.5, 27.75, Math.toRadians(90));
        Pose2d Score5 = new Pose2d(-6.25, 27.75, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Claw claw = new Claw(this);
        ClawRotator clawRotator = new ClawRotator(this);
        LiftRotator liftRotator = new LiftRotator(this);
        Lift lift = new Lift(this);


        while (!opModeIsActive() && !isStopRequested()) {
            claw.setClawClosed();
            //arm.ArmRest();
            //wrist.setScorePosition();
            //rotator.FullRotate();
        }

        waitForStart();

        if (opModeIsActive()) {
            // Preload
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .strafeTo(new Vector2d(1.5, 35.5))
                            .build()
            );

            //lift.moveHighRung();

            //lift.LeftLift.setTargetPosition(2080);
            //lift.RightLift.setTargetPosition(2080);
            //lift.LeftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //lift.RightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //lift.LeftLift.setPower(1);
            //lift.RightLift.setPower(1);
            //sleep(1000);
            //lift.LeftLift.setPower(0.1);
            //lift.RightLift.setPower(0.1);
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .strafeTo(new Vector2d(1.5, 34))
                            .build()
            );
         /*   arm.ArmScore();
            sleep(200);
            claw.setClawOpen();
            sleep(200);
            slide.Reset();
            */

            Actions.runBlocking(
                    drive.actionBuilder(Score1)
                            .splineToLinearHeading(new Pose2d(-34.5, 40, Math.toRadians(90)), Math.toRadians(270))
                            .strafeTo(new Vector2d(-34.5, 8))
                            .strafeTo(new Vector2d(-43, 8)) //move behind first sample to push
                            .strafeTo(new Vector2d(-43, 52.5))
                            .strafeTo(new Vector2d(-43, 10))
                            .strafeTo(new Vector2d(-55, 10))
                            .strafeTo(new Vector2d(-55, 52.5))
                            //.strafeTo(new Vector2d(-55, 10))
                            //.strafeTo(new Vector2d(-60, 10))
                            //.strafeTo(new Vector2d(-60, 52.5))
                            .strafeTo(new Vector2d(-45, 44.5))
                            .build());
            /*
            rotator.moveToHorizontal();
            sleep(200);
            arm.ArmIntake();
            slide.moveToWall();
            sleep(750);
            claw.setClawClosed();
            sleep(200);
            arm.ArmRest();

             *//*

            Actions.runBlocking(
                    drive.actionBuilder(WallIntake)
                            .strafeTo(new Vector2d(.5, 27.75))
                            .build());
            *//*
            rotator.FullRotate();
            slide.HighRung();
            sleep(200);
            arm.ArmScore();
            sleep(200);
            claw.setClawOpen();

             *//*

            Actions.runBlocking(drive.actionBuilder(Score2)
                    .strafeTo(new Vector2d(-37, 48))
                    .build());
            *//*
            rotator.moveToHorizontal();
            sleep(200);
            arm.ArmIntake();
            slide.moveToWall();
            sleep(750);
            claw.setClawClosed();
            sleep(200);
            arm.ArmRest();

             *//*

            Actions.runBlocking(
                    drive.actionBuilder(WallIntake)
                            .strafeTo(new Vector2d(-1, 27.75))
                            .build());
            *//*
            rotator.FullRotate();
            slide.HighRung();
            sleep(200);
            arm.ArmScore();
            sleep(200);
            claw.setClawOpen();
            sleep(200);

             *//*

            Actions.runBlocking(drive.actionBuilder(Score3)
                    .strafeTo(new Vector2d(-37, 48.25))
                    .build());
            *//*
            rotator.moveToHorizontal();
            sleep(200);
            arm.ArmIntake();
            slide.moveToWall();
            sleep(750);
            claw.setClawClosed();
            sleep(200);
            arm.ArmRest();

             *//*

            Actions.runBlocking(
                    drive.actionBuilder(WallIntake)
                            .strafeTo(new Vector2d(-4.5, 27.75))
                            .build());
            *//*
            rotator.FullRotate();
            slide.HighRung();
            sleep(200);
            arm.ArmScore();
            sleep(200);
            claw.setClawOpen();

             *//*

            Actions.runBlocking(drive.actionBuilder(Score4)
                    .strafeTo(new Vector2d(-37, 48))
                    .build());
            *//*
            rotator.moveToHorizontal();
            sleep(200);
            arm.ArmIntake();
            slide.moveToWall();
            sleep(750);
            claw.setClawClosed();
            sleep(200);
            arm.ArmRest();

             *//*

            Actions.runBlocking(
                    drive.actionBuilder(WallIntake)
                            .strafeTo(new Vector2d(-6.25, 28))
                            .build());
            *//*
            rotator.FullRotate();
            slide.HighRung();
            sleep(200);
            arm.ArmScore();
            sleep(200);
            claw.setClawOpen();
            sleep(200);
            rotator.moveToHorizontal();
            slide.Reset();

             *//*

            Actions.runBlocking(drive.actionBuilder(Score5)
                    .strafeTo(new Vector2d(-37, 48))
                    .build());*/
        }
    }
}
