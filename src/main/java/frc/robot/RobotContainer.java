// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  // create an instance of the Drive and Intake classes
  public Drive drive = new Drive();
  public Intake intake = new Intake();

  // create a CommandxboxController
  // this allows you to tell the command scheduler to run a specific command on a button press
  private final CommandXboxController controller =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // set the default command of the drive instance to be the drive command created in the drive class.
    // this command will be run until another command that requires the drive instance is scheduled.
    // at that point, this command will be cancled untill the other command finishes.
    drive.setDefaultCommand(drive.driveCommand(controller::getLeftY, controller::getRightX));

    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}
   */
  private void configureBindings() {
    // tell the command scheduler to run the intake's set command whenever the controller's
    // "a" button is pressed and cancle it when the button is released
    controller.a().whileTrue(intake.setCommand(0.9));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // tell the command scheduler to run an empty command for autonomous
    return Commands.none();
  }
}
