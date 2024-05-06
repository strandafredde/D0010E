package State.Events;
import State.State;


/**
 * Denna klass används för att skapa event.
 * @autor Fredrik Larsson
 */
public abstract class Event {

    /**
     * tillståndet för simuleringen.
     */
    public State state;

    /**
     * kön för simuleringen
     */
    public EventQueue queue;

    /**
     * tiden det tar att utföra eventet.
     */
    public double exTime;

    /**
     * konstruktorn för eventet.
     * @param state Det specifika shoppingtillståndet.
     * @param queue Den specifika shoppingkön.
     * @param exTime Exekveringstiden.
     */
    public Event(State state, EventQueue queue, double exTime) {
        this.state = state;
        this.exTime = exTime;
        this.queue = queue;
    }

    /**
     * @return Tiden det tar att utföra eventet.
     */
    public double getExTime() { return exTime; }

    /**
     * Används för att utföra effekten av ett event
     * @param state tillsåtdnet av den nuvarande simuleringen.
     */
    public abstract void effect(State state);

    /**
     * @return Namnet på denna typ av event.
     */
    public String EventName() { return null; }

}


