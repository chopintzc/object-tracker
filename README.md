# **Object-tracker**

## **Summary of the project:**

This is the Project for my Ubiquitous Computing class, written in Jave and OpevCV.
It controlls the webcam to take video and performs online analysis on the captured images.
It is designed to detect red and green objects on the captured images. There is a UI designed to visualize
the captured images with detected red and green objects outlined. 
A second UI is designed to output the number of red or green objects detected. Our business logic is that
red objects must be put at the left hand side of view and green objects must be put at the right hand side.
If an object is put at the wrong side, our program can report a mess up signal.
Our system has the potential to be deployed at the convenient store so that store employees can take advantage
of it to monitor whether the shelf goods are misplaced.

## **Requirement**

This program requires Java and OpenCV 2.4.9

## **Features**
* control webcam to take consecutive video
* online analysis on each video frame and detect all red and green objects
* visualize the captured images with detected objects outlined
* output the number of green or red objects detected
* if colored objects are placed at the wrong side of view, launch an alert signal
