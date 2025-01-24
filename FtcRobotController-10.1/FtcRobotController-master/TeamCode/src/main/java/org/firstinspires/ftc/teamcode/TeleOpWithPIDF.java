package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TeleOpWithPIDF extends LinearOpMode {
        private PIDFAttempt2 pidfArm;

        @Override
        public void runOpMode() {
            // Initialize hardware map and PIDFArm
            pidfArm = new PIDFAttempt2(hardwareMap);

            waitForStart();

            while (opModeIsActive()) {
                if (gamepad1.a) {
                    pidfArm.setSetpoint(1000); // Set encoder position to 1000
                } else if (gamepad1.b) {
                    pidfArm.setSetpoint(2000); // Set encoder position to 2000
                }

                // Update the PID loop to adjust motor power
                pidfArm.update();

                // Telemetry for debugging
                telemetry.addData("Target Position", pidfArm.getSetpoint());
                telemetry.addData("Current Position", pidfArm.getCurrentPosition());
                telemetry.update();
            }
        }
    }

