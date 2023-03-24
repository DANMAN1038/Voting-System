import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author coll1396
 *
 * The IRAudit Class which deals with creating the Media and Audit Files for IR Elections
 */
public class IRAudit {
    private IRElection election;


    /**
     * Method to create the Audit file of an IR Election
     * @param elec
     */
    //SAME AS produceMediaIR, TO BE REVISED
    //TODO Add how many votes a candidate had
    //TODO Add number of Candidates
    //TODO Add number of ballots
    //TODO Audit file should show how the election progressed
    //TODO Audit file should show the order of removal of candidates in IR and what ballots were redistributed
    public void produceAuditIT(IRElection elec) {
        elec = this.election;
        File audit = new File("Audit.txt");
        try {
            ArrayList<Candidate> candidates = elec.getCandidates();
            FileWriter writer = new FileWriter("Audit.txt");
            writer.write("Election Conducted: Instant Runoff Election \r\n");
            writer.write("Election Date: " + this.election.getDate() + "\r\n");
            writer.write("Candidates Participated:\r\n");
            for (Candidate i : candidates) {
                writer.write(i.getParty() + ": " + i.getName() + "\r\n");
            }
            writer.write("The Winner of The Election Was " + elec.getWinner().getName() + "!");
            System.out.println("Successfully created the Audit File");
        }
        catch (IOException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    /**
     * Method to produce the Media file for an IR Election
     * @param elec
     */
    //SAME AS produceAuditIT, TO BE REVISED
    public void produceMediaIR(IRElection elec) {
        elec = this.election;
        File audit = new File("Audit.txt");
        try {
            ArrayList<Candidate> candidates = elec.getCandidates();
            FileWriter writer = new FileWriter("Media.txt");
            writer.write("Election Conducted: Instant Runoff Election \r\n");
            writer.write("Election Date: " + this.election.getDate() + "\r\n");
            writer.write("Candidates Participated:");
            for (Candidate i : candidates) {
                writer.write(i.getParty() + ": " + i.getName() + "\r\n");
            }
            writer.write("The Winner of The Election Was " + elec.getWinner().getName() + "!");
            System.out.println("Successfully created the Media File");
        }
        catch (IOException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}