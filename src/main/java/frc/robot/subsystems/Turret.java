// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.kLimelightLensHeight;
import static frc.robot.Constants.kLimelightMountAngleDegrees;
import static frc.robot.Constants.kVisionTapeHeight;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.net.PortForwarder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.ShooterTable.*;
import static frc.robot.Constants.*;

public class Turret extends SubsystemBase {
	private CANSparkMax turret = new CANSparkMax(7, MotorType.kBrushless);
	private SparkMaxPIDController pid = turret.getPIDController();
	private RelativeEncoder encoder = turret.getEncoder();
	private Servo linearActuator = new Servo(1);
	private TurretState turretState = TurretState.STOP;
	private boolean flipInProgress = false;
	private boolean flippingLeft = false;

	public enum TurretState {
		LIMELIGHT, ODOMETRY, MANUAL, STOP;
	}

	public boolean atTarget = false;

	public boolean autoTurretEnabled = true;

	public double turretOffset;

	/** Creates a new Turret. */
	public Turret() {
		LimelightConfig();
		turret.restoreFactoryDefaults();
		turret.setIdleMode(IdleMode.kBrake);

		pid.setP(0.2);
		pid.setOutputRange(-1, 1);

		linearActuator.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
	}

	@Override
	public void periodic() {
		updateTurret(turretState);
		// This method will be called once per scheduler run

	}

	public void setTurretState(TurretState turretState) {
		this.turretState = turretState;
	}

	private void updateTurret(TurretState stateUpdate) {
		switch (stateUpdate) {
			case LIMELIGHT:

				break;
			case ODOMETRY:

				break;
			case MANUAL:

				break;
			case STOP:

				break;
		}
	}

	public boolean limelightAlign() {
		if (!flipInProgress) {
			// Proportional constant
			double kP = (0.02);
			// Minimum power needed to make the turret move
			double minimumPower = 0.03;
			// The allowed error from the center
			double allowedError = 0.35;
			// Distance from the center of the hub
			double tx = this.tx.getDouble(0);
			// Speed to set the turret to, defaults to 0
			double turretSpeed = 0;

			if (Math.abs(tx) > allowedError)
				turretSpeed = (kP * tx + ((minimumPower) * Math.signum(tx)));

			set(-turretSpeed);

			linearActuator.set(linearActuatorInterpolator.getInterpolatedValue(getDistance()));

			atTarget = (Math.abs(tx) < allowedError) ? true : false;
		}
		return atTarget;
	}

	public void set(double speed) {
		if (!(Math.signum(speed) == 1 && !atLimitLeft()) ^ !(Math.signum(speed) == -1 && !atLimitRight()))
			turret.set(speed);
		else
			turret.set(0);
	}

	public boolean atLimitLeft() {
		if (getTurretDegrees() > 180)
			return true;
		return false;
	}

	public boolean atLimitRight() {
		if (getTurretDegrees() < -180)
			return true;
		return false;
	}

	public double getTurretDegrees() {
		return getTurretRotations() * 360;
	}

	public double getTurretRotations() {
		return encoder.getPosition() / kTurretRatio;
	}

	private static final NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
	private final NetworkTableEntry tx = limelightTable.getEntry("tx");
	private static final NetworkTableEntry ty = limelightTable.getEntry("ty");
	private static final NetworkTableEntry tv = limelightTable.getEntry("tv");

	public void LimelightConfig() {
		PortForwarder.add(5800, "limelight.local", 5800);
		PortForwarder.add(5801, "limelight.local", 5801);
		PortForwarder.add(5802, "limelight.local", 5802);
		PortForwarder.add(5803, "limelight.local", 5803);
		PortForwarder.add(5804, "limelight.local", 5804);
		PortForwarder.add(5805, "limelight.local", 5805);
	}

	public static double getDistance() {
		// Calculate distance to the hub
		double angleToGoalDegrees = kLimelightMountAngleDegrees + ty.getDouble(0);
		double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
		return (kVisionTapeHeight - kLimelightLensHeight) / Math.tan(angleToGoalRadians);

	}

	public static boolean hasTarget() {
		if (tv.getDouble(0) == 1)
			return true;
		return false;
	}
}
