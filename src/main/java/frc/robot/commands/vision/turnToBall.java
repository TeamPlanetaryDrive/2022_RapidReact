package frc.robot.commands.vision;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class turnToBall extends CommandBase {

    NetworkTableEntry contourInfo;
    boolean finish;
    
    public turnToBall() {
        addRequirements(Robot.Cameras, Robot.Drive);
        contourInfo = NetworkTableInstance.getDefault().getTable("datatable").getEntry("Ball Contours");
    }

    public void initialize() {
        finish = false;
        System.out.println("mmm balls");
    }

    public void execute() {

    }

    public boolean isFinished() {
        return true;
    }

    public void end(boolean interrupted) {

    }

}
