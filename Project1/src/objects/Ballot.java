package objects;
import java.util.ArrayList;
/** Ballot class.
 * @author azamx016
 * @author coll1396
 */
public class Ballot {
    // private Integer amount;
    private ArrayList<String> preference;
    private ArrayList<Integer> votes;

    /**
     * Returns the amount of votes
     * @return
     */
    public ArrayList<Integer> getVotes() {
        return votes;
    }

    /**
     * Sets the votes of the Ballot
     * @param votes
     */
    public void setVotes(ArrayList<Integer> votes) {
        this.votes = votes;
    }

    /**
     * constructor for the ballot object
     * @param votes
     */
    public Ballot(ArrayList<Integer> votes) {
        this.votes = votes;
    }

    /**
     * Constructor for Ballot for election where named Candidate prefrence is given
     * @param preference ordered candidate prefrence
     * @param size size of array
     */
    public Ballot(ArrayList<String> preference, int size) {
        this.preference = preference;
    }
    /**
     * method to update the preference when candidate has been eliminated, transferring votes
     * to the next preferred candidate as well
     * @param candidates
     */
    public void updatePref(ArrayList<Candidate> candidates) {
        ArrayList<String> newPref = new ArrayList<String>();
        for(int x = 0; x < preference.size(); x++) {
            for(int y = 0; y < candidates.size(); y++) {
                if (preference.get(x).contains(candidates.get(y).getName())) {
                    newPref.add(candidates.get(y).getName());
                }
            }
        }
        this.preference = newPref;
    }

    /**
     * Method to add votes to the Ballot depending on what round of voting is being done
     * @param round
     * @param amount
     */
    public void addVotes(int round, int amount) {
        int index = round - 1;
        int newAmount = this.votes.get(index) + amount;
        this.votes.set(index, newAmount);
    }
    public ArrayList<String> getPreference() {
        return this.preference;
    }
}