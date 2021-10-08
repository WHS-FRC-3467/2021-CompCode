// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Autonomous;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Subsystems.BallProcessor.BallProcessor;
import frc.robot.Subsystems.BallProcessor.ManualProcessBalls;
import frc.robot.Subsystems.DriveSubsystem.DriveSubsystem;
import frc.robot.Subsystems.DriveSubsystem.DriveTime;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Shooter.AutonomousShoot;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveWallShootThree extends SequentialCommandGroup {
  /** Creates a new DriveWallShootThree. */
  private final ShooterSubsystem m_shooter;
  private final DriveSubsystem m_drive;
  private final BallProcessor m_processor;
  private final IntakeSubsystem m_intake;

  public DriveWallShootThree(DriveSubsystem drive, ShooterSubsystem shooter, BallProcessor processor, IntakeSubsystem intake) {
    m_shooter = shooter; 
    m_drive = drive;
    m_processor = processor;
    m_intake = intake;
    addRequirements(m_shooter);
    addRequirements(m_drive);
    addRequirements(m_processor);
    addRequirements(m_intake);
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands( 
      new InstantCommand(m_intake::deployIntake, m_intake),
      new AutonomousShoot(shooter, ShooterConstants.kWallShot).withTimeout(0.1),
      new WaitCommand(1.0),
      new ManualProcessBalls(m_processor, 0.5,m_shooter).withTimeout(5.0),
      new InstantCommand(m_shooter::stopShooter, m_shooter),
      new DriveTime(m_drive, 2, -0.25)
    );
  }
}
