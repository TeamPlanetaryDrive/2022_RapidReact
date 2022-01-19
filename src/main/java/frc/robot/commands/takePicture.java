/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class takePicture extends CommandBase {

    private int count = 0;

  public takePicture() {
    // Use addRequirements() here to declare subsystem dependencies.
     addRequirements(Robot.Cameras);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("initialized takePicture");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("executed takePicture");
    count++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("ended takePicture");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return count > 3;
  }
}
