#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
#include<opencv/cv.h>
#include "DistanceMeasurement.h"
#include <iostream>
#include "ros/ros.h"
#include "std_msgs/String.h"
#include <sstream>


using namespace cv;
using namespace std;


int main(int argc, char *argv[])
{
        DistanceMeasurement d;
        std_msgs::String msg;
        int frame = 0;
        ros::init(argc, argv, "distanceMeasurement");
        ros::NodeHandle n;
        ros::Publisher chatter_pub = n.advertise<std_msgs::String>("feature_detection", 1000);
        ros::Rate loop_rate(10);
	d.openCap(); // uses the movie yellow2.mp4
	namedWindow("Window", 1);
        namedWindow("Original", 1);
        d.linePointSettings();
        d.focalLength = -1;
	while (ros::ok() /*&& d.cap.isOpened()*/){
	   Mat image, work_image, cannyOut, ipmImage;
           bool temp = d.cap.read(image);
           if(!temp){
	      break;
	   }
	   
           work_image = image.clone();
           
	   d.pTransform(work_image, work_image);
           vector<vector<Point> > contours;
           d.setContours(work_image, cannyOut, contours);
	   
	   vector<float> res;
           d.drawLines(work_image, contours);
           if(d.focalLength != -1){
              
              std::stringstream ss;
              ss  << d.focalLength << ";" << d.truckAngle << ";" << d.globalAngle;
              msg.data = ss.str();
              ROS_INFO("%s", msg.data.c_str());
	      chatter_pub.publish(msg);
              ros::spinOnce();
           }
           if (waitKey(30) == 27){
    	      exit(0); 
	   }
           d.focalLength = -1;
	   imshow("Window", work_image);
           imshow("Original", image);
           //cout << "frame: " << ++frame << "\n";
           //waitKey(0);
        }
        d.closeCap();
        return(0);
}












