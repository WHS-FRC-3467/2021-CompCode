package frc.robot.Sensors.IMU;

import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 *
 */
public class ZeroGyro extends InstantCommand {

	private final IMU m_imu;

	// this command should be inlined
	public ZeroGyro(IMU imu_subsys) {
		m_imu = imu_subsys;
	}
	
    public void execute() {
    	m_imu.zeroAngle();
    }
    
}
