import static org.junit.Assert.*;
import org.junit.Test;
import objects.*;

import java.util.ArrayList;

public class CandidateTest {

    private ArrayList<Integer> votes = new ArrayList<>(4);
    private Candidate Dave = new Candidate(4, "Dave", "Democrat", null);

    @Test
    public void testCandidateName() {
        String expected = Dave.getName();
        assertEquals("The Name of the Candidate is returned", expected, "Dave");
    }

    @Test
    public void testCandidateParty() {
        String expectedParty = Dave.getParty();
        assertEquals("The Party of the Candidate is returned", expectedParty, "Democrat");
    }

    @Test
    public void testCandidateRank() {
        int expectedRank = Dave.getRank();
        assertEquals("The rank of the Candidate", expectedRank, 4);
    }

    @Test
    public void testGettingTotalVotes() {
        votes.add(4);
        votes.add(5);
        votes.add(2);
        Dave.setRanks(votes);
        int expectedVotes = Dave.calcTotalVotes(votes.size());
        assertEquals("This test adds all the votes in the array list and returns the candidate's total votes", 11, expectedVotes);
    }
}