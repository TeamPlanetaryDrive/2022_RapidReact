package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.commands.vision.turnToGoal;

public class auto1 extends CommandBase {
    
    int frameCount = 0;
    int moveDuration = 12;
    int aimDuration = 4;
    int spinDuration = 8;

    public auto1() {
        addRequirements(Robot.Cameras, Robot.Drive, Robot.Gun, Robot.Spintake);
    }

    public void initialize() {
        frameCount = 0;
    }

    public void execute() {
        if(frameCount < moveDuration)
            Robot.Drive.drive(-.8, -.8);
        else {
            new turnToGoal();
            if(frameCount < (moveDuration + aimDuration))
                Robot.Gun.rotate(.4);
            else
                Robot.Spintake.spin(1,1);
        }
        frameCount++;
    }

    public boolean isFinished() {
        return false || frameCount > (moveDuration + aimDuration + spinDuration);
    }

    public void end(boolean interrupted) {
    }

}
