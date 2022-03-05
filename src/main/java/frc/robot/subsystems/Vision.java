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
import edu.wpi.first.wpilibj.util.Color;

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

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat contProc = new Mat();
		Mat contOut = new Mat();
		Mat ballProc = new Mat();

		// create vision thread and camera feed
		new Thread(() -> {
			UsbCamera camera = CameraServer.startAutomaticCapture();
			camera.setResolution(640, 480);
	  
			cvSink = CameraServer.getVideo();
			CvSource balling = CameraServer.putVideo("We Balling", 640, 480);
			CvSource contouring = CameraServer.putVideo("We Contouring", 640, 480);

			while(!Thread.interrupted()) {
			  if (cvSink.grabFrame(contProc) == 0 || cvSink.grabFrame(ballProc) == 0) {
				continue;
			  }
			  
			  balling.putFrame(redBall(ballProc));
			  contouring.putFrame(detectGoal(contProc,contOut));

			  contProc.release();
			  contOut.release();
			  ballProc.release();
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

	public Mat detectGoal(Mat out, Mat contout) {
		// transpose so horizontal ordering
		Core.transpose(out, out);
		contout = Mat.zeros(out.size(),CvType.CV_8UC1);
		Imgproc.cvtColor(contout,contout,Imgproc.COLOR_GRAY2BGR);
		// mask out goal
		Scalar lb = new Scalar(61.0,132.0,96.0);
		Scalar ub = new Scalar(103.0,255.0,255.0);
		Imgproc.cvtColor(out, out, Imgproc.COLOR_BGR2HLS);
		Core.inRange(out, lb, ub, out);

		// find contours
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat();
		
		Imgproc.findContours(out,contours,hierarchy,Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);
		// Imgproc.drawContours(contout, contours, -1, new Scalar(100,100,100), 2);
		// find untransposed {x,y} positions for each contour
		ArrayList<Point> sortPositions = new ArrayList<Point>(); // top-left point, used to sort chains
		ArrayList<Point> centerPositions = new ArrayList<Point>(); // center of contour from moment
		ArrayList<RotatedRect> rects = new ArrayList<RotatedRect>(); // rotated rectangle fit to contour
		for(int i=0; i<contours.size(); i++){
			List<Point> contPoints = contours.get(i).toList();
			//System.out.println(contPoints.get(0).y+","+contPoints.get(0).x);
			Moments p = Imgproc.moments(contours.get(i), false);
			if(p.get_m00() == 0){
				contours.remove(i);
				i--;
			}else{
				MatOfPoint2f newt = new MatOfPoint2f(contours.get(i).toArray());
				RotatedRect rrect = Imgproc.minAreaRect(newt);
				Point[] rectPoints = new Point[4];
				rrect.points(rectPoints);
				for(int j = 0; j < 4; j++) {
					//System.out.print("("+(int)rectPoints[j].x+","+(int)rectPoints[j].y+")");
					Imgproc.line(contout,rectPoints[j],rectPoints[(j+1)%4],new Scalar(100,100,100), 2);
				}
				
				centerPositions.add(new Point(p.get_m10() / p.get_m00(),p.get_m01() / p.get_m00()));
				rects.add(rrect);
				sortPositions.add(contPoints.get(0));
				Imgproc.circle(contout,centerPositions.get(i),3,new Scalar(0,0,255));
			}
		}
		//System.out.println(" --" );
		
		// create list of chains
		int longind = -1;
		ArrayList<ArrayList<Integer>> chains = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<contours.size(); i++){
			ArrayList<Integer> newl = new ArrayList<Integer>();
			newl.add(i);
			chains.add(newl);
		}
		for(int i=contours.size()-1; i>=0; i--){
			// find close contour
			int j;
			for(j=i+1; j<contours.size() && !contourInRange(sortPositions,i,j); j++);
			// add chain list of other contour if possible
			//ArrayList<Integer> indlist = new ArrayList<Integer>();
			//indlist.add(i);
			if(j<contours.size()){
				for(int ind : chains.get(j)){
					chains.get(i).add(ind);
				}
			}
			// set this to longest chain if it is
			if(longind<0 || chains.get(i).size() > chains.get(longind).size()){
				longind = i;
			}

			//System.out.println("moweee");
			
			//System.out.println("balls in yo jaws");
		}
		
		if(longind >= 0) {
			//draw chain
			int ch = longind;
			for(int q = 0; q<chains.get(ch).size()-1; q++) {
				//System.out.println("WOWEEE");
				Point firstPoint = centerPositions.get(chains.get(ch).get(q));
				Point nextPoint = centerPositions.get(chains.get(ch).get(q+1));
				Imgproc.line(contout, firstPoint, nextPoint, new Scalar((ch%3)%2*255,((ch+1)%3)%2*255,((ch+2)%3)%2*255), 2);
				
			}
		// create list of x positions
			ArrayList<Integer> goalchain = chains.get(longind);
			double[] xposs = new double[goalchain.size()];
			for(int i=0; i<goalchain.size(); i++){
				xposs[i] = centerPositions.get(goalchain.get(i)).y;
			}
			// publish
			contourInfo.setDoubleArray(xposs);
		}
		Core.transpose(contout, contout);
		
		return contout;
		
	}

	// if the other contour is valid to move to as a chain
	public boolean contourInRange(ArrayList<Point> positions, int thisindex, int otherindex){
		final int ymax = 10;
		final int xmax = 65;
		Point thispos = positions.get(thisindex);
		Point otherpos = positions.get(otherindex);
		boolean withiny = Math.abs(thispos.x - otherpos.x) <= ymax;
		boolean withinx = thispos.y - otherpos.y <= xmax && thispos.y - otherpos.y >= 0;
		//System.out.println((withinx&&withiny)+"-"+thispos+"-"+otherpos);
		return withinx && withiny;
	}
}
