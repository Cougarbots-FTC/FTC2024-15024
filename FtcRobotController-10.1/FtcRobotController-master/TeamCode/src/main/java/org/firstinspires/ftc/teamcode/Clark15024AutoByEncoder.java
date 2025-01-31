package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Clark15024n Auto By Encoder", group = "auto")
public class Clark15024AutoByEncoder extends LinearOpMode {

    Clark15024HWMap robot = new Clark15024HWMap();
    ElapsedTime runtime = new ElapsedTime();

    Clark15024Auto auto = new Clark15024Auto();

    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    ///For drive train motors
    static final double     COUNTS_PER_MOTOR_REV    = 28 ;    // gear ratio * tick per = 20 * 28 = 560
    static final double     DRIVE_GEAR_REDUCTION    = 20 ;     //  External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 1;
    static final double     TURN_SPEED              = 0.5;

    static final double LIFT_GEAR_RATIO = 13.7;
    static final double LIFT_COUNTS_PER_INCH = (438 * LIFT_GEAR_RATIO) / (Math.PI * 2) * 2; //* 28;

    @Override
    public void runOpMode(){
        robot.Map(hardwareMap);
        telemetry.addData("Time", runtime.time());
        telemetry.addData("Starting at",  "%7d :%7d",
                robot.driveLeftFront.getCurrentPosition(),
                robot.driveRightFront.getCurrentPosition());
        telemetry.update();

        //TODO is this needed?
        robot.LiftA.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LiftB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.driveRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.driveLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.driveRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.driveLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.LiftA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LiftB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();

        // Step through each leg of the path,
        robot.claw.setPosition(0);
        encoderDrive(DRIVE_SPEED,  20,  20, 5.0);       //Move toward submersible - Total distance to submersible is 25.5

        deliver_specimen(4.21);

        //TODO: make this a function - move to get new piece
        encoderDrive(DRIVE_SPEED, -10, -10, 5.0);       //reverse
        //turn to left
        //move back
        //turn left
        //move forward
        //lift
        //close claw
        //TODO: make this a function - move to the submersible
        // reverse
        //turn right
        //forward
        //turn right
        //forward
        //deliver_specimen(10);
        //encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout

    }

    /**
     * This function lifts the arm,
     * moves forward to the submersible,
     * lowers the arm
     * opens the claw
     * @param distance_to_submersible - how far to go forward once the lift goes up
     */
    public void deliver_specimen(double distance_to_submersible) {
        encoderLift(DRIVE_SPEED*.1, -0.01, 0.4);                          // Lift the arm
        sleep(600);   // optional pause after each move.

        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED,  distance_to_submersible,  distance_to_submersible, 5.0);     // Move the rest of the way to the submersible
        encoderLift(DRIVE_SPEED*.3, 0.01, 0.2);                         // Drop arm
        robot.claw.setPosition(0.5);                                                 //open claw
    }

    public void encoderLift(double speed,
                            double inches,
                            double timeoutS) {

        int newLeftTarget;
        int newRightTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.LiftA.getCurrentPosition() + (int) (inches * LIFT_COUNTS_PER_INCH);
            newRightTarget = robot.LiftB.getCurrentPosition() + (int) (inches * LIFT_COUNTS_PER_INCH);

            robot.LiftA.setTargetPosition(newLeftTarget);
            robot.LiftB.setTargetPosition(newRightTarget);
            robot.LiftA.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.LiftB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.LiftA.setPower(Math.abs(speed));
            robot.LiftB.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.LiftA.isBusy() && robot.LiftB.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Lift running to", " %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Currently at", " at %7d :%7d",
                        robot.LiftA.getCurrentPosition(), robot.LiftB.getCurrentPosition());
                telemetry.update();

            }
        }
        sleep(600);   // optional pause after each move.

    }

    /**
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the OpMode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.driveLeftFront.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.driveRightFront.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            robot.driveLeftFront.setTargetPosition(newLeftTarget);
            robot.driveRightFront.setTargetPosition(newRightTarget);
            robot.driveLeftBack.setTargetPosition(newLeftTarget-11);
            robot.driveRightBack.setTargetPosition(newRightTarget-11);

            robot.driveLeftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.driveRightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.driveLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.driveRightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.driveLeftFront.setPower(Math.abs(speed));
            robot.driveRightFront.setPower(Math.abs(speed));
            robot.driveLeftBack.setPower(Math.abs(speed));
            robot.driveRightBack.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.driveLeftFront.isBusy() && robot.driveRightFront.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to", " %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Currently at", " at %7d :%7d",
                        robot.driveLeftFront.getCurrentPosition(), robot.driveRightFront.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.driveLeftFront.setPower(0);
            robot.driveRightFront.setPower(0);
            robot.driveLeftBack.setPower(0);
            robot.driveRightBack.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.driveLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.driveRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.driveLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.driveRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }

}
