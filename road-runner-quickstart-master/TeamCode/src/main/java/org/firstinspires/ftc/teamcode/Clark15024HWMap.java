package org.firstinspires.ftc.teamcode;
//Imports
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;



//Clark Robotics 15024 Hardware Map
public class Clark15024HWMap {
    //Instances variables which makes the variables we can use or assign later
    // null - placeholder of a variable and basically has nothing in the variable currently
    public DcMotor driveRightFront = null; //3c
    public DcMotor driveLeftFront = null; //1 c
    public DcMotor driveRightBack = null; //2 c
    public DcMotor driveLeftBack = null; // 0 c

    public DcMotor LiftA = null; // 1 e
    public DcMotor LiftB = null; // 0 e

    public DcMotorEx ArmExtender = null; // 2 e
    public DcMotorEx ArmRotator = null;  // 3 e

    public Servo clawRotate = null; // 0 c
    public Servo frontClaw = null; // 0 e
    public Servo backClaw = null; // 2 c
    public Servo bucketRotator = null; // 1 c

    public ColorSensor colorSensor;//i2c 2 e
    public SparkFunOTOS myOtos = null; //i2c 0 c

    //public SparkFunOTOS.Pose2D pos = null;

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
        ArmExtender = HWM.get(DcMotorEx.class, "ArmExtender");
        ArmRotator = HWM.get(DcMotorEx.class, "ArmRotator");



        myOtos = HWM.get(SparkFunOTOS.class, "SparkFun");


        clawRotate = HWM.get(Servo.class, "clawRotate");
        backClaw = HWM.get(Servo.class, "backClaw");
        frontClaw = HWM.get(Servo.class, "frontClaw");
        bucketRotator = HWM.get(Servo.class, "bucketRotator");

        colorSensor = HWM.get(ColorSensor.class, "colorSensor");

        //setDirection function sets the direction of the wheels which it has to go. Some say forward and some say reverse because that is how you are also to move the robot back and forth
        driveLeftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        //setZeroPowerBehavior function indicates to us that the power of the motor is at zero or not changing
        driveLeftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //setMode with Running without encoder means that the motor is on by the encoders on the motors are not activated yet
        //driveLeftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveLeftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        driveLeftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //driveLeftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveRightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        driveRightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //driveRightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        driveRightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        driveRightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //driveRightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        LiftA.setDirection(DcMotorSimple.Direction.FORWARD);
        LiftA.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        LiftB.setDirection(DcMotorSimple.Direction.REVERSE);
        LiftB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LiftB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ArmExtender.setDirection(DcMotorSimple.Direction.FORWARD);
        ArmExtender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ArmRotator.setDirection(DcMotorEx.Direction.REVERSE);


        bucketRotator.setDirection(Servo.Direction.FORWARD);
        backClaw.setDirection(Servo.Direction.FORWARD);
        frontClaw.setDirection(Servo.Direction.FORWARD);
        clawRotate.setDirection(Servo.Direction.FORWARD);






    }

}
