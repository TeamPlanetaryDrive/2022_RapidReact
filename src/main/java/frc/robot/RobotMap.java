/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/* logitech attack 3 garbage xbox superiority gang :muscle: :triumph:
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
*/
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
        public static final double PERIODIC_UPDATE_PERIOD = 0.020; // Periodic update period (s)

        // Power Channels
        public static final int DRIVE_POWER_LEFT_FRONT = 837148, DRIVE_POWER_RIGHT_FRONT = 378149,
                        DRIVE_POWER_LEFT_REAR = 893149, DRIVE_POWER_RIGHT_REAR = 81340723, CAMERA_ONE_POWER = 0,
                        CAMERA_TWO_POWER = 1, VRM_POWER = 38578942;

        // Camera Resolution Dimensions
        public static final int CAMERA_RESOLUTION_WIDTH = 320, CAMERA_RESOLUTION_HEIGHT = 240;

        // piston based
        public static int GRIPPER_CHANNEL_A = 0, GRIPPER_CHANNEL_B = 2;

        // Drive system PID Parameters
        public static final double DRIVE_PID_POSITION_KP = 2.00, // 2.00
                        DRIVE_PID_POSITION_KI = 0.01, // 0.01
                        DRIVE_PID_POSITION_KD = 1.00, // 1.00
                        DRIVE_PID_ANGLE_KP = 0.02, DRIVE_PID_ANGLE_KI = 0.001, DRIVE_PID_ANGLE_KD = 0.0;

        // Digital (0-9, 10-25)
        public static int LEFT_MOTOR_CHANNEL = 0, RIGHT_MOTOR_CHANNEL = 1;

        // motor based
        public static int LIFT_CHANNEL = 2;
        //outlining spintake motors, placeholder channels
        public static int spinTakeWheel1 = 1;
        public static int spinTakeWheel2 = 2;
        
                        // enconder channels
        // not final yet
        public static int LIFT_ENC_CHANNEL_A = 0, LIFT_ENC_CHANNEL_B = 1;
        // LEFT_ENC_CHANNEL_A = 0,
        // LEFT_ENC_CHANNEL_B = 1,
        // RIGHT_ENC_CHANNEL_A = 2,
        // RIGHT_ENC_CHANNEL_B = 3;

        // states for gripper
        public static final boolean GRIPPER_EXTEND = true, GRIPPER_RETRACT = false;
        public static boolean pneumaticsStart = false;

        // HARDWARE
        // motors
        public static Victor lMotor, rMotor, liftMotor, SIW1, SIW2;
        public static MotorController lift;

        // state for lift motor
        public static boolean liftStart = false;

        // joystick variables
        /* Logitech Attack 3? more like Logitech Sucks 3 B)
        public static final int LEFT_JOYSTICK_PORT = 0, RIGHT_JOYSTICK_PORT = 1;
        public static final Joystick leftJoystick = new Joystick(LEFT_JOYSTICK_PORT),
                rightJoystick = new Joystick(RIGHT_JOYSTICK_PORT);
        public static JoystickButton button1_left, button2_left, button3_left, button4_left, button5_left, button8_left, button9_left;
        public static JoystickButton button1_right, button2_right, button3_right, button4_right, button5_right, button8_right;
        */
        public static JoystickButton aButton, bButton, xButton, yButton, backButton, startButton, leftBumper, rightBumper, leftStickButton, rightStickButton;
        public static final int XBOX_PORT = 0;
        public static final XboxController XController = new XboxController(XBOX_PORT);

        /* new stuff */
        public static final int CLIMB_MOTOR_CHANNEL_1 = 2856, CLIMB_MOTOR_CHANNEL_2 = 2857;
        public static final int WINDOW_MOTOR_CHANNEL = 2856;
        public static final int SHOOTER_PISTON_CHANNEL_FORWARD = 2856, SHOOTER_PISTON_CHANNEL_REVERSE = 2758;
        public static final int SHOOTER_MOTOR_CHANNEL_1 = 2858, SHOOTER_MOTOR_CHANNEL_2 = 2656;
        public static final int GATE_SERVO_CHANNEL_1 = 3457, GATE_SERVO_CHANNEL_2 = 3446;
        
        public static final int CLIMB_ENCODER_CHANNEL_A = 6969, CLIMB_ENCODER_CHANNEL_B = 6969;
        public static final int FRAME_ENCODER_CHANNEL_A = 2432, FRAME_ENCODER_CHANNEL_B = 3423;

        public static Victor climbL, climbR, shootL, shootR, windowMotor;
        public static Encoder climbEncoder, frameEncoder;
        public static Servo barrierL, barrierR;
        public static DoubleSolenoid framePiston;

        // For example to map the left and right motors, you could define the
        // following variables to use with your drivetrain subsystem.
        // public static int leftMotor = 1;
        // public static int rightMotor = 2;

        // If you are using multiple modules, make sure to define both the port
        // number and the module. For example you with a rangefinder:
        // public static int rangefinderPort = 1;
        // public static int rangefinderModule = 1;
        public static void init() {
                // initializing motors
                lMotor = new Victor(LEFT_MOTOR_CHANNEL);
                rMotor = new Victor(RIGHT_MOTOR_CHANNEL);
                liftMotor = new Victor(LIFT_CHANNEL);
                //SIW1 = new Victor(spinTakeWheel1);
                //SIW2 = new Victor(spinTakeWheel2);
                rMotor.setInverted(true);

                //map each button to a JoystickButton
                aButton = new JoystickButton(XController, 1);
                bButton = new JoystickButton(XController, 2);
                xButton = new JoystickButton(XController, 3);
                yButton = new JoystickButton(XController, 4);
                leftBumper = new JoystickButton(XController, 5);
                rightBumper = new JoystickButton(XController, 6);
                backButton = new JoystickButton(XController, 7);
                startButton = new JoystickButton(XController, 8);
                leftStickButton = new JoystickButton(XController, 9);
                rightStickButton = new JoystickButton(XController, 10);

                
                // lift encoder and motor setup
                // liftEncoder = new Encoder(LIFT_ENC_CHANNEL_A, LIFT_ENC_CHANNEL_B);
                // liftEncoder.setDistancePerPulse(distancePerPulse);
                // liftEncoder.reset();
                // liftEncoder.setReverseDirection(true);
                //lift = new Spark(LIFT_CHANNEL);
                //lift.setInverted(true);
                
                //New stuff
                /*climbL = new Victor(CLIMB_MOTOR_CHANNEL_1);
                climbR = new Victor(CLIMB_MOTOR_CHANNEL_2);
                shootL = new Victor(SHOOTER_MOTOR_CHANNEL_1);
                shootR = new Victor(SHOOTER_MOTOR_CHANNEL_2);
                windowMotor = new Victor(WINDOW_MOTOR_CHANNEL);
                climbEncoder = new Encoder(CLIMB_ENCODER_CHANNEL_A, CLIMB_ENCODER_CHANNEL_B);
                climbEncoder.setDistancePerPulse(0);
                frameEncoder = new Encoder(FRAME_ENCODER_CHANNEL_A, FRAME_ENCODER_CHANNEL_B);

                barrierL = new Servo(GATE_SERVO_CHANNEL_1);
                barrierR = new Servo(GATE_SERVO_CHANNEL_2);

                framePiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,SHOOTER_PISTON_CHANNEL_FORWARD, SHOOTER_PISTON_CHANNEL_REVERSE);
                */
        }
}