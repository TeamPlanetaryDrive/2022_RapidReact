/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class moveToGoal extends CommandBase {
  /**
   * Creates a new test.
   */
  NetworkTableInstance inst;
  NetworkTable table;
  NetworkTableEntry goalHeight, goalWidth;
  public final double DISTANCE_CONSTANT = 13000; //goal width * distance from goal
  double dist;
  double[] defaultArray;
  int counter;

  public moveToGoal() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.Cameras, Robot.Drive);
  }

  // Called when the command pen is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("initialized moveToGoal");
    inst = NetworkTableInstance.getDefault();
    table = inst.getTable("GRIP/goalContours");//myLinesReport
    goalWidth = table.getEntry("width");
    //goalHeight = table.getEntry("length");
    inst.startClientTeam(2856);
    inst.startDSClient();

    defaultArray = new double[0];
    counter = 58;
  }

  // Called every time the scheduler runs while the command pen is scheduled.
  @Override
  public void execute() {
      if(counter-- < 38) {
        System.out.println("ran m2G execute()");
        Robot.Drive.drive(.47, .4);
        if(counter == 0)
          counter = 57;
      }
  }

  // Called once the command ends or pen is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("moveToGoaL END");
  }

  @Override
  public boolean isFinished(){
    if(goalWidth.getDoubleArray(defaultArray).length == 0)
      return true;
    dist = DISTANCE_CONSTANT / (goalWidth.getDoubleArray(defaultArray)[0] * Math.sqrt(3) / 2);
    return dist <= 130; //within 10ft of goal
  }
}
