/**
 * Track uniformly colored objects
 * 
 * @author Zhongchao
 * @since 2016-06-18
 */

package recognization;

import org.opencv.core.Core;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.*;
import org.opencv.core.MatOfPoint;

import java.util.ArrayList;
import java.util.List;


public class ObjectTracker {
    public static void main (String args[]){

	    /* Load the native library */
	    System.loadLibrary("opencv_java249");
	    
	    /* Define camera instance */
	    VideoCapture camera = new VideoCapture(0);
	    
	    /* Minimum size of an object */
    	double min_thre = 1000;
    	/* Maximum size of an object */
    	double max_thre = 100000;
    	/* Summation of x coordination */
    	double sum_x = 0;
    	/* Summation of y coordination */
    	double sum_y = 0;
    	/* Number of red objects identified */
    	int num_red;
    	/* Number of green objects identified */
    	int num_green;
    	/* Camara status */
    	String status1, status2;
    	/* Frame count */
	    int f = 0;
	    /* Maximum number of frames to capture */
	    int max_frame = 1000;
	    /* Window for raw image presentation */
	    Imshow im1 = new Imshow("Image");
	    /* Matrix for each following image */
	    Mat frame = new Mat();
	    /* Matrix for hsv true color image */
	    Mat hsv_image = new Mat();
	    /* Matrix for red thresholded image */
	    Mat thresholded_red = new Mat();
	    /* Matrix for green thresholded image */
	    Mat thresholded_green = new Mat();
	    
	    /* hsv color boundary for red */
	    Scalar hsv_min_red = new Scalar(0, 145, 100);
	    Scalar hsv_max_red = new Scalar(10, 225, 180);
	    /* hsv color boundary for green */
	    Scalar hsv_min_green = new Scalar(39, 76, 23);
	    Scalar hsv_max_green = new Scalar(109, 255, 165);
	    
	    /* Initialize Base */
	    Base base = new Base();
	    System.out.println("base");
	    
	    base.go();

    	
	    while (f<max_frame){
		    /* capture the frames */
		    status1 = "ON";
		    status2 = "ON";
		    camera.read(frame);
		    
		    /* Initialize object counts */
		    num_green = 0;
		    num_red = 0;		    
		    
		    /* Matrix containing contours of image */
	        Mat hierarchy = new Mat();
	        /* Matrix containing information about the image topology */
		    List<MatOfPoint> contours_red = new ArrayList<MatOfPoint>();
		    List<MatOfPoint> contours_green = new ArrayList<MatOfPoint>();
		    /* convert RGB colored image to hsv colored image */
		    Imgproc.cvtColor(frame, hsv_image, Imgproc.COLOR_BGR2HSV);
		    /* calculated thresholded image for red and green boundaries */
		    Core.inRange(hsv_image, hsv_min_red, hsv_max_red, thresholded_red);
		    Core.inRange(hsv_image, hsv_min_green, hsv_max_green, thresholded_green);
		    
		    /* image processing to obtain a smoothed boundaries */
		    //Imgproc.erode(thresholded_red, thresholded_red, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new org.opencv.core.Size(8,8)));
			Imgproc.dilate(thresholded_red, thresholded_red, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new org.opencv.core.Size(8, 8)));
			Imgproc.GaussianBlur(thresholded_red, thresholded_red, new org.opencv.core.Size(9,9),0,0);
			//Imgproc.erode(thresholded_green, thresholded_green, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new org.opencv.core.Size(8,8)));
			Imgproc.dilate(thresholded_green, thresholded_green, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new org.opencv.core.Size(8, 8)));
			Imgproc.GaussianBlur(thresholded_green, thresholded_green, new org.opencv.core.Size(9,9),0,0);
			
			/* find contour lines for each color thresholded image */
			Imgproc.findContours(thresholded_red, contours_red, hierarchy, Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);
			Imgproc.findContours(thresholded_green, contours_green, hierarchy, Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);	    
        	
	        double[] contourareas_red = new double[contours_red.size()];
	        for (int idx = 0; idx < contours_red.size(); idx++) {
	        	/* get current contour */
	            Mat contour = contours_red.get(idx);
	            /* get current contour area */
	            contourareas_red[idx] = Imgproc.contourArea(contour);
	            
	            /* draw the outline of an object and categorize identified object */
	            if (contourareas_red[idx]<max_thre && contourareas_red[idx]>min_thre){

			        /* obtain Points coordinates to form the object boundary */
	            	Point[] points1;
			        points1 = contours_red.get(idx).toArray();

			        sum_x = 0;
		        	sum_y = 0;
			    	
			        /* calculate the centroid */
		        	for (int j=0; j<points1.length; j++){
		        		sum_x += points1[j].x;
		        		sum_y += points1[j].y;
		        	}
		        	sum_x = sum_x / points1.length;
		        	sum_y = sum_y / points1.length;
		        	
		        	/* draw the boundary contours */
			        Imgproc.drawContours(frame, contours_red, idx, new Scalar(255,0,255), 2);
			        num_red++;	 
			        
			        /* set the object status if it is messed up */
			        if (sum_x > 320){
			        	status1 = "MESS";
			        }
	            }
	        }
	        
	        double[] contourareas_green = new double[contours_green.size()];
	        for (int idx = 0; idx < contours_green.size(); idx++) {
	        	/* get current contour */
	            Mat contour = contours_green.get(idx);
	            /* get current contour area */
	            contourareas_green[idx] = Imgproc.contourArea(contour);
	            
	            /* draw the outline of an object and categorize identified object */
	            if (contourareas_green[idx]<max_thre && contourareas_green[idx]>min_thre){

			        /* obtain Points coordinates to form the object boundary */
	            	Point[] points1;
			        points1 = contours_green.get(idx).toArray();

			        sum_x = 0;
		        	sum_y = 0;
			    	
			        /* calculate the centroid */
		        	for (int j=0; j<points1.length; j++){
		        		sum_x += points1[j].x;
		        		sum_y += points1[j].y;
		        	}
		        	sum_x = sum_x / points1.length;
		        	sum_y = sum_y / points1.length;
		        	
		        	/* draw the boundary contours */
		        	Imgproc.drawContours(frame, contours_green, idx, new Scalar(255,255,0), 2);
		        	num_green++;
		        	
		        	/* set the object status if it is messed up */
		        	if (sum_x < 320){
		        		status2 = "MESS";
		        	}
		        	
	            }
	        }
	        
	        /* set the status for red and green objects */
	        if (num_red < 1){
	        	status1 = "LOW";
	        }
	        if (num_green < 1){
	        	status2 = "LOW";
	        }
	        
	        /* update object status and object number */
        	base.cam.setStat(status1, num_red, "red book");    		
           	base.cam1.setStat(status2, num_green, "green book");
        	
	        
	        /* present the first image window */
		    im1.showImage(frame);
		    /* update the interface window */
		    base.updateWindow();
		    
		    f++;
	    }
	    
	    /* stop camera */
	    camera.release();
    }
}
