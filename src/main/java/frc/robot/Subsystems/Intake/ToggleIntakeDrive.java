/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ToggleIntakeDrive extends CommandBase {
  IntakeSubsystem m_intake;
  Boolean m_intakeState;
  public ToggleIntakeDrive(final IntakeSubsystem intakeSubsys) {
    m_intake = intakeSubsys;
    addRequirements(m_intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  @Override
  public void initialize() {
    if (m_intake.isIntakeDeployed()){
      m_intake.retractIntake();
      m_intakeState = false;
    } 

    else {
      m_intake.deployIntake();
      m_intakeState = true;
    }
  }
  // Called when the command is initially scheduled.
  @Override
  public void execute() {
    if (m_intakeState){
      m_intake.driveIntake(1.0);
    }

     else {
      m_intake.driveIntake(0.0);
    }
  }   
}