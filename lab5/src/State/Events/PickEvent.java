package State.Events;

import State.ShoppingState;
import State.State;
import State.Customer;

/**
 * Denna klass används för att skapa plockeventet
 * vilket skapar en händelse där en kund plockar varor.
 * @author Fredrik Larsson
 
 */
public class PickEvent extends Event {
    private Customer customer;
    /**
     * Detta är konstruktorn för plockeventet.
     * @param state Det specifika shoppingtillståndet.
     * @param queue Den specifika shoppingkön.
     * @param exTime Exekveringstiden.
     * @param customer eventets kund.
     */
    public PickEvent(State state, EventQueue queue, double exTime, Customer customer) {
        super(state, queue, exTime);
        this.customer = customer;
    }

    /**
     * Effekten av denna event är: eventet kontrollerar
     * om det finns några lediga kassor, om det finns så låt kunden
     * fortsätta med betalning. Annars,
     * skicka kunden till kassakön.
     * @param state Tillståndet som eventet ska modifiera.
     */
    public void effect(State state) {
        ShoppingState State = ((ShoppingState) this.state);
        double addTime = State.AddedPayTime();

        /* Beräkna tid sedan senaste eventet exekverades */
        double timeDiff = this.getExTime() - State.getEventTimeDelta();

        /* Lägg till total tid till inaktiva kassor och kötid */
        State.checkQueueTime(timeDiff);
        State.checkInactiveRegisterTime(timeDiff);
        State.setEventTimeDelta(this.getExTime());

        State.callObservers(this);

        if(State.availableRegisters()){
            /* Låt kunden slutföra transaktionsprocessen */
            Event e = new PayEvent(State, queue,this.getExTime() + addTime, customer);
            State.addOccupiedRegister();
            queue.add(e);
            return;
        }
        /* Låt kunden vänta i kön för tillgängliga kassor */
        Event e = new PayEvent(State, queue, addTime, customer);
        State.addRegisterQueue(e);

    }

    /**
     * @return Id för kunden som har tilldelats denna event.
     */
    public int getCustomerId(){ return customer.getId(); }

    /**
     * @return Namnet på denna typ av event.
     */
    @Override
    public String EventName() { return "Plock"; }
}