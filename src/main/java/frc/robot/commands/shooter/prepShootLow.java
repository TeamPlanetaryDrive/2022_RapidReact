/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class prepShootLow extends CommandBase {
  int targetAngle = 69;
  /**
   * Creates a new prepShootLow.
   */
  public prepShootLow() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.Launcher.setPistonState(false);
    double offset = Robot.Launcher.getPosition() - targetAngle;
    int direction = (int)(Math.abs(offset)/offset);
    Robot.Launcher.setWindowMotorSpeed(direction*0.7);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.Launcher.setWindowMotorSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(Robot.Launcher.getPosition()-targetAngle) < 1;
  }
}
