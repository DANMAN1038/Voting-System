import static org.junit.Assert.*;
import election.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import objects.*;
import org.junit.Test;

import objects.Ballot;
import objects.Candidate;

public class POElectionTest {

    @Test
    public void testElectionPO() {
        // Create a POElection object with some candidates and a date
        Candidate winner = new Candidate("Alice");
        ArrayList<Candidate> candidates = new ArrayList<Candidate>(Arrays.asList(
                new Candidate("Bob"),
                new Candidate("Charlie"),
                new Candidate("David")
        ));
        Date date = new Date();
        POElection election = new POElection(winner, candidates, date);

        // Create some ballots
        ArrayList<Integer> ranks1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> ranks2 = new ArrayList<>(Arrays.asList(2, 1, 3));
        ArrayList<Integer> ranks3 = new ArrayList<>(Arrays.asList(3, 1, 2));
        Ballot ballot1 = new Ballot(ranks1, true);
        Ballot ballot2 = new Ballot(ranks2, true);
        Ballot ballot3 = new Ballot(ranks3, true);
        ArrayList<Ballot> ballots = new ArrayList<Ballot>(Arrays.asList(ballot1, ballot2, ballot3));

        // Run the election and get the results
        ArrayList<Candidate> results = election.electionPO(ballots);
        int expected = 1;
        int expected2 = 2;
        int expected3 = 0;
        int actual =  candidates.get(0).getVotesPO();
        int actual2 = candidates.get(1).getVotesPO();
        int actual3 = candidates.get(2).getVotesPO();
        // Check that the vote counts are correct
        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
    }

    @Test
    public void testCreateCandidateBallots() {
        // Create a list of numbers to turn into ballots
        ArrayList<Integer> numbers = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));

        // Create the expected list of ballots
        ArrayList<ArrayList<Integer>> expectedBallots = new ArrayList<ArrayList<Integer>>(Arrays.asList(
                new ArrayList<Integer>(Arrays.asList(1, 5, 9)),
                new ArrayList<Integer>(Arrays.asList(2, 6, 10)),
                new ArrayList<Integer>(Arrays.asList(3, 7, 11)),
                new ArrayList<Integer>(Arrays.asList(4, 8, 12))
        ));

        // Run the method to create the ballots
        ArrayList<ArrayList<Integer>> actualBallots = POElection.createCandidateBallots(numbers);

        // Check that the actual ballots are the same as the expected ballots
        assertEquals(expectedBallots, actualBallots);
    }

    @Test
    public void testVoteCounter() {
        // Create some ballots
        ArrayList<Integer> ranks1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> ranks2 = new ArrayList<>(Arrays.asList(2, 1, 3));
        ArrayList<Integer> ranks3 = new ArrayList<>(Arrays.asList(3, 1, 2));
        Ballot ballot1 = new Ballot(ranks1, true);
        Ballot ballot2 = new Ballot(ranks2, true);
        Ballot ballot3 = new Ballot(ranks3, true);
        ArrayList<Ballot> ballots = new ArrayList<Ballot>(Arrays.asList(ballot1, ballot2, ballot3));

        // Count the votes for the first candidate in the first round
        int votes = POElection.voteCounter(ballots, 0, 1);

        // Check that the vote count is correct
        assertEquals(1, votes);
    }
}