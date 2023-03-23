import java.util.ArrayList;
public class Candidate {
    private Integer rank;
    private String name;
    private String party;
    private ArrayList<Ballot> votes;


    public Candidate(Integer rank, String name, String party, ArrayList<Ballot> votes) {
        this.rank = rank;
        this.name = name;
        this.party = party;
        this.votes = votes;
    }

    public String getParty() {
        return this.party;
    }

    public String getName() {
        return this.name;
    }

    public Integer getVotes() {
        int voteTotal = 0;
        for(int x = 0; x < votes.size(); x++) {
            if(votes.get(x).getPreference().get(0) == (this.name)) {
                voteTotal++;
            }
        }
        return voteTotal;
    }

    public Integer getRank() {
        return this.rank;
    }