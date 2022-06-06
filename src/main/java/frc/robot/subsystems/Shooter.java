package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
//import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Intake CAN ID = 4
public class Shooter extends SubsystemBase {
	// Calling our variables
	private final CANSparkMax m_shooter = new CANSparkMax(4, MotorType.kBrushless);
	// private final RelativeEncoder m_shooterEncoder = m_shooter.getEncoder();
	private State state = State.OFF;

	public enum State {
		SHOOT(0.5),
		UNLOAD(-0.25),
		OFF(0);

		public double speed;

		private State(double speed) {
			this.speed = speed;
		}
	}

	public Shooter() {
		m_shooter.setOpenLoopRampRate(0.5);
		m_shooter.setIdleMode(IdleMode.kCoast);
		m_shooter.restoreFactoryDefaults();
	}

	@Override
	public void periodic() {
		m_shooter.set(state.speed);

		// This method will be called once per scheduler run

	}

	public void setState(State state) {
		this.state = state;
	}

	// Sets the RPM of the shooter motor
	public void setRPM(double rpm) {
		m_shooter.setVoltage((rpm / kmaxRPM) * kbatteryMax);
	}

	public void setSpeed(double speed) {
		m_shooter.set(speed);
	}

	public void setVoltage(double volts) {
		m_shooter.setVoltage(volts);
	}

	public void stop() {
		m_shooter.setVoltage(0);
	}
}