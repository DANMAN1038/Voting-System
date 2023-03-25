/** Abstract Election class.
 * @author azamx016
*/
public abstract class IElection<T>{

    /** Creates an Election.
        * @param parties array of Party.
        * @param seatNo the amount of seat numbers.
    */
    public abstract Party winnnerTieDecider(Party[] parties, int seatNo);

    /** Decides winner given 2 candidates.
        * @param a first Candidate.
        * @param b second Candidate.
    */
    public abstract Candidate winnnerTieDecider(Candidate a, Candidate b);

    /** gets the winner of the Election
        * @return string for winner of Election.
    */
    public abstract void displayWinner();
}