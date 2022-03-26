package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase{

    private Victor motorC;//motorL, motorR, motorC;

    private Encoder shooterEnc;

    public Shooter() {
        //motorL = new Victor(RobotMap.LEFT_SHOOTER_CHANNEL);
        //motorR = new Victor(RobotMap.RIGHT_SHOOTER_CHANNEL);
        motorC = new Victor(RobotMap.SHOOTER_AIM_CHANNEL);
        shooterEnc = new Encoder(RobotMap.SHOOTER_ENCODER_CHANNEL_A, RobotMap.SHOOTER_ENCODER_CHANNEL_B);
        //motorR.setInverted(true);

        shooterEnc.setDistancePerPulse(4./256.); //[n/q] sets to distance of n every q pulses
    }

    /*public void spin(double left, double right) {
        motorL.set(left);
        motorR.set(right);
    }*/

    public void rotate(double central) {
        motorC.set(central);
    }

    public double getRotateSpeeds(){
        double yaxis = RobotMap.XController.getRightY();
        return (Math.abs(yaxis)>.2)?(yaxis*.5):0;
    }

    public double[] getSpinSpeeds(){
        double xaxis = RobotMap.XController.getRightX();
        xaxis = Math.abs(xaxis)>.2?xaxis:0;
        return new double[] {xaxis,xaxis};
    }

    public void periodic(){
        double rott = getRotateSpeeds();
        //double[] spinny = getSpinSpeeds();
        //if(spinny[0] > 0)
        //    rott = 0;
        //spin(spinny[0],spinny[1]);
        rotate(rott);
    }
}
