package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;

public class MotorTurning extends SubsystemBase {

    public static void drive(double motorS, MotorController motor) {
        motorS = MathUtil.applyDeadband(motorS, 0.02);
        motorS = MathUtil.clamp(motorS,-1.0,1.0);
        motorS = Math.copySign(motorS * motorS, motorS);
        motor.set(motorS*1.0);
      }
}
