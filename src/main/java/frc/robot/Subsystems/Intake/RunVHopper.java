// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class RunVHopper extends CommandBase {
  /** Creates a new runVhopper. */
  IntakeSubsystem m_intake;
  Double m_speed;

  public RunVHopper(IntakeSubsystem intakeSubsystem, double speed) {
    m_intake = intakeSubsystem;
    m_speed = speed;
    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_speed > 0.9){
      m_intake.driveVHopper(-m_speed);
    }
    else if (m_speed > -0.9){
      m_intake.driveVHopper(m_speed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
