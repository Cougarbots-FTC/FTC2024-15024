package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@Config
@TeleOp
public class PIDFArm extends OpMode {

    private PIDController controller;

    public static double p = 0, i = 0, d = 0;
    public static double f = 0;
    public static int target = -800;

    // TODO: update based on Motor
    private final double ticksInDegrees = 700/180.0;

    private DcMotorEx armRotator;

    @Override
    public void init() {
        //controller = new PIDController(p,i,d);
        //telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        armRotator = hardwareMap.get(DcMotorEx.class, "ArmRotator");
    }

    @Override
    public void loop() {
        controller.setPID(p, i, d);
        int armPos = armRotator.getCurrentPosition();

        double pid = controller.calculate(armPos, target);
        double ff = Math.cos(Math.toRadians(target / ticksInDegrees)) * f;

        double power = pid * ff;

        armRotator.setPower(power);

        telemetry.addData("Arm Position: ", armPos);
        telemetry.addData("target: ", target);
        telemetry.update();

    }
}
