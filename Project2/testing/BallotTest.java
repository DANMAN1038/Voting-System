
import static org.junit.Assert.*;
import objects.Ballot;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BallotTest {

    private Ballot ballot;

    @Before
    public void setUp() throws Exception {
        // Create a new Ballot object with some sample data for testing
        ArrayList<Integer> votes = new ArrayList<Integer>();
        votes.add(1);
        votes.add(2);
        votes.add(3);
        boolean validity = true;
        ballot = new Ballot(votes, validity);
    }

    @Test
    public void testGetVotes() {
        // Test that the getVotes() method returns the correct votes ArrayList
        ArrayList<Integer> expectedVotes = new ArrayList<Integer>();
        expectedVotes.add(1);
        expectedVotes.add(2);
        expectedVotes.add(3);
        assertEquals(expectedVotes, ballot.getVotes());
    }

    @Test
    public void testSetVotes() {
        // Test that the setVotes() method sets the votes ArrayList correctly
        ArrayList<Integer> newVotes = new ArrayList<Integer>();
        newVotes.add(4);
        newVotes.add(5);
        newVotes.add(6);
        ballot.setVotes(newVotes);
        assertEquals(newVotes, ballot.getVotes());
    }

    @Test
    public void testIsValidity() {
        // Test that the isValidity() method returns the correct validity boolean
        assertTrue(ballot.isValidity());
    }

    @Test
    public void testSetValidity() {
        // Test that the setValidity() method sets the validity boolean correctly
        boolean newValidity = false;
        ballot.setValidity(newValidity);
        assertFalse(ballot.isValidity());
    }

    @Test
    public void testGetPreference() {
        // Test that the getPreference() method returns the correct preference ArrayList
        ArrayList<String> expectedPreference = new ArrayList<String>();
        expectedPreference.add("John");
        expectedPreference.add("Jane");
        expectedPreference.add("Jim");
        Ballot namedBallot = new Ballot(expectedPreference, 3);
        assertEquals(expectedPreference, namedBallot.getPreference());
    }

}
