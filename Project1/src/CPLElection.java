import java.util.HashMap;
import java.lang.Math;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
public class CPLElection {
    private HashMap<Party, Integer> winner;
    private Party[] parties;
    private Integer seatNo;
    private HashMap<Party, Integer> partyRemainders;

    public CPLElection(Party[] parties) {
        this.parties = parties;
        this.winner = new HashMap<Party, Integer>();
        this.partyRemainders = new HashMap<Party, Integer>();
    }

    public void displayWinner() {
        System.out.println(winner);
    }

    public Party winnerTieDecider(Party[] parties, Integer seatNo) {
        winnerTieDecider(Party[], seatNo);//why are we calling IElection again?
    }

    public void setPartyWinner(Party party) {
        winner.put(party, winner.getOrDefault(party, 0) + 1);
    }

    public void readBallot() {
        try{
            File myObj = new File("filename.txt");//need to find out what the name of the input file is
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            //need to exit system
          }
    }

    public Integer getNumSeats() {
        return seatNo;
    }

    public void setNumSeats(Integer seatNo) {
        this.seatNo = seatNo;
    }

    public Integer getQuota() {
        return totalVotes / seatNo;
    }

    public void firstSeatWaveAllocation(Integer quota, Integer roundNo) {
        // TODO: allocate the first seat
    }

    public Boolean compareRemainders(HashMap<Party, Integer> remainders) {
        // TODO: compare the remainders
    }

    public void assignRemainderSeats(HashMap<Party, Integer> remainders) {
        // TODO: assign the remainders
    }
}
