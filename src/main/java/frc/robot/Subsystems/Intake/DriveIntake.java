// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.Intake;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class DriveIntake extends CommandBase {
  /** Creates a new runVhopper. */
  private final IntakeSubsystem m_intake;
  private final DoubleSupplier m_speedSupplier;
  private final double m_speed;

  public DriveIntake(IntakeSubsystem intakeSubsystem, double speed, DoubleSupplier speedSupplier) {
    m_intake = intakeSubsystem;
    m_speed = speed;
    m_speedSupplier = speedSupplier;
    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = (m_speedSupplier != null) ? m_speedSupplier.getAsDouble() : m_speed;
    m_intake.driveIntake(-speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.driveIntake(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
