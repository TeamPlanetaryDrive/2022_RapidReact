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
import org.opencv.imgcodecs.Imgcodecs;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

//use for the guidence through the camera
public class Vision extends SubsystemBase {

	CvSink cvSink;
	NetworkTableEntry totalEntry;
	public void init(){
		// get entries from NetworkTable
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
      	NetworkTable table = inst.getTable("datatable");
      	totalEntry = table.getEntry("Top Left Pixel");

		// create vision thread and camera feed
		new Thread(() -> {
			UsbCamera camera = CameraServer.startAutomaticCapture();
			camera.setResolution(640, 480);
	  
			cvSink = CameraServer.getVideo();
			CvSource balling = CameraServer.putVideo("We Balling", 640, 480);
	  
			Mat source = new Mat();
			while(!Thread.interrupted()) {
			  if (cvSink.grabFrame(source) == 0) {
				continue;
			  }
			  

			  balling.putFrame(redBall(source));
			}
		  }).start();
  	}

	public void saveImage(String filename){
		Mat source = new Mat();
		if(cvSink!=null){
			cvSink.grabFrame(source);
			Imgcodecs.imwrite(filename,source);
		}
	}

	public Mat makeKernel(int size) {
		Scalar k = new Scalar(255.0,255.0,255.0);
		Size s = new Size(size, size);
		return new Mat(s,CvType.CV_8UC1,k);
	}
	public Mat redBall(Mat out) {
		Scalar lb = new Scalar(32.0,28.0,96.0);
		Scalar ub = new Scalar(50.0,50.0,155.0);
		Mat kerny = makeKernel(3);
		//Imgproc.cvtColor(out, out, Imgproc.COLOR_BGR2HSV);
		Core.inRange(out, lb, ub, out);
		Imgproc.erode(out,out,kerny);
		Imgproc.dilate(out,out,makeKernel(6));
		Imgproc.erode(out,out,kerny);
		totalEntry.setDoubleArray(out.get(0,0));
		return out;

	}
}
