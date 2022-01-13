/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.cameraserver.*;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//use for the guidence through the camera
public class Vision extends SubsystemBase {

  public void init(){
    // // Axis camera (fixed IP)
    // AxisCamera aCamera = CameraServer.getInstance().addAxisCamera("10.28.56.10");
		// aCamera.setFPS(15);
		// aCamera.setResolution(RobotMap.CAMERA_RESOLUTION_WIDTH, RobotMap.CAMERA_RESOLUTION_HEIGHT);

		// USB camera (default = 0)
		UsbCamera uCamera = CameraServer.getInstance().startAutomaticCapture();
		uCamera.setFPS(15);
		uCamera.setResolution(320, 240);
  }
}
