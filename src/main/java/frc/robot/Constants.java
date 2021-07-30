// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class CanConstants{
        // drivebase
        public static final int left_drivebase_1 = 4;
        public static final int left_drivebase_2 = 5;
        public static final int right_drivebase_1 = 2;
        public static final int right_drivebase_2 = 3; 
        //Intake  
        public static final int intakeMotor = 6;
        //VHopper
        public static final int vHopperMotor = 15;
        public static final int vHopperMotor2 = 13;
        //Ball tower 
        public static final int ballTowerMotor = 9;
        //Shooter
        public static final int shooterMotor1 = 11;
        public static final int shooterMotor2 = 12;

        public static final int shooterGateMotor = 10;

        public static final int climberMotor = 20;

        
    }
    public static final class DriveConstants{
        // encoder ports
        public static final int[] kLeftEncoderPorts = new int[] { 0, 1 };
        public static final int[] kRightEncoderPorts = new int[] { 2, 3 };
        public static final boolean kLeftEncoderReversed = false;
        public static final boolean kRightEncoderReversed = true;

        // drivemeode
        public static final int driveMode_Tank = 0;
        public static final int driveMode_SplitArcade = 1;
        public static final int driveMode_RocketSpin = 2;

        public static final int m_driveMode = driveMode_SplitArcade;

        // other
        public static final double slewRate = 0.1;
        public static final int kEncoderCPR = 1024;
        public static final double kWheelDiameterInches = 6;
        public static final double kEncoderDistancePerPulse =
                // Assumes the encoders are directly mounted on the wheel shafts
                (kWheelDiameterInches * Math.PI) / (double) kEncoderCPR;
    }
    public static final class OIConstants{
        public static final int kDriverControllerPort = 0;
        public static final int kOperatorControllerPort = 1;
    }
    public static final class ShooterConstants{
        // Shooter Velocity Tolerance
     
        public static final int kShooterTolerance = 300;
        //double _kP, double _kI, double _kD, double _kF, int _kIzone, double _kPeakOutput
        public static final Gains kGains_Falcon = new Gains( 0.35, 0.0, 0.0, 0.0525, 0,  1.00);
		
        //Velocities used for shooter
        public static double kTestVelocity = 3060.0;
		public static double kShooterGateSpeed = 1.0;
        

        public static final double kAutoLine = 2175.0; 
        public static final double kWallShot = 1450.0;
        public static final double kTrenchShot = 2375.0;
        public static final double kDeepTrenchShot = 2550.0;

		public static final double kShooterGateTimout = 0.2; 
    }
    public static final class PneumaticConstants {
        //Intake Pistons
        public static final int kIntakePistonRetract = 2;
        public static final int kIntakePistonDeploy = 3;   

        //Pneumatic Constants
        public static final int kHoodRetract = 4;
            //Retracted means that the shooter hood touches the shooter path
        public static final int kHoodDeploy = 5;
            //Deployed means that the shooter hood is away from the shooter path

    }
    public static final class GainsConstants{

        public static final double kP = 0.0;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        public static final double kF = 0.0;


    }

}
 