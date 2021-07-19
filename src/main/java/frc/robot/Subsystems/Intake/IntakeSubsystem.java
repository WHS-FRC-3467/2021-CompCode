// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CanConstants;
import frc.robot.Constants.PneumaticConstants;

public class IntakeSubsystem extends SubsystemBase {

  /** Creates a new IntakeSubsystem. */
  // PC Intake motor
  private TalonSRX m_intakeMotor = new TalonSRX(CanConstants.intakeMotor);
  private TalonSRX m_vHopper1 = new TalonSRX(CanConstants.vHopperMotor);

  // Intake Deploy/Retract solenoids
  private DoubleSolenoid m_intakePiston;

  public IntakeSubsystem() 
  {
    m_intakePiston = new DoubleSolenoid(PneumaticConstants.kIntakePistonDeploy, PneumaticConstants.kIntakePistonRetract);
  }
  
  public void deployIntake()
  {
      m_intakePiston.set(Value.kForward);
  }
  
  public void retractIntake() 
  {
      m_intakePiston.set(Value.kReverse);
  }

  //Reterns true if the intake is deployed
  public boolean isIntakeDeployed()
  {
      return m_intakePiston.get() == Value.kForward;
  }
  
  public void driveIntake(double speed)
  {
      m_intakeMotor.set(ControlMode.PercentOutput, speed);
  }

  //Runs Vhopper motors
  public void driveVHopper(Double speed) {
    m_vHopper1.set(ControlMode.PercentOutput, speed);
  }
}
