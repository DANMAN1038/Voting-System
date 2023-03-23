import java.util.ArrayList;
public class Ballot {
    // private Integer amount;
    private ArrayList<String> preference;

    public Ballot(ArrayList<String> preference) {
        //this.amount = amount;
        this.preference = preference;
    }

    // public Integer getAmount() {
    //     return this.amount;
    // }

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

    public ArrayList<String> getPreference() {
        return this.preference;
    }
}