// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

//This command Doesn't turn the shooter off 

public class AutonomousShoot extends CommandBase {
  /** Creates a new AutonomousShoot. */
  ShooterSubsystem m_shooter;
  double m_shooterSpeed;
  public AutonomousShoot(ShooterSubsystem shooter, double shooterSpeed) {
    m_shooter = shooter;
    m_shooterSpeed = shooterSpeed;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.runShooter(m_shooterSpeed);
    
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
