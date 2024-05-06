package State.Time;

import java.util.Random;


/**
 * Denna klass används för att skapa betalningstider.
 * @author Fredrik Larsson
 */
public class PayTime {

    private Random rand;
    private double lower, width;

    /**
     * Konstruktorn för betalningstid.
     * @param lower nedre gräns för tid
     * @param upper övre gräns för tid
     * @param seed frö
     */
    public PayTime(double lower, double upper, long seed) {
        rand = new Random(seed);
        this.lower = lower;
        this.width = upper-lower;
    }

    /**
     * Konstruktorn för betalningstid.
     * @param lower övre gräns för tid
     * @param upper nedre gräns för tid
     */
    public PayTime(double lower, double upper) {
        rand = new Random();
        this.lower = lower;
        this.width = upper-lower;
    }

    /**
     * @return nästa betalningstid.
     */
    public double nextPay() {
        return lower+rand.nextDouble()*width;
    }

}
