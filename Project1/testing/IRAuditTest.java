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
    private ArrayList<Integer> votes = new ArrayList<>();
    private ArrayList<Candidate> candidates = new ArrayList<>();
    private Candidate a = new Candidate("Dave", "R", votes);
    private Candidate b = new Candidate("Jack", "L", votes);
    private Candidate c = new Candidate("Jim", "D", votes);
    private IRAudit irlAudit;
    private IRElection irElection;

    @Before
    public void setUp() {
        candidates.add(a);
        candidates.add(b);
        candidates.add(b);
        irlAudit = new IRAudit();
        irElection = new IRElection(a, candidates);
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("Audit.txt"));
        Files.deleteIfExists(Path.of("Media.txt"));
    }

    @Test
    public void testProduceAuditCPL() throws IOException {

        irlAudit.produceAuditIR(irElection);
        String expectedContent = "Election Conducted: Instant Runoff Election \r\n"
                + "Election Date: " + new Date() + "\r\n"
                + "Candidates Participated:\r\n"
                + "Candidates Participated:\r\n"
                + a.getParty() + ": " + a.getName() + "\r\n"
                + b.getParty() + ": " + b.getName() + "\r\n"
                + c.getParty() + ": " + c.getName() + "\r\n"
                + "The Winner of The Election Was " + irElection.getWinner().getName() + "!";

        String actualContent = Files.readString(Path.of("Audit.txt"));
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testProduceMediaCPL() throws IOException {
        irlAudit.produceMediaIR(irElection);
        String expectedContent = "Election Conducted: Instant Runoff Election \r\n"
                + "Election Date: " + new Date() + "\r\n"
                + "Candidates Participated:\r\n"
                + "Candidates Participated:\r\n"
                + a.getParty() + ": " + a.getName() + "\r\n"
                + b.getParty() + ": " + b.getName() + "\r\n"
                + c.getParty() + ": " + c.getName() + "\r\n"
                + "The Winner of The Election Was " + irElection.getWinner().getName() + "!";

        String actualContent = Files.readString(Path.of("Media.txt"));
        assertEquals(expectedContent, actualContent);
    }

}