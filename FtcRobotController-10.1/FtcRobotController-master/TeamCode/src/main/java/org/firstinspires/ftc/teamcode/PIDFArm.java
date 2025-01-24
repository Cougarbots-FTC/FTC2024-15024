package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
@TeleOp
public class PIDFArm extends OpMode {

    private PIDController controller;
    Clark15024HWMap robot;

    public static double p = 0, i = 0, d = 0;
    public static double f = 1;
    public int target = 70;

    // TODO: update based on Motor
    private final double ticksInDegrees = 2100;
    //private final FtcDashboard dashboard = FtcDashboard.getInstance();

    public PIDFArm(HardwareMap map) {
        robot = new Clark15024HWMap();

        robot.Map(map);
    }

    @Override
    public void init() {

        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


    }


    public void setSetPoint(int encoder){
        target = encoder;
    }

    public int getSetPoint(){
        return target;
    }

    public int getCurrentPosition(){
        return robot.ArmRotator.getCurrentPosition();
    }

    @Override
    public void loop() {

        controller.setPID(p, i, d);
        int armPos = robot.ArmRotator.getCurrentPosition();

        double pid = controller.calculate(armPos, target);
        double ff = Math.cos(Math.toRadians(target / ticksInDegrees)) * f;

        double power = pid * ff;

        robot.ArmRotator.setPower(power);



    }
}
