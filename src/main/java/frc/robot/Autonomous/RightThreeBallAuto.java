// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Subsystems.BallProcessor.BallProcessor;
import frc.robot.Subsystems.BallProcessor.ManualProcessBalls;
import frc.robot.Subsystems.DriveSubsystem.DriveSpeed;
import frc.robot.Subsystems.DriveSubsystem.DriveSubsystem;
import frc.robot.Subsystems.Shooter.RunShooter;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;
import frc.robot.Constants.ShooterConstants;
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class RightThreeBallAuto extends SequentialCommandGroup {
  /** Creates a new ThreeBallAuto. */

  private final ShooterSubsystem m_shooter;
  private final DriveSubsystem m_drive;
  private final BallProcessor m_processor;
  public RightThreeBallAuto(DriveSubsystem drive, ShooterSubsystem shooter, BallProcessor processor) {
    m_shooter = shooter; 
    m_drive = drive;
    m_processor = processor;
    addRequirements(m_shooter, m_drive);
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new RunShooter(m_shooter, ShooterConstants.kRightAuto).withTimeout(10),
      new WaitCommand(1),
      new ManualProcessBalls(m_processor, 60.0).withTimeout(8),
      new WaitCommand(5),
      new DriveSpeed(m_drive, 0.1, 0.0)
    );
  }
}


