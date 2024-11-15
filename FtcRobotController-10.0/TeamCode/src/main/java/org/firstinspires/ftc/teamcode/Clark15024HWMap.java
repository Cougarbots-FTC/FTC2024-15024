package org.firstinspires.ftc.teamcode;
//Imports
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


//Clark Robotics 15024 Hardware Map
//Currently only have the motors from the robot powering the wheels
//driveRightFront, driveRightBack, driveLeftFront, driveRightBack
public class Clark15024HWMap {
    //Instances variables which makes the variables we can use or assign later
    // null - placeholder of a variable and basically has nothing in the variable currently
    public DcMotor driveRightFront = null;
    public DcMotor driveLeftFront = null;
    public DcMotor driveRightBack = null;
    public DcMotor driveLeftBack = null;
    public DcMotor LiftA = null;
    public DcMotor LiftB = null;
    public DcMotor linearMotionRight1 = null;
    public DcMotor intakeHD = null;
    //public SensorSparkFunOTOS odom = null;
    public Servo drop = null;
    public SparkFunOTOS odom = null;
    public SparkFunOTOS.Pose2D pos = null;


    HardwareMap hM = null;
    //Map function which we can use to assign values to the instance variables
    //HWM - HardwareMap parameter which we can use to pull values from which are mostly used to assign to values
    public void Map(HardwareMap HWM){
        //Assigns values to instance variables using the parameter which the .get functions to pull the information from the control hub
        hM = HWM;
        //The second parameter of the get function is used to obtain the specific motor from the config file in the robot
        driveLeftBack = HWM.get(DcMotor.class, "driveLeftBack");
        driveLeftFront = HWM.get(DcMotor.class, "driveLeftFront");
        driveRightFront = HWM.get(DcMotor.class, "driveRightFront");
        driveRightBack = HWM.get(DcMotor.class, "driveRightBack");

        LiftA = HWM.get(DcMotor.class, "LiftA");
        LiftB = HWM.get(DcMotor.class, "LiftB");
        /*linearMotionRight1 = HWM.get(DcMotor.class, "linearMotionRight1");
        intakeHD = HWM.get(DcMotor.class, "Intake");
        //odom = HWM.get(SensorSparkFunOTOS.class, "sensor_otos");
        odom = HWM.get(SparkFunOTOS.class, "sensor_otos");*/

        drop = HWM.get(Servo.class, "drop");

        intakeHD = HWM.get(DcMotor.class, "Intake");




        //Using functions from the DcMotor class, this changes the direction of the motor, sets the power to 0, and makes the runmode to run without encoder as the robot is not moving
        //The direction of the some variables are different because they are needed to offset each other to move
       
        //setDirection function sets the direction of the wheels which it has to go. Some say forward and some say reverse because that is how you are also to move the robot back and forth
        driveLeftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        //setZeroPowerBehavior function indicates to us that the power of the motor is at zero or not changing
        driveLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //setMode with Running without encoder means that the motor is on by the encoders on the motors are not activated yet
        driveLeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveLeftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        driveLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveLeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveRightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        driveRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveRightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        driveRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveRightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        LiftA.setDirection(DcMotorSimple.Direction.FORWARD);
        LiftA.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        LiftB.setDirection(DcMotorSimple.Direction.REVERSE);
        LiftB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        /*TODO Change direction of motor according to the plans of hardware team
        StretchA.setDirection(DcMotorSimple.Direction.FORWARD);
        StretchA.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        StretchA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        StretchB.setDirection(DcMotorSimple.Direction.FORWARD);
        StretchB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        StretchB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);*/

        intakeHD.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeHD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeHD.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        drop.setDirection(Servo.Direction.FORWARD);
        //drop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //pos = odom.getPosition();





    }

}
