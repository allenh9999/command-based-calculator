// The backend for the calculator program

// Include all the libraries necessary
#include <iostream>
#include <cstring>

// For convenience
using namespace std;

// Main program
int main(int argc, char** argv) {
	// to switch between radians and degrees
	// 0 is radians, 1 is degrees
	bool is_degree = false;
	if (strcmp("degree",argv[1]) == 0) {
		is_degree = true;
	}

	// input the string
	string str;
	getline(cin, str);

	// Look at the java sample. It should be pretty similar
	// @TODO the rest of the program
	cout << str << endl;
	cerr << str << endl;
}
