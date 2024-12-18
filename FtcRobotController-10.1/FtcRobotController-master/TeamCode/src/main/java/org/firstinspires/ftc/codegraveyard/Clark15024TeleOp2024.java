package org.firstinspires.ftc.teamcode;
//Imports
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


//Used to name this specific TeleOP to show in the driver hub
@TeleOp(name = "Clark15024TeleOp2024")
//Base TeleOp class which currently has the Hardware map Initialized and the running Op mode
//extends LinearOpMode - used as a parent class of this child class, meaning you can use all the functions from the parent class in this child class
public class Clark15024TeleOp2024 extends LinearOpMode {
    //Initialized Hardware map instance variable assigned to "robot"
    Clark15024HWMap robot = new Clark15024HWMap();

    //@Override - Used to rewrite the runOpMode function which is in the LinearOpMode class
    //runOpMode - runs when the button before the start button is pressed
    @Override
    public void runOpMode(){
        //Initiates the Map function, assigning items to instance variables in the hardware map
        robot.Map(hardwareMap);

        telemetry.addData("Say", "Starting");
        telemetry.update();
        //Local boolean variables for one-button-two-functions operations
        Boolean gamepad1AState = false;
        Boolean gamepad1BState = false;
        Boolean gamepad1XState = false;
        Boolean gamepad1YState = false;

        //Sets the mode all motors for driving to reset the counter for the encode and to run without using the encoders(encoders will not pick up change)
        robot.driveLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.driveLeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.driveLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.driveLeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.driveRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.driveRightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.driveRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.driveRightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        robot.LiftA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LiftA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.LiftB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LiftB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        /*robot.linearMotionRight1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.linearMotionRight1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.intakeHD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.intakeHD.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);*/




        //TODO Add SparkFun Sensor and additional ability
        //Waits for the button to start on the driver hub to be pressed
        waitForStart();


        //while loop starts once the start button is pressed
        while(opModeIsActive()){
            Boolean gamepad1ABuffer = false;
            Boolean gamepad1BBuffer = false;
            Boolean gamepad1CBuffer = false;
            Boolean gamepad1DBuffer = false;
            //Assigned Spark fun variable in the hardware map as a 2DPos and using that we get the x,y,h
            /*telemetry.addData("Sensor X", robot.pos.x);
            telemetry.addData("Sensor Y", robot.pos.y);
            telemetry.addData("Sensor H", robot.pos.h);*/
            telemetry.update();

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
            double rightFrontPower = (vertical - horizontal - pivot) / denominator;
            double rightBackPower = (vertical + horizontal - pivot) / denominator;
            double LeftFrontPower = (pivot + vertical + horizontal) / denominator;
            double LeftBackPower = (vertical - horizontal + pivot) / denominator;

            //Sets Power to the motors and changed the signed of the math in order to proportion the wheels right to move
            robot.driveRightFront.setPower(rightFrontPower * slow);
            robot.driveRightBack.setPower(rightBackPower * slow);
            robot.driveLeftBack.setPower(LeftBackPower * slow);
            robot.driveLeftFront.setPower(LeftFrontPower * slow);
            /*
            //One-button operation of the spinning intake
            if (gamepad1.a){
                gamepad1ABuffer=true;
            }
            //One-button operation of the lift itself.
            if (gamepad1.y){
                gamepad1ABuffer=true;
            }
            //One-button operation of the lift bucket
            if (gamepad1.b){
                gamepad1ABuffer=true;
            }
            //One-button operation of the horizontal linear motion arm
            if (gamepad1.x) {
                gamepad1ABuffer=true;
            }
            */
            //One-button operation of the spinning intake
            if (gamepad1.a==true&&gamepad1AState==false){
                robot.intakeHD.setPower(1);
                gamepad1AState = true;
            } else if (gamepad1.a==true&&gamepad1AState==true) {
                robot.intakeHD.setPower(0);
                gamepad1AState=false;
            }
            //One-button operation of the lift itself.
            if (gamepad1.y==true&&gamepad1YState==false){
                robot.LiftA.setTargetPosition(1);
                robot.LiftB.setTargetPosition(1);
                gamepad1YState = true;
            } else if (gamepad1.y==true&&gamepad1YState==true) {
                robot.LiftA.setTargetPosition(0);
                robot.LiftB.setTargetPosition(0);
                gamepad1YState=false;
            }
            //One-button operation of the lift bucket
            if (gamepad1.b==true&&gamepad1BState==false){
                robot.drop.setPosition(0.5);
                gamepad1BState = true;
            } else if (gamepad1.b==true&&gamepad1BState==false) {
                robot.drop.setPosition(0);
                gamepad1BState=false;
            }
            //One-button operation of the horizontal linear motion arm
            if (gamepad1.x==true&&gamepad1XState==false){
                gamepad1XState = true;
            } else if (gamepad1.x==true&&gamepad1XState==true) {
                gamepad1XState=false;
            }

            //forward
            //robot.driveRightFront.setPower();
            /*The code below has been put into the graveyard for testing purposes
            if(gamepad2.right_stick_y > 0 || gamepad2.right_stick_y < 0){
                robot.linearMotionUp1.setPower(gamepad2.right_stick_y);
            }
            if(gamepad1.right_bumper){
                robot.linearMotionUp1.setPower(0);
            }


            if(gamepad2.left_stick_y > 0 || gamepad2.left_stick_y < 0) {
                robot.linearMotionUp1.setPower(gamepad2.left_stick_y);
                robot.linearMotionUp2.setPower(gamepad2.left_stick_y);
            }
            if(gamepad2.left_bumper){
                robot.linearMotionUp1.setPower(0);
                robot.linearMotionUp2.setPower(0);
            }

            if(gamepad2.right_trigger > 0 || gamepad2.right_trigger < 0) {
                robot.intakeHD.setPower(gamepad1.left_stick_y);
            }
            if(gamepad2.right_trigger == 0){
                robot.intakeHD.setPower(0);
            }

            if (gamepad2.a){
                robot.drop.setPosition(0.5);
            }

             */
            telemetry.update();
        }
    }
}
