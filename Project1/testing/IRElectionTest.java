import election.IRElection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import objects.Ballot;
import objects.Candidate;

public class IRElectionTest {

    @Test
    public void testElectionIR() {
        // Create some candidates
        Candidate c1 = new Candidate("Alice", null, (ArrayList<Integer>) null);
        Candidate c2 = new Candidate("Bob", null, (ArrayList<Integer>) null);
        Candidate c3 = new Candidate("Charlie", null, (ArrayList<Integer>) null);
        Candidate c4 = new Candidate("Dave", null, (ArrayList<Integer>) null);
        ArrayList<Candidate> candidates = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));

        // Create some ballots
        Ballot b1 = new Ballot(new ArrayList<>(Arrays.asList(1, 2, 3, 4)));
        Ballot b2 = new Ballot(new ArrayList<>(Arrays.asList(2, 3, 1, 4)));
        Ballot b3 = new Ballot(new ArrayList<>(Arrays.asList(3, 2, 1, 4)));
        Ballot b4 = new Ballot(new ArrayList<>(Arrays.asList(4, 2, 3, 1)));
        Ballot b5 = new Ballot(new ArrayList<>(Arrays.asList(2, 4, 3, 1)));
        ArrayList<Ballot> ballots = new ArrayList<>(Arrays.asList(b1, b2, b3, b4, b5));

        // Run the election
        IRElection election = new IRElection(null, candidates);
        ArrayList<Candidate> result = election.electionIR(ballots);
        election.setWinner(result, 1);

        // Check that the winner is correct
        assertEquals(c2.getName(), election.getWinner().getName());
    }

}