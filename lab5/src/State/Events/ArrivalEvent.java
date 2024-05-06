package State.Events;

import State.ShoppingState;
import State.State;
import State.Customer;

/**
 * Detta är arrival event som skapas när en kund anländer till butiken.
 * @author Fredrik Larsson
 */
public class ArrivalEvent extends Event {
    private Customer customer;

    /**
     * Detta är konstruktorn för ankomsthändelsen.
     * @param state det specifika shoppingtillståndet.
     * @param queue den specifika shoppingkön.
     * @param exTime exekveringstiden.
     * @param customer händelsens kund.
     */
    ArrivalEvent(State state, EventQueue queue, double exTime, Customer customer) {
        super(state, queue, exTime);
        this.customer = customer;
    }

    /**
    * Kontrollerar om butiken är tom eller har nått sin maximala kapacitet. Om det är fallet, 
    * kommer den inkommande kunden att nekas, och en ny kundankomsthändelse kommer att skapas. 
    * Om butiken är öppen kommer en PickEvent också att skapas för den aktuella kunden.
    * @param state Tillståndet som denna händelses effekt kommer att ändra.
     */
    public void effect(State state) {
        ShoppingState State = ((ShoppingState) this.state);

        /* Beräkna tid sen senaste händelsen executades och få kötid */
        double timeDiff = this.getExTime() - State.getEventTimeDelta();

        /* Lägg till total tid till inaktiva register och kötid */
        if(State.getStoreEntry()) {
            State.checkQueueTime(timeDiff);
            State.checkInactiveRegisterTime(timeDiff);
            State.setEventTimeDelta(this.getExTime());
        }

        State.callObservers(this);

        if(!State.getStoreEntry()){
            /* Butiken är stängd och nekar inträde. */
            return;
        }
        double addTimeArrival = State.AddedArrivalTime();
        Event a = new ArrivalEvent(state, queue, this.getExTime() + addTimeArrival, State.getCustomer());
        queue.add(a);

        if(State.atCapacity()) {
            /* Butiken är full och kan inte tillåta ytterligare inträde. */
            State.addLostCustomers();
            return;
        }
        /* Butiken är öppen och inte ännu vid full kapacitet och tillåter därför inträde.*/
        State.addOccupant();
        double addTimePick = State.AddedPickTime();
        Event p = new PickEvent(state, queue, this.getExTime() + addTimePick, customer);
        queue.add(p);

    }

    /**
     * @return Id för kunden som har tilldelats denna händelse.
     */
    public int getCustomerId(){ return customer.getId(); }

    /**
     * @return namnet på denna typ av händelse.
     */
    @Override
    public String EventName() { return "Ankomst"; }

}