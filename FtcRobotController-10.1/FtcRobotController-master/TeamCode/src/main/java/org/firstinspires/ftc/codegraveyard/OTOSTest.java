package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "OTOSTest", group = "Tests")
public class OTOSTest extends LinearOpMode {
    SparkFunOTOS odom = null;

    @Override
    public void runOpMode(){
        odom = hardwareMap.get(SparkFunOTOS.class, "sensor_otos");

        while(opModeIsActive()){
            SparkFunOTOS.Pose2D pos = odom.getPosition();

            telemetry.addData("Sensor X", pos.x);
            telemetry.addData("Sensor Y", pos.y);
            telemetry.addData("Sensor H", pos.h);
            telemetry.update();
        }
    }

}
