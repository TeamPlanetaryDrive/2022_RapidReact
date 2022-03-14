/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.vision.turnToGoal;

/**
 * This class is what binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    private static final int LEFT_STICK_PRESS = 0, RIGHT_STICK_PRESS = 1;    

  public OI() {
    
    /*
    teleop lift controls
    //RobotMap.button8_left.whileHeld(new ManualLift());
    RobotMap.button3_right.whenPressed(new auto2());
    RobotMap.button2_right.whenPressed(new findGoal());
    RobotMap.button2_right.whenPressed(new prepareForIntake());
    intake (spinning wheels inwards)
    RobotMap.button1_right.whileHeld(new spinShooterWheels(0.4));
    shoot (spinning wheels outwards)
    RobotMap.button1_right.whileHeld(new spinShooterWheels(-0.4));
    RobotMap.button4_right.whenPressed(new turnToGoal());
    RobotMap.button5_right.whenPressed(new moveToGoal());
    RobotMap.button4_left.whenPressed(new prepShootLow());
    RobotMap.button5_left.whenPressed(new prepShootHigh());
    RobotMap.button8_right.whenPressed(new releaseBallLeft());
    RobotMap.button9_right.whenPressed(new releaseBallRight());
    friccaroo
    */
    RobotMap.aButton.whenPressed(new turnToGoal());    
    RobotMap.bButton.whenPressed(new testCommand());
    RobotMap.leftBumper.whenHeld(new lift(-1.0));
    RobotMap.rightBumper.whenHeld(new lift(1.0));
    RobotMap.leftStickButton.whenHeld(new movement(LEFT_STICK_PRESS));
    RobotMap.rightStickButton.whenHeld(new movement(RIGHT_STICK_PRESS));

    /*
    Goals for Xbox Controller Button Pressing Mapping
    -------------------------------------------------
    D-Pad: Fine Movement Control (X)
    Left Stick: Movement (X)
    Right Stick: Manual Shooter Control (X)
    Left Trigger: Spintake Wheels (H)
    Right Trigger: Spin Shooter Wheels (H)
    Left Bumper: Open Gates for a time interval (H/P)
    Right Bumper: Group Command Shoot (P)
    X: Turn to Ball (P)
    Y: Climb (P)
    A: Turn to Goal (P)
    B: ? [Reset?, Debug?] (P)
    */
    
    
  }
}
