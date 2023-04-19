package election;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import objects.*;

/**The IRAudit Class which deals with creating the Media and Audit Files for IR Elections
 * @author coll1396
 *
 */
public class IRAudit {
    private IRElection election;


    /**
     * Method to create the Audit file of an IR Election
     * @param elec the election file being utilized to mak the audit file
     */
    public void produceAuditIR(IRElection elec) {
        File audit = new File("Audit.txt");
        try {
            Date today = new Date();
            ArrayList<Candidate> candidates = elec.getCandidates();
            FileWriter writer = new FileWriter("Audit.txt");
            writer.write("Election Conducted: Instant Runoff Election \r\n");
            writer.write("Election Date: " + today + "\r\n");
            writer.write("Candidates Participated:\r\n");
            for (Candidate i : candidates) {
                writer.write("\r\n" + i.getParty() + ": " + i.getName() + " getting the following votes in each round: \r\n");
                for (Integer n: i.getRanks())
                    writer.write(n + " votes |");
            }
            writer.write("\r\nThe Winner of The Election Was " + elec.getWinner().getName() + "!");
            System.out.println("Successfully created the Audit File");
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    /**
     * Method to produce the Media file for an IR Election
     * @param elec the election file being utilized to mak the media file
     */
    public void produceMediaIR(IRElection elec) {
        File audit = new File("Media.txt");
        try {
            Date today = new Date();
            ArrayList<Candidate> candidates = elec.getCandidates();
            FileWriter writer = new FileWriter("Media.txt");
            writer.write("Election Conducted: Instant Runoff Election \r\n");
            writer.write("Election Date: " + today + "\r\n");
            writer.write("Candidates Participated:\r\n");
            for (Candidate i : candidates) {
                writer.write(i.getParty() + ": " + i.getName() + "\r\n");
            }
            writer.write("The Winner of The Election Was " + elec.getWinner().getName() + "!");
            System.out.println("Successfully created the Media File");
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}