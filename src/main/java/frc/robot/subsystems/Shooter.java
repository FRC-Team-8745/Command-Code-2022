package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import static frc.robot.Constants.*;
import static frc.robot.ShooterTable.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Intake CAN ID = 4
public class Shooter extends SubsystemBase {
	// Calling our variables
	private final CANSparkMax m_shooter = new CANSparkMax(4, MotorType.kBrushless);
	private final RelativeEncoder m_shooterEncoder = m_shooter.getEncoder();
	private State state = State.OFF;

	public enum State {
		SHOOT(3000, "SHOOT"),
		UNLOAD(-1135, "UNLOAD"),
		OFF(0, "OFF");

		public double speed;
        public String string;
		private State(double speed, String string) {
			this.speed = speed;
            this.string = string;
		}
	}

	public Shooter() {
		m_shooter.restoreFactoryDefaults();
		m_shooter.setOpenLoopRampRate(0.2);
		m_shooter.setIdleMode(IdleMode.kCoast);
	}

	@Override
	public void periodic() {
		setRPM(state.speed);
        SmartDashboard.putString("Shooter State", state.string);
        SmartDashboard.putNumber("Shooter Speed", getRPM());
		// This method will be called once per scheduler run

	}

	public void setState(State state) {
		this.state = state;
	}

	// Sets the RPM of the shooter motor
	public void setRPM(double rpm) {
		m_shooter.setVoltage((rpm / kmaxRPM) * kbatteryMax);
	}

	public void setVoltage(double volts) {
		m_shooter.setVoltage(volts);
	}

	public void stop() {
		m_shooter.setVoltage(0);
	}
    public double getRPM() {
        return 
        m_shooterEncoder.getVelocity();
        
    }
}