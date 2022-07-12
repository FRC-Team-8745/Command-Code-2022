// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;
import static  frc.robot.subsystems.Shooter.LoaderState.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import static frc.robot.subsystems.Shooter.ShooterState.*;

public class Shoot extends CommandBase {
  /** Creates a new Shoot. */
  boolean boole = false;
  private final Shooter shooter;
  public Shoot(Shooter shooter) {
	this.shooter = shooter;
   // Use addRequirements() here to declare subsystem dependencies.
   addRequirements(shooter);
}
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
	  new SequentialCommandGroup(
				new InstantCommand(() -> shooter.setShooterState(SHOOT)),
				new WaitCommand(2),
				new InstantCommand(() -> shooter.setLoaderState(LOAD)),
				new WaitCommand(1.75),
				new InstantCommand(() -> shooter.setLoaderState(OFF)),
				new WaitCommand(shooter.getTimeBetweenShots()),
				new InstantCommand(() -> shooter.setLoaderState(LOAD)),
				new WaitCommand(1.25),
				new InstantCommand(() -> boole = true)).schedule();
	}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
	  shooter.setShooterState(STOP);
	  shooter.setLoaderState(OFF);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return boole;
  }
}
