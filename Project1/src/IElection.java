import java.util.Date;

public abstract class IElection<T>{
    private Date date;


    public abstract Party winnnerTieDecider(Party[] parties, int seatNo);
    public abstract Candidate winnnerTieDecider(Candidate a, Candidate b);

    public abstract void displayWinner();
}