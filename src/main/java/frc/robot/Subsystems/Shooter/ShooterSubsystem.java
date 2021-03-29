package frc.robot.Subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Gains;
import frc.robot.Constants.ShooterConstants;;

public class ShooterSubsystem {
    
    Gains m_speedGains;
    FalconVelocity m_speedControl;
    public ShooterSubsystem m_flyWheel;
    

    public ShooterSubsystem()
    {
        // Decide which motors to use for shooting
        

        /* Initialize Smart Dashboard display */
         SmartDashboard.putNumber("P Gain", m_speedGains.kP);
        SmartDashboard.putNumber("Feed Forward", m_speedGains.kF);

        SmartDashboard.putNumber("Current Velocity", 0.0);
        SmartDashboard.putNumber("Current Output Percent", 0.0);
        SmartDashboard.putNumber("Velocity Error", 0.0);

        SmartDashboard.putNumber("Target Velocity", 4500.0);

    }

    /*
     *
     *  Shooter Wheel control
     * 
     */
    /**
     * void runManual() - run Shooter Wheel at speed commanded by Shuffleboard
     */
    public void runManual(Double velocity)
    {
        // Run Shooter Wheel, getting desired m_velocity in RPM from SmartDasboard
        runShooter(velocity);
    }

    /**
     * void runTracking() - run Shooter Wheel at speed commanded by Limelight tracking
     */
    public void runTracking()
    {
        // Get desired velocity from LimeLight tracking
        double targetVelocity = 0.0; // TO DO: Implement Limelight distance->RPM determination

        runShooter(targetVelocity);
    }

    /**
     * boolean isWheelAtSpeed() - return TRUE when wheel is equal to target, or within tolerance
     *
     * @return TRUE if shooter wheel is at commanded speed
     */
    public boolean isWheelAtSpeed()
    {
        return (Math.abs(m_speedControl.getError()) <= ShooterConstants.kShooterTolerance) && (m_speedControl.getOutputPercent() != 0);
    }

    /**
     * void runShooter() - run the shooter at the speed commanded
     */
    public void runShooter(double targetVelocity)
    {
        
        if (targetVelocity == 0.0)
        {
           stopShooter();
            SmartDashboard.putNumber("Current Velocity", 0.0);
            return;
        }    
        
        // Show the commanded velocity on the SmartDashboard
        SmartDashboard.putNumber("Target Velocity", targetVelocity);

        //read PID coefficients from SmartDashboard
        double kP = SmartDashboard.getNumber("P Gain", 0.0);
        double kI = SmartDashboard.getNumber("I Gain", 0.0);
        double kD = SmartDashboard.getNumber("D Gain", 0.0);
        double kF = SmartDashboard.getNumber("Feed Forward", 0.0);

        //Update gains on the controller
        m_speedControl.updateGains(kP, kI, kD, kF);

        //Update the target velocity and get back the current velocity
        double currentVelocity = m_speedControl.runVelocityPIDF(targetVelocity);

        //Show the Current Velocity, Error, and Current Output Percent on the SDB
        SmartDashboard.putNumber("Current Velocity", currentVelocity);
        SmartDashboard.putNumber("Error", m_speedControl.getError());
        SmartDashboard.putNumber("Current Output Percent", m_speedControl.getOutputPercent());
    }
    /**
     * void stopShooter() - Stop the Shooter by simply turning off the motors instead of commanding Velocity PID to 0.0
     */
    public void stopShooter()
    {
        m_speedControl.m_motor1.set(ControlMode.PercentOutput, 0.0);
    }
}
