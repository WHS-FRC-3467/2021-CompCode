// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.Climber;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CanConstants;

public class ClimberSubsystem extends SubsystemBase {
  /** Creates a new ClimberSubsystem. */

  TalonSRX m_climber = new TalonSRX(CanConstants.climberMotor);

  public ClimberSubsystem() {}

  @Override
  public void periodic() {}

  public void runClimber(double speed){
    m_climber.set(TalonSRXControlMode.PercentOutput, speed);
  }

}
