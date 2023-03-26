package objects;
/** Party object class.
 * @author syed0053
 * @author azamx016
*/
public class Party {
    private Integer votes;
    private String party;
    private Candidate[] candidates;
    private Integer totalVotes;

    /** Creates a party object with specified information.
        * @param votes party votes.
        * @param candidates an array of candidates.
        * @param party specified party.
    */
    public Party(Integer votes, String party, Candidate[] candidates) {
        this.votes = votes;
        this.party = party;
        this.candidates = candidates;
    }

    /** Creates a party object with candidates.
        * @param votes party votes.
        * @param party specified party.
    */
    public Party(Integer votes, String party) {
        this.votes = votes;
        this.party = party;
    }

    /** gets the party of the candidate
        * @return a string for party.
    */
    public String getParty() {
        return this.party;
    }

    /** gets the party of the candidate
        * @return a integer for party votes.
    */
    public Integer getVotes() {
        return this.votes;
    }  

    /** adds votes to parties votes.
        * @param votes votes to be added.
    */
    public void addPartyVotes(int votes) {
        this.votes += votes;
    }

    /** adds votes to total votes.
        * @param votes votes to be added.
    */
    public void addTotalVotes(int votes) {
        totalVotes += votes;
    }

    /** gets candidates array from the party
        * @return a candidate array with all candidates.
    */
    public Candidate[] getCandidates() {
        return this.candidates;
    }
}
