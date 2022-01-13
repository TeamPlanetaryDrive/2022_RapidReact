/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
//import frc.robot.commands.robotMovement;

//use this for anything on th drivetrain like guiding electricty or something (likely redundent so delete if un needed)
public class DriveTrain extends SubsystemBase {
  // Put methods for controlling this subsystem here. Call these from Commands.

  /*
   * //Wood Robot Code Jaguar w_left = new Jaguar(0); Jaguar w_right = new
   * Jaguar(1);
   * 
   * DifferentialDrive robotDrive = new DifferentialDrive(w_left, w_right);
   */

  // Metal Robot Code
  //Talon leftMotor = new Talon(RobotMap.LEFT_MOTOR_CHANNEL);
  //Talon rightMotor = new Talon(RobotMap.RIGHT_MOTOR_CHANNEL);
  DifferentialDrive robotDrive;

  public DriveTrain() {
    // calls the subsystem to let it know that it needs to be called as a subsystem
    super();
    robotDrive = new DifferentialDrive(RobotMap.lMotor, RobotMap.rMotor);
    robotDrive.setSafetyEnabled(false);
    // setDefaultCommand(new robotMovement());
  }

  

  public void drive(double left, double right) {
    robotDrive.tankDrive(left, right);
  }

  public DifferentialDrive getDrive() {
    return robotDrive;
  }

  public SpeedController getSPRight() {
    return RobotMap.rMotor;
  }

  public SpeedController getSPLeft() {
    return RobotMap.lMotor;
  }


}
