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
    Clark15024HWMap robot = new Clark15024HWMap();

    public static double p = 0, i = 0, d = 0;
    public static double f = 0;
    public static int target = 0;

    // TODO: update based on Motor
    private final double ticksInDegrees = 28
            ;
    //private final FtcDashboard dashboard = FtcDashboard.getInstance();


    @Override
    public void init() {
        robot.Map(hardwareMap);

        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

    }

    @Override
    public void loop() {

        controller.setPID(p, i, d);
        int armPos = robot.ArmRotator.getCurrentPosition();

        double pid = controller.calculate(armPos, target);
        double ff = Math.cos(Math.toRadians(target / ticksInDegrees)) * f;

        double power = pid * ff;

        robot.ArmRotator.setPower(power);

        telemetry.addData("Arm Position: ", armPos);
        telemetry.addData("target: ", target);
        telemetry.update();



    }
}
