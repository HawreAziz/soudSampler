
#include "ros/ros.h"
#include "mapserver/getMarkPos.h"


int main(int argc, char **argv)
{
    ros::init(argc, argv, "mapserver_client");
    if(argc != 4){
        ROS_INFO("usage: rosrun mapserver mapClient angle, x, y");
        return 1;
    }

    ros::NodeHandle n;
    ros::ServiceClient client = n.serviceClient<mapserver::getMarkPos>("get_marking_position");
    mapserver::getMarkPos srv;
    srv.request.angle  = atoll(argv[1]);
    srv.request.truckx = atoll(argv[2]);
    srv.request.trucky = atoll(argv[3]);
    if(client.call(srv)){
        ROS_INFO("ans: (%d,%d)", srv.response.x, srv.response.y);
    }else{
        ROS_ERROR("Failed to call service ..");
    }

    return 0;
}
