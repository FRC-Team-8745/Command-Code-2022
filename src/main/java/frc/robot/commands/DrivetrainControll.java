package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DrivetrainControll extends CommandBase {
	private final Drivetrain drivetrain;
	private final DoubleSupplier speed;
	private final DoubleSupplier rotation;
	private double r, s;

	public DrivetrainControll(Drivetrain drivetrain, DoubleSupplier speed, DoubleSupplier rotation) {
		this.drivetrain = drivetrain;
		this.speed = speed;
		this.rotation = rotation;
		addRequirements(this.drivetrain);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
		r = rotation.getAsDouble();
		s = speed.getAsDouble();
		if (Math.abs(s) <= .2) {
			s = 0;
		}
		if (Math.abs(r) <= .2) {
			r = 0;
		}
		drivetrain.arcadeDrive(s, r);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	public void end(boolean interrupted) {
	}
}
