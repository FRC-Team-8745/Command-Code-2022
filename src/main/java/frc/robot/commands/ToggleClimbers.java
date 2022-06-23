package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Climber.ClimberState;

public class ToggleClimbers extends CommandBase{
    private final Climber climber;
    private final ClimberState stateLeft;
	private final ClimberState stateRight;
    public ToggleClimbers(Climber climber, ClimberState stateLeft, ClimberState stateRight) {
      this.climber = climber;
      this.stateLeft = stateLeft;
	  this.stateRight = stateRight;
      addRequirements(this.climber);
    }
    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
		climber.setStateLeft(stateLeft);
		climber.setStateRight(stateRight);
    }
    @Override
    public boolean isFinished(){
      return false;
    }
    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
      climber.setStateLeft(ClimberState.OFF);
	  climber.setStateRight(ClimberState.OFF);
    }
}