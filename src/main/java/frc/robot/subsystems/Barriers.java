/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Barriers extends SubsystemBase {
  
  public final double DOWN_ANGLE = 0, UP_ANGLE = 170;
  
  /**
   * Creates a new Barriers.
   */
  public Barriers() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setAngleLeft(double angle) {
    RobotMap.barrierL.setAngle(angle);
  }

  public void setAngleRight(double angle) {
    RobotMap.barrierR.setAngle(angle);
  }
}
