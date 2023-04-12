package objects;
import java.util.ArrayList;
/** Candidate class.
 * @author azamx016
 */

/** Creates a candidate with specified information.
 *
 */
public class Candidate {
    private Integer rank;
    private String name;
    private String party;
    private Ballot ballot;
    private ArrayList<Integer> ranks;
    private ArrayList<Ballot> votes;

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
}