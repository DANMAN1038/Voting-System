import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

import election.IRAudit;
import election.IRElection;
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

    @Before
    public void setUp() {
        candidates.add(a);
        candidates.add(b);
        candidates.add(c);
        aVotes.add(5);
        bVotes.add(3);
        cVotes.add(1);
        irlAudit = new IRAudit();
        irElection = new IRElection(null, candidates);
        irElection.setWinner(a);
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("Audit.txt"));
        Files.deleteIfExists(Path.of("Media.txt"));
    }

    @Test
    //creates the expected Output of the Audit IR file and compares to the actual output of the Audit file
    public void testProduceAuditCPL() throws IOException {
        Date today = new Date();
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
                + "Election Date: " + new Date() + "\r\n"
                + "Candidates Participated:\r\n"
                + a.getParty() + ": " + a.getName() + "\r\n"
                + b.getParty() + ": " + b.getName() + "\r\n"
                + c.getParty() + ": " + c.getName() + "\r\n"
                + "The Winner of The Election Was " + irElection.getWinner().getName() + "!";

        String actualContent = Files.readString(Path.of("Media.txt"));
        assertEquals(expectedContent, actualContent);
    }

}