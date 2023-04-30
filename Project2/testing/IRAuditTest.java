import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import election.IRAudit;
import election.IRElection;
import objects.Ballot;
import objects.Candidate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class IRAuditTest {
    private ArrayList<Integer> aVotes = new ArrayList<>();
    private ArrayList<Integer> bVotes = new ArrayList<>();
    private ArrayList<Integer> cVotes = new ArrayList<>();
    private ArrayList<Candidate> candidates = new ArrayList<>();
    private Candidate a = new Candidate("Dave", "Republican", aVotes);
    private Candidate b = new Candidate("Jack", "Libertarian", bVotes);
    private Candidate c = new Candidate("Jim", "Democrat", cVotes);
    private IRAudit irlAudit;
    private IRElection irElection;
    private Date today = new Date();
    private static final String TEST_FILE = "test.txt";
    private Path testFilePath;

    @Before
    public void setUp() throws Exception  {
        candidates.add(a);
        candidates.add(b);
        candidates.add(c);
        aVotes.add(5);
        bVotes.add(3);
        cVotes.add(1);
        irlAudit = new IRAudit();
        irElection = new IRElection(null, candidates, today);
        irElection.setWinner(a);
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("Audit.txt"));
        Files.deleteIfExists(Path.of("Media.txt"));
        //Files.deleteIfExists(Path.of(filename));
    }

    @Test
    //creates the expected Output of the Audit IR file and compares to the actual output of the Audit file
    public void testProduceAuditCPL() throws IOException {
        irlAudit.produceAuditIR(irElection);
        String expectedContent = "Election Conducted: Instant Runoff Election \r\n"
                + "Election Date: " + today + "\r\n"
                + "Candidates Participated:\r\n"
                + "\r\n" + a.getParty() + ": " + a.getName() + " getting the following votes in each round: \r\n"
                + a.getRanks().get(0) + " votes |\r\n"
                + b.getParty() + ": " + b.getName() + " getting the following votes in each round: \r\n"
                + b.getRanks().get(0) + " votes |\r\n"
                + c.getParty() + ": " + c.getName() + " getting the following votes in each round: \r\n"
                + c.getRanks().get(0) + " votes |\r\n"
                + "The Winner of The Election Was " + irElection.getWinner().getName() + "!";

        String actualContent = Files.readString(Path.of("Audit.txt"));
        assertEquals(expectedContent, actualContent);
    }

    @Test
    //creates the expected Output of the Media IR file and compares to the actual output of the Media file
    public void testProduceMediaCPL() throws IOException {
        irlAudit.produceMediaIR(irElection);
        String expectedContent = "Election Conducted: Instant Runoff Election \r\n"
                + "Election Date: " + today + "\r\n"
                + "Candidates Participated:\r\n"
                + a.getParty() + ": " + a.getName() + "\r\n"
                + b.getParty() + ": " + b.getName() + "\r\n"
                + c.getParty() + ": " + c.getName() + "\r\n"
                + "The Winner of The Election Was " + irElection.getWinner().getName() + "!";

        String actualContent = Files.readString(Path.of("Media.txt"));
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testWriteBallotsToFile() throws IOException {
        // Create a sample date
        Date date = new Date();

        // Create a sample ballot list
        ArrayList<Ballot> ballots = new ArrayList<Ballot>();
        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(3);
        Ballot a = new Ballot(ints, true);
        Ballot b = new Ballot(ints, true);
        Ballot c = new Ballot(ints, true);
        ballots.add(a);
        ballots.add(b);
        ballots.add(c);

        String expectedContent = "1 2 3 \n1 2 3 \n1 2 3 \n";
        // Write the ballots to a file
        irlAudit.writeBallotsToFile(irElection, ballots);
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         String filename = "invalidated_" + dateFormat.format(irElection.getDate()) + ".txt";
        // Create a SimpleDateFormat object to format the date

        String actualContent =  Files.readString(Path.of(filename));

        // Check that the file contents are correct
        assertEquals(expectedContent, actualContent);
        //assertEquals("2022-05-01\n1,2,3\n3,2,1\n", fileContents);
    }
}