package org.firstinspires.ftc.teamcode;
//Imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


//Used to name this specific TeleOP in the driver hub
@TeleOp(name = "Clark 15024 TeleOp - This One")
//Base TeleOp class which currently has the Hardware map Initialized and the running Op mode
//extends LinearOpMode - used as a parent class of this child class, meaning you can use all the functions from the parent class in this child class
public class Clark15024TeleOp extends LinearOpMode {
    //Initialized Hardware map instance variable assigned to "robot"
    Clark15024HWMap robot = new Clark15024HWMap();
    private PIDFAttempt2 pidfArm;
    Boolean gamepad2ALastPressed = false;
    Boolean gamepad2BLastPressed = false;
    Boolean gamepad2XLastPressed = false;
    Boolean gamepad2YLastPressed = false;

    //@Override  Used to rewrite the runOpMode function which is in the LinearOpMode class
    //runOpMode - runs when the button before the start button is pressed
    @Override
    public void runOpMode(){
        robot.Map(hardwareMap);

        //robot.ArmRotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Say", "Starting 15024 TeleOp using PIDF");
        telemetry.addData("Arm Position: ", robot.ArmRotator.getCurrentPosition());
        telemetry.update();

        //TODO check the new position of the arm
        pidfArm = new PIDFAttempt2(robot.ArmRotator,0.007,0,0.0005,0.12,0, 2100);

        //Waits for the button to start on the driver hub to be pressed
        waitForStart();

        //while loop starts once the start button is pressed
        while(opModeIsActive()){

            //Slow mode whenever you need to go slower to get precise blocks
            double slow = gamepad1.right_bumper ? 0.5 : 1.0;

            driveTrain(slow);
            lift(slow);
            armExtender(slow);
            armRotator(slow);
            backClawFunctionality();
            delivery();
            clawRotatorFunctionality();
            frontCclawFunctionality();

            // Telemetry for debugging
            telemetry.addData("Target Position", pidfArm.getSetpoint());
            telemetry.addData("Current Position", robot.ArmRotator.getCurrentPosition());
            telemetry.update();

        }
    }

    /**
     * This function controls the drive train of the robot using the right and left joy stick
     * The right joystick is for pivoting
     * The left joystick is for vertical and horizontal movement
     * @param slow - if the right bumper is depressed, the motor speed will reduce by half
     */
    public void driveTrain(double slow) {
        //Instances variables assigned to double or decimal values to the different gamepad
        //Set the vertical as a negative because of the different values needed for the right side as they are in reverse
        double vertical = gamepad1.left_stick_y;
        double horizontal = gamepad1.left_stick_x * 1.1; //  Multiply by 1.1 to negate imperfect strafing
        double pivot = gamepad1.right_stick_x;

        //Obtains values for each motor through the positions through values
        //from  left joystick which has up/down(vertical) and left/right values(horizontal), and right joystick which has left/right values(pivot)
        double denominator = Math.max(Math.abs(vertical) + Math.abs(horizontal) + Math.abs(pivot), 1);
        double rightFrontPower = (vertical + horizontal + pivot) / denominator;
        double rightBackPower = (vertical - horizontal + pivot) / denominator;
        double LeftFrontPower = (vertical - horizontal - pivot) / denominator;
        double LeftBackPower = (vertical + horizontal - pivot) / denominator;

        //Sets Power to the motors and changed the signed of the math in order to proportion the wheels right to move
        robot.driveRightFront.setPower(rightFrontPower * slow);
        robot.driveRightBack.setPower(rightBackPower * slow);
        robot.driveLeftBack.setPower(LeftBackPower * slow);
        robot.driveLeftFront.setPower(LeftFrontPower * slow);

    }

    ///  This function contains the code for the lift system. The motors work in tandem controlled
    ///  by the right and left trigger.
    ///  Left trigger moves the lift up.
    ///  Right trigger moves the lift down.
    ///
    /// @param slow - if the right bumper is depressed, the slow values is 0.5 to reduce the power
    ///             if not the values is 1 for full power
    public void lift(double slow) {
        //left_trigger - liftA and liftB up
        //right_trigger - liftA and liftB down
        double liftPowerUp = gamepad1.left_trigger * slow;
        double liftPowerDown = gamepad1.right_trigger * slow;
        if (gamepad1.left_trigger > 0.1) {
            robot.LiftA.setPower(liftPowerUp);
            robot.LiftB.setPower(liftPowerUp);
        } else if (gamepad1.right_trigger > 0.1) {
            robot.LiftA.setPower(-1 * liftPowerDown);
            robot.LiftB.setPower(-1 * liftPowerDown);
        } else {
            robot.LiftA.setPower(0);
            robot.LiftB.setPower(0);
        }
    }

    public void armExtender(double slow) {
        //ArmExtender on left y stick
        double armExtenderPower = -gamepad2.left_stick_y * slow;
        robot.ArmExtender.setPower(armExtenderPower);
    }

    //TODO fix the setpoint values for the pidf arm
    public void armRotator(double slow) {
        //ArmRotator on left x stick
        double armRotatorPower = gamepad2.left_stick_x * slow;
        //robot.ArmRotator.setPower(armRotatorPower);
        //if (armRotatorPower != 0) {
            robot.ArmRotator.setPower(armRotatorPower);
        /*} else {
            if (gamepad2.a) {
                pidfArm.setSetpoint(0); // Set encoder position to 1000
            } else if (gamepad2.b) {
                pidfArm.setSetpoint(-550); // Set encoder position to 2000
            }
            pidfArm.loop();
        }*/
    }
    public void clawRotatorFunctionality() {
        boolean aPressed = gamepad2.a;
        if (gamepad2.a && !gamepad2ALastPressed) {
            if (robot.clawRotate.getPosition() == 0) {
                robot.clawRotate.setPosition(0.5);
            } else {
                robot.clawRotate.setPosition(0);
            }
        }
        gamepad2ALastPressed = aPressed;
    }
    public void frontCclawFunctionality() {
        boolean bPressed = gamepad2.b;
        if (gamepad2.b && !gamepad2BLastPressed) {
            if (robot.frontClaw.getPosition() == 0) {
                robot.frontClaw.setPosition(0.5);
            } else {
                robot.frontClaw.setPosition(0);
            }
        }
        gamepad2BLastPressed = bPressed;
    }
    public void backClawFunctionality() {
        boolean yPressed = gamepad2.y;
        if (gamepad2.y && !gamepad2YLastPressed) {
            if (robot.backClaw.getPosition() == 0) {
                robot.backClaw.setPosition(0.5);
            } else {
                robot.backClaw.setPosition(0);
            }
        }
        gamepad2YLastPressed = yPressed;
    }

    //TODO: Check if the position values need to be updated
    public void delivery() {
        //delivery bucket on B - on press roll forward to deliver, on release roll back to start position
        if (gamepad1.b) {
            robot.bucketRotator.setPosition(robot.bucketRotator.getPosition()-0.1);
        } else {
            robot.bucketRotator.setPosition(0.4);
        }
    }

}
