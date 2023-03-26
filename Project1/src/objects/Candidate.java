package objects;
import java.util.ArrayList;
/** Candidate class.
 * @author azamx016
*/
public class Candidate {
    private Integer rank;
    private String name;
    private String party;
    private ArrayList<Ballot> votes;

    /** Creates a candidate with specified information.
        * @param rank candidate rank.
        * @param name candidate party.
        * @param party candidate party.
        * @param votes array list of type Ballot.
    */
    public Candidate(Integer rank, String name, String party, ArrayList<Ballot> votes) {
        this.rank = rank;
        this.name = name;
        this.party = party;
        this.votes = votes;
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