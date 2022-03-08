package frc.robot.commands.vision;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class turnToGoal extends CommandBase {
    
    NetworkTableEntry contourInfo;
    double[] contourPositions;
    double goalSize, goalX, goalY, goalSlope;
    double camCenter = 320.0;
    double tolerance = 20.0;
    double xDiff;
    double lightThrust = .3;
    public turnToGoal() {
        this.addRequirements(Robot.Cameras, Robot.Drive);
        contourInfo = NetworkTableInstance.getDefault().getTable("datatable").getEntry("Contour Positions rawr xd nya~");
    }

    public void initialize() {
        
    }

    public void execute() {
        contourPositions = contourInfo.getDoubleArray(new double[]{0,0,0,0});
        goalSize = contourPositions[0];
        goalX = contourPositions[1];
        goalY = contourPositions[2];
        goalSlope = contourPositions[3];
        xDiff = goalX - camCenter;
        if(goalSize < 0) { //case where goal is not seen
            Robot.Drive.drive(-lightThrust, lightThrust);
        } else if(xDiff < tolerance) { //case where goal is seen, but it is in the center
            return;
        } else { //case where goal is seen
            if(xDiff > 0) { //goal on right side of image
                Robot.Drive.drive(lightThrust/2, -lightThrust/2);
            } else { //goal on left side of image
                Robot.Drive.drive(-lightThrust/2, lightThrust/2);
            }
        }
    }

    public boolean isFinished() {
        return super.isFinished();
    }

    public void end(boolean interrupted) {
        Robot.Drive.drive(0, 0);
    }

}
