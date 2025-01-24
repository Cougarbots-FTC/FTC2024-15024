package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TeleOpWithPIDF extends LinearOpMode {
        private PIDFAttempt2 pidfArm;

        @Override
        public void runOpMode() {
            // Initialize hardware map and PIDFArm
            pidfArm = new PIDFArm(hardwareMap);

            waitForStart();

            while (opModeIsActive()) {
                // Add your TeleOp logic here
            }
        }
    }

