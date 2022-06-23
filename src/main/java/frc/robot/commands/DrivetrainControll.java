package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;


public class DrivetrainControll extends CommandBase{
    private final Drivetrain drive;

    public DrivetrainControll(Drivetrain drive) {
      this.drive = drive;
      addRequirements(this.drive);
    }
    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    }
    @Override
    public boolean isFinished(){
      return false;
    }
    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }
}
