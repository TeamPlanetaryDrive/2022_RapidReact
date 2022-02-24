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

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.imgcodecs.Imgcodecs;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

//use for the guidence through the camera
public class Vision extends SubsystemBase {

	CvSink cvSink;
	NetworkTableEntry totalEntry;
	NetworkTableEntry contourInfo;
	public void init(){
		// get entries from NetworkTable
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
      	NetworkTable table = inst.getTable("datatable");
      	totalEntry = table.getEntry("Top Left Pixel");
		contourInfo = table.getEntry("Contour Positions rawr xd nya~");

		// create vision thread and camera feed
		new Thread(() -> {
			UsbCamera camera = CameraServer.startAutomaticCapture();
			camera.setResolution(640, 480);
	  
			cvSink = CameraServer.getVideo();
			CvSource balling = CameraServer.putVideo("We Balling", 640, 480);
			CvSource contouring = CameraServer.putVideo("We Contouring", 640, 480);

			Mat source = new Mat();
			while(!Thread.interrupted()) {
			  if (cvSink.grabFrame(source) == 0) {
				continue;
			  }
			  
			  balling.putFrame(redBall(source));
			  contouring.putFrame(detectGoal(source));
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
	public Mat redBall(Mat in) {
		Mat out = in.clone();
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

	public Mat detectGoal(Mat in) {
		Mat out = in.clone();

		// transpose so horizontal ordering
		Core.transpose(out, out);

		// mask out goal
		Scalar lb = new Scalar(61.0,132.0,96.0);
		Scalar ub = new Scalar(103.0,194.0,187.0);
		Imgproc.cvtColor(out, out, Imgproc.COLOR_BGR2HLS);
		Core.inRange(out, lb, ub, out);

		// find contours
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(out,contours,hierarchy,Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);

		Imgproc.drawContours(out, contours, -1, new Scalar(100,100,100), 1);
		// find untransposed {x,y} positions for each contour
		ArrayList<double[]> positions = new ArrayList<double[]>();
		for(int i=0; i<contours.size(); i++){
			Moments p = Imgproc.moments(contours.get(i), false);
			positions.add(new double[]{(p.get_m01() / p.get_m00()),(p.get_m10() / p.get_m00())});
			System.out.println("["+positions.get(positions.size()-1)[0]+" ,"+positions.get(positions.size()-1)[1]+"]");
		}
		System.out.println(" --" );
		
		// create list of chains
		int longind = -1;
		ArrayList<ArrayList<Integer>> chains = new ArrayList<ArrayList<Integer>>();
		for(int i=contours.size()-1; i>=0; i--){
			// find close contour
			int j;
			for(j=i+1; j<contours.size() && !contourInRange(positions,i,j); j++);
			// add chain list of other contour if possible
			ArrayList<Integer> indlist = new ArrayList<Integer>();
			indlist.add(i);
			if(j<contours.size()){
				for(int ind : chains.get(j-i-1)){
					indlist.add(ind);
				}
			}
			chains.add(0,indlist);
			// set this to longest chain if it is
			if(longind<0 || chains.get(longind-i).size() < chains.get(0).size()){
				longind = i;
			}
			//System.out.println("moweee");
			for(int q = 0; q<indlist.size()-1; q++) {
				//System.out.println("WOWEEE");
				Point firstPoint = new Point(positions.get(q)[0], positions.get(q)[1]);
				Point nextPoint = new Point(positions.get(q+1)[0], positions.get(q+1)[1]);
				Imgproc.line(out, firstPoint, nextPoint, new Scalar(0,255,0), 10);
				
			}
			//System.out.println("balls in yo jaws");
		}
		
		if(longind > 0) {
		// create list of x positions
			ArrayList<Integer> goalchain = chains.get(longind);
			double[] xposs = new double[goalchain.size()];
			for(int i=0; i<goalchain.size(); i++){
				xposs[i] = positions.get(goalchain.get(i))[0];
			}
			// publish
			contourInfo.setDoubleArray(xposs);
		}
		Core.transpose(out, out);
		return out;
	}

	// if the other contour is valid to move to as a chain
	public boolean contourInRange(ArrayList<double[]> positions, int thisindex, int otherindex){
		return true;
		/*final int ymax = 16;
		final int xmax = 50;
		double[] thispos = positions.get(thisindex);
		double[] otherpos = positions.get(otherindex);
		boolean withiny = Math.abs(thispos[1] - otherpos[1]) <= ymax;
		boolean withinx = thispos[0] - otherpos[0] <= xmax && thispos[0] - otherpos[0] >= 0;
		return withinx && withiny;*/
	}
}
