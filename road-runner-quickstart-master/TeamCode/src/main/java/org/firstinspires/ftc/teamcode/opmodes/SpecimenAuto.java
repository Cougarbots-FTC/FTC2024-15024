package org.firstinspires.ftc.teamcode.opmodes;

// RR-specific imports

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.*;

@Config
@Autonomous(name = "Specimen Auto V2", group = "auto")
public class SpecimenAuto extends LinearOpMode{

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


        }
    }
}
