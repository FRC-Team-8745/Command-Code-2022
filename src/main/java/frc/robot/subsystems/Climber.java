package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

//TODO: TEST

// Climber CAN ID = 5 for Right, 6 for left
public class Climber extends SubsystemBase {
	// Calling our variables
	private final CANSparkMax m_rightClimber = new CANSparkMax(5, MotorType.kBrushless);
	private final CANSparkMax m_leftClimber = new CANSparkMax(6, MotorType.kBrushless);
	private ClimberState stateRight = ClimberState.OFF;
	private ClimberState stateLeft = ClimberState.OFF;

	public enum ClimberState {
		UP(0.75),
		SETUP(0.1),
		OFF(0),
		SETDOWN(-0.1),
		DOWN(-0.75);
		public double speed;
		private ClimberState(double speed) {
			this.speed = speed;
		}
	}
	private void configNEO(CANSparkMax id, boolean isinverted) {
		id.restoreFactoryDefaults();
		id.setOpenLoopRampRate(0.5);
		id.setIdleMode(IdleMode.kBrake);
		id.setInverted(isinverted);
	}

	public Climber() {
		configNEO(m_leftClimber, false);
		configNEO(m_rightClimber, false);
	}

	@Override
	public void periodic() {
		m_leftClimber.set(stateLeft.speed);
		m_rightClimber.set(stateRight.speed);

		// This method will be called once per scheduler run

	}

	public void setStateLeft(ClimberState leftState) {
		this.stateLeft = leftState;
	}	
	public void setStateRight(ClimberState rightState) {
		this.stateRight = rightState;
	}
	

	public void setLeftSpeed(double leftSpeed) {
		m_leftClimber.set(leftSpeed);
	}
	public void setRightSpeed(double rightSpeed) {
		m_leftClimber.set(rightSpeed);
	}

	public void setLeftVoltage(double leftVolts) {
		m_leftClimber.setVoltage(leftVolts);
	}
	public void setRightVoltage(double rightVolts) {
		m_leftClimber.setVoltage(rightVolts);
	}

	public void stop() {
		m_leftClimber.setVoltage(0);
		m_rightClimber.setVoltage(0);
	}
}