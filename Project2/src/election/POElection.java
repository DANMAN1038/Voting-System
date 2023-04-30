package election;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import objects.*;

/**The IRElection class to be used to produce where the Instant Runoff Election is executed
 * @author coll1396
 * @author moha1113
 *
 */
public class POElection extends IElection {
    private Candidate winner;
    private ArrayList<Candidate> candidates;
    private Date date;
    private double allVotes;

    /**
     * Constructor for IRElection class
     * @param winner The winner of the election
     * @param candidates All candidates participating in the election
     */
    public POElection(Candidate winner, ArrayList<Candidate> candidates, Date date) {
        this.winner = winner;
        this.candidates = candidates;
        this.date = date;
    }

    /**
     * Method to actually run the Instant RunOff Election and return the winner of the election
     * @return The Candidate that won the Election according to the Instant Runoff rules
     */
    public ArrayList<Candidate> electionPO(ArrayList<Ballot> ballots) {
        //creates an arraylist where its to add all ballots for later parsing
        ArrayList<Candidate> allCandidates = this.candidates;

        for (int i = 0; i < allCandidates.size(); i++) {
            int votes = countOnesAtIndex(ballots, i);
            allCandidates.get(i).setVotesPO(votes);
        }
        this.candidates = allCandidates;
        return allCandidates;
    }

    /**
     * Array list to set the votes for all the candidate objects
     * @param candidates all the candidates
     * @param votes the votes being set
     */
    public static void setVotes(ArrayList<Candidate> candidates, ArrayList<ArrayList<Integer>> votes) {
        if (candidates.size() != votes.size()) {
            System.out.println(candidates.size());
            System.out.println(votes.size());
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
     * Method for calculating the winner in a PO ELection
     * @param candidates
     * @return
     */
    public Candidate getWinner(ArrayList<Candidate> candidates) {
        Candidate winner = null;
        int maxVotes = Integer.MIN_VALUE;

        for (Candidate candidate : candidates) {
            if (candidate.getVotesPO() > maxVotes) {
                maxVotes = candidate.getVotesPO();
                winner = candidate;
            }
        }

        // Check for ties
        ArrayList<Candidate> tiedCandidates = new ArrayList<Candidate>();
        for (Candidate candidate : candidates) {
            if (candidate.getVotesPercentage() == maxVotes) {
                tiedCandidates.add(candidate);
            }
        }
        if (tiedCandidates.size() <= 1) {
            // Only one candidate has the maximum number of votes, return the winner
            System.out.println("is the winner");
            this.winner = winner;
            return winner;
        } else {
            // There is a tie between candidates, use tiebreaker method

            winner = tiebreaker(tiedCandidates);
            this.winner = winner;
            return winner;
        }
    }

    /**
     * Method to decide tiebreaker between two or more candidates by returning a random candidate
     * @param candidates
     * @return
     */
    public static Candidate tiebreaker(ArrayList<Candidate> candidates) {
        // Shuffle the list of tied candidates
        Collections.shuffle(candidates, new Random());

        // Return the first candidate in the shuffled list
        return candidates.get(0);
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
     * Method to return the Date of the Election in String format
     * @return The String formatted date of the Election
     */
    public String getDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String elecDate = df.format(this.date);
        return elecDate;
    }

    public static int countOnesAtIndex(ArrayList<Ballot> ballots, int index) {
        int count = 0;
        for (Ballot ballot : ballots) {
            ArrayList<Integer> vote = ballot.getVotes();
            if (vote.size() > index && vote.get(index) == 1) {
                count++;
            }
        }
        return count;
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

    /**
     *
     * @param candidates
     */
    public void showResults(ArrayList<Candidate> candidates) {
        // Calculate total number of votes
        double totalVotes = 0;
        for (Candidate candidate : candidates) {
            totalVotes += candidate.getVotesPO();

        }

        // Calculate vote percentages and store in candidates
        for (Candidate candidate : candidates) {
            double percentage = candidate.getVotesPO() / totalVotes * 100;
            candidate.setVotesPercentage(percentage); // Set the percentage of votes in the candidate object
        }

        // Sort candidates in descending order by vote percentage
        Collections.sort(candidates, new Comparator<Candidate>() {
            public int compare(Candidate candidate1, Candidate candidate2) {
                return Double.compare(candidate2.getVotesPercentage(), candidate1.getVotesPercentage());
            }
        });

        // Create panel to hold candidate information
        JPanel panel = new JPanel(new GridLayout(candidates.size() + 1, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add headers to panel
        panel.add(new JLabel("Rank", SwingConstants.CENTER));
        panel.add(new JLabel("Candidate", SwingConstants.CENTER));
        panel.add(new JLabel("Votes (%)", SwingConstants.CENTER));

        // Add candidate information to panel
        int rank = 1;
        for (Candidate candidate : candidates) {
            String candidateName = candidate.getName();
            int votes = candidate.getVotesPO();
            double percentage = candidate.getVotesPercentage();
            DecimalFormat df = new DecimalFormat("#.##");
            String percentageStr = df.format(percentage) + "%";
            panel.add(new JLabel(String.valueOf(rank), SwingConstants.CENTER));
            panel.add(new JLabel(candidateName, SwingConstants.CENTER));
            panel.add(new JLabel(votes + " (" + percentageStr + ")", SwingConstants.CENTER));
            rank++;
        }

        // Determine the winner
        Candidate winner = candidates.get(0);
        String winnerName = winner.getName();
        int winnerVotes = winner.getVotesPO();
        double winnerPercentage = winner.getVotesPercentage();
        DecimalFormat df = new DecimalFormat("#.##");
        String winnerPercentageStr = df.format(winnerPercentage) + "%";

        // Create label to display winner information
        JLabel winnerLabel = new JLabel("Winner: " + winnerName + " with " + winnerVotes + " votes (" + winnerPercentageStr + ")", SwingConstants.CENTER);
        winnerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create panel to hold winner label
        JPanel winnerPanel = new JPanel(new BorderLayout());
        winnerPanel.add(winnerLabel, BorderLayout.CENTER);

        // Create main frame and add panels to it
        JFrame frame = new JFrame("Election Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.add(panel, BorderLayout.CENTER);
        frame.add(winnerPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }


}