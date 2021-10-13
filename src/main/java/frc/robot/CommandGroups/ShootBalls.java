// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.CommandGroups;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.BallProcessor.BallProcessor;
import frc.robot.Subsystems.Shooter.ShooterSubsystem;

public class ShootBalls extends CommandBase {
  /** Creates a new ShootBalls. */
  ShooterSubsystem m_shooter;
  BallProcessor m_processor;
  double m_velocity;
  Boolean m_hoodPosition;
  double time;
  //hood position 
  //true = back = trench shot
  //false = forward = autoline shot
  public ShootBalls(ShooterSubsystem shooter, BallProcessor processor, double velocity, Boolean hoodPosition) {
    m_velocity = velocity;
    m_shooter = shooter;
    m_processor = processor;
    m_hoodPosition = hoodPosition;
    addRequirements(m_shooter, m_processor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    time = Timer.getFPGATimestamp();
    if(m_hoodPosition){
      m_shooter.retractHood();
    }
    else{
      m_shooter.deployHood();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.runShooter(m_velocity);

    if(Timer.getFPGATimestamp() - time > 0.25){
      m_processor.runVHopper(1.0);
      m_processor.runBallTower(1.0);
      if(m_shooter.isWheelAtSpeed()){
        m_processor.runGateMotor(1.0);
      }
      else{
        m_processor.runGateMotor(0.0);
      }
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
    m_shooter.runShooter(0.0);
    m_processor.runVHopper(0.0);
    m_processor.runBallTower(0.0);
    m_processor.runGateMotor(0.0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
