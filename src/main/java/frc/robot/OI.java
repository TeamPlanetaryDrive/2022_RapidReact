/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import frc.robot.commands.*;

/**
 * This class is what binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    static double r2o2 = Math.sqrt(2)/2;
    static double thrust = 0.75;

    static double[] getDriveSpeed() {
      double yaxis = RobotMap.XController.getLeftY();
      double xaxis = RobotMap.XController.getLeftX();
      double dpadAngle = RobotMap.XController.getPOV()*(Math.PI/180);
      double dxaxis = Math.sin(dpadAngle);
      double dyaxis = -Math.cos(dpadAngle);
      if(dpadAngle < 0) {
        dxaxis = 0;
        dyaxis = 0;
      }
      double left = thrust*((xaxis-yaxis)*r2o2+0.66*(dxaxis-dyaxis)*r2o2);
      double right = thrust*((-xaxis-yaxis)*r2o2+0.66*(-dxaxis-dyaxis)*r2o2);
      double[] lr = {left,right};
      return lr;
    }
    
    static double getLiftSpeed() {
      double right = RobotMap.XController.getRightBumper()?1.0:0.0;
      double left = RobotMap.XController.getLeftBumper()?-1.0:0.0;
      return right + left;
    }
    
    
    
    

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
    RobotMap.bButton.whenPressed(new testCommand());
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
