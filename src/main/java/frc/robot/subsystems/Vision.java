/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.cameraserver.*;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

//use for the guidence through the camera
public class Vision extends SubsystemBase {

  public void init(){
    // // Axis camera (fixed IP)
    // AxisCamera aCamera = CameraServer.addAxisCamera("10.28.56.10");
		// aCamera.setFPS(15);
		// aCamera.setResolution(RobotMap.CAMERA_RESOLUTION_WIDTH, RobotMap.CAMERA_RESOLUTION_HEIGHT);

		// USB camera (default = 0)
		UsbCamera uCamera = CameraServer.startAutomaticCapture();
		uCamera.setFPS(15);
		uCamera.setResolution(320, 240);
		CvSink cvSink = CameraServer.getVideo();
      	CvSource outputStream = CameraServer.putVideo("Blur", 640, 480);

      	Mat source = new Mat();
      	Mat output = new Mat();

		while(!Thread.interrupted()) {
			if (cvSink.grabFrame(source) == 0) {
			continue;
			}
			Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
			outputStream.putFrame(output);
  		}
  	}
}
