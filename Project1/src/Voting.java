import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Voting {
    private Integer totalC;
    private Integer totalP;
    private String electionType;
    private File filePath;
    private List<Candidate> candidates;
    private List<Party> parties;

    public Voting(String filePath, Integer totalC, Integer totalP, String electionType, List<Party> parties, List<Candidate> candidates) {
        this.filePath = filePath;
        this.totalC = totalC;
        this.totalP = totalP;
        this.electionType = electionType;
        this.parties = parties;
        this.candidates = candidates;
    }
    public void checkFileType() {
        if (!filePath.endsWith(".csv")) {
            System.out.println("The file you provided is not a CSV file. Please provide a CSV file.");
        }
    }
    public void userPrompter() {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter the Election file name with extension here: ");
        File electionFile = new File(userInput.nextLine());
        this.filePath = filePath;
        scanner.nextLine(); // consume newline
        System.out.print("Enter the type of election: ");
        String electionType = scanner.nextLine();
        this.electionType = electionType;
    }

    public List<Candidate> candidateParser() {
        try {
            File IRFile = new File(filePath);

            //Creating Scanner instance to read File in Java
            Scanner IR = new Scanner(IRFile);

            //Reading each line of the file using Scanner class
            int lineNumber = 1;
            while (IR.hasNextLine()) {
                String line = IR.nextLine().replaceAll(",", "");
                Scanner S = new Scanner(line);

                if (lineNumber == 2) {
                    String NumofCand = S.next();
                    this.totalC = Integer.parseInt(NumofCand);
                    // Create a list of Candidate objects with the given total number of candidates
                    this.candidates = new ArrayList<Candidate>(this.totalC);
                }
                if (lineNumber == 3) {
                    int index = 0;
                    while (S.hasNext()) {
                        String candidateName = S.next();
                        Candidate candidate = new Candidate(index, candidateName, "", new ArrayList<Ballot>());
                        this.candidates.add(candidate);
                        index++;
                    }
                }
                lineNumber++;
            }
            IR.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please check if the file path is correct.");
        }

        return this.candidates;
    }
    public List<Party> partyParser() {
        try {
            File partyFile = new File(filePath);

            //Creating Scanner instance to read File in Java
            Scanner partyScanner = new Scanner(partyFile);

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
                        Party party = new Party(partyName);
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
        return this.parties;

    }

    public Ballot ballParser() {
        // TODO: implement ballot parser
        return null;
    }

    // public Candidate[] candidateParser(){

    //     File IRFile = new File("C:/sc/IRV.csv");

    //     //Creating Scanner instance to read File in Java
    //     Scanner IR = new Scanner(IRFile);
    //   //Reading each line of the file using Scanner class
    //   int lineNumber = 1;
    //   while(IR.hasNextLine()){
    //       String line = IR.nextLine().replaceAll(",","");
    //       Scanner S = new Scanner(IR.nextLine());
    //       if(lineNumber == 2){
    //         String NumofCand = S.next();
    //         this.totalC = Integer.parseInt(NumofCand);
    //       }
    //       if(lineNumber == 3){
    //         ArrayList<String> candArray = new ArrayList<String>();
    //         while(S.hasNext()){
    //             candArray.add(S.next());
    //         }
    //       }
    //       lineNumber++;
    //   }
    //

    public static void main(String[] args) {
        Voting voting = new Voting(null, null, null, null, new ArrayList<Party>(), new ArrayList<Candidate>());
        voting.userPrompter();
        // Candidate[] candidates = voting.candidateParser();
        // Party[] parties = voting.partyParser();
        // int result = voting.candidatePerParty(candidates, parties);
        // System.out.println("Ratio is: " + result);
    }
}

