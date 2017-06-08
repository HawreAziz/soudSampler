#include <stdio.h>

int main( void ){

	int n = 10;
	int *point = &n;
	int **tpoint = &point;
	
	printf("Address of *point = %p", point);
	printf(" Address of = %d \n", *tpoint);
	return 0;
}
