import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import election.CPLAudit;
import election.CPLElection;

public class CPLAuditTest {

    private CPLAudit cplAudit;
    private CPLElection cplElection;

    @Before
    public void setUp() {
        cplAudit = new CPLAudit();
        cplElection = new CPLElection();
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of("Audit.txt"));
        Files.deleteIfExists(Path.of("Media.txt"));
    }

    @Test
    public void testProduceAuditCPL() throws IOException {
        cplElection.setTotalVotes(1000);
        cplElection.setPartyVotes("PartyA", 500);
        cplElection.setPartySeats("PartyA", 10);
        cplElection.setPartyVotes("PartyB", 300);
        cplElection.setPartySeats("PartyB", 8);
        cplElection.setWinner("PartyA");
        cplAudit.produceAuditCPL(cplElection);

        String expectedContent = "Election Conducted: Closed Party List Election \r\n"
                + "Election Date: " + new Date() + "\r\n"
                + "The total votes in this election was: 1000\r\n"
                + "Party and Seat totals: {PartyA=10, PartyB=8}\r\n"
                + "Party and Vote totals: {PartyA=500, PartyB=300}\r\n"
                + "The Winner of The Election Was PartyA!";

        String actualContent = Files.readString(Path.of("Audit.txt"));
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testProduceMediaCPL() throws IOException {
        cplElection.setPartySeats("PartyA", 10);
        cplElection.setWinner("PartyA");
        cplAudit.produceMediaCPL(cplElection);

        String expectedContent = "Election Conducted: Closed Party List Election \r\n"
                + "Election Date: " + new Date() + "\r\n"
                + "Party and Seat totals: {PartyA=10}\r\n"
                + "The Winner of The Election Was PartyA!";

        String actualContent = Files.readString(Path.of("Media.txt"));

        assertEquals(expectedContent, actualContent);
    }

}