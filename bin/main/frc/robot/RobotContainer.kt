/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot

import frc.robot.commands.*
import frc.robot.subsystems.*
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.drive.DifferentialDrive

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.*

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private val m_exampleSubsystem: ExampleSubsystem = ExampleSubsystem()

  val m_autoCommand: ExampleCommand = ExampleCommand(m_exampleSubsystem)

  var m_autoCommandChooser: SendableChooser<Command> = SendableChooser()

  /** --- setup drivetrain --- **/
  val motorFrontLeft =  WPI_TalonSRX(0)
  val motorBackLeft =   WPI_TalonSRX(1)
  val motorFrontRight = WPI_TalonSRX(2)
  val motorBackRight =  WPI_TalonSRX(3)

  /* keep speeds same on motors on each side */
  val motorsLeft = SpeedControllerGroup(motorFrontLeft, motorBackLeft)
  val motorsRight = SpeedControllerGroup(motorFrontRight, motorBackRight)

  val drivetrain = DrivetrainSubsystem(DifferentialDrive(motorsLeft, motorsRight))

  val joystickDriveCommand = JoystickDrive(drivetrain, Joystick(0))


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  init {
    // Configure the button bindings
    configureButtonBindings()
    configureDefaultCommands()
    m_autoCommandChooser.setDefaultOption("Default Auto", m_autoCommand)
    SmartDashboard.putData("Auto mode", m_autoCommandChooser)
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  fun configureButtonBindings() {
  }

  /**
   * Set default commands for each subsystem
   * They will be run if no other commands are sheduled that have a dependency on that subsystem
   */
  fun configureDefaultCommands() {
    drivetrain.setDefaultCommand(joystickDriveCommand);
  }


  fun getAutonomousCommand(): Command {
    // Return the selected command
    return m_autoCommandChooser.getSelected()
  }
}
