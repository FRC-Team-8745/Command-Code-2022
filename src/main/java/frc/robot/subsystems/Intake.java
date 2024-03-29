package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Intake CAN ID = 4
public class Intake extends SubsystemBase {
	// Calling our variables
	private final CANSparkMax m_intake = new CANSparkMax(4, MotorType.kBrushless);
	// private final RelativeEncoder m_intakeEncoder = m_intake.getEncoder();
	private State state = State.OFF;

	public enum State {
		IN(0.5),
		EX(-1),
		OFF(0);

		public double speed;

		private State(double speed) {
			this.speed = speed;
		}
	}

	public Intake() {
		m_intake.restoreFactoryDefaults();
		m_intake.setOpenLoopRampRate(0.5);
		m_intake.setIdleMode(IdleMode.kCoast);
	}

	@Override
	public void periodic() {
		m_intake.set(state.speed);
	}

	public void setState(State state) {
		this.state = state;
	}

}