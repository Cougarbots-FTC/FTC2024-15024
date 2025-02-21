package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@Config
public class Color_Sensor {
    private final NormalizedColorSensor colorSensor;
    private boolean isLightOn = false;

    private double REDTUNER = 0.5;

    public Color_Sensor(OpMode opMode) {
        colorSensor = opMode.hardwareMap.get(NormalizedColorSensor.class, "colorSensor"); //i2c contol 0

        // Check if the sensor has a controllable light and turn it off initially
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(false);
        }
        turnLightOn();
    }

    /**
     * Toggles the built-in light if the sensor supports it.
     */
    public void toggleLight() {
        if (colorSensor instanceof SwitchableLight) {
            isLightOn = !isLightOn;
            ((SwitchableLight) colorSensor).enableLight(isLightOn);
        }
    }

    /**
     * Turns the light ON if the sensor supports it.
     */
    public void turnLightOn() {
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
            isLightOn = true;
        }
    }

    /**
     * Turns the light OFF if the sensor supports it.
     */
    public void turnLightOff() {
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(false);
            isLightOn = false;
        }
    }

    /**
     * Retrieves the current color values detected by the sensor.
     * @return NormalizedRGBA object containing alpha, red, green, and blue values.
     */
    public NormalizedRGBA getColor() {
        return colorSensor.getNormalizedColors();
    }

    /**
     * Determines if the sensor detects blue, red, or neither.
     * @return "Blue" if blue is dominant, "Red" if red is dominant, otherwise "Neither".
     */
    public String getColorDetected() {
        NormalizedRGBA colors = getColor();
        if (colors.blue > colors.red * REDTUNER && colors.blue > colors.green) {
            return "Blue";
        } else if (colors.red * REDTUNER > colors.blue && colors.red * REDTUNER > colors.green) {
            return "Red";
        } else {
            return "Neither";
        }
    }

    /**
     * Adds telemetry data for debugging and monitoring sensor readings.
     */
    public void addTelemetry(OpMode opMode) {
        NormalizedRGBA colors = getColor();
        opMode.telemetry.addData("Red", colors.red);
        opMode.telemetry.addData("Green", colors.green);
        opMode.telemetry.addData("Blue", colors.blue);
        opMode.telemetry.addData("Alpha", colors.alpha);
        opMode.telemetry.addData("Light State", isLightOn ? "On" : "Off");
        opMode.telemetry.addData("Detected Color", getColorDetected());
        opMode.telemetry.update();
    }
}
