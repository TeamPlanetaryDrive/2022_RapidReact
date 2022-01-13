/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import frc.robot.commands.auto.*;
import frc.robot.commands.barrier.*;
import frc.robot.commands.*;

/**
 * This class is what binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  public OI() {
    // teleop lift controls
    // //RobotMap.button8_left.whileHeld(new ManualLift());
    RobotMap.button3_right.whenPressed(new auto2());
    RobotMap.button2_right.whenPressed(new findGoal());
    // RobotMap.button2_right.whenPressed(new prepareForIntake());
    // intake (spinning wheels inwards)
    // RobotMap.button1_right.whileHeld(new spinShooterWheels(0.4));
    // shoot (spinning wheels outwards)
    // RobotMap.button1_right.whileHeld(new spinShooterWheels(-0.4));
    RobotMap.button4_right.whenPressed(new turnToGoal());
    RobotMap.button5_right.whenPressed(new moveToGoal());
    // RobotMap.button4_left.whenPressed(new prepShootLow());
    // RobotMap.button5_left.whenPressed(new prepShootHigh());
    // RobotMap.button8_right.whenPressed(new releaseBallLeft());
    // RobotMap.button9_right.whenPressed(new releaseBallRight());
    // friccaroo
  }
}
