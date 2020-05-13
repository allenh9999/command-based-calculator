# Here is the main driver for the program

# Importing all of the objects required for the program
import os as cmd
import subprocess
import time

# Checks to make sure the program is running correctly
if __name__ == "__main__":
    
    # Make the c++ program backend
    print("Creating the C++ backend...")
    try:
        cmd.system("make clean")
    except:
        print("You need to install make")
        print("sudo apt-get install make")
        exit(1)
    
    try:
        cmd.system("make calculator")
    except:
        print("You need to install g++")
        print("sudo apt-get install g++")
        exit(1)

    # Initialize the calculator
    calculator = None

    # Cout the instructions
    print("*"*60)
    print("*** Instructions:".ljust(57) + "***")
    print("*** Type in an equation for the program to solve".ljust(57) + "***")
    print("*** Acceptable symbols:".ljust(57) + "***")
    print("*** +    -    /    *    (    }    {    }    [    ]    %".ljust(57) + "***")
    print("*** ^    sin  cos  tan  atan asin acos sec  csc  cot".ljust(57) + "***")
    print("*** Then press enter to solve the equation".ljust(57) + "***")
    print("*** To exit the program, simply type in 'exit'".ljust(57) + "***")
    print("*"*60)
    
    # Now actually start the input loop
    input_str = ""
    # Exits only when typing in exit
    while True:
        # Cin a value
        input_str = input(">>> ")

        # Exit only when the command "exit" is typed in
        if(input_str.lower() == "exit"):
            break

        # Start the calculator
        calculator = subprocess.Popen(["./calculator.exe"],stdin = subprocess.PIPE,stdout = subprocess.PIPE,stderr = subprocess.PIPE, bufsize = 1)

        # Get the input values
        cout, cerr = calculator.communicate(bytes(str(input_str + '\n'),"utf-8"))

        # Prints out an error if there is any
        if cerr:
            print("Error: " + str(cerr.decode("UTF8").rstrip()))

        # Prints out the output of the C++ program
        if cout:
            print(str(cout.decode("UTF8").rstrip()))

    # Clean up
    print("Cleaning up the program...")
    cmd.system("make clean")
    print("Exiting...")
    
else:
    # Exits the program
    print("Make sure you are running the program with the command 'python3 main.py'")
    exit()
