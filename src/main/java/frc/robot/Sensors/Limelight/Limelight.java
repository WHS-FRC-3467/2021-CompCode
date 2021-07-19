/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Sensors.Limelight;

import java.util.Map;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase
{
    public static NetworkTableInstance table = NetworkTableInstance.getDefault();
    private static HttpCamera limelightFeed;
    
    /**
     * Initialize Limelight and Shuffleboard
     */
    public static void initialize()
    {
        
        // Get Driver Dashboard tab
        ShuffleboardTab dashboardTab = Shuffleboard.getTab("DriverDash");

        // Setup Limelight parameters list on Driver Dash
        ShuffleboardLayout limelightList = dashboardTab.getLayout("Limelight", BuiltInLayouts.kList).withPosition(0, 0).withSize(1, 8);

        // Add the various Limelight return values to the "Limelight" list
        limelightList.add("tv", 0);
        limelightList.add("tx", 0);
        limelightList.add("ty", 0);
        limelightList.add("ta", 0);
        limelightList.add("ts", 0);
        limelightList.add("tl", 0);

        // Setup Limelight Feed on Driver Dash
        limelightFeed = new HttpCamera("limelight", "http://limelight.local:5800/stream.mjpg");
        dashboardTab.add("LL", limelightFeed).withPosition(1,0).withSize(12, 6).withProperties(Map.of("Show Crosshair", true, "Show Controls", false));
        /*
        // Setup Limelight controls on Driver Dash
        ShuffleboardLayout LLCtrlsList = dashboardTab.getLayout("Commands", BuiltInLayouts.kList).withPosition(16, 0).withSize(1, 4);

        // Add the various Limelight return values to the "Commands" list
        LLCtrlsList.add(new InstantCommand(Limelight::setDriverMode));
        LLCtrlsList.add(new InstantCommand(Limelight::setVisionMode));
        LLCtrlsList.add(new InstantCommand(Limelight::turnOffLEDs));
        */     
        // Initialize Limelight in Driver mode and USB Cam as PIP
        Limelight.setDriverMode();
        Limelight.setStreamMode(StreamMode.ePIPMain);
    }

    /**
     * Gets whether a target is detected by the Limelight.
     * 
     * @return true if a target is detected, false otherwise.
     */
    public static boolean isTarget()
    {
        return getValue("tv").getDouble(0) == 1;
    }

    /**
     * Horizontal offset from crosshair to target (-27 degrees to 27 degrees).
     * 
     * @return tx as reported by the Limelight.
     */
    public static double getTx()
    {
        return getValue("tx").getDouble(0);
    }

    /**
     * Vertical offset from crosshair to target (-20.5 degrees to 20.5 degrees).
     * 
     * @return ty as reported by the Limelight.
     */
    public static double getTy()
    {
        return getValue("ty").getDouble(0);
    }

    /**
     * Area that the detected target takes up in total camera FOV (0% to 100%).
     * 
     * @return Area of target.
     */
    public static double getTa()
    {
        return getValue("ta").getDouble(0);
    }

    /**
     * Gets target skew or rotation (-90 degrees to 0 degrees).
     * 
     * @return Target skew.
     */
    public static double getTs()
    {
        return getValue("ts").getDouble(0);
    }

    /**
     * Gets target latency (ms).
     * 
     * @return Target latency.
     */
    public static double getTl()
    {
        return getValue("tl").getDouble(0);
    }
    /**
	 * Light modes for Limelight.
	 */
	public static enum LightMode {
		ePipeline, eOff, eBlink, eOn
	}
    /**
	 * Camera modes for Limelight.
	 */
	public static enum CameraMode {
		eVision, eDriver
	}

	/**
	 * Stream modes for Limelight.
	 */
	public static enum StreamMode {
		eStandard, ePIPMain, ePIPSecondary
	}

	/**
	 * Sets LED mode of Limelight.
	 * 
	 * @param mode
	 *            Light mode for Limelight.
	 */
	public static void setLedMode(LightMode mode) {
		getValue("ledMode").setNumber(mode.ordinal());
	}

	/**
	 * Sets camera mode for Limelight.
	 * 
	 * @param mode
	 *            Camera mode for Limelight.
	 */
	public static void setCameraMode(CameraMode mode) {
		getValue("camMode").setNumber(mode.ordinal());
	}

	/**
	 * Sets pipeline number (0-9 value).
	 * 
	 * @param number
	 *            Pipeline number (0-9).
	 */
	public static void setPipeline(int number) {
		getValue("pipeline").setNumber(number);
	}

   	/**
	 * Sets streaming mode for Limelight.
	 * 
	 * @param mode
	 *            Stream mode for Limelight.
	 */
	public static void setStreamMode(StreamMode mode) {
		getValue("stream").setNumber(mode.ordinal());
	}


	/**
	 * Sets Limelight to "Driver" mode.
	 */
	public static void setDriverMode() {
        setCameraMode(CameraMode.eDriver);
        setPipeline(0);
        setLedMode(LightMode.eOff);
	}

	/**
	 * Sets Limelight to "Vision" mode with specified Pipeline.
	 */
	public static void setVisionMode(int pipelinenumber) {
        setCameraMode(CameraMode.eVision);
        setPipeline(pipelinenumber);
        setLedMode(LightMode.ePipeline);
	}

	/**
	 * Sets Limelight to "Vision" mode with Pipeline 1
	 */
	public static void setVisionMode() {
        setCameraMode(CameraMode.eVision);
        setPipeline(1);
        setLedMode(LightMode.ePipeline);
	}

    /**
	 * Turns Limelight LEDS off
	 */
	public static void turnOffLEDs() {
            setLedMode(LightMode.eOff);
	}
    
    /**
     * Helper method to get an entry from the Limelight NetworkTable.
     * 
     * @param key Key for entry.
     * @return NetworkTableEntry of given entry.
     */
    private static NetworkTableEntry getValue(String key)
    {
        return table.getTable("limelight").getEntry(key);
    }

}
