package frc.robot.subsystems;

import static frc.robot.Constants.kDiameter;
import static frc.robot.Constants.kDriveGearbox;
import static frc.robot.Constants.kHubCenterX;
import static frc.robot.Constants.kHubCenterY;
import static frc.robot.Constants.kRobotStartPosX;
import static frc.robot.Constants.kRobotStartPosY;
import static frc.robot.Constants.kRobotStartRotY;

import com.kauailabs.navx.frc.AHRS;
// Spark Max Imports
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


// Left Wheels CAN ID = 2
// Right Wheels CAN ID = 1
public class Drivetrain extends SubsystemBase {
	// Calling our variables
	private final AHRS imu = new AHRS(Port.kUSB1);
	private final CANSparkMax m_left = new CANSparkMax(2, MotorType.kBrushless);
	private final CANSparkMax m_right = new CANSparkMax(1, MotorType.kBrushless);
	private final RelativeEncoder m_leftEncoder = m_left.getEncoder();
	private final RelativeEncoder m_rightEncoder = m_right.getEncoder();
	private Field2d m_field = new Field2d();
	private DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(getGyro(), startPos());

	private final DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

	public static double distanceToHub;

	public Drivetrain() {
		configNEO(m_right, false);
		configNEO(m_left, true);
	}

	private void configNEO(CANSparkMax id, boolean isinverted) {
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
		distanceToHub = getDistanceToHub();
	}

	private Rotation2d getGyro() {
		Rotation2d rotate = new Rotation2d(Math.toRadians(-imu.getYaw() + kRobotStartRotY));
		return rotate;
	}

	private Pose2d startPos() {
		Pose2d xy = new Pose2d(kRobotStartPosX, kRobotStartPosY, getGyro());
		return xy;
	}

	private double RotationToMeters(RelativeEncoder encoder) {
		return ((encoder.getPosition() / kDriveGearbox) * (Math.PI * kDiameter)) / 39.37;
	}

	private Pose2d getPosition() {
		return m_odometry.getPoseMeters();
	}

	public void arcadeDrive(double fwd, double sideways) {
		m_drive.arcadeDrive(fwd, -sideways);
	}

	private double getDistanceToHub() {
		Pose2d pose = getPosition();
		double differenceY = pose.getY() - kHubCenterY;
		double differenceX = pose.getX() - kHubCenterX;

		return Math.sqrt((differenceY * differenceY) + (differenceX * differenceX)) * 39.37;
	}

}