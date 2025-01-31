package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Testing PIDF")
public class TeleOpWithPIDF extends LinearOpMode {
    private PIDFAttempt2 liftA;
    private PIDFAttempt2 liftB;
    Clark15024HWMap robot = new Clark15024HWMap();

    @Override
    public void runOpMode() {
        // Initialize hardware map and PIDFArm
        robot.Map(hardwareMap);
        //liftA = new PIDFAttempt2(robot.LiftA,0.007,0,0.0005,0.12,300, 2100);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                liftA.setSetpoint(600); // Set encoder position to 1000
            } else if (gamepad1.b) {
                liftA.setSetpoint(50); // Set encoder position to 2000
            }

            // Update the PID loop to adjust motor power
            liftA.loop();

            // Telemetry for debugging
            telemetry.addData("Target Position", liftA.getSetpoint());
            telemetry.addData("Current Position", liftA.getCurrentPosition());
            telemetry.update();
        }
    }
}

