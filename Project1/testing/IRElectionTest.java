import election.IRElection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import objects.Ballot;
import objects.Candidate;

public class IRElectionTest {
    private ArrayList<Candidate> candidates;
    private IRElection election;

    @Before
    public void setUp() throws Exception {
        // Create the ballots for the candidates
        ArrayList<Integer> b1 = new ArrayList<Integer>();
        b1.add(3);
        b1.add(2);
        b1.add(2);
        ArrayList<Integer> b2 = new ArrayList<Integer>();
        b2.add(3);
        b2.add(1);
        b2.add(2);
        ArrayList<Integer> b3 = new ArrayList<Integer>();
        b3.add(1);
        b3.add(2);
        b3.add(4);

        // Create some candidates and with the predetermined ballots
        Candidate c1 = new Candidate("Alice", null, (ArrayList<Integer>) b1);
        Candidate c2 = new Candidate("Bob", null, (ArrayList<Integer>) b2);
        Candidate c3 = new Candidate("Charlie", null, (ArrayList<Integer>) b3);
        candidates = new ArrayList<Candidate>();
        candidates.add(c1);
        candidates.add(c2);
        candidates.add(c3);

    }

    @Test
    public void testElectionIR() {

        // Run the election, setting the winner with the candidates and their votes
        election = new IRElection(null, candidates);
        election.setWinner(candidates, 1);

        // Check that the winner is correct
        assertEquals("Alice", election.getWinner().getName());
    }

    @Test
    public void testTieBreaker() {
        Candidate a = new Candidate("Jill", null, (ArrayList<Integer>) null);
        Candidate b = new Candidate("Jack", null, (ArrayList<Integer>) null);
        candidates.add(a);
        candidates.add(b);
        election = new IRElection(null, candidates);
        Candidate c = election.winnnerTieDecider(a, b);
        String winnerName = c.getName();
        assertTrue(winnerName.equals("Jill") || winnerName.equals("Jack"));

    }

}