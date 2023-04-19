import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import election.*;
import main.*;
import objects.Party;

public class CPLElectionTest {

    Voting obj = new Voting();
    private List<Party> parties = new ArrayList<>();
    Party party3 = new Party(0, "Democrat");
    Party party2 = new Party(0, "Republican");
    File electionFile;
    CPLElection runCPL;

    @BeforeEach
    public void setUp() {
        electionFile = new File("home/syed0053/CSCI5801/repo-Team20/Project1/misc/CPL_Election.csv");
        parties.add(party3);
        parties.add(party2);
        obj.setFilePath(electionFile);
        runCPL = new CPLElection(parties);
        runCPL.readFile(obj.getFilePath());
        runCPL.firstSeatWaveAllocation();
    }

    @Test
    public void testTotalSeats() {
        int a = runCPL.getNumSeats();
        assertEquals("The number of seats is returned", a, 20);
    }

    @Test
    public void testWinner() {
        String party = runCPL.getWinner();
        assertEquals("The winner is returned", party, "Independent");
    }

    @Test
    public void testPartyVotes() {
        Map<String, Integer> test = runCPL.getPartyVotes();
        assertEquals("The party and its votes returned", test, "{Democratic=72, Republican=143, New Wave=0, Reform=144, Green=72, Independent=216}");
    }

    @Test
    public void testPartySeats() {
        Map<String, Integer> test = runCPL.getPartySeats();
        assertEquals("The party and its votes returned", test, "{Democratic=2, Republican=4, New Wave=0, Reform=4, Green=2, Independent=6}");
    }

    @Test
    public void testPartyRemainders() {
        Map<String, Integer> test = runCPL.getPartyRemainders();
        assertEquals("The party and its votes returned", test, "{Democratic=8, Republican=15, New Wave=0, Reform=16, Green=8, Independent=24}");
    }

    @Test
    public void testQuota() {
        int quota = runCPL.getQuota();
        assertEquals("The number of seats is returned", quota, 32);
    }

    @Test
    public void testTotalVotes() {
        int votes = runCPL.getTotalVotes();
        assertEquals("The number of seats is returned", votes, 647);
    }

    @Test
    public void testWinnerTieDecider() {
        int number = runCPL.winnerTieDecider();
        assertEquals("The number of seats is returned", number, 1);
    }
}