package State.Events;

import State.ShoppingState;
import State.State;
import State.Customer;


/**
 * Denna klass hanterar betalningseventet.
 * @author Fredrik Larsson
 */
public class PayEvent extends Event {

    Customer customer;
    /**
     * Detta är konstruktorn för betalningshändelsen.
     * @param state Det specifika shoppingtillståndet.
     * @param queue Den specifika shoppingkön.
     * @param exTime Exekveringstiden.
     * @param customer Händelsens kund.
     */
    public PayEvent(State state, EventQueue queue, double exTime, Customer customer) {
        super(state, queue, exTime);
        this.customer = customer;
    }

    /**
     * Effekten av denna händelse är: evetet
     * kontrollerar om kassakö har någon kund, om
     * den har det så skapas ett PayEvent för den kunden. Effekten uppdaterar också
     * de nödvändiga variablerna
     * @param state Tillståndet som denna effekt ska
     * modifiera.
     */
    public void effect(State state){
        ShoppingState State = ((ShoppingState) this.state);

        /* Beräkna tiden sen senaste eventexekverades och få kötid */
        double timeDiff = this.getExTime() - State.getEventTimeDelta();

        /* Lägg till tiden till inaktiva kassor och kötid */
        State.checkQueueTime(timeDiff);
        State.checkInactiveRegisterTime(timeDiff);
        State.setEventTimeDelta(this.getExTime());

        State.callObservers(this);

        /* Uppdatera relevanta variabler */
        State.removeOccupiedRegister();
        State.removeOccupant();
        State.addFinishedCustomers();

        if(!State.registerIsEmpty()){
            /* Ta fram en annan kund till kassan från kön */
            PayEvent e = (PayEvent) State.popRegisterQueue();
            e = new PayEvent(e.state, e.queue, this.getExTime() + e.getExTime(), e.getEventCustomer());
            queue.add(e);
            State.addOccupiedRegister();
        }

        State.setLastPayTime(this.getExTime());
    }

    /**
     * @return Id för Kunden som har detta event
     */
    public int getCustomerId(){ return customer.getId(); }

    /**
     * @return Kunden som har detta event
     */
    public Customer getEventCustomer(){ return this.customer; }

    /**
     * @return Namnet på denna typ av händelse.
     */
    @Override
    public String EventName() { return "Betalning"; }

}