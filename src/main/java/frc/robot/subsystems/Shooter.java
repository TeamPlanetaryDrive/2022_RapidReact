package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase{

    private Victor motorL, motorR, motorC;

    private Encoder shooterEnc;

    public Shooter() {
        motorL = new Victor(RobotMap.LEFT_SHOOTER_CHANNEL);
        motorR = new Victor(RobotMap.RIGHT_SHOOTER_CHANNEL);
        motorC = new Victor(RobotMap.SHOOTER_AIM_CHANNEL);
        shooterEnc = new Encoder(RobotMap.SHOOTER_ENCODER_CHANNEL_A, RobotMap.SHOOTER_ENCODER_CHANNEL_B);
        motorR.setInverted(true);

        shooterEnc.setDistancePerPulse(4./256.); //[n/q] sets to distance of n every q pulses
    }

    public void spin(double left, double right) {
        motorL.set(left);
        motorR.set(right);
    }

    public void rotate(double central) {
        motorC.set(central);
    }
}
