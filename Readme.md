## Project Overview

This project is an implementation of a voting system that allows for two election types: Closed Party Listing (CPL) and Instant Runoff (IR). This project was created by a group of 4 students using 2 methodologies, Waterfall (Project 1) and Agile/Scrum (Project 2). Our Waterfall portion of the project primarily focuses on designing our system and implementing a rough draft. Our Agile/Scrum section focused more on review and maintanence of our existing system. Users are able to input voting ballots (either by file path or GUI, more on that below) and our system will output a winner based off the given voting method.

## How to run with file path:

Click run button in IDE (typically a play button already located on screen, or through the Run tab at the top)
Enter file location, (example: /home/syed0053/CSCI5801/repo-Team20/Project2/misc/CPL_Election.csv)

If the election is an Instant Runoff election, enter , otherwise enter for Closed Party Listing

Election will run and automatically process the associated algorithm as well as creating the Audit and Media file

## How to run with file select GUI

Click run button in IDE (typically a play button already located on screen, or through the Run tab at the top), afterwards a file selector GUI will show up, navigate to the election file(s) you want to run in the testing/Election_File directory and select them. 

The election files must be of the same type. Afterwards enter the date in the format _dd/mm/yyyy_ and the election will commence.
Election will run and automatically process the associated algorithm as well as creating the Audit and Media file and invalid ballots file for IR

The newly created files should be viewable in the same directory as the SRS, SDD, etc.


## Notes:
* This program's election files used for testing can be found under the "ELection_File" folder in the testing directory
* The program classes are packaged with every class regarding the election in the election package; the Main class in the main package; the testing classes in the testing packages; and all other classes in the object package
* Both Audit and Media files will be generated on the home direcotry of the project
* Many class object have extra constructors because we wanted to be flexible with what information we would recieve to parse for our Election CSV files
* The Voting.java class is our "Main" class with our main method to run the program
* For the CPLElection test algorithm, you must manually changed the directory the _electionFile_ variable is set to the path where the _CPL_Election.csv_ file is located on your local system
