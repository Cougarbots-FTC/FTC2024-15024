package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Clark15024BlueAuto")
public class Clark15024BlueAuto extends LinearOpMode {

    Clark15024HWMap robot = new Clark15024HWMap();
    ElapsedTime time = new ElapsedTime();

    Clark15024Auto auto = new Clark15024Auto();

    @Override
    public void runOpMode(){
        robot.Map(hardwareMap);
        telemetry.addData("Time", time.time());
        telemetry.update();
        waitForStart();

        //auto.moveLeft(1, 500);

    }


}
