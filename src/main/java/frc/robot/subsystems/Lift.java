/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

//use to move the grippers up and down on the elevator
public class Lift extends SubsystemBase {

  public Lift() {
    // // super("Lift", 0.25, 0.01, 0);
    // super("Lift", 5.75, 0.000001, 0);

    // setOutputRange(-0.2, 0.5);
    // setAbsoluteTolerance(0.01);
    // getPIDController().setContinuous(false);
    // // RobotMap.liftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
    // setInputRange(0, 34);
  }

  public double inchesToSetpoint(double inches) {
    double setpoint = inches;
    return setpoint;
  }

  public void initDefaultCommand() {
    // setDefaultCommand(new manualLiftMove());
  }

  // inherited methods
  protected double returnPIDInput() {
    return 23423; // not the actual value; supposed to return the sensor value
  }

  protected void usePIDOutput(double output) {
    liftMove(output); // this is where the computed output value from the PIDController is applied to the motor
  }

  public void liftMove(double speed) {
    RobotMap.lift.set(speed);
  }

}
