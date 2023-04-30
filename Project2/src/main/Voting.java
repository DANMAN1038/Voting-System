package main;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;

import election.*;
import objects.*;

/**The Main class of the program where the election is conducted and the subsequent methods to induce it is run
 *
 * @author moha1113
 * @author coll1396
 * @author syed0053
 * @author azamx016
 *
 */
public class Voting {
    private static Integer totalC;
    private static List<Party> parties;
    private Integer totalP;
    private static String electionType;
    private static ArrayList<File> filePath;
    private static ArrayList<Candidate> candidates;
    private static ArrayList<Ballot> ballots = new ArrayList<>();

    /**
     * Method for returning the type of election
     *
     * @return
     */
    public String getElectionType() {

        return electionType;
    }

    /**
     * Method for setting the type of election being conducted
     *
     * @param electType the type of election being done
     */
    public static void setElectionType(String electType) {

        electionType = electType;
    }

    /**
     * Method for checking that the file type is indeed a CSV and that it exist in the given directory
     *
     * @param files the File being checked
     * @return the boolean which says true if the file exist and is valid and false if it isn't
     */
    public static boolean checkFileType(ArrayList<File> files) {
        for (File file : files) {
            if (!file.getName().endsWith(".csv")) {
                return false;
            }
        }
        return true;
    }


    /**
     * Method to check the election of a file to make sure its a CSV file and that it exists
     *
     * @param type the type of election enacted entered by the user
     * @return
     */
    public static boolean electionTypeChecker(String type) {
        if (type.equals("IR") || type.equals("CPL") || type.equals("PO")) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method to return the file of the election being conducted
     * @return
     */
    public static ArrayList<File> getFilePath() {

        return filePath;
    }

    /**
     * Method to set the file of the election being conducted
     * @param electFiles
     */
    public static void setFilePath(ArrayList<File> electFiles) {
        filePath = electFiles;
    }

    /**
     * Method to choose file(s) to be parsed for election
     * @return
     */
    public static ArrayList<File> chooseFiles() {
        ArrayList<File> files = new ArrayList<File>();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose Election File(s) to be Parsed");
        chooser.setMultiSelectionEnabled(true);
        int option = chooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = chooser.getSelectedFiles();
            for (int i = 0; i < selectedFiles.length; i++) {
                files.add(selectedFiles[i]);
            }
        }
        return files;
    }

    /**
     * Method to prompt the user for input; asking for the type of election being run and the election file
     *
     */
    public static void userPrompter() {
        ArrayList<File> files = chooseFiles();
        Scanner userInput = new Scanner(System.in);
       // System.out.println("Enter the Election file name with extension here: ");
      //  String file = userInput.nextLine();
        if (!checkFileType(files)) {
            System.err.println("At least one of the files you provided is not a CSV file or does not exist in the given directory. Please" +
                    " enter a valid file");
            userPrompter();
            return;
        }
        ArrayList<File> electionFiles = files;
        setFilePath(electionFiles);
        System.out.println("Thank you, the election is now being processed...");
    }

    /**
     * Method to create the full party name from the Abbreviated name given
     *
     * @param partyArg the abbreviated name of the party [i.e. (D)]
     * @return
     */
    public static String partyStringParser(String partyArg) {
        String party;
        switch (partyArg) {
            case "(D)":
            case "D":
                party = "Democrat";
                break;
            case "(R)":
            case "R":
                party = "Republican";
                break;
            case "(I)":
            case "I":
                party = "Independent";
                break;
            case "(L)":
            case "L":
                party = "Liberal";
                break;
            default:
                party = "Unknown";
        }
        return party;
    }

