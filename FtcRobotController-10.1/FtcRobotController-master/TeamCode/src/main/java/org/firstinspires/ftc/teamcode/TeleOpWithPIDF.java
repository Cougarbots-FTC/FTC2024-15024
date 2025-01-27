package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Clark 15024 PIDF TeleOP")
public class TeleOpWithPIDF extends LinearOpMode {
        private PIDFAttempt2 pidfArm;
        Clark15024HWMap robot = new Clark15024HWMap();

        @Override
        public void runOpMode() {
            // Initialize hardware map and PIDFArm
            robot.Map(hardwareMap);
            //pidfArm.init();
            pidfArm = new PIDFAttempt2(robot.ArmRotator);

            waitForStart();

            while (opModeIsActive()) {
                if (gamepad1.a) {
                    pidfArm.setSetpoint(600); // Set encoder position to 1000
                } else if (gamepad1.b) {
                    pidfArm.setSetpoint(50); // Set encoder position to 2000
                }

                // Update the PID loop to adjust motor power
                pidfArm.loop();

                // Telemetry for debugging
                telemetry.addData("Target Position", pidfArm.getSetpoint());
                telemetry.addData("Current Position", pidfArm.getCurrentPosition());
                telemetry.update();
            }
        }
    }

