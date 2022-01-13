/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;//new
import frc.robot.RobotMap;//new

/**
 * Add your docs here.
 */



public class Multi extends SubsystemBase {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  //DoubleSolenoid testSolenoid = new DoubleSolenoid(RobotMap.GRIPPER_CHANNEL_A, RobotMap.GRIPPER_CHANNEL_B);//new
  public Multi(){
    // setDefaultCommand(new liftBarrier());
  }

  //makes piston go up
  public void barrierUp () {

  }
  //makes piston go down
  public void barrierDown () {

  }
  //returns barrier state
  public boolean getBarrierState () {
    return false;
  }
  //rotates shooter wheels by desired amount
  public void rotateShooter (boolean highGoalAim) {
    /*if(highGoalAim) {
      testSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    else {
      testSolenoid.set(DoubleSolenoid.Value.kForward);
    }*/
  }
  //returns angle of shooter wheels
  public double getShooterAngle () {
    return 0;
  }
  //sets speed of the wheels
  public void hotWheels (double speed) {

  }
  //stop wheels
  public void stopWheels () {

  }
  //returns wheel speed
  public double getWheelSpeed () {
    return 0;
  }


}
