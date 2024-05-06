package State;

/**
 * Denna klass används för kunder.
 * @Author Fredrik Larsson
 */
public class Customer {
    int id;


    /**
     * Konstruktor för kund.
     * @param id kundens id
     */
    public Customer(int id){
        this.id = id;
    }

    /**
     * @return kundens id
     */
    public int getId(){
        return this.id;
    }
}
