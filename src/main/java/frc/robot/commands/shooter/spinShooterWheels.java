/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
// no alan!
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class spinShooterWheels extends CommandBase {
  double wheelSpeed;
  private final double RELEASE_TIME = 4; //seconds
  int timer;
  /**
   * Creates a new spinWheels.
   */
  public spinShooterWheels(double speed) {
    addRequirements(Robot.Launcher);
    wheelSpeed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.Launcher.setLauncherSpeed(wheelSpeed);
    timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.Launcher.setLauncherSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (timer++) >= RELEASE_TIME / .02;
  }
}
