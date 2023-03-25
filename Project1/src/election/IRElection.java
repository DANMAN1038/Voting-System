package election;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import objects.*;

/**
 * @author coll1396
 *
 * The IRElection class to be used to produce
 * Audit and Media File
 */
public class IRElection extends IElection {
    private Candidate winner;
    private ArrayList<Candidate> candidates;
    private Date date;

    public IRElection(Candidate winner, ArrayList<Candidate> candidates, Date date) {
        this.winner = winner;
        this.candidates = candidates;
        this.date = date;
    }
    /**
     * Method to actually run the Instant RunOff Election and return the winner of the election
     * @param allCandidates All the candidates taking part in the Instant Runoff Election
     * @return The Candidate that won the Election according to the Instant Runoff rules
     */
    public Candidate electionIR(ArrayList<Candidate> allCandidates) {
        allCandidates = this.candidates;
        int totalVotes;
        for (Candidate i : allCandidates) {
            // To Be Completed
        }
        return null;
    }

    /**
     * Method to decide the winner of a tie between Candidates in an IR Election
     * @param a One of the two Candidates in the tiebreak
     * @param b One of the two Candidates in the tiebreak
     * @return One of the two candidates which won the tiebreaker at random
     */
    public Candidate winnnerTieDecider(Candidate a, Candidate b) {
        Candidate[] tieBreaker= new Candidate[1];
        tieBreaker[0] = a;
        tieBreaker[1] = b;
        Candidate c = tieBreaker[(int)Math.random() * tieBreaker.length];
        return c;
    }

    /**
     * Method to decide the winner of a tie between Candidates in an CPL Election
     * @param parties
     * @param seatNo
     * @return
     */
    public Party winnnerTieDecider(Party[] parties, int seatNo) {
        throw new UnsupportedOperationException("invalid operation for Tie Breaker in IR Election");
    }
    /**
     * Method to display the Winner of the IR Election to the terminal
     */
    public void displayWinner() {
        System.out.println("The Winner of the Election is: " + this.winner.getName() + "!");
    }

    /**
     * Method to set the Candidate that won this instance of the Instant Runoff Election
     * @param x The Candidate that won
     */
    public void setCandidateWinner(Candidate x) {
        this.winner = x;
    }

    /**
     * Method to return the Date of the Election in String format
     * @return The String formatted date of the Election
     */
    public String getDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String elecDate = df.format(this.date);
        return  elecDate;
    }

    /**
     * Method to return the ArrayList of Candidates
     * @return
     */
    public ArrayList<Candidate> getCandidates() {
        return this.candidates;
    }

    /**
     * Method to return the winner of the IR Election
     * @return
     */
    public Candidate getWinner() {
        return this.winner;
    }
}