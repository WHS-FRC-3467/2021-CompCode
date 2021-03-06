// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems.BallProcessor.BallProcessor;
import frc.robot.Subsystems.DriveSubsystem.DriveSpeed;
import frc.robot.Subsystems.DriveSubsystem.DriveSubsystem;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
import frc.robot.CommandGroups.AutoIntakeController;
import frc.robot.CommandGroups.ShootBalls;
import frc.robot.Constants.ShooterConstants;
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ThreeBallAuto extends SequentialCommandGroup {
  /** Creates a new ThreeBallAuto. */
  private final ShooterSubsystem m_shooter;
  private final DriveSubsystem m_drive;
  private final BallProcessor m_processor;
  private final IntakeSubsystem m_intake;
  public ThreeBallAuto(DriveSubsystem drive, ShooterSubsystem shooter, BallProcessor processor, IntakeSubsystem intake) {
    m_shooter = shooter; 
    m_drive = drive;
    m_processor = processor;
    m_intake = intake;
    addRequirements(m_shooter, m_drive, m_processor, m_intake);
    
    // this should be fine accoring to: 
    // https://docs.wpilib.org/en/stable/docs/software/commandbased/command-groups.html#recursive-composition-of-command-groups
    addCommands(
      new ParallelCommandGroup(
        new AutoIntakeController(m_intake),
        new ShootBalls(m_shooter, m_processor, ShooterConstants.kAutoLine, false).withTimeout(7.0)
      ),
      new DriveSpeed(m_drive, -0.5, 0.0).withTimeout(1.5)
    );
  }
}
