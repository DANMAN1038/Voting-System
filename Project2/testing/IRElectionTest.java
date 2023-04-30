import election.IRElection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import objects.Ballot;
import objects.Candidate;

public class IRElectionTest {
    private ArrayList<Candidate> candidates;
    private ArrayList<Candidate> otherCandidates;
    private IRElection election;
    private Date today = new Date();
    private Candidate candidate1;
    private Candidate candidate2;
    private Candidate candidate3;

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
        candidate1 = new Candidate("John", "D", b1);
        candidate2 = new Candidate("Jane", "R", b2);
        candidate3 = new Candidate("Mike", "L", b3);
        otherCandidates = new ArrayList<>(Arrays.asList(candidate1, candidate2, candidate3));
        election = new IRElection(null, candidates, today);

    }

    @Test
    public void testElectionIR() {

        // Run the election, setting the winner with the candidates and their votes
        election = new IRElection(null, candidates, today);
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
        election = new IRElection(null, candidates, today);
        Candidate c = election.winnnerTieDecider(a, b);
        String winnerName = c.getName();
        assertTrue(winnerName.equals("Jill") || winnerName.equals("Jack"));

    }

    @Test
    public void testCreateCandidateBallots() {
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        ArrayList<ArrayList<Integer>> expected = new ArrayList<>();
        expected.add(new ArrayList<>(Arrays.asList(1, 5)));
        expected.add(new ArrayList<>(Arrays.asList(2, 6)));
        expected.add(new ArrayList<>(Arrays.asList(3, 7)));
        expected.add(new ArrayList<>(Arrays.asList(4, 8)));
        ArrayList<ArrayList<Integer>> result = election.createCandidateBallots(numbers);
        assertEquals(expected, result);
    }

    @Test
    public void testSetVotes() {
        ArrayList<Integer> ranks1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> ranks2 = new ArrayList<>(Arrays.asList(2, 1, 3));
        ArrayList<Integer> ranks3 = new ArrayList<>(Arrays.asList(3, 1, 2));
        ArrayList<ArrayList<Integer>> votes = new ArrayList<>(Arrays.asList(ranks1, ranks2, ranks3));
        ArrayList<Candidate> expected = new ArrayList<>(Arrays.asList(candidate1, candidate2, candidate3));
        election.setVotes(expected, votes);
        for (int i = 0; i < expected.size(); i++) {
            Candidate candidate = expected.get(i);
            assertEquals(votes.get(i), candidate.getRanks());
        }
    }

    @Test
    public void testVoteCounter() {
        ArrayList<Integer> ranks1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> ranks2 = new ArrayList<>(Arrays.asList(2, 1, 3));
        ArrayList<Integer> ranks3 = new ArrayList<>(Arrays.asList(3, 1, 2));
        Ballot ballot1 = new Ballot(ranks1, true);
        Ballot ballot2 = new Ballot(ranks2, true);
        Ballot ballot3 = new Ballot(ranks3, true);
        ArrayList<Ballot> ballots = new ArrayList<>(Arrays.asList(ballot1, ballot2, ballot3));
        int index = 0;
        int round = 1;
        int expected = 1;
        int result = election.voteCounter(ballots, index, round);
        assertEquals(expected, result);
    }

}