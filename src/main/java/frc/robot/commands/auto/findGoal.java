/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
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
//import frc.robot.commands.grip.*;

public class findGoal extends CommandBase {
  NetworkTableInstance inst;
  NetworkTable table;
  NetworkTableEntry goalPosition;
  double[] defaultArray;
  final int IMAGEWIDTH = 320;
  double goalX;
  double goodRange;

  boolean close = false;
  int counter;

  public findGoal() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    addRequirements(Robot.Cameras, Robot.Drive);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    counter = 57;
    System.out.println("initialized findGoal");
    inst = NetworkTableInstance.getDefault();
    table = inst.getTable("GRIP/goalContours"); //myLinesReport
    goalPosition = table.getEntry("centerX"); //length
    inst.startClientTeam(2856);
    inst.startDSClient();

    defaultArray = new double[0];
  }
  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    if (goalPosition.getDoubleArray(defaultArray).length == 0) {
      System.out.println("ran fG execute()");
      // int direction = (int) (goalX / Math.abs(goalX));
      //System.out.println("direction: " + direction);
      // Robot.Drive.drive(.46, -.46);
      if(counter-- < 38) {
        // System.out.println("ran m2G execute()");
        Robot.Drive.drive(.47, -.46);
        System.out.println("rotated");
        if(counter == 0)
          counter = 100;
      }else{
        Robot.Drive.drive(0, 0);
      }

    } 
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return goalPosition.getDoubleArray(defaultArray).length > 0;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    System.out.println("findGoal end");
    for(Double d : goalPosition.getDoubleArray(defaultArray))
      System.out.println(d);
    if(goalPosition.getDoubleArray(defaultArray).length == 0)
      System.out.println("ERRORRRRRRRRRRRR");
    Robot.Drive.drive(0,0);
  }
}
