// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.ShooterConstants;

import frc.robot.Sensors.Limelight.Limelight;
import frc.robot.Subsystems.BallProcessor.BallProcessor;
import frc.robot.Subsystems.BallProcessor.ProcessBalls;
import frc.robot.Subsystems.Climber.ClimberSubsystem;
import frc.robot.Subsystems.Climber.RunClimber;
import frc.robot.Subsystems.DriveSubsystem.DriveSubsystem;
import frc.robot.Subsystems.DriveSubsystem.DriveTime;
import frc.robot.Subsystems.DriveSubsystem.SplitArcadeDrive;
import frc.robot.Subsystems.Intake.IntakeSubsystem;
import frc.robot.Subsystems.Intake.ToggleIntakeDrive;
import frc.robot.Subsystems.Intake.DriveIntake;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;

import frc.robot.control.XBoxControllerDPad;
import frc.robot.control.XboxController;
import frc.robot.control.XboxControllerButton;

import frc.robot.CommandGroups.ShootBalls;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */

public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final IntakeSubsystem m_intake = new IntakeSubsystem();
  private final ShooterSubsystem m_shooter = new ShooterSubsystem();
  private final ClimberSubsystem m_climber = new ClimberSubsystem();
  private final BallProcessor m_ballProcessor = new BallProcessor();

  public static XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);
  public static XboxController m_operatorController = new XboxController(OIConstants.kOperatorControllerPort);

  // Autonomous routines
  
  private final SequentialCommandGroup m_threeBallAuto = new SequentialCommandGroup (
    
    new InstantCommand(m_intake::deployIntake, m_intake),
    new ParallelCommandGroup (
      new DriveIntake(m_intake, 0.5, null).withTimeout(5.0),
      new ShootBalls(m_shooter, m_ballProcessor, ShooterConstants.kAutoLine, false, 1.0).withTimeout(5.0)
    ),
    new DriveTime(m_robotDrive, 3, -0.25)
  );
    
  private final SequentialCommandGroup m_driveWallShootThree = new SequentialCommandGroup (
    
    new DriveTime(m_robotDrive, 3, -0.25),
    new InstantCommand(m_intake::deployIntake, m_intake),
    new ParallelCommandGroup (
      new DriveIntake(m_intake, 0.5, null).withTimeout(5.0),
      new ShootBalls(m_shooter, m_ballProcessor, ShooterConstants.kWallShot, false, 1.0).withTimeout(5.0)
    )
  );


  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /** 
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Configure the button bindings
    configureButtonBindings();

    // Initialize Limelight vision processor
    Limelight.initialize();
    Limelight.setDriverMode();

    // Set up Autonomous Routine Chooser
    Shuffleboard.getTab("Driver Dash").add(m_chooser);
    m_chooser.addOption("Autoline Shot Auto", m_threeBallAuto);
    m_chooser.addOption("Wall Shot Auto", m_driveWallShootThree);

    //
    // Driver Controller Subsystem Defaults
    //
    // Split Arcade: forward/back leftY, right/left rightX
    m_robotDrive.setDefaultCommand(
        new SplitArcadeDrive(m_robotDrive, 
                            () -> m_driverController.getLeftY(), 
                            () -> m_driverController.getRightX()));

    //
    // Operator Controller Subsystem Defaults
    //
    m_climber.setDefaultCommand(new RunClimber(m_climber, 
                                              () -> m_operatorController.getRightY()));

    m_ballProcessor.setDefaultCommand(new ProcessBalls(m_ballProcessor, 
                                                        () ->  m_operatorController.getLeftTrigger(),
                                                        () ->  m_operatorController.getRightTrigger()
                                                      )
                                      );

    m_intake.setDefaultCommand(new DriveIntake(m_intake, 0, () -> m_operatorController.getLeftY()));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {  

        //
        // Driver Controller
        //
        
        //Deploy intake
        new XboxControllerButton(m_driverController, XboxController.Button.kBumperLeft)
        .whileActiveContinuous(new InstantCommand(m_intake::deployIntake, m_intake));

        //Retract intake
        new XboxControllerButton(m_driverController, XboxController.Button.kBumperRight)
        .whileActiveContinuous(new InstantCommand(m_intake::retractIntake, m_intake));
        
        //Toggle intake
        new XboxControllerButton(m_driverController, XboxController.Button.kA)
        .whileActiveContinuous(new ToggleIntakeDrive(m_intake));
         

        //
        // Operator Controller
        //
        
        //Shoot from autoline
        new XboxControllerButton(m_operatorController, XboxController.Button.kA)
        .whileHeld(new ShootBalls(m_shooter, m_ballProcessor, ShooterConstants.kAutoLine, false, 1.0));
        
        //Shoot from trench
        new XboxControllerButton(m_operatorController, XboxController.Button.kX)
        .whileHeld(new ShootBalls(m_shooter, m_ballProcessor, ShooterConstants.kDeepTrenchShot, true, 1.0)); 

        // hood up
	    	new XBoxControllerDPad(m_operatorController, XboxController.DPad.kDPadUp)
        .whileActiveContinuous(new InstantCommand(m_shooter::deployHood, m_shooter)); 
        
        //hood down
	    	new XBoxControllerDPad(m_operatorController, XboxController.DPad.kDPadDown)
        .whileActiveContinuous(new InstantCommand(m_shooter::retractHood, m_shooter)); 
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_chooser.getSelected();
  }
}
