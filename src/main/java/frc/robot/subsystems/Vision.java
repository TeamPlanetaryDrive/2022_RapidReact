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
	NetworkTableEntry totalEntry, contourInfo, goalBlurEntry;
	final int FRAME_AVG = 12;
	double slopeavg = 0;

	ArrayList<MatOfPoint> contours;
	Mat hierarchy;

	public void init(){
		// get entries from NetworkTable
		NetworkTableInstance inst = NetworkTableInstance.getDefault();
      	NetworkTable table = inst.getTable("datatable");
      	totalEntry = table.getEntry("Top Left Pixel");
		contourInfo = table.getEntry("Contour Positions rawr xd nya~");
		goalBlurEntry = table.getEntry("blrur");

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
		Scalar lb = new Scalar(61.0,100.0,96.0);
		Scalar ub = new Scalar(103.0,225.0,255.0);
		Imgproc.cvtColor(out, out, Imgproc.COLOR_BGR2HLS);
		Core.inRange(out, lb, ub, out);
		Imgproc.dilate(out,out,makeKernel(3));

		// find contours
		contours = new ArrayList<>();
		hierarchy = new Mat();
		
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
			if(j<contours.size()){
				for(int ind : chains.get(j)){
					chains.get(i).add(ind);
				}
			}
			// set this to longest chain if it is
			if(longind<0 || chains.get(i).size() > chains.get(longind).size()){
				longind = i;
			}
		}
		
		// publish info on goal contour
		if(longind >= 0 && chains.get(longind).size() > 3) {
			// create list of x positions
			ArrayList<Integer> goalchain = chains.get(longind);
			double[] xposs = new double[goalchain.size()];
			double[] yposs = new double[goalchain.size()];
			double xtotal = 0;
			double ytotal = 0;
			boolean keep = true;
			for(int i=0; i<goalchain.size(); i++){
				xposs[i] = centerPositions.get(goalchain.get(i)).y;
				yposs[i] = centerPositions.get(goalchain.get(i)).x;
				xtotal += xposs[i];
				ytotal += yposs[i];
				// reject uneven goals
				if(i>1){
					double xd = Math.abs((xposs[i]-xposs[i-1])/(xposs[i-1]-xposs[i-2]));
					//double yd = Math.abs((yposs[i]-yposs[i-1])/(yposs[i-1]-yposs[i-2]));
					if(xd<.8 || xd>1.25 ){//|| yd<.5 || yd>2){
						keep = false;
					}
				}
				// draw goal lines
				if(i<goalchain.size()-1){
					Scalar color = new Scalar(0,keep?255:0,keep?0:255);
					Point firstPoint = centerPositions.get(goalchain.get(i));
					Point nextPoint = centerPositions.get(goalchain.get(i+1));
					Imgproc.line(contout, firstPoint, nextPoint, color, 2);
				}
			}
			if(keep){
				double gsize = Math.abs(xposs[0]-xposs[xposs.length-1]);
				double gypos = ytotal / yposs.length;
				double gslope = (-yposs[0]+yposs[yposs.length-1])/(-xposs[0]+xposs[xposs.length-1]);
				slopeavg = ((FRAME_AVG-1) * slopeavg + gslope)/FRAME_AVG;
				double gxpos = xtotal / xposs.length;
				// publish
				contourInfo.setDoubleArray(new double[] {gsize,gypos,gxpos,slopeavg});
			}else{
				contourInfo.setDoubleArray(new double[] {-1,-1,-1,-1});
			}
			
		}else{
			contourInfo.setDoubleArray(new double[] {-1,-1,-1,-1});
		}
		Core.transpose(contout, contout);
		
		// check for blurred goal as a rotated rect
		boolean blurSpotted = false;
		for(RotatedRect rr : rects){
			double ratio = Math.max(rr.size.width,rr.size.height)/Math.min(rr.size.width,rr.size.height);
			if(ratio > 5){
				blurSpotted = true;
			}
			goalBlurEntry.setDouble(ratio);
		}
		//goalBlurEntry.setBoolean(blurSpotted);

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
		boolean horiz = Math.abs(thispos.x - otherpos.x) < Math.abs(thispos.y - otherpos.y);
		return withinx && withiny && horiz;
	}
}
