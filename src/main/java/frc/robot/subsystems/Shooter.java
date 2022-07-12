package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import static frc.robot.Constants.*;
import static frc.robot.ShooterTable.*;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Shooter CAN ID = 3
// Loader PWM Port = 0
public class Shooter extends SubsystemBase {
	// Calling our variables
	private final CANSparkMax m_shooter = new CANSparkMax(3, MotorType.kBrushless);
	private LoaderState loaderState = LoaderState.OFF;
	private final Spark m_loader = new Spark(0);
	private ShooterState shooterState = ShooterState.STOP;

	public enum LoaderState {
		LOAD(1),
		UNLOAD(-1),
		OFF(0);

		public double speed;

		private LoaderState(double speed) {
			this.speed = speed;
		}
	}

	public enum ShooterState {
		SHOOT, UNSHOOT, STOP;
	}

	public Shooter() {
		m_loader.setInverted(true);
		m_shooter.restoreFactoryDefaults();
		m_shooter.setOpenLoopRampRate(0.2);
		m_shooter.setIdleMode(IdleMode.kCoast);
		m_shooter.setInverted(true);
	}

	@Override
	public void periodic() {
		setLoaderSpeed(loaderState.speed);
		//SmartDashboard.putNumber("Shooter Speed", getRPM());
		// This method will be called once per scheduler run
		updateShooter(shooterState);
	}

	public void setLoaderState(LoaderState loaderState) {
		this.loaderState = loaderState;
	}

	public void setShooterState(ShooterState shooterState) {
		this.shooterState = shooterState;
	}

	// Sets the RPM of the shooter motor
	private void setRPM(double rpm) {
		m_shooter.setVoltage((rpm / kmaxRPM) * kbatteryMax);
	}

	private void stop() {
		m_shooter.stopMotor();
	}


	private void setLoaderSpeed(double speed) {
		m_loader.set(speed);
	}

	private double calculateSpeed() {
		if (Turret.hasTarget())
			return shooterRPMInterpolator.getInterpolatedValue(Turret.getDistance());
		else
			return shooterRPMInterpolator.getInterpolatedValue(Drivetrain.distanceToHub);
	}

	private void updateShooter(ShooterState stateUpdate) {
		switch (stateUpdate) {
			case SHOOT:
				setRPM(this.calculateSpeed());
				break;
			case UNSHOOT:
				this.setRPM(-1135);
				break;
			case STOP:
				this.stop();
				break;
		}
	}

	public double getTimeBetweenShots() {
		return shooterTimingInterpolator.getInterpolatedValue(Turret.getDistance());
	}

}