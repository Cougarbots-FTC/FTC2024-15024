package org.firstinspires.ftc.teamcode;
//Imports
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


/// @version 3.0
/// Used for match 3

//Used to name this specific TeleOP in the driver hub
@TeleOp(name = "Clark 15024 TeleOp 3.0")
//Base TeleOp class which currently has the Hardware map Initialized and the running Op mode
//extends LinearOpMode - used as a parent class of this child class, meaning you can use all the functions from the parent class in this child class
public class Clark15024TeleOp extends LinearOpMode {
    //Initialized Hardware map instance variable assigned to "robot"
    Clark15024HWMap robot = new Clark15024HWMap();

    //PIDF Test
    PIDFArm pidfArm = new PIDFArm();
    private PIDFController exampleCPIDF = new PIDFController(0.005, 0, 0.0005, 0.15);
    int targetPosition = 500;

    //@Override - Used to rewrite the runOpMode function which is in the LinearOpMode class
    //runOpMode - runs when the button before the start button is pressed
    @Override
    public void runOpMode(){
        //Initiates the Map function, assigning items to instance variables in the hardware map
        robot.Map(hardwareMap);
        //Launch easter eggs
        telemetry.addData("Say", "Starting 15024 TeleOp 3.0");
        telemetry.update();
        //Local boolean variables for one-button-two-functions operations
        Boolean gamepad2ALastPressed = false;
        Boolean gamepad2BLastPressed = false;
        Boolean gamepad2YLastPressed = false;
        Boolean gamepad2XLastPressed = false;

        int clawOpenPosition = 1;
        int clawClosedPosition = 0;

        int clawRotatorClosedPosition = 0;
        int clawRotatorOpenPosition = 1;

        int armRotatorDownPosition = 0;
        int armRotatorUpPosition = 1;

        //Waits for the button to start on the driver hub to be pressed
        waitForStart();

        //while loop starts once the start button is pressed
        while(opModeIsActive()){
            //Slow mode whenever you need to go slower to get precise blocks
            //TODO change controller input if needed
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

            //left_trigger - liftA and liftB up
            //right_trigger - liftA and liftB down
            float liftPowerUp = gamepad2.left_trigger;
            float liftPowerDown = gamepad2.right_trigger;
            if (liftPowerUp > 0.1) {
                robot.LiftA.setPower(liftPowerUp);
                robot.LiftB.setPower(liftPowerUp);
            } else if (liftPowerDown > 0.1) {
                robot.LiftA.setPower(-1 * liftPowerDown);
                robot.LiftB.setPower(-1 * liftPowerDown);
            } else {
                robot.LiftA.setPower(0);
                robot.LiftB.setPower(0);
            }

            //two CRServos for arm extender - Right stick y for forward and back
            double armExtnderPower = gamepad2.right_stick_y;
            if (armExtnderPower > 0.1) {
                robot.armExtender1.setPower(armExtnderPower);
                robot.armExtender2.setPower(-armExtnderPower);
            } else if (armExtnderPower < 0.1){
                robot.armExtender1.setPower(armExtnderPower);
                robot.armExtender2.setPower(-armExtnderPower);
            } else {
                robot.armExtender1.setPower(-1);
                robot.armExtender2.setPower(-1);
            }

            // toggle for claw rotator
            boolean aPressed = gamepad2.a;
            if (aPressed && !gamepad2ALastPressed) {
                if (robot.clawRotator.getPosition() == clawOpenPosition) {
                    robot.clawRotator.setPosition(clawRotatorClosedPosition);
                } else {
                    robot.clawRotator.setPosition(clawRotatorOpenPosition);
                }
            }
            gamepad2ALastPressed = aPressed;

            //toggle for claw - fixed position B to open and close
            boolean bPressed = gamepad2.b;
            if (bPressed && !gamepad2BLastPressed) {
                if (robot.claw.getPosition() == clawOpenPosition) {
                    robot.claw.setPosition(clawClosedPosition);
                } else {
                    robot.claw.setPosition(clawOpenPosition);
                }
            }
            gamepad2BLastPressed = bPressed;

            //servo for door - 180 - right bumper - hold to move open - if not pressed, move to close


            //ArmRotator on DPad left and Right
            boolean yPressed = gamepad2.y;

            if (yPressed && !gamepad2YLastPressed) {
                if (robot.armRotator.getPosition() == armRotatorUpPosition) {
                    robot.armRotator.setPosition(armRotatorDownPosition);
                } else {
                    robot.armRotator.setPosition(armRotatorUpPosition);
                }
            }
            gamepad2YLastPressed = yPressed;

            if (gamepad2.x) {
                robot.bucketRotator.setPosition(robot.bucketRotator.getPosition()-0.1);
            } else {
                robot.bucketRotator.setPosition(0.4);
            }

        }
    }
}
