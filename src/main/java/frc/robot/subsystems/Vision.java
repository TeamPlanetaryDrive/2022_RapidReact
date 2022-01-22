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
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

//use for the guidence through the camera
public class Vision extends SubsystemBase {

  public void init(){
	new Thread(() -> {
		UsbCamera camera = CameraServer.startAutomaticCapture();
		camera.setResolution(640, 480);
  
		CvSink cvSink = CameraServer.getVideo();
		CvSource outputStream = CameraServer.putVideo("Blur", 640, 480);
  
		Mat source = new Mat();
		Mat output = new Mat();
		Scalar k = new Scalar(255.0,255.0,255.0);
		Size s = new Size(3.0, 3.0);
		Mat kerny = new Mat(s, CvType.CV_8UC1, k);
		System.out.println("vision");
		while(!Thread.interrupted()) {
			System.out.println(" wa ");
		  if (cvSink.grabFrame(source) == 0) {
			continue;
		  }
		  Scalar lb = new Scalar(100.0,100.0,100.0);
		  Scalar ub = new Scalar(255.0,255.0,255.0);
		  Core.inRange(source, lb, ub, output);
		  Imgproc.dilate(output, output, kerny);
		  //Imgproc.cvtColor(output, output, Imgproc.COLOR_BGR2GRAY);
		  //Imgproc.threshold(source, output, 170.0, 255.0, 0);
		  outputStream.putFrame(output);
		}
	  }).start();
  	}
}
