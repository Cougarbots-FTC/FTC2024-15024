package org.firstinspires.ftc.teamcode;
//Imports
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


//Used to name this specific TeleOP in the driver hub
@TeleOp(name = "Clark 15024 Testing ")
//Base TeleOp class which currently has the Hardware map Initialized and the running Op mode
//extends LinearOpMode - used as a parent class of this child class, meaning you can use all the functions from the parent class in this child class
public class Clark15024TeleOp extends LinearOpMode {
    //Initialized Hardware map instance variable assigned to "robot"
    Clark15024HWMap robot = new Clark15024HWMap();

    //PIDF Arm
    private PIDFArm pidfArm;


    //@Override - Used to rewrite the runOpMode function which is in the LinearOpMode class
    //runOpMode - runs when the button before the start button is pressed
    @Override
    public void runOpMode(){



        robot.Map(hardwareMap);
        telemetry.addData("Say", "Starting 15024 TeleOp Testing");
        telemetry.addData("Arm Position: ", robot.ArmRotator.getCurrentPosition());
        telemetry.update();


        // PID arm code
        pidfArm = new PIDFArm(hardwareMap);
        pidfArm.init();
        //Local boolean variables for one-button-two-functions operations
        Boolean gamepad2ALastPressed = false;
        Boolean gamepad2BLastPressed = false;
        Boolean gamepad2XLastPressed = false;
        Boolean gamepad2YLastPressed = false;

        //Waits for the button to start on the driver hub to be pressed
        waitForStart();

        //while loop starts once the start button is pressed
        while(opModeIsActive()){
            //PIDF Arm (swtching between positions)
            if(gamepad2.a){
                pidfArm.setSetPoint(580);
            }else if(gamepad2.b){
                pidfArm.setSetPoint(70);
            }

            // Updating and rerunning the loop
            pidfArm.loop();

            //Telemetry for PID, for debugging

            telemetry.addData("Target Position", pidfArm.getSetPoint());
            telemetry.addData("Current Position", pidfArm.getCurrentPosition());
            telemetry.update();


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

            robot.LiftA.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.LiftB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            robot.ArmRotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.ArmExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

            //ArmExtender on DPad up and down
            double armExtenderPower = gamepad1.right_bumper ? 1 : 0.5;
            if (gamepad1.dpad_down) {
                robot.ArmExtender.setPower(-armExtenderPower);
            } else if (gamepad1.dpad_up) {
                robot.ArmExtender.setPower(armExtenderPower);
            } else {
               robot.ArmExtender.setPower(0);
            }

            //ArmRotator on DPad left and Right

            /*
            double armRotatorPower = gamepad1.right_bumper ? 0.5 : 0.3;
            if (gamepad1.dpad_left) {
                robot.ArmRotator.setPower(armRotatorPower);
                telemetry.addData("Arm Rotator Power: ", armRotatorPower);
                telemetry.update();

            } else if (gamepad1.dpad_right){
                //down
                robot.ArmRotator.setPower(-0.3);
            } else {
                robot.ArmRotator.setPower(0);
            }
*/
            //servo for intake - x toggle on and off
            boolean xPressed = gamepad1.x;
            if (xPressed && !gamepad2XLastPressed) {
                if (robot.intake.getPower() == -1) {
                    robot.intake.setPower(0);
                } else {
                    robot.intake.setPower(-1);
                }
            }
            gamepad2XLastPressed = xPressed;

            //delivery bucket on B - on press roll forward to deliver, on release roll back to start position
            if (gamepad1.b) {
                robot.bucketRotator.setPosition(robot.bucketRotator.getPosition()-0.1);
            } else {
                robot.bucketRotator.setPosition(0.4);
            }


        }
    }
}
