import static org.junit.Assert.*;
import org.junit.Test;
import objects.*;
import java.util.ArrayList;
public class CandidateTest {

    private ArrayList<String> testList = new ArrayList<String>();
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
}