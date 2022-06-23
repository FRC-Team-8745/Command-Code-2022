// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ToggleIntake;
//import frc.robot.commands.Autonomous;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Climber.ClimberState;
import frc.robot.subsystems.Intake.State;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.ToggleClimbers;
import frc.robot.subsystems.Climber;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
	// The robot's subsystems and commands are defined here...
	private final Intake m_intake = new Intake();
	private final Climber m_climber = new Climber();

	//private final Autonomous m_autoCommand = new Autonomous();

	// The Driver's Controllers
	private final XboxController m_operaterController = new XboxController(0);
	private final Joystick m_driverController = new Joystick(1);

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		// Configure the button bindings

		configureButtonBindings();
	}

	/*
	 * Use this method to define your button->command mappings. Buttons can be
	 * created by
	 * instantiating a {@link GenericHID} or one of its subclasses ({@link
	 * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
	 * it to a {@link
	 * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
	 */
	private void configureButtonBindings() {
		// Intake on
    	new JoystickButton(m_driverController, 3)
      	.whileActiveContinuous(new ToggleIntake(m_intake, State.IN), true)
      	.whenInactive(new ToggleIntake(m_intake, State.OFF), true);

		// Intake reverse
    	new JoystickButton(m_driverController, 5)
    	  .whileActiveContinuous(new ToggleIntake(m_intake, State.EX), true)
    	  .whenInactive(new ToggleIntake(m_intake, State.OFF), true);

		// Climbers up
		new Trigger(() -> {return m_operaterController.getPOV() == 0;})
			//									   	   climber import | left state | right state
			.whileActiveContinuous(new ToggleClimbers(m_climber, ClimberState.UP, ClimberState.UP))
			.whenInactive(new ToggleClimbers(m_climber, ClimberState.OFF, ClimberState.OFF));

		// Climbers down
		new Trigger(() -> {return m_operaterController.getPOV() == 180;})
		//									   	      climber import | left state | right state
			.whileActiveContinuous(new ToggleClimbers(m_climber, ClimberState.DOWN, ClimberState.DOWN))
			.whenInactive(new ToggleClimbers(m_climber, ClimberState.OFF, ClimberState.OFF));

		// Climbers Manual Calibration
		// Right Down
		new JoystickButton(m_driverController, 8)
		.and(new JoystickButton(m_driverController, 10))
		//									   	      climber import | left state | right state
			.whileActiveContinuous(new ToggleClimbers(m_climber, ClimberState.OFF, ClimberState.UP))
			.whenInactive(new ToggleClimbers(m_climber, ClimberState.OFF, ClimberState.OFF));
		//Left Up
		new JoystickButton(m_driverController, 8)
		.and(new JoystickButton(m_driverController, 9))
		//									   	      climber import | left state | right state
			.whileActiveContinuous(new ToggleClimbers(m_climber, ClimberState.UP, ClimberState.OFF))
			.whenInactive(new ToggleClimbers(m_climber, ClimberState.OFF, ClimberState.OFF));
		new JoystickButton(m_driverController, 8)
		.and(new JoystickButton(m_driverController, 12))
		//									   	      climber import | left state | right state
			.whileActiveContinuous(new ToggleClimbers(m_climber, ClimberState.OFF, ClimberState.DOWN))
			.whenInactive(new ToggleClimbers(m_climber, ClimberState.OFF, ClimberState.OFF));
		new JoystickButton(m_driverController, 8)
		.and(new JoystickButton(m_driverController, 11))
		//									   	      climber import | left state | right state
			.whileActiveContinuous(new ToggleClimbers(m_climber, ClimberState.DOWN, ClimberState.OFF))
			.whenInactive(new ToggleClimbers(m_climber, ClimberState.OFF, ClimberState.OFF));

    }

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	// public Command getAutonomousCommand() {
	// An ExampleCommand will run in autonomous
	// return m_autoCommand;
	// }
}
