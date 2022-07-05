// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.net.PortForwarder;

public class Turret extends SubsystemBase {
	/** Creates a new Turret. */
	public Turret() {
		LimelightConfig();
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run

	}

	public static final NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
	public static final NetworkTableEntry tx = limelightTable.getEntry("tx");
	public static final NetworkTableEntry ty = limelightTable.getEntry("ty");
	public static final NetworkTableEntry ta = limelightTable.getEntry("ta");
	public static final NetworkTableEntry tv = limelightTable.getEntry("tv");
	public static final NetworkTableEntry camMode = limelightTable.getEntry("camMode");
	public static final NetworkTableEntry ledMode = limelightTable.getEntry("ledMode");
	public static final NetworkTableEntry pipeline = limelightTable.getEntry("pipeline");

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

	public boolean hasTarget() {
		if (tv.getDouble(0) == 1)
			return true;
		return false;
	}
}
