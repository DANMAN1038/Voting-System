package election;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.*;
import java.util.Map.Entry;
import objects.*;
/** CPLElection class.
 * @author syed0053
 */
public class CPLElection extends IElection {
    private String winner;
    private Map<String, Integer> partySeats;
    private Map<String, Integer> partyVotes;
    private Map<String, Integer> partyRemaindersSorted;
    private List<Party> parties;
    private Integer numSeats;
    private Integer totalVotes;

    /**
     * Constructor for CPLElection class
     * @param parties2 an array of Party objects
     */
    public CPLElection(List<Party> parties2) {
        this.parties = parties2;
        this.winner = "";
        this.partyVotes = new LinkedHashMap<>();
        this.partySeats = new LinkedHashMap<>();
        this.partyRemaindersSorted = new LinkedHashMap<>();
        this.totalVotes = 0;
    }

    /**
     * Constructor overload for CPL audit
     */
    public CPLElection() {
    }


    /**
     * Overide the IElection methods, if they are called, something went wrong
     * @param parties
     * @param seatNo
     * @return
     */
    @Override
    public Party winnnerTieDecider(Party[] parties, int seatNo) {
        return null;
    }

    /**
     * Overide the IElection methods, if they are called, something went wrong
     * @param a
     * @param b
     * @return
     */
    @Override
    public Candidate winnnerTieDecider(Candidate a, Candidate b) {
        return null;
    }

    /**
     * Displays the winner of the election
     */
    public void displayWinner() {
        System.out.println("The winner of this election is: ");
        System.out.println(winner);
    }

    /**
     * Method to return party remainders sorted
     * @param partyRemaindersSorted
     */
    public void setPartyRemaindersSorted(Map<String, Integer> partyRemaindersSorted) {
        this.partyRemaindersSorted = partyRemaindersSorted;
    }

    /**
     * Returns the winner of the election
     * @return winner of the Election as a string
     */
    public String getWinner() {
        return winner;
    }

    /**
     * Sets the winner of the election
     * @param party which holds the winning party
     */
    public void setWinner(String party) {
        this.winner = party;
    }

    /**
     * Returns the number of votes received by each party
     * @return HashMap containing the name of each party and their number of votes
     */
    public Map<String, Integer> getPartyVotes() {
        return partyVotes;
    }

    /**
     * Returns the number of seats won by each party
     * @return HashMap containing the name of each party and their number of seats
     */
    public Map<String, Integer> getPartySeats() {
        return partySeats;
    }

    /**
     * Returns the remainders of votes for each party sorted in descending order
     * @return HashMap containing the name of each party and their remainder of votes
     */
    public Map<String, Integer> getPartyRemainders() {
        return partyRemaindersSorted;
    }

    /**
     * Returns the total number of votes cast in the election
     * @return total number of votes as a Integer
     */
    public Integer getTotalVotes() {
        return totalVotes;
    }

