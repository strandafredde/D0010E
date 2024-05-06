package State;
import State.Events.Event;
import java.util.Observable;


/**
 * Detta är den generella klassen för en state
 * Som blir ärvd av de  specfika staterna.
 */
public abstract class State extends Observable {
    private boolean brake = false;
    double currentTime = 0;

    /**
     * Används för att få simuleringens
     * tillstånd.
     * @return om simuleringens tillstånd
     * är stopp eller start.
     */
    public boolean getBrake(){ return brake; }

    /**
     * Används för att byta simuleringens tillstånd
     * till ett stopp.
     */
    public void stopSim(){ brake = true; }

    /**
     * Används för att byta simuleringens tillstånd
     * till en start.
     */
    public void startSim(){ brake = false; }

    /**
     * Används för att få den fasta nuvarande tiden
     * för simuleringen.
     * @return den nuvarande tiden för
     * simuleringen.
     */
    public double getCurrentTime(){ return currentTime; }

    /**
     * Används för att göra ett anrop till observatören för denna klass
     * med ett specificerat event.
     * @param Event att användas av observatören.
     */
    public void callObservers(Event Event){
        setChanged();
        notifyObservers(Event);
    }

}