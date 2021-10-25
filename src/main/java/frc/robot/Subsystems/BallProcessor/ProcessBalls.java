// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.BallProcessor;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ProcessBalls extends CommandBase {
  /** Creates a new ProcessBalls. */
  private final BallProcessor m_ballProcessor;
  private final DoubleSupplier m_fwdSpeed;
  private final DoubleSupplier m_revSpeed;
  
  // This command is used to run all the Ball Processing Tower components using passed-in DoubleSuppiliers
  // (Most commonly driven by user controls, e.g. XBox Trigger axes)
  
  public ProcessBalls(BallProcessor ballprocessor, DoubleSupplier fwdSpeed, DoubleSupplier revSpeed) {
    m_fwdSpeed = fwdSpeed;
    m_revSpeed = revSpeed;
    m_ballProcessor = ballprocessor;
    addRequirements(m_ballProcessor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  
    // Use the Fwd Speed control. unless it is zero, then use the Rev Speed control
    double speed = m_fwdSpeed.getAsDouble();
    if (speed == 0.0) {
      speed = (-1.0) * m_revSpeed.getAsDouble();
    }
  
    m_ballProcessor.runVHopper(-speed);
    m_ballProcessor.runBallTower(speed);
    m_ballProcessor.runGateMotor(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_ballProcessor.runVHopper(0.0);
    m_ballProcessor.runBallTower(0.0);
    m_ballProcessor.runGateMotor(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
