
#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>
#include <opencv/cv.h>
#include <cmath>
#include <math.h>
#include "map.h"

#define PI 3.14159265359

using namespace std;

void Map::printMarking(Marking *marking)
{
    cout << "id: " << marking->id << endl;
    cout << "pos: (" << marking->x << "," << marking->y << ")" << endl; 
}

void Map::createMarking(ifstream &in, Marking &marking)
{
    string str;
    getline(in,str);
    str.erase(remove(str.begin(), str.end(), ' '), str.end()); // remove all white spaces
    marking.id = stoi(str);
    getline(in,str);
    size_t index = str.find(',');
    marking.x = stoi(str.substr(0,index));
    marking.y = stoi(str.substr(index+1,str.length()-index));
    getline(in,str);
    if(str.compare(MARKING_END)){
        cerr << "Something wrong with a marking.." << str << endl;
        marking.x = -1; marking.y = -1; marking.id = -1;
    }    
}

bool Map::isCommentLine(string &str)
{
    return str[0] == COMMENT_SIGN;
}

void Map::getMarkingPos(int id, int &x, int &y)
{
    for(int i = 0; i < markings.size(); i++){
        struct Marking marking = markings.at(i);
        if(marking.id == id){
            x = marking.x;
            y = marking.y;
            return;
        }
    }
    x = -1;
    y = -1;
}

/*
  Angle supplied in degrees, x and y supplied in integers.
  Angle must be within the interval [-180, 180]                <---- IMPORTANT

  Returns the closest position in the direction of the supplied angle. Will store the result in x and y.
  If no marking within its sight is found, x and y will both have the values -1.

  A marking is deemed within the trucks sight if it is +- around 22.5 degrees from the trucks angle.
*/
void Map::getMarkingPosition(double angle, int xin, int yin, int * x, int * y) {
  /* The marking has to be within +- this many radians from the trucks direction to be deemed within its sight. */
  double interval = 0.4;
  double angleInRadians = ((double)angle * PI) / 180;

  /* Initialize first result */
  struct Marking result = {-10,-10,-10};
  double resdistance = -1.0;

  for(int i = 0; i < markings.size(); i++) {
    /* Check if next marking is closer. */
    struct Marking marking = markings[i];

    /* Calculate distance and angle to marking.. */
    double distX = marking.x - xin;
    double distY = marking.y - yin;
    double sdistance = sqrt(distX*distX + distY*distY);
    double markangle = atan2(distY, distX);

    /* If marking is within sight.. */
    if(abs(angleInRadians - markangle) <= interval) {
      cout << "Angle to point x = " << marking.x << " and y = " << marking.y << " is " << markangle << endl;
      if(result.y != -10 && result.x != -10) {
        cout << "result is currently not NULL.." << endl;
        if(sdistance < resdistance) {
          cout << "This one is closer..." << endl;
          result.x = marking.x; result.y = marking.y;
          resdistance = sdistance;
        } else {
          cout << "this marking is not closer, discarding it.." << endl;
        }
      } else {
        cout << "result is NULL.." << endl;
        result.x = marking.x; result.y = marking.y;
        resdistance = sdistance;
      }
    }
  }
  /* If there was a marking in sight, result should not be NULL and we can return its values. */
  if(result.y != -10 && result.x != -10) {
    *x = result.x; *y = result.y;
  } else {
    *x = -1; *y = -1;
  }
}

string Map::getexepath()
{
  char result[ PATH_MAX ];
  ssize_t count = readlink( "/proc/self/exe", result, PATH_MAX );
  return std::string( result, (count > 0) ? count : 0 );
}

Map::Map()
{	
    string executionPath = getexepath();
    string lok = "devel";
    int splitIndex = 0;
    for(int i = 0; i < executionPath.length(); i++){
        if(executionPath[i] == '/'){
            if(!executionPath.substr(i,6).compare(lok)){
                splitIndex = i;
                break;
            }
        }
    }
    string path = executionPath.substr(0,splitIndex) + "src/mapserver/src/db.db";

    ifstream in(path);

    if(!in){
        cout << "Cannot open input file" << endl;
    }
	
	string str;
    while(getline(in,str)){
       
        if(isCommentLine(str)){
            continue;
        }            
        
        if(!str.compare(MARKING_START)){
            struct Marking marking;
            createMarking(in, marking);
            markings.push_back(marking);
        }else if(!str.empty()){
            cout << "Can't parse line: " << str << endl;
        }       
    }
}
