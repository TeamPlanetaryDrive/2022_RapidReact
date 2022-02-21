package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

public class lift extends CommandBase {
    private final double speed;

    public lift(double motorS) {
        motorS = MathUtil.applyDeadband(motorS, 0.02);
        motorS = MathUtil.clamp(motorS,-1.0,1.0);
        speed = Math.copySign(motorS * motorS, motorS);
        
    }
    @Override
    public void initialize() {
      RobotMap.liftMotor.set(speed*1.0);

    }
    public void end(boolean interrupted) {
      RobotMap.liftMotor.set(speed*0.0);
    }
}
