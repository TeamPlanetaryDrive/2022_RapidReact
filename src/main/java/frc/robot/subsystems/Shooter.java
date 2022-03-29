package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase{

    private Talon motorC;

    private Encoder shooterEnc;

    public Shooter() {
        motorC = new Talon(RobotMap.SHOOTER_AIM_CHANNEL);
        //shooterEnc.setDistancePerPulse(4./256.); //[n/q] sets to distance of n every q pulses
    }

    public void rotate(double central) {
        motorC.set(central);
    }

    public double getRotateSpeeds(){
        double yaxis = RobotMap.XController.getRightY();
        return Math.abs(yaxis)>.2?yaxis*.65:0;
    }

    public double[] getSpinSpeeds(){
        double xaxis = RobotMap.XController.getRightX();
        xaxis = Math.abs(xaxis)>.2?xaxis:0;
        return new double[] {xaxis,xaxis};
    }

    public void periodic(){
        double rott = getRotateSpeeds();
        rotate(rott);
    }
}
