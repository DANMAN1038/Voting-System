package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import election.*;
import objects.*;

/**
 * @author moha1113
 * @author coll1396
 * @author syed0053
 * @author azamx016
 *
 * The Main class of the program where the election is conducted and the subsequent methods to induce it is run
 */
public class Voting {
    private static Integer totalC;
    private static List<Party> parties;
    private Integer totalP;
    private static String electionType;
    private static File filePath;
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
     * @param electType
     */
    public static void setElectionType(String electType) {
        electionType = electType;
    }

    /**
     * Method for checking that the file type is indeed a CSV and that it exist in the given directory
     *
     * @param file the File being checked
     * @return the boolean which says true if the file exist and is valid and false if it isn't
     */
    public static boolean checkFileType(String file) {
        File f = new File(file);
        if (file.endsWith(".csv") && f.exists()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method to check the election of a file to make sure its a CSV file and that it exists
     *
     * @param type
     * @return
     */
    public static boolean electionTypeChecker(String type) {
        if (type.equals("IR") || type.equals("CPL")) {
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
    public static File getFilePath() {

        return filePath;
    }

    /**
     * Method to set the file of the election being conducted
     * @param electFile
     */
    public static void setFilePath(File electFile) {
        filePath = electFile;
    }

    /**
     * Method to prompt the user for input; asking for the type of election being run and the election file
     *
     */
    public static void userPrompter() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter the Election file name with extension here: ");
        String file = userInput.nextLine();
        if (!checkFileType(file)) {
            System.err.println("The file you provided is not a CSV file or does not exist in the given directory. Please" +
                    " enter a valid file");
            userPrompter();
            return;
        }
        File electionFile = new File(file);
        setFilePath(electionFile);
        System.out.println("Next can you provide the type of election being run \r\nWrite \"IR\" for Instant Runoff Election \r\n" +
                "Write \"CPL\" for Close Party Listing Election ");
        String type = userInput.nextLine();
        while (!electionTypeChecker(type)) {
            System.err.println("You have provided and invalid election type please try again ");
            type = userInput.nextLine();
        }
        setElectionType(type);
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
                party = "Democrat";
                break;
            case "(R)":
                party = "Republican";
                break;
            case "(I)":
                party = "Independent";
                break;
            case "(L)":
                party = "Liberal";
                break;
            default:
                party = "Unknown";
        }
        return party;
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
            File IRFile = getFilePath();

            //Creating Scanner instance to read File in Java
            Scanner IR = new Scanner(IRFile);

            //Reading each line of the file using Scanner class
            int lineNumber = 1;
            while (IR.hasNextLine()) {
                //Removes the commas from the file for more seamless reading with Scanner
                String line = IR.nextLine().replaceAll(",", " ");
                Scanner s = new Scanner(line);
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
                if (lineNumber > 4) {
                    ArrayList<Integer> rankings = new ArrayList<>();
                    String values[] = line.split(" ", totalC);
                    for (int i = 0; i < values.length; i++) {
                        if (values[i].equals("")) {
                            rankings.add(0);
                        }
                        else {
                            rankings.add(Integer.parseInt(values[i]));
                        }
                    }
                    Ballot entry = new Ballot(rankings);
                    ballots.add(entry);
                }
                lineNumber++;
            }
            IR.close();

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
     * Method to parse through IR Election file and get all the Party's information creating an array of Parties
     * @return
     */
    public void partyParser() {
        try {
            File partyFile = getFilePath();

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
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please check if the file path is correct");
        }

    }


    /**
     * Main method where program is ran
     * @param args
     */
    public static void main(String[] args) {
        userPrompter();
        candidateParser();
        switch (electionType) {
            case "IR":
                ArrayList<Candidate> cands = candidates;
                IRElection election = new IRElection(null, cands, null);
                ArrayList<Candidate> c = election.electionIR(ballots);
            candidates = c;
            Candidate w = election.decideWinner(candidates);
            election.setWinner(w);
            election.displayWinner();
            System.out.println(election.getCandidates().size());
            IRAudit auditIR = new IRAudit();

                auditIR.produceAuditIT(election);

                    break;
                    case "CPL":
                        CPLElection runCPL = new CPLElection(parties);
                        runCPL.readFile(getFilePath());
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
        }
    }
}

