import objects.Ballot;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class BallotTest {

    private Ballot ballot;

    @Before
    public void setUp() throws Exception {
        ArrayList<Integer> votes = new ArrayList<>();
        votes.add(1);
        votes.add(2);
        votes.add(3);
        ballot = new Ballot(votes);
    }

    @Test
    public void testGetVotes() {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        assertEquals("The method to return the array list of votes of the ballot object",expected, ballot.getVotes());
    }

    @Test
    public void testSetVotes() {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(4);
        expected.add(5);
        expected.add(6);
        ballot.setVotes(expected);
        assertEquals("The method to set the votes for the ballot object",expected, ballot.getVotes());
    }

    @Test
    public void testGetPreference() {
        ArrayList<String> expected = null;
        assertNull(ballot.getPreference());
    }
}