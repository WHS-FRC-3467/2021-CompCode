// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ToggleDriveMode extends InstantCommand {
  /** Creates a new ToggleDriveMode. */
  DriveSubsystem m_drive;

  public ToggleDriveMode(DriveSubsystem drive) {
    m_drive = drive;
    addRequirements(m_drive);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(m_drive.getNeutralMode()){
      m_drive.brakeDriveMode();
    }
    else{
      m_drive.coastDriveMode();

    }
  }
}
