package election;
import objects.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
/** CPLAudit class.
 * @author syed0053
 */
public class CPLAudit extends CPLElection{
    /**
     * Constructor for CPLAudit class
     */
    public CPLAudit() {
        super();
    }

    private CPLElection election;

    /**
     * Produces audit file for CPL election, includes all relevant info to election process
     * @param elec, an object that allows access to election that was just made
     */
    public void produceAuditCPL(CPLElection elec) {
        File audit = new File("Audit.txt");
        try {
            FileWriter writer = new FileWriter("Audit.txt");
            writer.write("Election Conducted: Closed Party List Election \r\n");
            Date today = new Date();
            writer.write("Election Date: " + today + "\r\n");
            writer.write("The total votes in this election was: " + elec.getTotalVotes() + "\r\n");
            writer.write("Party and Seat totals: " + elec.getPartySeats() + "\r\n");
            writer.write("Party and Vote totals: " + elec.getPartyVotes() + "\r\n");
            writer.write("The Winner of The Election Was " + elec.getWinner() + "!");
            System.out.println("Successfully created the Audit File");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    /**
     * Produces media file for CPL election
     * @param elec, an object that allows access to election that was just made
     */
    public void produceMediaCPL(CPLElection elec) {
        File media = new File("Media.txt");
        try {
            FileWriter writer = new FileWriter("Media.txt");
            writer.write("Election Conducted: Closed Party List Election \r\n");
            Date today = new Date();
            writer.write("Election Date: " + today + "\r\n");
            writer.write("Party and Seat totals: " + elec.getPartySeats() + "\r\n");
            writer.write("The Winner of The Election Was " + elec.getWinner() + "!");
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