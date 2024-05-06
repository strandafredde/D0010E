package State.Time;

import java.util.Random;


/**
 * Denna klass används för att skapa plockningstider.
 * @author Fredrik Larsson
 */
public class PickTime {
    private Random rand;
    private double lower, width;

    /**
     * Konstruktorn för plockningstid.
     * @param lower nedre gräns för plockningstid
     * @param upper övre gräns för plockningstid
     * @param seed frö
     */
    public PickTime(double lower, double upper, long seed) {
        rand = new Random(seed);
        this.lower = lower;
        this.width = upper-lower;
    }

    /**
     * Konstruktorn för plockningstid.
     * @param lower övre gräns för tid
     * @param upper nedre gräns för tid
     */
    public PickTime(double lower, double upper) {
        rand = new Random();
        this.lower = lower;
        this.width = upper-lower;
    }

    /**
     * genererar nästa plockningstid.
     * @return nästa plockningstid.
     */
    public double nextPick() {
        return lower+rand.nextDouble()*width;
    }
}
