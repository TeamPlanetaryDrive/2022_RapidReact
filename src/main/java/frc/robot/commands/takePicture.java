/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
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

public class takePicture extends CommandBase {

  private int count = 0;
  NetworkTableEntry totalEntry;

  public takePicture() {
    // Use addRequirements() here to declare subsystem dependencies.
     addRequirements(Robot.Cameras);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("initialized takePicture");
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("datatable");
    totalEntry = table.getEntry("Top Left Pixel");
    double[] bobert = totalEntry.getDoubleArray(new double[] {});
    System.out.print("[");
    for(int i = 0; i < bobert.length; i++) {
      System.out.print(bobert[i] + " ");
    }
    System.out.println("]");

    Robot.Cameras.saveImage("2022_RapidReact/Pictures/ballimg.jpg");
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
