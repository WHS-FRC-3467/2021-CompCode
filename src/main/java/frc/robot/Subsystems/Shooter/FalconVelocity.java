package frc.robot.Subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CanConstants;

public class FalconVelocity extends SubsystemBase
{ //implements ISpeedControl
     /* Hardware */
     TalonFX m_motor1 = new TalonFX(CanConstants.shooterMotor1);
     TalonFX m_motor2 = new TalonFX(CanConstants.shooterMotor2);
 
     /* Gains */
     double m_kP = 0.0;
     double m_kI = 0.0;
     double m_kD = 0.0;
     double m_kF = 0.0;
 
    /* Current Limiting */
    private SupplyCurrentLimitConfiguration talonCurrentLimit;
    private final boolean ENABLE_CURRENT_LIMIT = true;
    private final double CONTINUOUS_CURRENT_LIMIT = 25; //amps
    private final double TRIGGER_THRESHOLD_LIMIT = 35; //amps
    private final double TRIGGER_THRESHOLD_TIME = .2; //secs


     public FalconVelocity()
     {
 
         /* Factory Default all hardware to prevent unexpected behaviour */
         m_motor1.configFactoryDefault();
         m_motor2.configFactoryDefault();
 
         /* Config sensor used for Primary PID [m_Velocity] */
         m_motor1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 30);
 
         //m_motor1.configClosedloopRamp(0.3);
         //m_motor2.configClosedloopRamp(0.3);        
         
        /* Instead of throttle ramping, use current limits to moderate the wheel acceleration */
         talonCurrentLimit = new SupplyCurrentLimitConfiguration(ENABLE_CURRENT_LIMIT,
                CONTINUOUS_CURRENT_LIMIT, TRIGGER_THRESHOLD_LIMIT, TRIGGER_THRESHOLD_TIME);
 
         m_motor1.configSupplyCurrentLimit(talonCurrentLimit);
         m_motor2.configSupplyCurrentLimit(talonCurrentLimit);
 
         /* Set motors to Coast */
         m_motor1.setNeutralMode(NeutralMode.Coast);
         m_motor2.setNeutralMode(NeutralMode.Coast);
         
         /*
          * Phase sensor accordingly. 
          * Positive Sensor Reading should match Green (blinm_king) Leds on Talon
          */
         m_motor1.setSensorPhase(false);
         m_motor2.setSensorPhase(false);
         
         /* Config the peak and nominal outputs */
         m_motor1.configNominalOutputForward(0.0, 30);
         m_motor1.configNominalOutputReverse(0.0, 30);
         m_motor1.configPeakOutputForward(1.0, 30);
         m_motor1.configPeakOutputReverse(0.0, 30); // Don't go in reverse
 
         m_motor2.configNominalOutputForward(0.0, 30);
         m_motor2.configNominalOutputReverse(0.0, 30);
         m_motor2.configPeakOutputForward(1.0, 30);
         m_motor2.configPeakOutputReverse(0.0, 30); // Don't go in reverse
 
         /* Set up voltage compensation to maintain consistent velocities across a range of battery voltages */
         m_motor1.configVoltageCompSaturation(12.0);
         m_motor1.enableVoltageCompensation(true);
         m_motor2.configVoltageCompSaturation(12.0);
         m_motor2.enableVoltageCompensation(true);

         /* Invert motor2 and have it follow motor1 */
         m_motor2.follow(m_motor1);
         m_motor2.setInverted(true);
     }
 
     public void updateGains(double kP, double kI, double kD, double kF)
     {
         // if PIDF coefficients have changed, write new values to controller
         if((m_kP != kP)) { m_motor1.config_kP(0, kP, 30); m_kP = kP; }
         if((m_kI != kI)) { m_motor1.config_kI(0, kI, 30); m_kI = kI; }
         if((m_kD != kD)) { m_motor1.config_kD(0, kD, 30); m_kD = kD; }
         if((m_kF != kF)) { m_motor1.config_kF(0, kF, 30); m_kF = kF; }
     }
 
     public int runVelocityPIDF(double targetVelocity)
     {
         // Convert RPM to raw units per 100ms
         double targetVelocity_UnitsPer100ms = targetVelocity * 2048 / 600;
                 
         // Set Velocity setpoint
         m_motor1.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
 
         // Get current speed and convert back to RPM
         return (int)((double)m_motor1.getSelectedSensorVelocity() * 600 / 2048);
     }
 
     public double getError()
     {
         return m_motor1.getClosedLoopError();
     }
 
     public double getOutputPercent()
     {
         return (m_motor1.getMotorOutputPercent() * 100);
     }
 
     public void stop()
     {
         m_motor1.set(ControlMode.PercentOutput, 0.0);
     }
  
}
