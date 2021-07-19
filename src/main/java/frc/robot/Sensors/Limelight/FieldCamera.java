/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Sensors.Limelight;

import frc.robot.Sensors.Limelight.Limelight.StreamMode;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class FieldCamera extends SubsystemBase {

  
  public FieldCamera() {
  
      //
      // Run Limelight Camera
      //
      // Default to Driver Mode
      Limelight.setDriverMode();

      // Place Secondary stream in lower right of Primary stream
      Limelight.setStreamMode(StreamMode.ePIPMain);


    // Run one USB camera
     runOne();

    // Run two USB cameras
    // runTwo();
  }
  
  private void runOne() {
    
  UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture("MS Lifecam Camera", 0);
      //camera1.setResolution(320, 240);
      //camera1.setFPS(15);
  camera1.setResolution(640, 480);
  camera1.setExposureAuto();
  camera1.setWhiteBalanceAuto();
      camera1.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
      
      // These settings should be less than 1mbps streaming to Shuffleboard.
      // Make sure that you don’t change anything on the Shuffleboard side, just leave the defaults and
      // -1 as the compression so you don’t waste CPU cycles re-compressing the mjpg stream.
  }
}


