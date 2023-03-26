import static org.junit.Assert.*;
import org.junit.Test;
import objects.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class BallotTest {

    private ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c"));
    private Ballot test = new Ballot(list);

    @Test
    public void testPartyName() {
        ArrayList<String> expected = test.getPreference();
        assertEquals("The list of preferences is returned", expected, list);
    }
}
