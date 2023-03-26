import static org.junit.Assert.*;
import org.junit.Test;
import objects.*;
public class PartyTest {

    private Party Democrat = new Party(4, "democrat");

    @Test
    public void testPartyName() {
        String expected = Democrat.getParty();
        assertEquals("The Name of the Party is returned", expected, "democrat");
    }

    @Test
    public void testPartyVotes() {
        int expectedVotes = Democrat.getVotes();
        assertEquals("The votes of the party is returned", expectedVotes, 4);
    }

    @Test
    public void testPartyAddVotes() {
        Democrat.addPartyVotes(4);
        int expectedVotes = Democrat.getVotes();
        assertEquals("The votes of the party is returned", expectedVotes, 8);
    }
}
