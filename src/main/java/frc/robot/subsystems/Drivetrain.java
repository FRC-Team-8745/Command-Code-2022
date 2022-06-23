package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static frc.robot.Constants.*;
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
    private Field2d m_field = new Field2d();
    private DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(getGyro(), startPos());
    private PIDController leftController = new PIDController(kP, kI, kD);
    private final DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);


	public Drivetrain() {
        configNEO(m_right, true);
        configNEO(m_left, false);
	}

    private void configNEO(CANSparkMax id, boolean isinverted ){
        id.restoreFactoryDefaults();
        id.setOpenLoopRampRate(0.5);
		id.setIdleMode(IdleMode.kCoast);
        id.setInverted(isinverted);
    }
	@Override
	public void periodic() {
		// This method will be called once per scheduler run
        m_odometry.update(getGyro(), RotationToMeters(m_leftEncoder), RotationToMeters(m_rightEncoder));
        m_field.setRobotPose(getPosition());
        SmartDashboard.putData("Field 2D", m_field);
	}
// Methods
	public void setSpeed(double speed) {
		m_left.set(speed);
	}

	public void setVoltage(double volts) {
		m_left.setVoltage(volts);
	}

	public void stop() {
		m_left.setVoltage(0);
        m_right.setVoltage(0);
	}
    public Rotation2d getGyro(){
        Rotation2d rotate = new Rotation2d(Math.toRadians(-imu.getYaw() + kRobotStartRotY));
        return rotate;
    }
    public Pose2d startPos(){
        Pose2d xy = new Pose2d(kRobotStartPosX, kRobotStartPosY, getGyro());
        return xy;
    }
    public double RotationToMeters(RelativeEncoder encoder){
        return (((encoder.getPosition() * Math.PI) / kDriveGearbox) * 6) / 39.37;
    }
    public Pose2d getPosition(){
        return m_odometry.getPoseMeters();
    }
    public void arcadeDrive(double fwd, double sideways){
        m_drive.arcadeDrive(fwd, sideways);
    }
    public void setMaxOutput(double maxOutput){
        m_drive.setMaxOutput(maxOutput);
    }
    public void resetEncoders(){
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
    }
    public void resetGyro(){
        imu.zeroYaw();
    }
}
