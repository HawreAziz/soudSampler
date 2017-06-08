
// Needs to be compiled with flag -std=c++11 


#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include <limits.h>
#include <unistd.h>
#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>
#include <opencv/cv.h>

#define POLY_START      "BEGIN POLYGON"
#define POLY_END        "END POLYGON"
#define MARKING_START   "BEGIN MARKING"
#define MARKING_END     "END MARKING" 
#define PATH_START      "BEGIN PATH"
#define PATH_END        "END PATH"
#define COMMENT_SIGN    '#'
using namespace std;

struct Marking
{
    int id;
    int x, y;
};

class Map{
    public:
        vector<Marking> markings;
        void printMarking(Marking *marking);
        void getMarkingPos(int id, int &x, int &y);
        void getMarkingPosition(double angle, int xin, int yin, int * x, int * y);
        string getexepath();
        Map();        
        
    private:
        void createMarking(ifstream &in, Marking &marking);
        bool isCommentLine(string &str);
};




