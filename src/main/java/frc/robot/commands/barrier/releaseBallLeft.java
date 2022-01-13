/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.barrier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class releaseBallLeft extends CommandBase {
  
  private final double RELEASE_TIME = 2; //seconds
  private int timer;
  /**
   * Creates a new releaseBallLeft.
   */
  public releaseBallLeft() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.Gates);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.Gates.setAngleLeft(Robot.Gates.DOWN_ANGLE);
    timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.Gates.setAngleLeft(Robot.Gates.UP_ANGLE);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (timer++) >= RELEASE_TIME / .02;
  }
}
