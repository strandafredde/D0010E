package State.Events;

import State.ShoppingState;
import State.State;
// import State.Events.Event;
// import State.Events.EventQueue;

/**
 * Detta är closing eventet som stänger butiken och hindrar kunder från att komma in.
 * @author Fredrik Larsson
 */
public class ClosingEvent extends Event {

    /**
     * Detta är konstruktorn för stängningshändelsen.
     * @param state Det specifika shoppingtillståndet.
     * @param queue Den specifika shoppingkön.
     * @param exTime Exekveringstiden.
     */
    public ClosingEvent(State state, EventQueue queue, double exTime){
        super(state, queue, exTime);
    }

    /**
     * Effekten av detta event är: händelsen nekar
     * ytterligare inträde i butiken men nuvarande kunder får fortsätta.
     * @param state Tillståndet som denna effekt ska modifiera.
     */
    public void effect(State state){
        ShoppingState State = ((ShoppingState) this.state);

        /* Beräkna tid sedan senaste eventet exekverades */
        double timeDiff = this.getExTime() - State.getEventTimeDelta();

        /* Lägg till total tid till inaktiva kassor och kötid */
        State.checkQueueTime(timeDiff);
        State.checkInactiveRegisterTime(timeDiff);
        State.setEventTimeDelta(this.getExTime());

        State.callObservers(this);
        State.denyEntry();
    }

    /**
     * @return Namnet på denna typ av event.
     */
    @Override
    public String EventName() { return "Stängning"; }


}