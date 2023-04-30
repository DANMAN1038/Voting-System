package objects;
import java.util.ArrayList;
/** Creates a candidate with specified information.
 * @author azamx016
 * @author coll1396
 */
public class Candidate {
    private Integer rank;
    private String name;
    private String party;
    private Ballot ballot;
    private ArrayList<Integer> ranks;
    private ArrayList<Ballot> votes;
    private Integer totalVotes;
    private Integer votesPO;

    /**
     * Method to get the votes for PO Election
     * @return
     */
    public Integer getVotesPO() {
        return votesPO;
    }

    /**
     * Method to set the votes for PO Election
     * @param votesPO
     */
    public void setVotesPO(Integer votesPO) {
        this.votesPO = votesPO;
    }

    /**
     * Method to get the percentage of votes a Candidate has
     * @return
     */
    public double getVotesPercentage() {
        return votesPercentage;
    }

    /**
     * Method to set the percentage of votes a candidate has
     * @param votesPercentage
     */
    public void setVotesPercentage(double votesPercentage) {
        this.votesPercentage = votesPercentage;
    }

    private double votesPercentage;

    /**
     * Method to return Ballot of Candidate
     * @return
     */
    public Ballot getBallot() {
        return ballot;
    }

    /**
     * Method to set Ballot of Candidate
     * @param ballot
     */
    public void setBallot(Ballot ballot) {
        this.ballot = ballot;
    }

    /**
     * Constructor for candidate with identifiable rank
     * @param rank
     * @param name
     * @param party
     * @param votes
     */
    public Candidate(Integer rank, String name, String party, ArrayList<Ballot> votes) {
        this.rank = rank;
        this.name = name;
        this.party = party;
        this.votes = votes;
    }

    /**
     * Constructor for candidate with unidentifiable rank
     * @param name
     * @param party
     * @param ballot
     */
    public Candidate(String name, String party, Ballot ballot) {
        this.name = name;
        this.party = party;
        this.ballot = ballot;
    }

    /**
     * Simple constructor for candidate for testing purposes
     * @param name
     */
    public Candidate(String name) {
        this.name = name;
    }

    /**
     * Sets the Rank Array list object
     * @return
     */
    public ArrayList<Integer> getRanks() {
        return ranks;
    }

    /**
     * Gets the ranks array list object
     * @param ranks
     */
    public void setRanks(ArrayList<Integer> ranks) {
        this.ranks = ranks;
    }

    /**
     * Constructor for candidate with unidentifiable rank
     * @param name
     * @param party
     * @param ranks
     */
    public Candidate(String name, String party, ArrayList<Integer> ranks) {
        this.name = name;
        this.party = party;
        this.ranks = ranks;
    }


    /** gets the party of the candidate
     * @return a string for candidates party.
     */
    public String getParty() {
        return this.party;
    }

    /** gets the name of the candidate
     * @return a string for candidates name.
     */
    public String getName() {
        return this.name;
    }

    /** gets the votes of the candidate
     * @return a integer for candidates total votes.
     */
    public Integer getVotes() {
        int voteTotal = 0;
        for (int x = 0; x < votes.size(); x++) {
            if (votes.get(x).getPreference().get(0) == (this.name)) {
                voteTotal++;
            }
        }
        return voteTotal;
    }

    /** gets the rank of the candidate
     * @return a integer for candidates rank.
     */
    public Integer getRank() {

        return this.rank;
    }

    /** Returns the total of all the votes the candidate has
     *
     * @return
     */
    public Integer getTotalVotes() {
        int total = 0;

        for (int vote : ranks) {
            total += vote;
        }

        this.totalVotes = total;
        return total;
    }

    /** Sets the total of all the votes the candidate has
     *
     * @param totalVotes the votes to be set as the total number of votes
     */
    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    /** Calculates the total votes given what round of voting we are on
     *
     * @param round refers to what round of voting or what prefrences of votes are we counting for the candidates
     * @return
     */
    public int calcTotalVotes(int round) {
        int index = round - 1;
        int sum = 0;
        for (int i = 0; i <= index && i < ranks.size(); i++) {
            sum += ranks.get(i);
        }
        this.totalVotes = sum;
        return sum;
    }
}