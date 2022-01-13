/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {
  //DoubleSolenoid testSolenoid = new DoubleSolenoid(RobotMap.SHOOTER_PISTON_CHANNEL_1, RobotMap.SHOOTER_PISTON_CHANNEL_2);
  /**
   * Creates a new Shooter.
   */
  public Shooter() {
  

  }

  public void setLauncherSpeed(double speed){
    RobotMap.shootL.set(speed);
    RobotMap.shootR.set(-speed);
  }

  public void setPistonState(boolean up){
    if(up)
      RobotMap.framePiston.set(DoubleSolenoid.Value.kReverse);
    else
      RobotMap.framePiston.set(DoubleSolenoid.Value.kForward);
  }

  public double getPosition(){
    return RobotMap.frameEncoder.getDistance();
  }
  
  public void setWindowMotorSpeed(double speed) {
    RobotMap.windowMotor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
