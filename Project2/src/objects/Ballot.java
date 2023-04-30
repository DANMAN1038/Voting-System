package objects;
import java.util.ArrayList;
/** The Ballot class to create the Ballot object used to store votes and rankings for candidates
 * @author azamx016
 * @author coll1396
 */
public class Ballot {
    private ArrayList<String> preference;
    private ArrayList<Integer> votes;
    private boolean validity;

    /**
     * Returns the array with all the votes
     * @return
     */
    public ArrayList<Integer> getVotes() {
        return votes;
    }

    /**
     * Sets the votes of the Ballot
     * @param votes The votes it is to be set to
     */
    public void setVotes(ArrayList<Integer> votes) {
        this.votes = votes;
    }

    /**
     * Gets the boolean validity variable
     * @return
     */
    public boolean isValidity() {
        return validity;
    }

    /**
     * Sets the boolean validity variable
     * @param validity
     */
    public void setValidity(boolean validity) {
        this.validity = validity;
    }

    /**
     * constructor for the ballot object
     * @param votes represents all votes cast in the election
     * @param validity
     */
    public Ballot(ArrayList<Integer> votes, boolean validity) {

        this.votes = votes;
        this.validity = validity;
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
     * Getter to get the preference arraylist of the Ballot object
     * @return
     */
    public ArrayList<String> getPreference() {

        return this.preference;
    }
}