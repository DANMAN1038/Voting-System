package election;
import java.awt.*;
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

    /**
     * Constructor for IRElection class
     * @param winner The winner of the election
     * @param candidates All candidates participating int the election
     * @param date The Date the election was decided
     */
    public IRElection(Candidate winner, ArrayList<Candidate> candidates, Date date) {
        this.winner = winner;
        this.candidates = candidates;
        this.date = date;
    }

    /**
     * Method to actually run the Instant RunOff Election and return the winner of the election
     * @return The Candidate that won the Election according to the Instant Runoff rules
     */
    public ArrayList<Candidate> electionIR(ArrayList<Ballot> ballots) {
        ArrayList<Integer> test = new ArrayList<>();
        ArrayList<Candidate> allCandidates = this.candidates;
       // allCandidates.get(0).getBallot().getVotes().clear();
       // allCandidates.get(1).getBallot().getVotes().clear();
       // allCandidates.get(2).getBallot().getVotes().clear();
      //  allCandidates.get(3).getBallot().getVotes().clear();

        int indexer = -1;
        for (int x = 1; x < allCandidates.size() + 1; x++) {
            indexer ++;
            for (int i = 0; i < allCandidates.size(); i++) {
                int amount = voteCounter(ballots, i, x);
                System.out.println(amount);
                //System.out.println(i +" " + x + " " + amount);
                if (i == 1) {
                   test.add(amount);
                }
                allCandidates.get(i).getBallot().getVotes().add(amount);
              // allCandidates.get(i).getBallot().getVotes().set(indexer, amount);
               // allCandidates.get(i).getBallot().getVotes().clear();


            }
            System.out.println("LOOP");
        }
System.out.println("Start");
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }
        return allCandidates;
    }

    /**
     * Counts how many ranked votes a candidate recieved
     * @param ballots the votes from the election that will be transferred to the candidates
     * @param index the index of the candidate
     * @param round the round of voting taking place
     * @return
     */
    public static int voteCounter(ArrayList<Ballot> ballots, int index, int round) {
        int count = 0;
        for (Ballot arr : ballots) {
            if (!arr.getVotes().isEmpty() && arr.getVotes().get(index) == round) {
                count++;
            }
        }
        return count;
    }
    /**
     * Method to decide the winner of a tie between Candidates in an IR Election
     * @param a One of the two Candidates in the tiebreak
     * @param b One of the two Candidates in the tiebreak
     * @return One of the two candidates which won the tiebreaker at random
     */
    public Candidate winnerTieDecider(Candidate a, Candidate b) {
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
    public Party winnerTieDecider(Party[] parties, int seatNo) {
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
        return elecDate;
    }

    /**
     * Method to set the winner of the Election
     * @param winner
     */
    public void setWinner(Candidate winner) {
        this.winner = winner;
    }

    /**
     * Method to set the candidates participating in the election
     * @param candidates
     */
    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
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