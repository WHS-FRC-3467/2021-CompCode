// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.BallProcessor;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ManualProcessBalls extends CommandBase {
  private final BallProcessor m_ballProcessor;
  private final double m_speed;

  public ManualProcessBalls(BallProcessor ballprocessor, double speed) {
    m_speed = speed;
    m_ballProcessor = ballprocessor;
    addRequirements(m_ballProcessor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_ballProcessor.runBallTower(m_speed);
    m_ballProcessor.runVHopper(m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ballProcessor.runBallTower(0.0);
    m_ballProcessor.runVHopper(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
