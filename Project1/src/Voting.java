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
    private static String electionType;
    private static File filePath;
    private List<Candidate> candidates;
    private List<Party> parties;

    public String getElectionType() {
        return electionType;
    }

    public static void setElectionType(String electType) {
        electionType = electType;
    }

    public static boolean checkFileType(String file) {
        File f = new File(file);
        if (file.endsWith(".csv") && f.exists()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean electionTypeChecker(String type) {
        if (type.equals("IR") || type.equals("CPL")) {
            return true;
        }
        else {
            return false;
        }
    }

    public File getFilePath() {
        return filePath;
    }

    public static void setFilePath(File electFile) {
        filePath = electFile;
    }

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

    public List<Candidate> candidateParser() {
        try {
            File IRFile = getFilePath();

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
        userPrompter();

    }
}

