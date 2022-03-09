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
    double lightThrust = 1;
    boolean finish = false;
    double[] defArray = {0,0,0,0};
    
    public turnToGoal() {
        this.addRequirements(Robot.Cameras, Robot.Drive);
        contourInfo = NetworkTableInstance.getDefault().getTable("datatable").getEntry("Contour Positions rawr xd nya~");
    }

    public void initialize() {
        System.out.println("hoobop");
        finish = false;
    }

    public void execute() {
        contourPositions = contourInfo.getDoubleArray(defArray);
        goalSize = contourPositions[0];
        goalX = contourPositions[2];
        goalY = contourPositions[1];
        goalSlope = contourPositions[3];
        xDiff = goalX - camCenter;
        if(goalSize < 0) { //case where goal is not seen
            Robot.Drive.drive(-lightThrust/2, lightThrust/2);
            System.out.println("no see");
        } else if(Math.abs(xDiff) < tolerance) { //case where goal is seen, but it is in the center
            finish = true;
            System.out.println("we did it");
        } else { //case where goal is seen
            if(xDiff > 0) { //goal on right side of image
                Robot.Drive.drive(lightThrust/2, -lightThrust/2);
                System.out.println("right");
            } else { //goal on left side of image
                Robot.Drive.drive(-lightThrust/2, lightThrust/2);
                System.out.println("left");
            }
        }
    }

    public boolean isFinished() {
        return super.isFinished() || finish;
    }

    public void end(boolean interrupted) {
        Robot.Drive.drive(0, 0);
    }

}
