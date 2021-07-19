/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems.DriveSubsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.CanConstants;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase
{

    // Four Falcon 500 motors with Talon FX controllers
    private final WPI_TalonFX m_leftTalon1 = new WPI_TalonFX(CanConstants.left_drivebase_1);
    private final WPI_TalonFX m_leftTalon2 = new WPI_TalonFX(CanConstants.left_drivebase_2);
    private final WPI_TalonFX m_rightTalon1 = new WPI_TalonFX(CanConstants.right_drivebase_1);
    private final WPI_TalonFX m_rightTalon2 = new WPI_TalonFX(CanConstants.right_drivebase_2);
 
    // The robot's drive object
    private final DifferentialDrive m_drive = new DifferentialDrive(m_leftTalon1, m_rightTalon1);

    private double m_last_speed = 0.0;

    /**
     * Creates a new DriveSubsystem.
     */
    public DriveSubsystem()
    {
        // Reset the Talons to factory defaults
        m_leftTalon1.configFactoryDefault();
        m_leftTalon2.configFactoryDefault();
        m_rightTalon1.configFactoryDefault();
        m_rightTalon2.configFactoryDefault();

        // Slave the second Talon on each side to the first
        m_leftTalon2.follow(m_leftTalon1);
        m_rightTalon2.follow(m_rightTalon1);

        // Configure Falcons to use integrated encoder
        m_leftTalon1.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
        m_rightTalon1.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
        
        m_leftTalon1.configOpenloopRamp(0.4);
        m_leftTalon2.configOpenloopRamp(0.4);        
        m_rightTalon2.configOpenloopRamp(0.4);        
        m_rightTalon1.configOpenloopRamp(0.4);
    }

    
    /**
     * Drives the robot using arcade controls.
     *
     * @param speed the commanded forward movement
     * @param curve the commanded rotation
     */

    public void arcadeDrive(double fwd, double rot)
    {
        fwd = limitAcceleration(fwd, m_last_speed, DriveConstants.slewRate);
        m_last_speed = fwd;
        m_drive.arcadeDrive(fwd, -rot);
        displayEncoderValues();
    }

    public void rocketDrive(double fwd, double rot)
    {
        fwd = limitAcceleration(fwd, m_last_speed, DriveConstants.slewRate);
        m_last_speed = fwd;
        m_drive.curvatureDrive(fwd, rot, true);
        displayEncoderValues();
    }

    public void tankDrive(double left, double right)
    {
        m_drive.tankDrive(left, right);
        displayEncoderValues();
    }

    /**
     * Resets the drive encoders to currently read a position of 0.
     */
    public void resetEncoders()
    {
        m_leftTalon1.setSelectedSensorPosition(0);
        m_rightTalon1.setSelectedSensorPosition(0);
    }

    /**
     * Gets the average distance of the TWO encoders.
     *
     * @return the average of the TWO encoder readings
     */
    public double getAverageEncoderDistance()
    {
        return (m_leftTalon1.getSelectedSensorPosition() + m_rightTalon1.getSelectedSensorPosition()) / 2.0;
    }

    /**
     * Gets the left drive encoder value.
     *
     * @return the left drive encoder value
     */
    public double getLeftEncoderValue()
    {
        return m_leftTalon1.getSelectedSensorPosition();
    }

    /**
     * Gets the right drive encoder value.
     *
     * @return the right drive encoder value
     */
    public double getRightEncoderValue()
    {
        return m_rightTalon1.getSelectedSensorPosition();
    }

    public void displayEncoderValues()
    {
        //SmartDashboard.putNumber("Left Drive Encoder", getLeftEncoderValue());
        //SmartDashboard.putNumber("Right Drive Encoder", getRightEncoderValue());
    }

    public WPI_TalonFX getLeftTalon()
    {
        return m_leftTalon1;
    }

    public WPI_TalonFX getRightTalon()
    {
        return m_rightTalon1;
    }

    /**
     * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
     *
     * @param maxOutput the maximum output to which the drive will be constrained
     */
    public void setMaxOutput(double maxOutput)
    {
        m_drive.setMaxOutput(maxOutput);
    }

    private double limitAcceleration(double input, double lastVal, double changeLimit) {
		
		double val = input;
		double change;
        
		/*
         *  Slew rate limiter - limit rate of change
         */
    	change = val - lastVal;
    		
    	if (change > changeLimit)
    		change = changeLimit;
    	else if (change < -changeLimit)
    		change = -changeLimit;
    	
    	val = lastVal += change;
        
        return val;
		
    }
    
    public void runDriveBase(double speed)
    {
        //sets robot drivebase at speed
        m_leftTalon1.set(ControlMode.PercentOutput, speed);
        m_rightTalon1.set(ControlMode.PercentOutput, speed);
    }
}
