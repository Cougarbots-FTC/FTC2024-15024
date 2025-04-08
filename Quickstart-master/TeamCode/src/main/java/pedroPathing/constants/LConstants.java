package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class LConstants {
    static {
        TwoWheelConstants.forwardTicksToInches = 0.0005;
        TwoWheelConstants.strafeTicksToInches = 0.0005;
        //https://pedropathing.com/localization/setup.html#robot-coordinate-grid
        TwoWheelConstants.forwardY = -2.25;
        TwoWheelConstants.strafeX = -6.125;
        TwoWheelConstants.forwardEncoder_HardwareMapName = "leftRear";
        TwoWheelConstants.strafeEncoder_HardwareMapName = "rightFront";
        TwoWheelConstants.forwardEncoderDirection = Encoder.FORWARD; // positive forward
        TwoWheelConstants.strafeEncoderDirection = Encoder.FORWARD; // positive to left
        TwoWheelConstants.IMU_HardwareMapName = "imu";
        TwoWheelConstants.IMU_Orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.UP);
    }
}




