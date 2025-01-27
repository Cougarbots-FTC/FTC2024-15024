package org.firstinspires.ftc.teamcode;
//Imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


//Used to name this specific TeleOP in the driver hub
@TeleOp(name = "Clark 15024 TeleOp ")
//Base TeleOp class which currently has the Hardware map Initialized and the running Op mode
//extends LinearOpMode - used as a parent class of this child class, meaning you can use all the functions from the parent class in this child class
public class Clark15024TeleOp extends LinearOpMode {
    //Initialized Hardware map instance variable assigned to "robot"
    Clark15024HWMap robot = new Clark15024HWMap();
    private PIDFAttempt2 pidfArm;

    //@Override - Used to rewrite the runOpMode function which is in the LinearOpMode class
    //runOpMode - runs when the button before the start button is pressed
    @Override
    public void runOpMode(){

        robot.Map(hardwareMap);
        pidfArm = new PIDFAttempt2(robot.ArmRotator);


        telemetry.addData("Say", "Starting 15024 TeleOp using PIDF");
        telemetry.addData("Arm Position: ", robot.ArmRotator.getCurrentPosition());
        telemetry.update();


        //Local boolean variables for one-button-two-functions operations
        Boolean gamepad2ALastPressed = false;
        Boolean gamepad2BLastPressed = false;
        Boolean gamepad2XLastPressed = false;
        Boolean gamepad2YLastPressed = false;

        //Waits for the button to start on the driver hub to be pressed
        waitForStart();

        //while loop starts once the start button is pressed
        while(opModeIsActive()){

            //Slow mode whenever you need to go slower to get precise blocks
            double slow = gamepad1.right_bumper ? 0.5 : 1.0;

            //Instances variables assigned to double or decimal values to the different gamepad
            //Set the vertical as a negative because of the different values needed for the right side as they are in reverse
            //TODO change negative signs of all variables if robot not moving or working as the values for the robot could need to be switched
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


            //left_trigger - liftA and liftB up
            //right_trigger - liftA and liftB down
            float liftPowerUp = gamepad1.left_trigger;
            float liftPowerDown = gamepad1.right_trigger;
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

            //ArmExtender on left y stick
            double armExtenderPower = -gamepad2.left_stick_y;
            robot.ArmExtender.setPower(armExtenderPower);


            //ArmRotator on left x stick
            double armRotatorPower = gamepad2.left_stick_x;
            robot.ArmRotator.setPower(armRotatorPower);

            if (gamepad2.a) {
                pidfArm.setSetpoint(600); // Set encoder position to 1000
            } else if (gamepad2.b) {
                pidfArm.setSetpoint(50); // Set encoder position to 2000
            }
            pidfArm.loop();

            // Telemetry for debugging
            //telemetry.addData("Target Position", pidfArm.getSetpoint());
            telemetry.addData("Current Position", robot.ArmRotator.getCurrentPosition());
            telemetry.update();

            //servo for intake - x toggle on and off
            boolean xPressed = gamepad2.x;
            if (xPressed && !gamepad2XLastPressed) {
                if (robot.intake.getPower() == -1) {
                    robot.intake.setPower(0);
                } else {
                    robot.intake.setPower(-1);
                }
            }
            gamepad2XLastPressed = xPressed;

            boolean yPressed = gamepad2.y;
            if (gamepad2.y && !gamepad2YLastPressed) {
                if (robot.claw.getPosition() == 0) {
                    robot.claw.setPosition(1);
                } else {
                    robot.claw.setPosition(0);
                }
            }
            gamepad2YLastPressed = yPressed;

            //delivery bucket on B - on press roll forward to deliver, on release roll back to start position
            if (gamepad1.b) {
                robot.bucketRotator.setPosition(robot.bucketRotator.getPosition()-0.1);
            } else {
                robot.bucketRotator.setPosition(0.4);
            }




        }
    }
}
