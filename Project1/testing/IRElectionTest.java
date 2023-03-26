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
    private IRElection election = new IRElection(a, candidates);

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
    @Test
    public void DecideElectionWInner() {
        candidates.add(a);
        candidates.add(b);
        candidates.add(c);
        candidates.get(0).getRanks().add(5);
        candidates.get(0).getRanks().add(3);
        candidates.get(0).getRanks().add(2);
        candidates.get(1).getRanks().add(2);
        candidates.get(1).getRanks().add(3);
        candidates.get(1).getRanks().add(1);
        candidates.get(2).getRanks().add(0);
        candidates.get(2).getRanks().add(0);
        candidates.get(2).getRanks().add(1);
        Candidate actual = election.decideWinner(candidates);
        Candidate expected = candidates.get(0) ;
        assertEquals("The winning candidate is returned", expected, actual);

    }
}