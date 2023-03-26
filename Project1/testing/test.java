import static org.junit.Assert.*;
import objects.*;
import org.junit.Test;
public class test {

    private Candidate Dave = new Candidate(4, "Dave", "Democrat", null );

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
}
