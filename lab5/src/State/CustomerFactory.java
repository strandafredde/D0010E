package State;

//import State.Customer;
public class CustomerFactory {

    private int id = 0;

    /**
     * Used to get a new customer
     * with an assigned id.
     * @return customer with
     * specific id.
     */
    public Customer getCustomer(){
        Customer c = new Customer(id);
        id++;
        return c;
    }

}
