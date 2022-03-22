package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class intakeDTF extends CommandBase {
    
    private int frameCount;

    public intakeDTF() {
        addRequirements(Robot.Spintake);
    }

    public void initialize() {
        frameCount = 0;
    }

    public void execute() { 
        Robot.Spintake.spin(1,1);
        frameCount++;

    }

    public boolean isFinished() {
        return super.isFinished() || frameCount > 8;
    }

    public void end(boolean interrupted) {
        Robot.Spintake.spin(0,0);
    }

}
