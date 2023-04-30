## Important Change Branch
*The finalize project after deadlineis located in the "Update-Branch"

## How to run:

Click run button in IDE (typically a play button already located on screen, or through the Run tab at the top)
Enter file location, (example: /home/syed0053/CSCI5801/repo-Team20/Project1/misc/CPL_Election.csv)
If the election is an Instant Runoff election, enter , otherwise enter for Closed Party Listing
Election will run and automatically process the associated algorithm as well as creating the Audit and Media file
The newly created files should be viewable in the same directory as the SRS, SDD, etc.


## Notes:
* This program's election files used for testing can be found under the "ELection_File" folder in the testing directory
* THe program classes are packaged with every class regarding the election in the election package; the Main class in the main package; the testing classes in the testing packages; and all other classes in the object package
* Both Audit and Media files will be generated on the home direcotry of the project
* Many class object have extra constructors because we wanted to be flexible with what information we would recieve to parse for our Election CSV files
* The Voting.java class is our "Main" class with our main method to run the program
* For the CPLElection test algorithm, you must manually changed the directory the _electionFile_ variable is set to the path where the _CPL_Election.csv_ file is located on your local system
