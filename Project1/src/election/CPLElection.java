package election;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import objects.*;

public class CPLElection {
    private HashMap<String, Integer> winner;
    private HashMap<String, Integer> partySeats;
    private HashMap<String, Integer> partyVotes;
    private Party[] parties;
    private Integer numSeats;
    private Integer totalVotes;

    public CPLElection(Party[] parties) {
        this.parties = parties;
        this.winner = new HashMap<String, Integer>();
        this.partyVotes = new HashMap<String, Integer>();
        this.partySeats = new HashMap<String, Integer>();
        this.totalVotes = 0;
    }

    public void displayWinner() {
        System.out.println(winner);
    }

    public void readBallot(String fileName) {
        try{
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            String checkPartyType = myReader.nextLine();//first line, check voting method
            if(!checkPartyType.equals("CPL")) {
                System.out.println("An error occurred, incorrect voting type");
                System.exit(0);
            }
            int numParties = Integer.parseInt(myReader.nextLine());//second line, hold and error check number of parties
            if(numParties < 0) {
                System.out.println("No parties are provided in the file");
                System.exit(0);
            }
            String[] partyNames = myReader.nextLine().split(",");//third line, hold the names of the parties
            for (String party : partyNames) {
                partyVotes.put(party, 0);//intialize party vote hashmap with the name of the parties (keys) and default 0 votes (values) per party
                partySeats.put(party, 0);
            }
            numSeats = Integer.parseInt(myReader.nextLine());
            if(numSeats < 0) {
                System.out.println("No number of seats are provided in the file");
                System.exit(0);
            }
            int numBallots = Integer.parseInt(myReader.nextLine());
            if(numBallots < 0) {
                System.out.println("No number of ballots are provided in the file");
                System.exit(0);
            }

            for (int i = 0; i < numBallots; i++) {
                String[] vote = myReader.nextLine().split(",");
                for (int j = 0; j < numParties; j++) {
                    String party = partyNames[j];
                    int numVotes = Integer.parseInt(vote[j]);
                    this.totalVotes += numVotes;
                    int currentCount = partyVotes.get(party);
                    partyVotes.put(party, currentCount + numVotes);
                }
            }
            myReader.close();
            firstSeatWaveAllocation();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred, invalid file");
            System.exit(0);
        }
    }

    public Integer getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(Integer seatNo) {
        this.numSeats = numSeats;
    }

    public Integer getQuota() {
        return totalVotes / numSeats;
    }

    public void firstSeatWaveAllocation() {
        Iterator it = partyVotes.entrySet().iterator();
        while(it.hasNext()) {

        }
    }

    public Boolean compareRemainders(HashMap<Party, Integer> remainders) {
        //TODO
        return null;
    }

    public void assignRemainderSeats(HashMap<Party, Integer> remainders) {
        //TODO
    }
}