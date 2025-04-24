package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * This is an example minimal implementation of the mecanum drivetrain
 * for demonstration purposes.  Not tested and not guaranteed to be bug free.
 *
 * @author Brandon Gong
 */
@TeleOp(name="Mecanum Drive Example", group="Iterative Opmode")
public class MecanumDriveTest extends OpMode {

    /*
     * The mecanum drivetrain involves four separate motors that spin in
     * different directions and different speeds to produce the desired
     * movement at the desired speed.
     */

    // declare and initialize four DcMotors.
    private DcMotor front_left  = null;
    private DcMotor front_right = null;
    private DcMotor back_left   = null;
    private DcMotor back_right  = null;

    @Override
    public void init() {

        // Name strings must match up with the config on the Robot Controller
        // app.
        front_left   = hardwareMap.get(DcMotor.class, "FrontLeft");
        front_right  = hardwareMap.get(DcMotor.class, "FrontRight");
        back_left    = hardwareMap.get(DcMotor.class, "BackLeft");
        back_right   = hardwareMap.get(DcMotor.class, "BackRight");

        front_right.setDirection(DcMotor.Direction.REVERSE);
        front_left.setDirection(DcMotor.Direction.REVERSE);
        front_right.setDirection(DcMotor.Direction.FORWARD);

    }

    @Override
    public void loop() {

        // Mecanum drive is controlled with three axes: drive (front-and-back),
        // strafe (left-and-right), and twist (rotating the whole chassis).


        /*
         * If we had a gyro and wanted to do field-oriented control, here
         * is where we would implement it.
         *
         * The idea is fairly simple; we have a robot-oriented Cartesian (x,y)
         * coordinate (strafe, drive), and we just rotate it by the gyro
         * reading minus the offset that we read in the init() method.
         * Some rough pseudocode demonstrating:
         *
         * if Field Oriented Control:
         *     get gyro heading
         *     subtract initial offset from heading
         *     convert heading to radians (if necessary)
         *     new strafe = strafe * cos(heading) - drive * sin(heading)
         *     new drive  = strafe * sin(heading) + drive * cos(heading)
         *
         * If you want more understanding on where these rotation formulas come
         * from, refer to
         * https://en.wikipedia.org/wiki/Rotation_(mathematics)#Two_dimensions
         */

        // You may need to multiply some of these by -1 to invert direction of
        // the motor.  This is not an issue with the calculations themselves.
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

        front_left.setPower(LeftFrontPower);
        front_right.setPower(rightFrontPower);
        back_left.setPower(LeftBackPower);
        back_right.setPower(rightBackPower);
    }
}