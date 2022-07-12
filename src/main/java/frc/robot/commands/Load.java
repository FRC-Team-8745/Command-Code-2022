// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Shooter;
import static frc.robot.subsystems.Shooter.LoaderState.*;

public class Load extends CommandBase {
  /** Creates a new Load. */
  boolean bool = false;
  private final Shooter loader;
  public Load(Shooter loader) {
	this.loader = loader;
	
	
    // Use addRequirements() here to declare subsystem dependencies.
	addRequirements(loader);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
	new SequentialCommandGroup(
		new InstantCommand(() -> loader.setLoaderState(LOAD)),
		new WaitCommand(1.3),
		new InstantCommand(() -> bool = true)).schedule();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
	  loader.setLoaderState(OFF);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return bool;
  }
}
