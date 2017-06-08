
#include "ros/ros.h"
#include "mapserver/getMarkPos.h"
#include "../map.h"

Map g_map;


bool getMarkingPosition(mapserver::getMarkPos::Request &req,
                        mapserver::getMarkPos::Response &res)
{
    int truckx, trucky, angle;
    truckx = (int) req.truckx;
    trucky = (int) req.trucky;
    angle =  (int) req.angle;

    int *x, *y;
    g_map.getMarkingPosition(angle, truckx, trucky, x, y);

    res.x = *x;
    res.y = *y;
    ROS_INFO("Received a request with angle %i, truckx %i and trucky %i.\nResponding with closest marking having x %i and y %i.", angle, truckx, trucky, *x, *y);

    return true;
}


int main(int argc, char **argv)
{
    ros::init(argc, argv, "mapserver_server");
    ros::NodeHandle n;

    ros::ServiceServer service = n.advertiseService("get_marking_position", getMarkingPosition);
    ROS_INFO("Ready to reply to mapserver requests now.\nInput: trucks x,y and accepted angle.\nOutput: The closest markings x and y position.");
    ros::spin();
    return 0;
}
