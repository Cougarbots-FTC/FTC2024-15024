package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (name = "Mechanum Drive Tests", group = "TeleOp")
public class MechanumDriveTest extends OpMode {
    private DcMotor leftRear;
    @Override
    public void init() {
        leftRear = hardwareMap.get(DcMotor.class, "BackLeft");
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        if (gamepad1.a) {
            leftRear.setPower(1);
        } else {
            leftRear.setPower(0);
        }

    }
}