    /**
     * Method for GUI display which prompts user for the DAte of the Election
     * @return
     */
    public static Date getDate() {
        // Create a new JPanel to hold the input fields
        JPanel panel = new JPanel(new GridLayout(0, 1));

        // Create a new JLabel to display the prompt
        JLabel promptLabel = new JLabel("Enter the date of the election being run (dd/mm/yyyy)");

        // Create a new JTextField to accept the input
        JTextField inputField = new JTextField();

        // Add the components to the panel
        panel.add(promptLabel);
        panel.add(inputField);

        // Display the input dialog and wait for the user to input a value
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // If the user clicked the OK button, parse the date input
        if (result == JOptionPane.OK_OPTION) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = format.parse(inputField.getText());
                return date;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please enter date in dd/MM/yyyy format.");
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Method used to parse PO election file for candidates
     * @return
     */
    public static List<Candidate> candidateParserPO() {
        try {
            //An ArrayList instantiated to read and store the information of the Line where the Candidates are in the file
            ArrayList<String> candidateLines = new ArrayList<>();
            ArrayList<File> POFiles = getFilePath();

            //Creating Scanner instance to read File in Java
            Scanner po = new Scanner(POFiles.get(0));

            //Reading each line of the file using Scanner class
            int lineNumber = 1;
            while (po.hasNextLine()) {
                //Removes the commas from the file for more seamless reading with Scanner
                String line =po.nextLine().replaceAll(",", " ");
                line.replaceAll("\\[|\\]","");
                //line.replace("[", "");
                Scanner s = new Scanner(line);
                if (lineNumber == 1) {
                    String type = s.next();
                    if (!type.equals("PO")) {
                        System.err.println("The election that type you have given contradicts the type of election file being parsed, program" +
                                " will abort");
                        System.exit(0);
                    }
                    setElectionType(type);
                }
                //Gets and saves the total number of candidates in the IR Election File
                if (lineNumber == 2) {
                    String numOfCand = s.next();
                    totalC = Integer.parseInt(numOfCand);
                    // Create a list of Candidate objects with the given total number of candidates
                    candidates = new ArrayList<Candidate>(totalC);
                }
                //If statement for reading and saving the Candidates and their Abbreviated parties
                if (lineNumber == 3) {

                    int index = 1;
                    while (s.hasNext()) {
                        String candidateLine = s.next();

                        //Used to transform the party abbreviations into full party Strings
                        if (index % 2 == 0) {
                            candidateLine = candidateLine.replace("]", "");
                            candidateLine = partyStringParser(candidateLine);
                        }
                        candidateLine = candidateLine.replace("[", "");
                        candidateLines.add(candidateLine);
                        index++;
                    }
                }
                lineNumber++;
            }
            po.close();

            //for statement for creating the Candidate Variables with the Candidate names and parties
            for (int i =0; i < candidateLines.size() - 1; i += 2){
                ArrayList<Integer> votes = new ArrayList<Integer>();
                Candidate candidate = new Candidate(candidateLines.get(i), candidateLines.get(i + 1), votes);
                candidates.add(candidate);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please check if the file path is correct.");
        }
        return candidates;
    }
    /**
     * Method to parse through IR Election file and get all the Candidate's information creating an array of Candidates
     *
     * @return
     */
    public static List<Candidate> candidateParser() {
        try {
            //An ArrayList instantiated to read and store the information of the Line where the Candidates are in the file
            ArrayList<String> candidateLines = new ArrayList<>();
            ArrayList<File> filesIR = getFilePath();
                //Creating Scanner instance to read File in Java
                Scanner IR = new Scanner(filesIR.get(0));

                //Reading each line of the file using Scanner class
                int lineNumber = 1;
                while (IR.hasNextLine()) {
                    //Removes the commas from the file for more seamless reading with Scanner
                    String line = IR.nextLine().replaceAll(",", " ");
                    Scanner s = new Scanner(line);
                    if (lineNumber == 1) {
                        String type = s.next();
                        if (!type.equals("IR") && !type.equals("CPL")) {
                            System.err.println("The election that type you have given contradicts the type of election file being parsed, program" +
                                    " will abort");
                            System.exit(0);
                        }
                        setElectionType(type);
                    }
                    //Gets and saves the total number of candidates in the IR Election File
                    if (lineNumber == 2) {
                        String numOfCand = s.next();
                        totalC = Integer.parseInt(numOfCand);
                        // Create a list of Candidate objects with the given total number of candidates
                        candidates = new ArrayList<Candidate>(totalC);
                    }
                    //If statement for reading and saving the Candidates and their Abbreviated parties
                    if (lineNumber == 3) {
                        int index = 1;
                        while (s.hasNext()) {
                            String candidateLine = s.next();

                            //Used to transform the party abbreviations into full party Strings
                            if (index % 2 == 0) {
                                candidateLine = partyStringParser(candidateLine);
                            }
                            candidateLines.add(candidateLine);
                            index++;
                        }
                    }

                    //s.close(); //delete if not working
                    lineNumber++;
                }
                IR.close();

                //for statement for creating the Candidate Variables with the Candidate names and parties
                for (int i = 0; i < candidateLines.size() - 1; i += 2) {
                    ArrayList<Integer> votes = new ArrayList<Integer>();
                    Candidate candidate = new Candidate(candidateLines.get(i), candidateLines.get(i + 1), votes);
                    candidates.add(candidate);
                }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please check if the file path is correct.");
        }
        return candidates;
    }

    /**
     * Method to parse, retrieve and set the type of election being ran
     */
    public static void electionTypeParser() {
        try {
            File electionFile = getFilePath().get(0);
            Scanner s = new Scanner(electionFile);
            String line = s.nextLine().replaceAll(",", " ");;
            Scanner sc = new Scanner(line);
            String type = sc.next();
            System.out.println(type);
            if (electionTypeChecker(type)) {
                setElectionType(type);
            }
            else {
                System.err.println("This isn't an election type the program is familiar with, program is aborting...");
                System.exit(0);
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
}
    public static void ballotParser() {
        try {
            //An ArrayList instantiated to read and store the information of the Line where the Candidates are in the file
            ArrayList<File> filesIR = getFilePath();
            for (File file : filesIR) {
                Scanner IR = new Scanner(file);

                //Reading each line of the file using Scanner class
                int lineNumber = 1;
                while (IR.hasNextLine()) {
                    //Removes the commas from the file for more seamless reading with Scanner
                    String line = IR.nextLine().replaceAll(",", " ");
                    Scanner s = new Scanner(line);
                    // if statement that enacts everything under line 4 to be recorded as the Ballot
                    if (lineNumber > 4) {
                        ArrayList<Integer> rankings = new ArrayList<>();
                        String values[] = line.split(" ", totalC);
                        //loop to record votes and add them to ballot object
                        int validity = 0;
                        for (int i = 0; i < values.length; i++) {
                            //counter that determines if a ballot is valid or not
                            if (values[i].equals("")) {
                                validity ++;
                                rankings.add(0);
                            } else {
                                rankings.add(Integer.parseInt(values[i]));
                            }
                        }
                        Ballot entry = new Ballot(rankings, true);
                        if (validity >= (values.length / 2)) {
                            entry.setValidity(false);
                        }
                        ballots.add(entry);
                    }
                    s.close(); //delete if not working
                    lineNumber++;
                }
                IR.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to return valid or invalid ballots
     * @param ballots
     * @return
     */
    public static ArrayList<Ballot> filterBallots(ArrayList<Ballot> ballots, boolean validityFlag) {
        ArrayList<Ballot> filteredBallots = new ArrayList<Ballot>();
        for (Ballot ballot : ballots) {
            if (ballot.isValidity() == validityFlag) {
                filteredBallots.add(ballot);
            }
        }
        return filteredBallots;
    }
        /**
     * Method to parse through IR Election file and get all the Party's information creating an array of Parties
     * @return
     */
    public void partyParser() {
        try {
            ArrayList<File> partyFiles = getFilePath();

            for (File partyFile : partyFiles) {
                //Creating Scanner instance to read File in Java
                Scanner partyScanner = new Scanner(partyFile);

                //Starting votes of each party
                int startingVotes = 0;

                //Reading each line of the file using Scanner class
                int lineNumber = 1;
                while (partyScanner.hasNextLine()) {
                    String line = partyScanner.nextLine();
                    if (lineNumber == 6) {
                        ArrayList<String> partyArray = new ArrayList<String>();
                        int begin_index = line.indexOf('(') + 1;
                        int end_index = line.indexOf(')', begin_index);
                        while (begin_index != 0 && end_index != -1) {
                            String partyName = line.substring(begin_index, end_index);
                            Party party = new Party(startingVotes, partyName);
                            this.parties.add(party);
                            begin_index = line.indexOf('(', end_index) + 1;
                            end_index = line.indexOf(')', begin_index);
                        }
                    }

                    lineNumber++;
                }
                partyScanner.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please check if the file path is correct");
        }

    }

    /**
     * Main method where program is ran
     * @param args
     */
    public static void main(String[] args) {
        //calls the userprompter to ask user for input on election file and type
        userPrompter();
        //Gets the type of election being ran
        electionTypeParser();
        Date elecDate = getDate();
        //calls electiontype variable and runs either IR or CPL method depending on input
        switch (electionType) {
            case "IR":
                candidateParser();
                ballotParser();
                //creates roster list to be used to store all candidates
                ArrayList<Candidate> roster = new ArrayList<Candidate>();
                roster = (ArrayList)candidates.clone();
                //creates IRElection object to run IR election
                IRElection election = new IRElection(null, candidates, elecDate);
                //Gets the valid ballots from the ballots parsed
                ArrayList<Ballot> validBallots = filterBallots(ballots, true);
                //Gets the invalid ballots from the ballots parsed
                ArrayList<Ballot> invalidBallots = filterBallots(ballots, false);
                //runs the electionIR method with the ballots as the parameter
                ArrayList<Candidate> c = election.electionIR(validBallots);

                //sets the Winner of the Candidates with votes returned by the prior method
                election.setWinner(candidates, 1);
                //sets the candidates in election to the full roster
                election.setCandidates(roster);
                //displays the winner to the terminal
                election.displayWinner();
                //creates the object to generate the Audit and Media file
                IRAudit auditMediaIR = new IRAudit();
                auditMediaIR.produceAuditIR(election);
                auditMediaIR.produceMediaIR(election);
                auditMediaIR.writeBallotsToFile(election, invalidBallots);
                election.displayTable();
                break;
            case "CPL":
                CPLElection runCPL = new CPLElection(parties);
                runCPL.readFile(getFilePath().get(0));
                runCPL.firstSeatWaveAllocation();

                String partyWithMostSeats = "";
                Integer maxSeats = 0;
                for (Map.Entry<String, Integer> entry : runCPL.getPartySeats().entrySet()) {//find party with most seats
                    if (entry.getValue() > maxSeats) {
                        maxSeats = entry.getValue();
                        partyWithMostSeats = entry.getKey();
                    }
                }
                runCPL.setWinner(partyWithMostSeats);
                runCPL.displayWinner();

                CPLAudit audit = new CPLAudit();
                audit.produceAuditCPL(runCPL);
                audit.produceMediaCPL(runCPL);
                break;
            case "PO":
                candidateParserPO();
                ballotParser();
                //creates roster list to be used to store all candidates
                ArrayList<Candidate> rosterPO = new ArrayList<Candidate>();
                rosterPO = (ArrayList)candidates.clone();
                //creates IRElection object to run IR election
                POElection electionPO = new POElection(null, candidates, elecDate);
                for (Candidate candidate : candidates) {
                       // System.out.println(candidate.getName());
                    }
                //Run the PO election algorithm with the Ballots
                ArrayList<Candidate> cPO = electionPO.electionPO(ballots);
                //Get the Winner
                electionPO.getWinner(cPO);
                electionPO.setCandidates(rosterPO);
                //displays the winner to the terminal
               electionPO.showResults(rosterPO);
                break;
        }
    }
}
