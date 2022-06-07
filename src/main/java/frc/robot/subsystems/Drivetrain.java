package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
//TODO: TEST

// Intake CAN ID = 4
public class Drivetrain extends SubsystemBase {
	// Calling our variables
    private final AHRS imu = new AHRS(Port.kUSB1);
	private final CANSparkMax m_left = new CANSparkMax(2, MotorType.kBrushless);
    private final CANSparkMax m_right = new CANSparkMax(1, MotorType.kBrushless);
	private final RelativeEncoder m_leftEncoder = m_left.getEncoder();
    private final RelativeEncoder m_rightEncoder = m_right.getEncoder();
    private final double kP = 0.2;
    private final double kI = 0.0;
    private final double kD = 0.0;
	private State state = State.OFF;
    private Field2d field = new Field2d();
    private DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(gyroAngle)
    private PIDController leftController = new PIDController(kP, kI, kD);

	public enum State {
		FWD(1),
		BWD(-1),
		OFF(0);
		public double speed;
		private State(double speed) {
			this.speed = speed;
		}
	}

	public Drivetrain() {
		m_left.setOpenLoopRampRate(0.5);
		m_left.setIdleMode(IdleMode.kCoast);
		m_left.restoreFactoryDefaults();
	}

	@Override
	public void periodic() {
		m_left.set(state.speed);

		// This method will be called once per scheduler run

	}

	public void setState(State state) {
		this.state = state;
	}

	public void setSpeed(double speed) {
		m_left.set(speed);
	}

	public void setVoltage(double volts) {
		m_left.setVoltage(volts);
	}

	public void stop() {
		m_left.setVoltage(0);
	}
    public double getGyro(){
        Rotation2d rotate = new Rotation2d();
        
    }
}