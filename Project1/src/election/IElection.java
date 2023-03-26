package election;
import java.util.Date;
import objects.*;

/**
 * The Main Abstract Election Class, From which the IR & CPL Election inherit methods from
 */
public abstract class IElection{
    private Date date;


    /**
     * Method to decide the winner of a tie in a CPL Election
     * @param parties
     * @param seatNo
     * @return
     */
    public abstract Party winnerTieDecider(Party[] parties, int seatNo);

    /**
     * Method to decide the winner of a tie in an IR Election
     * @param a
     * @param b
     * @return
     */
    public abstract Candidate winnerTieDecider(Candidate a, Candidate b);

    /**
     * Method to Display the winner of the election
     */
    public abstract void displayWinner();
}