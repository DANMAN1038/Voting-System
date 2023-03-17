public class Party {
    private Integer votes;
    private String party;
    private Candidate[] candidates;
    private Integer totalVotes;

    public Party(Integer votes, String party, Candidate[] candidates) {
        this.votes = votes;
        this.party = party;
        this.candidates = candidates;
    }

    public String getParty() {
        return this.party;
    }

    public Integer getVotes() {
        return this.votes;
    }

    public void addPartyVotes(int votes) {
        this.votes += votes;
    }

    public void addTotalVotes(int votes) {
        totalVotes += votes;
    }

    public Candidate[] getCandidates() {
        return this.candidates;
    }
}
