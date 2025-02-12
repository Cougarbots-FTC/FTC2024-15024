package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Encoder Test", group = "auto")
public class EncoderTest extends LinearOpMode {
    ElapsedTime runtime = new ElapsedTime();
    static final double     COUNTS_PER_MOTOR_REV    = 28 ;    // gear ratio * tick per = 20 * 28 = 560
    static final double     DRIVE_GEAR_REDUCTION    = 20 ;     //  External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 1;
    private DcMotor driveLeftBack = null;
    @Override
    public void runOpMode(){
        driveLeftBack = hardwareMap.get(DcMotor.class, "driveLeftBack"); //e 0

        driveLeftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        driveLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Starting at",  "%7d",
                driveLeftBack.getCurrentPosition());
        telemetry.update();

        waitForStart();
        encoderDrive(DRIVE_SPEED, 40, 40, 5.0);

    }
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {

            newLeftTarget = driveLeftBack.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            //newRightTarget = rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            driveLeftBack.setTargetPosition(newLeftTarget);
            //rightDrive.setTargetPosition(newRightTarget);

            driveLeftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            driveLeftBack.setPower(Math.abs(speed));
            //rightDrive.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (driveLeftBack.isBusy())) { //&& rightDrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to", " %7d ", newLeftTarget);
                telemetry.addData("Currently at", " at %7d ",
                        driveLeftBack.getCurrentPosition());//, rightDrive.getCurrentPosition());
                telemetry.update();
            }

            driveLeftBack.setPower(0);
            //rightDrive.setPower(0);

            driveLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }


}