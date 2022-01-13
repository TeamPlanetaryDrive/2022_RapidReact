/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class breakStartLine extends CommandBase {
  /**
   * Creates a new TestCommand.
   */
  public int suckIt; //pavan's totally real choice

  public breakStartLine() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.Drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("initialized breakStartLine");
    System.out.println("bsl");
    suckIt = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    Robot.Drive.drive(-.4, -.4);
    suckIt++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.Drive.drive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return suckIt > 100;
  }
}
