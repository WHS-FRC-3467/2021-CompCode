package frc.robot.Subsystems.Shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RunShooter extends CommandBase{
    
    private final ShooterSubsystem m_shooter;
    private final double m_targetVelocity;
    Timer time;

    public RunShooter(ShooterSubsystem shooterSubsys, double targetVelocity)
    {
        time = new Timer();
        m_shooter = shooterSubsys;
        m_targetVelocity = targetVelocity;
        // addRequirements(m_shooter);
    }
    @Override
    public void initialize() {
        time.start();
    }
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        m_shooter.runShooter(m_targetVelocity);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean isFinished)
    {
        m_shooter.runShooter(0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false;
    }
}