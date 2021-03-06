

/*
Lane detection algorithm implemented in C++ with the OpenCV libarary: http://opencv.org

Requirements: OpenCV 3.1 (+),   C++11/gnu++11

Input: Video capture from camera device, resolution 640x360 

Output: Detected outer lanes and computed midlane overlayed on the original video. 

        Each lane represented by a set of two points. (x,y)

Commands: Press "ESC" to exit


        type following to compile the program 
        g++ -o main main.cpp `pkg-config opencv --cflags --libs`  -std=gnu++11

*/








#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
#include "opencv/cv.h"
#include<unistd.h>
#include<iostream>
#include "laneDetection.h"
using namespace std;
using namespace cv;

/*
	type following to compile the program 
        g++ -o main main.cpp `pkg-config opencv --cflags --libs`  -std=gnu++11

*/

#define M_PI 3.14159265358979323846 
#define X1 0
#define X2 2
#define Y1 1
#define Y2 3
#define THICKNESS 10
#define BLUE Scalar(255,0,0)
#define BLACK Scalar(255,255,255)
#define WIDTH 360 
#define HEIGHT 480




/* linear regression function */


void LaneDetection::linearFit(double &slope, double &m, vector<int>& xValues, vector<int>& yValues)
{
    int i,k,n;
    n = xValues.size();
    double a, b;
    double xsum=0.0,x2sum=0.0,ysum=0.0,xysum=0.0;
    
    for (i=0;i<n;i++){
        xsum += xValues[i];                        //calculate sigma(xi)
        ysum += yValues[i];                        //calculate sigma(yi)
        x2sum += pow(xValues[i],2);                //calculate sigma(x^2i)
        xysum += xValues[i]*yValues[i];                    //calculate sigma(xi*yi)
    }

    a=(n*xysum-xsum*ysum)/(n*x2sum-xsum*xsum);            //calculate slope
    b=(x2sum*ysum-xsum*xysum)/(x2sum*n-xsum*xsum);            //calculate intercept
    
    slope = a;
    m = b;
}



/* This function calculates slopes of lines passed in as argument */
void LaneDetection::findSlopes(vector<Vec4i> lines, double thresholdSlope, vector<float>& s, vector<Vec4i>& tmp){
   vector<float>slopes; 
   vector<Vec4i>lanesTemp;
   int x1, x2, y1, y2;
   float slope;
   for(Vec4i line: lines){
      x1 = line[X1]; y1 = line[Y1]; x2 = line[X2]; y2 = line[Y2]; 
      if(x2 == x1)
         x2 += 1;
      
      slope = float(y2 - y1)/(x2 - x1);
      if(abs(slope) > thresholdSlope){
	slopes.push_back(slope);
        lanesTemp.push_back(line);
      }
   }
   s = slopes;
   tmp = lanesTemp;
}


void LaneDetection::estimateOptimalLane(vector<Vec4i> lines, double& slope, double& lineConst, bool& drawLine){
    bool draw = true;
    vector<int> linesX, linesY;
    double line_slope, line_const;
    int x1, x2, y1, y2;
    for(Vec4i line : lines){
       x1 = line[X1]; y1 = line[Y1]; x2 = line[X2]; y2 = line[Y2];
       linesX.push_back(x1);
       linesX.push_back(x2);
       linesY.push_back(y1);
       linesY.push_back(y2);
    }
    
    if(linesX.size() > 0){    
      linearFit(line_slope, line_const, linesX, linesY);
   
    }else{
       line_slope = 1; 	
       line_const = 1;
       draw = false;
    }
    slope = line_slope;
    lineConst = line_const;
    drawLine = draw;
}




