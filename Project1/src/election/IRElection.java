package election;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import objects.*;
import java.util.Random;

/**The IRElection class to be used to produce where the Instant Runoff Election is executed
 * @author coll1396
 *
 */
public class IRElection extends IElection {
    private Candidate winner;
    private ArrayList<Candidate> candidates;
    private Date date;

    /**
     * Constructor for IRElection class
     * @param winner The winner of the election
     * @param candidates All candidates participating in the election
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
        //creates an arraylist where its to add all ballots for later parsing
        ArrayList<Integer> votes = new ArrayList<>();
        ArrayList<Candidate> allCandidates = this.candidates;
        //these for loops go through each candidate and calculates the amount of votes they got for every rank
        for (int x = 1; x < allCandidates.size() + 1; x++) {
            for (int i = 0; i < allCandidates.size(); i++) {
                int amount = voteCounter(ballots, i, x);
                //adds all the candidate vote's rankings into the arraylist
                votes.add(amount);
            }
        }
        //The next two lines of code creates the arraylist schema for the candidate ballots and set them toward their candidates
        ArrayList<ArrayList<Integer>> rankings = createCandidateBallots(votes);
        setVotes(allCandidates, rankings);
        return allCandidates;
    }

    /**
     * Array list to set the votes for all the candidate objects
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
        int stepSize = 4;
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
     * Method to decide the winner of the election given all candidates with their assigned votes
     * @param candidates all candidates participating in the election
     * @param round the round of voting which the IR election is one, starts at 1
     * @return
     */
    public boolean setWinner(ArrayList<Candidate> candidates, int round) {
        //Declaring this variable to be used to keep track of the popular Vote a candidate has to surpass to win the Election
        int popularVote = 0;
        //The method is recursive and only ends if it sets a winner and thus executes the block in this if statement to return true
        if (winner != null) {
            return true;
            //statement to check if only one candidate remains after running meaning it's the last candidate left and the election winner
        } else if (candidates.size() == 1) {
            setWinner(candidates.get(0));
        } else {
            //for statement to calculate the popular vote in the election candidates have to have over 50% in
            for (Candidate c : candidates) {
                popularVote += c.calcTotalVotes(round);
            }
            //for statement to check if any candidate won the popular vote, to have them set as the winner
            for (Candidate c : candidates) {
                if (c.getTotalVotes() > (popularVote - c.getTotalVotes())) {
                    setWinner(c);
                }
            }
            //Code after for loop that runs if no winner is set and so deletes the candidate with the lowest vote total and recursively goes to the next round of voting
            Candidate deleted = new Candidate(null, null, (ArrayList<Integer>) null);
            for (Candidate c : candidates) {
                if (c.getTotalVotes() == deleted.getTotalVotes() && c.getRanks().get(round-1) < deleted.getRanks().get(round-1)) {
                    deleted = c;
                    popularVote = c.getTotalVotes();
                }
                else if (c.getTotalVotes() < popularVote) {
                    deleted = c;
                    popularVote = c.getTotalVotes();
                }
            }
            // to remove the candidate that had the lowest votes
            candidates.remove(deleted);
            //to increment the vote preferences we will be looking for
            round++;
            //calling the method to run again until a winner is set
            setWinner(candidates, round);
        }
        return true;
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
        Random rand = new Random();
        int randomIndex = rand.nextInt(2); // Generate a random integer between 0 and 1 (inclusive)
        if (randomIndex == 0) {
            return a;
        } else {
            return b;
        }
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