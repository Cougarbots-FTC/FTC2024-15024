package org.firstinspires.ftc.teamcode.prac;
//Imports
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

//Indicates what OpMode this part of code should be in
//This class is intended to be the code for our wheels, Mecanum Wheels
@TeleOp
public class MecanumDriveTrain extends OpMode {
    //Initializes all 4 motors(instance variables) for driving to be null
    DcMotor rightFront = null;
    DcMotor leftFront = null;
    DcMotor rightBack = null;
    DcMotor leftBack = null;

    //Overrides the init function of OpMode
    //meant to set the power of the motors in order to move
    @Override
    public void init(){

        //Initialized variables meant to change the values according to the gamepad
        double vertical = 0;
        double horizontal = 0;
        double pivot = 0;

        //Instances variables assigned to double or decimal values to the different gamepad
        //Set the vertical as a negative because of the different values needed for the right side as they are in reverse
        //TODO change negative signs of all variables if robot not moving or working as the values for the robot could need to be switched
        vertical = -gamepad1.left_stick_y;
        horizontal = gamepad1.left_stick_x * 1.1; //  Multiply by 1.1 to negate imperfect strafing
        pivot = gamepad1.right_stick_x;

        //Obtains values for each motor through the positions through values
        //from  left joystick which has up/down(vertical) and left/right values(horizontal), and right joystick which has left/right values(pivot)
        double demominator = Math.max(Math.abs(vertical) + Math.abs(horizontal) + Math.abs(pivot), 1);
        double rightFrontPower = (vertical - horizontal - pivot) / demominator;
        double rightBackPower = (vertical + horizontal - pivot) / demominator;
        double LeftFrontPower = (pivot + vertical + horizontal) / demominator;
        double LeftBackPower = (vertical - horizontal + pivot) / demominator;

        //Sets Power to the motors and changed the signed of the math in order to proportion the wheels right to move
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
        leftBack.setPower(LeftBackPower);
        leftFront.setPower(LeftFrontPower);


    }

    //Overrides the init_loop function inside of OpMode
    //Assigns values to the 4 motors and the direction
    @Override
    public void init_loop(){
        //Assigns values to motors from the hardWareMap
        rightFront = hardwareMap.get(DcMotor.class, "driveRightFront");
        leftFront = hardwareMap.get(DcMotor.class, "driveLeftFront");
        rightBack = hardwareMap.get(DcMotor.class, "driveRightBack");
        leftBack = hardwareMap.get(DcMotor.class, "driveLeftBack");
        //Changes direction for both right motors to Reverse in order for the robot to move(Right side motors are "flipped")
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    @Override
    public void loop(){}

}
