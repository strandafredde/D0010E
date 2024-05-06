package State.Events;
import State.ShoppingState;
import State.State;

/**
 * Denna klass används för att skapa startevetet
 * vilket skapar händelseförlopp genom första
 * ankomsten till butiken.
 * @author Fredrik Larsson
 */
public class StartEvent extends Event {

    /**
     * Detta är konstruktorn för ankomsteventet.
     * @param state Det specifika shoppingtillståndet.
     * @param queue Den specifika shoppingkön.
     * @param exTime Exekveringstiden.
     */
    public StartEvent(State state, EventQueue queue, double exTime){
        super(state, queue, exTime);
    }

    /**
     * Effekten av denna event är: eventet skapar
     * en ny ankomst för en ny kund.
     * @param state Tillståndet som denna effekt ska modifiera.
     */
    public void effect(State state){
        ShoppingState State = ((ShoppingState) this.state);
        double addTime = State.AddedArrivalTime();
        State.callObservers(this);

        /* Skapar en ny ankomst för en ny kund. */
        Event e = new ArrivalEvent(State, queue, addTime + this.getExTime(), State.getCustomer());
        queue.add(e);
    }

    /**
     * @return Namnet på denna typ av event.
     */
    @Override
    public String EventName() { return "Start"; }
}