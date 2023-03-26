package objects;
import java.util.ArrayList;
/** Ballot class.
 * @author azamx016
*/
public class Ballot {
    private ArrayList<String> preference;

    /** Stors ballot information
        * @param preference ballot preference.
    */
    public Ballot(ArrayList<String> preference) {
        this.preference = preference;
    }

    /** updates the preference based on candidate array list
        * @param candidate array list of type candidate.
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

    /** gets the preference 
        * @return a string array list for the preference.
    */
    public ArrayList<String> getPreference() {
        return this.preference;
    }
}