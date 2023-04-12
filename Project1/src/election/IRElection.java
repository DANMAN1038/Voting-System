package election;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
     */
    public IRElection(Candidate winner, ArrayList<Candidate> candidates) {
        this.winner = winner;
        this.candidates = candidates;
    }

    /**
     * Method to actually run the Instant RunOff Election and return the winner of the election
     * @return The Candidate that won the Election according to the Instant Runoff rules
     */
    public ArrayList<Candidate> electionIR(ArrayList<Ballot> ballots) {
        ArrayList<Integer> votes = new ArrayList<>();
        ArrayList<Candidate> allCandidates = this.candidates;


      //  int indexer = -1;
        for (int x = 1; x < allCandidates.size() + 1; x++) {
           // indexer ++;
            for (int i = 0; i < allCandidates.size(); i++) {
                int amount = voteCounter(ballots, i, x);
                votes.add(amount);

            }

        }
        ArrayList<ArrayList<Integer>> rankings = createCandidateBallots(votes);
        setVotes(allCandidates, rankings);
        return allCandidates;
    }

    /**
     * Array list to set the votes for all the candidate object
     * @param candidates all the candidates
     * @param votes the votes being set
     */
    public static void setVotes(ArrayList<Candidate> candidates, ArrayList<ArrayList<Integer>> votes) {
        if (candidates.size() != votes.size()) {
            throw new IllegalArgumentException("ArrayLists must have the same size.");
        }
        for (int i = 0; i < candidates.size(); i++) {
            candidates.get(i).setRanks(votes.get(i));
        }
    }
    /**
     * Creates the candidate ballot array lists
     * @param numbers the ordered votes every candidate got
     * @return
     */
    public static ArrayList<ArrayList<Integer>> createCandidateBallots(ArrayList<Integer> numbers) {
        ArrayList<ArrayList<Integer>> arrayListOfArrayLists = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 4; i++) {
            arrayListOfArrayLists.add(new ArrayList<Integer>());
        }
        int stepSize = 3;
        int[] currentIndexes = new int[4];
        Arrays.fill(currentIndexes, -1);
        for (int i = 0; i < numbers.size(); i++) {
            int index = i % 4; // Get the index of the ArrayList to add to
            ArrayList<Integer> currentArrayList = arrayListOfArrayLists.get(index);
            if (currentIndexes[index] < 0) { // If it's the first element for this ArrayList
                currentArrayList.add(numbers.get(i)); // Add the current number to the ArrayList
                currentIndexes[index] = i; // Save the current index for this ArrayList
            } else if (currentArrayList.size() < 4) { // If the ArrayList is not yet complete
                int nextIndex = currentIndexes[index] + stepSize; // Get the next index to add to
                if (nextIndex >= numbers.size()) { // If the next index is out of bounds, we're done
                    break;
                }
                currentArrayList.add(numbers.get(nextIndex)); // Add the current number to the ArrayList
                currentIndexes[index] = nextIndex; // Save the current index for this ArrayList
            }
        }
        return arrayListOfArrayLists;
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
     * Method to decide the winner of the election
     * @param candidates
     * @return
     */
    public Candidate decideWinner(ArrayList<Candidate> candidates) {
        ArrayList<Integer> firstIndices = new ArrayList<Integer>();
        for (Candidate c : candidates) {
            ArrayList<Integer> list = c.getRanks();
            int first = list.get(0);
            firstIndices.add(first);
        }

        while (!candidates.isEmpty()) {
            int max = Collections.max(firstIndices);
            int index = firstIndices.indexOf(max);
            Candidate candidate = candidates.get(index);
            ArrayList<Integer> list = candidate.getRanks();
            boolean isMax = true;
            for (int i = 0; i < firstIndices.size(); i++) {
                if (i == index) {
                    continue;
                }
                int combined = list.get(0) + candidates.get(i).getRanks().get(0);
                if (combined > max) {
                    isMax = false;
                    break;
                }
            }
            if (isMax) {
                return candidate;
            } else {
                candidates.remove(index);
                firstIndices.remove(index);
            }
            if (candidates.isEmpty()) {
                break;
            }
            int min = Collections.min(firstIndices);
            int minIndex = firstIndices.indexOf(min);
            Candidate minCandidate = candidates.get(minIndex);
            candidates.remove(minIndex);
            firstIndices.remove(minIndex);
            int secondMax = Collections.max(firstIndices);
            int secondIndex = firstIndices.indexOf(secondMax);
            Candidate secondCandidate = candidates.get(secondIndex);
            int combined = minCandidate.getRanks().get(0) + secondCandidate.getRanks().get(0);
            candidates.add(minIndex, minCandidate);
            firstIndices.add(minIndex, combined);
        }
        return null;
    }

    /**
     * Method to decide the winner of a tie between Candidates in an CPL Election
     * @param parties
     * @param seatNo
     * @return
     */
    @Override
    public Party winnnerTieDecider(Party[] parties, int seatNo) {
        throw new UnsupportedOperationException("invalid operation for Tie Breaker in IR Election");
    }

    /**
     * Method to decide the winner of a tie between Candidates in an IR Election
     * @param a One of the two Candidates in the tiebreak
     * @param b One of the two Candidates in the tiebreak
     * @return One of the two candidates which won the tiebreaker at random
     */
    @Override
    public Candidate winnnerTieDecider(Candidate a, Candidate b) {
        Candidate[] tieBreaker= new Candidate[1];
        tieBreaker[0] = a;
        tieBreaker[1] = b;
        Candidate c = tieBreaker[(int)Math.random() * tieBreaker.length];
        return c;
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