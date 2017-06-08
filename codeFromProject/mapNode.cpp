
#include "ros/ros.h"
#include "std_msgs/String.h"
#include <iostream>

using namespace std;


void getMarkingPosition(const std_msgs::String::ConstPtr& msg){
   ROS_INFO("%s", msg->data.c_str());
}


int main(int argc, char **argv)
{
    ros::init(argc, argv, "mapserver");
    ros::NodeHandle n;
    ros::Subscriber sub = n.subscribe("map_node", 1000, getMarkingPosition);
    ros::Rate loop_rate(20);
    ros::spin();
    return 0;
} 	


