# Makefile for this program

# Removes the executable
clean:
	rm -rf *.exe

# Makes the calculator backend
calculator:
	g++ -Wall -Werror -pedantic --std=c++11 calculator.cpp -o calculator.exe
