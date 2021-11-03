// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.CommandGroups;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.Intake.IntakeSubsystem;

public class AutoIntakeController extends CommandBase {
  private final IntakeSubsystem m_intake;
  private boolean m_done;
  private boolean m_intakeDeployed;
  private double m_startTime;
  
  /** Creates a new IntakeController. */
  public AutoIntakeController(IntakeSubsystem intake) {
    m_intake = intake;

    // addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_done = false;
    // deploy intake on startup
    m_intake.deployIntake();
    m_intakeDeployed = true;
    m_startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double timeElapsed = Timer.getFPGATimestamp() - m_startTime;
    // wait at least 2 seconds to retract
    m_intake.driveIntake(1.0);
    if (timeElapsed >= 2.0 && m_intakeDeployed) {
      m_intake.retractIntake();
      m_intakeDeployed = false;
    }
    // then wait one more second to deploy again
    else if (timeElapsed >= 3.0 && !m_intakeDeployed) {
      m_intake.deployIntake();
      m_intakeDeployed = true;
      m_done = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_done;
  }
}
