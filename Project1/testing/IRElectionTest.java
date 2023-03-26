import static org.junit.Assert.*;
import org.junit.Test;
import objects.*;
import election.*;
import objects.*;
import java.util.ArrayList;
import java.util.Date;

public class IRElectionTest {
    private ArrayList<Integer> votes = new ArrayList<>();
    private Date date = new Date();
    private ArrayList<Candidate> candidates = new ArrayList<>();
    private Candidate a = new Candidate("Dave", "R", votes);
    private Candidate b = new Candidate("Jack", "L", votes);
    private Candidate c = new Candidate("Jim", "D", votes);
    private IRElection election = new IRElection(a, candidates, date);

    @Test
    public void testElectionWinner() {
        String expected = b.getName();
        election.setWinner(b);
        assertEquals("The Winner of the election is returned", expected, election.getWinner().getName());
    }
    @Test
    public void testElectionCandidates() {
        candidates.add(a);
        candidates.add(b);
        candidates.add(c);
        assertEquals("The first candidate is returned", candidates.get(0), election.getCandidates().get(0));
        assertEquals("The first candidate is returned", candidates.get(1), election.getCandidates().get(1));
        assertEquals("The first candidate is returned", candidates.get(2), election.getCandidates().get(2));
    }
}