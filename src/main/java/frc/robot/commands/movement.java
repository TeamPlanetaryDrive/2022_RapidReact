package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class movement extends CommandBase {
    
    private static final int LEFT_STICK = 0, RIGHT_STICK = 1;
    private double thrust = 0.75, leftMotorSpeed = 0, rightMotorSpeed = 0;
    private double motorSpeeds[] = {leftMotorSpeed, rightMotorSpeed};

    public movement(int movementOrigin) {
        addRequirements(Robot.Drive);
        switch (movementOrigin) {
            case LEFT_STICK:
                leftMotorSpeed = -thrust;
                rightMotorSpeed = thrust;
                break;
            case RIGHT_STICK:
                leftMotorSpeed = thrust;
                rightMotorSpeed = -thrust;
                break;
            default:
                leftMotorSpeed = 0;
                rightMotorSpeed = 0;
                break;
        }
        motorSpeeds[0] = leftMotorSpeed;
        motorSpeeds[1] = rightMotorSpeed;
    }

    public void initialize() {
        Robot.Drive.drive(motorSpeeds[0], motorSpeeds[1]);
    }

    public void execute() {

    }

    public boolean isFinished() {
        return super.isFinished();
    }

    public void end(boolean interrupted) {
        Robot.Drive.drive(0, 0);
    }

}
