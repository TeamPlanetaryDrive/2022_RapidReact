/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//import frc.robot.commands.grip.*;

public class turnToGoal extends CommandBase {
  NetworkTableInstance inst;
  NetworkTable table;
  NetworkTableEntry goalPosition;
  NetworkTableEntry goalWidth;
  double[] defaultArray;
  final int IMAGEWIDTH = 320;
  double goalX;
  double goodRange;
  int counter;

  boolean close = false;

  public turnToGoal() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    addRequirements(Robot.Cameras, Robot.Drive);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    inst = NetworkTableInstance.getDefault();
    table = inst.getTable("GRIP/goalContours");
    goalPosition = table.getEntry("centerX");
    goalWidth = table.getEntry("width");
    inst.startClientTeam(2856);
    inst.startDSClient();

    defaultArray = new double[1];
    defaultArray[0] = Integer.MAX_VALUE;
    counter = 40;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    if (goalPosition.getDoubleArray(defaultArray).length > 0) {
      System.out.println("ran tTG execute()");
      goalX = goalPosition.getDoubleArray(defaultArray)[0] - IMAGEWIDTH / 2;
      // System.out.println("goal position: " + goalPosition.getDoubleArray(defaultArray)[0]);
      // System.out.println("image width: " + IMAGEWIDTH / 2);
      // System.out.println("goalX: " + goalX);
      int direction = (int) (goalX / Math.abs(goalX));
      // System.out.println("direction: " + direction);
      if(counter-- < 15) {
      Robot.Drive.drive(.4 * direction, (-.4) * direction);
      // System.out.println("rotated");
      if(counter==0)counter=40;
      }else{
        Robot.Drive.drive(0,0);
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    double[] cont = goalWidth.getDoubleArray(defaultArray);
    if (cont.length == 0)
      return true;
    goodRange = cont[0] / 3;
    // System.out.println("goodRange: " + goodRange);
    // System.out.println("goalX*2: " + goalX * 2);
    // System.out.println("END: " + (Math.abs(goalX) * 2 < goodRange));
    return Math.abs(goalX) * 2 < goodRange;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    System.out.println("turnToGoAl END");
    Robot.Drive.drive(0,0);
  }
}
