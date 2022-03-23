/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
//import frc.robot.commands.robotMovement;

//use this for anything on th drivetrain like guiding electricty or something (likely redundent so delete if un needed)
public class DriveTrain extends SubsystemBase {
  // Put methods for controlling this subsystem here. Call these from Commands.

  /*
   * //Wood Robot Code Jaguar w_left = new Jaguar(0); Jaguar w_right = new
   * Jaguar(1);
   * 
   * DifferentialDrive robotDrive = new DifferentialDrive(w_left, w_right);
   */

  // Metal Robot Code
  //Talon leftMotor = new Talon(RobotMap.LEFT_MOTOR_CHANNEL);
  //Talon rightMotor = new Talon(RobotMap.RIGHT_MOTOR_CHANNEL);
  DifferentialDrive robotDrive;
  static final double r2o2 = Math.sqrt(2)/2;
  double thrust = 0.75;
  public static final int ARCADE=-1,WILLIAM=0,BBALL=1,BURGERKING=2,JAEGER=3;
  private double stationaryTolerance = 0.05;
  Victor lMotor, rMotor;

  public DriveTrain() {
    // calls the subsystem to let it know that it needs to be called as a subsystem
    super();
    lMotor = new Victor(RobotMap.LEFT_MOTOR_CHANNEL);
    rMotor = new Victor(RobotMap.RIGHT_MOTOR_CHANNEL);
    rMotor.setInverted(true);
    robotDrive = new DifferentialDrive(lMotor, rMotor);
    robotDrive.setSafetyEnabled(false);
    // setDefaultCommand(new robotMovement());
  }

  public double[] getDriveSpeed(int mode) {
    double yaxis = RobotMap.XController.getLeftY();
    double xaxis = RobotMap.XController.getLeftX();
    double dpadAngle = RobotMap.XController.getPOV()*(Math.PI/180);
    double dxaxis = Math.sin(dpadAngle);
    double dyaxis = -Math.cos(dpadAngle);
    if(dpadAngle < 0) {
      dxaxis = 0;
      dyaxis = 0;
    }
    double mag = Math.sqrt(yaxis*yaxis+xaxis*xaxis);
    double theta = Math.atan2(yaxis,xaxis);
    double left = 0, right = 0;
    if(!(Math.abs(xaxis) < stationaryTolerance && Math.abs(yaxis) < stationaryTolerance)) {
      switch(mode){
        case WILLIAM: 
          left = thrust*((xaxis-yaxis)*r2o2+0.66*(dxaxis-dyaxis)*r2o2);
          right = thrust*((-xaxis-yaxis)*r2o2+0.66*(-dxaxis-dyaxis)*r2o2);
          System.out.println(left + ", " + right);
          //System.out.println("SCHPEED: " + Math.sqrt(Math.pow(lMotor.get(), 2) + Math.pow(rMotor.get(), 2)));
          break;
        case BBALL:
          double c = 1;
          //double k = 1;
          double fixit = 0;
          //double y = mag * Math.sin(theta);
          // System.out.println("(" + xaxis + ", " + yaxis + ")");
          //double ye = 2/(1 + Math.exp(-k*y)) - 1;
          left = -thrust * fixit * yaxis * Math.sqrt(Math.pow(mag,2) + Math.pow(c,2) + 2*mag*c*Math.cos(theta));
          right = -thrust * fixit * yaxis * Math.sqrt(Math.pow(mag,2) + Math.pow(c,2) - 2*mag*c*Math.cos(theta));
          break;
        case BURGERKING:
          double speed = Math.sqrt(Math.pow(lMotor.get(),2)+Math.pow(rMotor.get(),2));
          double ratio = Math.min(1,speed/1);
          double tuorn = (1-ratio) * xaxis + ratio * xaxis * Math.abs(yaxis);
          left = thrust*((tuorn-yaxis)*r2o2);
          right = thrust*((-tuorn-yaxis)*r2o2);
          System.out.println(rMotor.getChannel() + " motor kingspeed:" + rMotor.get());
          break;
        case JAEGER:
          left = thrust;
          right = thrust;
        break;
      }
    }
    double[] lr = {left,right};
    return lr;
  }
  
  /*
  static double getLiftSpeed() {
    double right = RobotMap.XController.getRightBumper()?1.0:0.0;
    double left = RobotMap.XController.getLeftBumper()?-1.0:0.0;
    return right + left;
  }
  */


  public void drive(double left, double right) {
    //robotDrive.tankDrive(left, right);
  }

  public DifferentialDrive getDrive() {
    return robotDrive;
  }

  public MotorController getSPRight() {
    return rMotor;
  }

  public MotorController getSPLeft() {
    return lMotor;
  }

  public void periodic(){
    int mode = WILLIAM;
    if(mode == ARCADE){
      robotDrive.arcadeDrive(-RobotMap.XController.getLeftY(),RobotMap.XController.getLeftX());
      //m_drive.curvatureDrive(-RobotMap.XController.getLeftY(),RobotMap.XController.getLeftX(), RobotMap.XController.getLeftStickButtonPressed());
    }
    else if(!(RobotMap.XController.getLeftStickButtonPressed() || RobotMap.XController.getRightStickButtonPressed() )) {
      double[] speeds = getDriveSpeed(mode);
      drive(speeds[0], speeds[1]);
    }
  }


}
