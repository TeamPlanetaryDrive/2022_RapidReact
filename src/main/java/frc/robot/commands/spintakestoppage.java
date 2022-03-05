package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.MathUtil;
import frc.robot.RobotMap;

public class spintakestoppage extends CommandBase {
    
    final double speed;

    public spintakestoppage(double motorS) {
        motorS = MathUtil.applyDeadband(motorS, 0.02);
        motorS = MathUtil.clamp(motorS,-1.0,1.0);
        speed = Math.copySign(motorS * motorS, motorS);
    }

    @Override
    public void initialize() {
        RobotMap.SIW1.set(speed*1.0);
        RobotMap.SIW2.set(speed*1.0);
    }

    public void execute() {

    }

    public void end(boolean interrupted) {
        RobotMap.SIW1.set(speed*1.0);
        RobotMap.SIW2.set(speed*1.0);
    }
}
