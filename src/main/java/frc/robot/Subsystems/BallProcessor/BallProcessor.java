// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.BallProcessor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CanConstants;

public class BallProcessor extends SubsystemBase {
  /** Creates a new BallProcessor. */
  // A combination of Vhopper and Ball tower
  private static TalonSRX m_vHopper = new TalonSRX(CanConstants.vHopperMotor);
  private static TalonSRX m_vHopper2 = new TalonSRX(CanConstants.vHopperMotor2);
  private static TalonSRX m_ballTowerMotor = new TalonSRX(CanConstants.ballTowerMotor);
  private static TalonSRX m_gateMotor = new TalonSRX(CanConstants.shooterGateMotor);

  public BallProcessor() {
    m_vHopper2.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void runBallTower (double speed){
    m_ballTowerMotor.set(ControlMode.PercentOutput, speed);
  }

  public void runVHopper (double speed){
    m_vHopper.set(ControlMode.PercentOutput, speed);
    m_vHopper2.set(ControlMode.PercentOutput, (speed) * 0.30);
  }
  
  public void runGateMotor (double speed){
    m_gateMotor.set(ControlMode.PercentOutput, speed);
  }

  public void stopGateMotor (){
    m_gateMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void stopBallTower (){
    m_ballTowerMotor.set(ControlMode.PercentOutput, 0.0);
  }

  public void stopVHopper (){
    m_vHopper.set(ControlMode.PercentOutput, 0.0);
    m_vHopper2.set(ControlMode.PercentOutput, 0.0);
  }

}