    /**
     * Reads the data from a file and initializes the election data
     * @param fileLocation location of the input file
     */
    public void readFile(File fileLocation) {
        Scanner myReader;
        try {
            myReader = new Scanner(fileLocation);
            String checkPartyType = myReader.nextLine();//first line, double check voting method
            if(!checkPartyType.contains("CPL")) {
                System.out.println(checkPartyType);
                System.out.println("An error occurred, incorrect voting type");
                System.exit(0);
            }
            String line = myReader.nextLine().replaceAll(",", "");
            int numParties = Integer.parseInt(line);//second line, hold and error check number of parties
            if(numParties <= 0) {
                System.out.println("No parties are provided in the file");
                System.exit(0);
            }
            String[] partyNames = myReader.nextLine().split(",");//third line, hold the names of the parties
            for (String party : partyNames) {
                partyVotes.put(party, 0);//initialize party vote map with the name of the parties (keys) and default 0
                partySeats.put(party, 0);
            }
            String seatLine = myReader.nextLine().replaceAll(",", "");
            numSeats = Integer.parseInt(seatLine);
            if(numSeats <= 0) {
                System.out.println("No number of seats are provided in the file");
                System.exit(0);
            }
            String ballotLine = myReader.nextLine().replaceAll(",", "");
            int numBallots = Integer.parseInt(ballotLine);
            if(numBallots <= 0) {
                System.out.println("No number of ballots are provided in the file");
                System.exit(0);
            }

            for (int i = 0; i < numBallots; i++) {
                String[] vote = myReader.nextLine().split(",");
                for (int j = 0; j < partyNames.length; j++) {
                    if (vote[j].equals("1")) {
                        String party = partyNames[j];
                        int currentCount = partyVotes.get(party);
                        partyVotes.put(party, currentCount + 1);
                        totalVotes += 1;
                        break;
                    }
                }
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found. Please check if the file path is correct");
            System.exit(0);
        }
    }

    /**
     * Returns the total number of seats in the election
     * @return total number of seats as a Integer
     */
    public Integer getNumSeats() {
        return numSeats;
    }

    /**
     * Sets the total number of votes seats in the election
     * @param seatNo is the number of seats as a Integer
     */
    public void setNumSeats(Integer seatNo) {
        this.numSeats = numSeats;
    }

    /**
     * Returns the quota used for seat allocation
     * @return quota calculated by the total number of Votes divided by the number of seats
     */
    public Integer getQuota() {
        return totalVotes / numSeats;
    }

    /**
     *  Allocates the first wave of seats based off a parties initial vote total and the quota
     *  This method also holds the remainder of votes for each party after the first wave of allocation
     */
    public void firstSeatWaveAllocation() {
        HashMap<String, Integer> partyRemaindersSorted = new LinkedHashMap<>();
        HashMap<String, Integer> partyRemaindersUnsorted = new HashMap<String, Integer>();
        Set<String> keys = partyVotes.keySet();
        for (String key : keys) {
            int value = partyVotes.get(key);
            int seats = value / getQuota();
            partySeats.put(key, seats);
            setNumSeats(numSeats-seats);
            int remainder = value % getQuota();
            partyRemaindersUnsorted.put(key, remainder); //hold modulus votes
        }
        List<Entry<String, Integer>> list = new ArrayList<>(partyRemaindersUnsorted.entrySet());//convert hashmap to list
        Collections.sort(list, (a, b) -> b.getValue().compareTo(a.getValue()));//sort the list from greatest to least by number of votes
        for (Entry<String, Integer> entry : list) {//transfer sorted list to hashmap
            partyRemaindersSorted.put(entry.getKey(), entry.getValue());
        }
        if(numSeats > 0){
            compareRemainders(partyRemaindersSorted);
        }
        setPartyRemaindersSorted(partyRemaindersSorted);
    }

    /**
     * randomly selects either the first party or second
     * @return a random number between 1 and 2
     */
    public Integer winnerTieDecider(){
        int randomNumber = (int) ((Math.random() * 2) + 1);
        return randomNumber;
    }

    /**
     * Compares remainders between parties and checks to see if two parties are equal in remainder
     * @param remainders of remaining votes per party
     */
    public void compareRemainders(HashMap<String, Integer> remainders) {
        Set<String> keys = remainders.keySet();
        boolean equalFlag = false;
        int index = 0;
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key1 = it.next();
            int value1 = remainders.get(key1);
            if (it.hasNext()) { //check if there is another key
                String key2 = it.next();
                int value2 = remainders.get(key2);
                if (value1 == value2) {
                    equalFlag = true;
                }
            }
            index+=1;
        }
        assignRemainderSeats(index, equalFlag);
    }

    /**
     * Assign the rest of the seats, check if there was a tie and break it, otherwise
     * @param partyIndex refers to the index of the party that needs to be checked if there was an equal remainder
     * @param check check indicates there was an equality of remainders
     */
    public void assignRemainderSeats(int partyIndex, boolean check) {
        Set<String> keys = partyRemaindersSorted.keySet();
        Set<String> keys2 = partySeats.keySet();
        int index = 0;
        for(String key: keys){
            if(numSeats > 0) {
                if(index==partyIndex && check == true && numSeats == 1){//tie breaker is only needed if there is 1 seat remaining
                    if(winnerTieDecider() == 1) {
                        for(String key2 : keys2) {
                            if(key.equals(key2)){
                                partySeats.put(key2, (partySeats.get(key2)+1));
                            }
                        }
                    }
                    else{
                        for(String key2 : keys2) {
                            if(key.equals(key2)){
                                partySeats.put(key2, (partySeats.get(key2)+1));//this implementation isn't quite right yet, need to figure out how to skip current key
                            }
                        }
                    }
                }
                else{
                    for(String key2 : keys2) {
                        if(key.equals(key2)){
                            partySeats.put(key2, (partySeats.get(key2)+1));
                        }
                    }
                }
                numSeats-=1;
                index+=1;
            }
            else{
                break;
            }
        }
    }

    /**
     * setters for testing
     */
    public void setTotalVotes(int i) {
        totalVotes = i;
    }

    public void setPartyVotes(String party, int votes) {
        partyVotes.put(party, votes);
    }

    public void setPartyVotes(Map<String, Integer> partyVotes) {
        this.partyVotes = partyVotes;
    }

    public void setPartySeats(Map<String, Integer> partySeats) {
        this.partySeats = partySeats;
    }

    public void setPartySeats(String party, int seats) {
        partySeats.put(party, seats);
    }


}