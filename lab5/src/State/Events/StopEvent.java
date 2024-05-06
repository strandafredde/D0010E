package State.Events;
import State.ShoppingState;
import State.State;


/** 
 * Denn klass används för att skapa stoppeventet
 * vilket stoppar simuleringen.
 * @author Fredrik Larsson
 */
public class StopEvent extends Event {

    /**
     * Detta är konstruktorn för stoppeventet.
     * @param state Det specifika shoppingtillståndet.
     * @param queue Den specifika shoppingkön.
     * @param time Exekveringstiden.
     */
    public StopEvent(State state, EventQueue queue, double time){
        super(state, queue, time);
    }

    /**
     * Effekten av denna event är: eventet stoppar
     * alla processer i denna simulering.
     * @param state Tillståndet som denna effekt ska modifiera.
     */
    public void effect(State state){
        ShoppingState State = ((ShoppingState) this.state);
        State.callObservers(this);
        State.stopSim();
    }

    /**
     * @return Namnet på denna typ av event.
     */
    @Override
    public String EventName() { return "Stopp"; }
}