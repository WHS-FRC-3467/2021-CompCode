// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Autonomous;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Subsystems.BallProcessor.BallProcessor;
import frc.robot.Subsystems.BallProcessor.ManualProcessBalls;
import frc.robot.Subsystems.DriveSubsystem.DriveSubsystem;
import frc.robot.Subsystems.DriveSubsystem.DriveTime;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Shooter.AutonomousShoot;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
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
    addRequirements(m_shooter);
    addRequirements(m_drive);
    addRequirements(m_processor);
    addRequirements(m_intake);
    
    addCommands(
      new InstantCommand(m_intake::deployIntake),
      new AutonomousShoot(shooter, ShooterConstants.kAutoLine).withTimeout(0.1),
      new WaitCommand(1.0),
      new ManualProcessBalls(m_processor, 0.5).withTimeout(5.0),
      new InstantCommand(m_shooter::stopShooter),
      new DriveTime(m_drive, 1, -0.25)
    );
  }
}