/* Function for drawing the lines on the screen */
void LaneDetection::drawLanes(Mat cameraFeed, vector<Vec4i> lines, double thresholdSlope, double trapH){
  bool draw_right = false;
  bool draw_left = false;
  vector<float>slopes;
  vector<Vec4i>lanesTemp, rightLines, leftLines;
  int x1, x2, y1, y2;
  
  findSlopes(lines, thresholdSlope, slopes, lanesTemp);
 
  int i =0;
  for(Vec4i line : lanesTemp){
     x1 = line[X1]; y1 = line[Y1]; x2 = line[X2]; y2 = line[Y2];
     float imgXCenter = cameraFeed.size().width / 2;
     if(slopes[i] > 0 && x1 > imgXCenter && x2 > imgXCenter){
        rightLines.push_back(line);
     }else if(slopes[i] < 0 && x1 < imgXCenter && x2 < imgXCenter){
        leftLines.push_back(line);
     }
     i++;
  }
  
  double right_slope, right_const, left_slope, left_const;
  estimateOptimalLane(rightLines, right_slope, right_const, draw_right);
  estimateOptimalLane(leftLines, left_slope, left_const, draw_left);
  y1 = int(cameraFeed.size().height);	
  y2 = int(cameraFeed.size().height * (1- trapH));
 
  int right_x1 = int((y1 - right_const) / right_slope);
  int right_x2 = int((y2 - right_const) / right_slope);
  int left_x1 = int((y1 - left_const) / left_slope);
  int left_x2 = int((y2 - left_const) / left_slope);
  int mid_x1 = (right_x1 + left_x1) / 2;
  int mid_x2 = (right_x2+ left_x2) / 2;

  if(draw_right){
     line(cameraFeed, Point(right_x1, y1), Point(right_x2, y2), BLUE, THICKNESS, CV_AA);
  }

  if(draw_left){
      line(cameraFeed, Point(left_x1, y1), Point(left_x2, y2), BLUE, THICKNESS, CV_AA);
  }
  if(draw_right && draw_left){
     line(cameraFeed, Point(mid_x1, y1), Point(mid_x2, y2), BLACK, THICKNESS, CV_AA);
     error = mid_x2 - 320;
  }
   

}



/* Adds some filters to the original image */
void LaneDetection::setROI(Mat img_full, Mat & res){
    Mat mask = Mat::zeros(img_full.size(), img_full.type());
    Point roi_corners[][6] = {{Point(0,360),Point(0,230),Point(340,180),Point(360,180),Point(640,240),Point(640,360)}};
    const Point * pp[1] = {roi_corners[0]};
    int numOfPoints[] = {6}; 
    fillPoly(mask, pp, numOfPoints, 1, BLACK);
    Mat re;
    bitwise_and(img_full, mask, re);
    res = re;
}


double LaneDetection::runLD(){
       Mat cameraFeed, histImage, edge, roi_img, grayScale, binary_img;
       vector<Vec4i> lines;
       
       VideoCapture cap;
       cap.open("road.avi");   // replace this line with cap.open(0);  to use the camera.

       cap.set(CV_CAP_PROP_FRAME_WIDTH, WIDTH);
       cap.set(CV_CAP_PROP_FRAME_HEIGHT, HEIGHT);

       //while(cap.isOpened()){
       cap.read(cameraFeed);

       setROI(cameraFeed, roi_img);

       cvtColor(roi_img, grayScale,  COLOR_BGR2GRAY);
       equalizeHist(grayScale, histImage);
       Canny(histImage, edge, 200, 255, 3);
       threshold(histImage, binary_img, 250, 255, 0);

       HoughLinesP(binary_img, lines, 1, M_PI/180, 50, 10, 10);
       usleep(15000);                            // feel free to change sleeping time to get a better quality
       drawLanes(cameraFeed, lines, 0.4, 0.4);
       return error;
    
          //imshow("Window", cameraFeed);

       

       
        /*  switch(waitKey(1)){
             case 27: // Press ESC to exit
               //cameraFeed.release();
    	       // destroyAllWindows();
               exit(0);
          }*/
      //}

}





/*int main(int argc, char *argv[])
{
      runLD();
    Mat cameraFeed;
    VideoCapture cap;
    cap.open(0);   // replace this line with cap.open(0);  to use the camera.

    cap.set(CV_CAP_PROP_FRAME_WIDTH, WIDTH);
    cap.set(CV_CAP_PROP_FRAME_HEIGHT, HEIGHT);

    
    namedWindow("Window", 1);

    while(cap.isOpened()){
       cap.read(cameraFeed);

       runLD(cameraFeed);
    
       imshow("Window", cameraFeed);

       

       
       switch(waitKey(1)){
          case 27: // Press ESC to exit
              //cameraFeed.release();
    	     // destroyAllWindows();
              exit(0);
       }
    }*/
   







