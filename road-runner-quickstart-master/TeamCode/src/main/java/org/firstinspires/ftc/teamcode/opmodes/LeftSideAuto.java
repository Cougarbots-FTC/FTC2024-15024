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
@Autonomous(name = "Left Side Auto", group = "auto")
public class LeftSideAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d beginPose = new Pose2d(12, 61.25, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Claw claw = new Claw(this);
        ClawRotator clawRotator = new ClawRotator(this);
        LiftRotator liftRotator = new LiftRotator(this);
        Lift lift = new Lift(this);


        while (!opModeIsActive() && !isStopRequested()) {
            claw.setClawClosed();
            lift.resetLift();
            liftRotator.setLiftBack();
        }

        Action park = drive.actionBuilder(beginPose)
                .strafeTo(new Vector2d(24, 50))
                //.turn(Math.toRadians(270))
                .splineTo(new Vector2d(24, 0), Math.toRadians(0))
                .build();


        waitForStart();

        //////////////////////////// START HERE ////////////////////////////////////////////////
        if (opModeIsActive()) {
            Actions.runBlocking(
                    new SequentialAction(
                            park
                    )
            );

            clawRotator.setRotatorForward();

        }


    }
}