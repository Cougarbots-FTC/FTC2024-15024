package org.firstinspires.ftc.teamcode.prac;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.opencv.core.Mat;

//TODO In FTC Dashboard, tune all the p i d f constants.
//For the f(feed forward), you have to test at which values it will hold the arm against gravity(drop claw and see if it stops immediately) and start at 0.3
//For the p(portional), increase whether or not if it reaches the target position and start at 0.001
//For the d, introduces the dampening component to the controller which acts as a dampener on the oscillations of the pid controller and start at 0.0001
@Config
@TeleOp
public class PIDF_Arm extends OpMode {

    private PIDController controller;

    //Need to tune all these variable according to the motor using ftc dashboard
    public static double p = 0, i = 0, d = 0;
    public static double f = 0;

    public static int target = 0;

    private final double ticks_in_degree = 700/180.0; //TODO determine how much ticks are in one spin or just how ticks there are

    private DcMotorEx arm_motor;

    @Override
    public void init(){
        controller = new PIDController(p,i,d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        arm_motor =  hardwareMap.get(DcMotorEx.class, "arm_motor0");
    }

    @Override
    public void loop(){
        controller.setPID(p,i,d);
        int armPos = arm_motor.getCurrentPosition();
        double pid = controller.calculate(armPos,target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

        double power = pid + ff;

        arm_motor.setPower(power);
        telemetry.addData("Target", target);
        telemetry.update();
    }
}
