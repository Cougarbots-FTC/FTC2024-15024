package org.firstinspires.ftc.teamcode;
/*
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//TeleOp class to test a servo for it's capabilities
//This class is mainly formed to be produced for a video
@TeleOp(name = "ServoTester", group = "Tests")
public class Clark15024ServoTest extends LinearOpMode {
    //TODO change this into only the servo variable instead of all of the hardware map and also change
    //TODO it for .Map function in the runOPMode to intializing the variable
    Clark15024HWMap robot = new Clark15024HWMap();
    //Note, the variables manipulated are part of the above Hardware Map object, unlike some online templates
    //which directly put the variables in this class.


    @Override
    public void runOpMode() throws InterruptedException {
        robot.Map(hardwareMap);


        waitForStart();

        //Operator mode
        //The controller has a D-pad, 2 joysticks, and 4 buttons, ABXY.
        //Note: The Computing Division will probably gut this to replace the buttons with one-button operations which have complex functions.
        //TODO: Add lower>extend function, then retract>raise function that runs on the second press of the button, probably using a boolean [A]
        //TODO: Add toggle to spinning intake. This might be linked to the above function, despite us being asked to make it a toggle [B]
        //TODO: Add a toggle to the lift itself [X]
        //TODO: Add a toggle to the lift's bucket [Y]
        while(opModeIsActive()){
            //If y is pressed on gamepad 1, then the servo is going to go to 0 degrees
            if(gamepad1.y){
                robot.drop.setPosition(0);
                //If a is pressed on gamepad 1, then the servo is going to go to 180 degrees
            } else if (gamepad1.a) {
                robot.drop.setPosition(0.5);
                //If a is pressed on gamepad 1, then the servo is going to go to 360 degrees
            } else if (gamepad1.b) {
                robot.linearMotionUp1.setPosition(0);
                //If x is pressed on gamepad 1, this happens (PLACEHOLDER)
            } else if (gamepad1.x) {
                robot.linearMotionUp2.setPosition(0.5);
            }

            telemetry.addData("Servo Position", robot.drop.getPosition());
            telemetry.update();
        }
    }
}
/*Code Graveyard, old code that may be useful for reference is stored here.
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
            //If x is pressed on gamepad 1, this happens (PLACEHOLDER)
            } else if (gamepad1.x) {
                robot.drop.setPosition(1);
            }

            telemetry.addData("Servo Position", robot.drop.getPosition());
            telemetry.update();
        }
 */