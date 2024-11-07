package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//TeleOp class to test a servo for it's capabilities
//This class is mainly formed to be produced for a video
@TeleOp(name = "ServoTester", group = "Tests")
public class Clark15024ServoTest extends LinearOpMode {
    //TODO change this into only the servo variable instead of all of the hardware map and also change
    //TODO it for .Map function in the runOPMode to intializing the variable
    Clark15024HWMap robot = new Clark15024HWMap();



    @Override
    public void runOpMode() throws InterruptedException {
        robot.Map(hardwareMap);


        waitForStart();

        while(opModeIsActive()){
            //If y is pressed on gamepad 1, then the servo is going to go to 0 degrees
            if(gamepad1.y){
                robot.drop.setPosition(0);
            //If a is pressed on gamepad 1, then the servo is going to go to 180 degrees
            } else if (gamepad1.a) {
                robot.drop.setPosition(0.5);
            //If a is pressed on gamepad 1, then the servo is going to go to 360 degrees
            } else if (gamepad1.b) {
                robot.drop.setPosition(1);
            }

            telemetry.addData("Servo Position", robot.drop.getPosition());
            telemetry.update();
        }
    }
}
