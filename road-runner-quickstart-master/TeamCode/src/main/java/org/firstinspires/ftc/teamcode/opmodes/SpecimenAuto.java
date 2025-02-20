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

        //Pose2d beginPose = new Pose2d(12, 60, Math.toRadians(90));

        Pose2d beginPose = new Pose2d(0, 0, Math.toRadians(90));
        Pose2d WallIntake = new Pose2d(-36, -10, Math.toRadians(180)); // back claw facing wall
        Pose2d Score1 = new Pose2d(1.5, -10, Math.toRadians(90));
        Pose2d Score2 = new Pose2d(3.5, -25.5, Math.toRadians(180)); // done with specimen 2
        Pose2d Score3 = new Pose2d(3.5, 25.5, Math.toRadians(90)); // ready to score specimen 3
        Pose2d Score4 = new Pose2d(5, 25.5, Math.toRadians(90)); // ready to score specimen 4
        Pose2d Score5 = new Pose2d(7, 27.75, Math.toRadians(90));

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
            /// Preload & move to submersible to score 1st specimen
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .strafeTo(new Vector2d(1.5,-25.5)) //27.75))
                            .build()
            );
            lift.HighRung();
            sleep(300);

            ///move up to score
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(1.5, -25.5, Math.toRadians(90)))
                            .lineToY(-29)
                            .build()
            );
            lift.ResetAndOpenClaw();
            sleep(250); // can remove if action is moving smoothly

            ///move back
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(1.5, -29, Math.toRadians(90)))
                            .lineToY(-20)
                            .build()
            );
            lift.smallReset();

            /// Finished scoring specimen 1
            /// Push the three samples into the observation zone
            Actions.runBlocking(
                    drive.actionBuilder(Score1)     //1.5, -29, 90
                            //TODO: check degrees should it be 180?

                            .splineToLinearHeading(new Pose2d(-36, -60, Math.toRadians(180)), Math.toRadians(270))

                            //.lineToY(-60)
                            //.lineToX(-48) //move back to second sample
                            .strafeTo(new Vector2d(-48,10)) // push second sample into observation zone
                            .strafeTo(new Vector2d(-60, -53)) // move back to third sample
                            .lineToY(-10) // push third sample into observation zone
                            .strafeTo(new Vector2d(-36,-10)) // move back to get the second specimen - not necessary??
                            .build()
            );

            /// move to wall to pick up 2nd specimen
            Actions.runBlocking(
                    drive.actionBuilder(WallIntake)
                            .strafeTo(new Vector2d(-36, -4))
                            .build()
            );
            /// pick up specimen off the wall
            claw.setClawClosed();
            lift.liftSpecimen();
            ///move back from wall
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(-36, -4, Math.toRadians(180)))
                            .strafeTo(new Vector2d(-36, -10))
                            .build()
            );
            lift.smallReset();
            sleep(200);

            /// move to submersible to score 2nd specimen
            Actions.runBlocking(
                    drive.actionBuilder(WallIntake) //-36, -10, 180
                        .splineToLinearHeading(new Pose2d(3.5, -25.5, Math.toRadians(90)), Math.toRadians(180))
                        .build()
            );
            lift.HighRung();
            sleep(300);

            /// move forward to score
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(3.5, -25.5, Math.toRadians(90)))
                            .lineToY(-29)
                            .build()
            );
            lift.ResetAndOpenClaw();
            sleep(250); // can remove if action is moving smoothly

            ///move back
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(3.5, -29, Math.toRadians(90)))
                            .lineToY(-25.5)
                            .build()
            );
            lift.smallReset();

            /// Go Back to wall and pick up 3rd specimen
            Actions.runBlocking(
                    drive.actionBuilder(Score3) //3.5, -25.5, 90
                            .splineToLinearHeading(new Pose2d(-36, -4, Math.toRadians(90)), Math.toRadians(180))
                            .build()

            );
            /// pick up 3rd specimen off the wall
            claw.setClawClosed();
            lift.liftSpecimen();
            ///move back from wall
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(-36, -4, Math.toRadians(180)))
                            .strafeTo(new Vector2d(-36, -10))
                            .build()
            );
            lift.smallReset();
            sleep(250); // can remove if action is moving smoothly

            /// move to submersible to Score 3rd specimen
            Actions.runBlocking(
                    drive.actionBuilder(Score2) //-36, -10, 180
                            .splineToLinearHeading(new Pose2d(5, -25.5, Math.toRadians(90)), Math.toRadians(180))
                            .build()
            );
            lift.HighRung();
            sleep(250);

            /// move forward
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(5, -25.5, Math.toRadians(90)))
                            .lineToY(-29)
                            .build()
            );
            lift.smallReset();
            sleep(250); // can remove if action is moving smoothly

            ///move back
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(5, -29, Math.toRadians(90)))
                            .lineToY(-25.5)
                            .build()
            );

            /// Go Back to wall and pick up 4th specimen
            Actions.runBlocking(
                    drive.actionBuilder(Score4) //5, -25.5, 90
                            .splineToLinearHeading(new Pose2d(-36, -4, Math.toRadians(90)), Math.toRadians(180))
                            .build()
            );

            /// pick up 4th specimen off the wall
            claw.setClawClosed();
            lift.liftSpecimen();
            ///move back from wall
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(-36, -4, Math.toRadians(180)))
                            .strafeTo(new Vector2d(-36, -10))
                            .build()
            );
            lift.smallReset();
            sleep(250); // can remove if action is moving smoothly

            /// move to submersible to Score 4th specimen
            Actions.runBlocking(
                    drive.actionBuilder(WallIntake) //-36, -10, 180
                            .splineToLinearHeading(new Pose2d(7, -25.5, Math.toRadians(90)), Math.toRadians(180))
                            .build()
            );
            lift.HighRung();
            sleep(250);

            /// move forward
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(7, -25.5, Math.toRadians(90)))
                            .lineToY(-29)
                            .build()
            );
            lift.smallReset();
            sleep(250); // can remove if action is moving smoothly

            ///move back
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(7, -29, Math.toRadians(90)))
                            .lineToY(-25.5)
                            .build()
            );

            /// Go Back to wall and pick up 5th specimen
            Actions.runBlocking(
                    drive.actionBuilder(Score5) //7, -25.5, 90
                            .splineToLinearHeading(new Pose2d(-36, -4, Math.toRadians(90)), Math.toRadians(180))
                            .build()
            );

            /// pick up 5th specimen off the wall
            claw.setClawClosed();
            lift.liftSpecimen();
            ///move back from wall
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(-36, -4, Math.toRadians(180)))
                            .strafeTo(new Vector2d(-36, -10))
                            .build()
            );
            lift.smallReset();
            sleep(250); // can remove if action is moving smoothly

            /// move to submersible to Score 5th specimen
            Actions.runBlocking(
                    drive.actionBuilder(WallIntake) //-36, -10, 180
                            .splineToLinearHeading(new Pose2d(9, -25.5, Math.toRadians(90)), Math.toRadians(180))
                            .build()
            );
            lift.HighRung();
            sleep(250);

            /// move forward
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(9, -25.5, Math.toRadians(90)))
                            .lineToY(-29)
                            .build()
            );
            lift.smallReset();
            sleep(250); // can remove if action is moving smoothly

            ///move back
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(9, -29, Math.toRadians(90)))
                            .lineToY(-25.5)
                            .build()
            );

            ///TODO: Move toward observation zone and drop arm for parking point???


        }
    }
}
