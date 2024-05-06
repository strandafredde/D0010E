package State.Time;
import java.util.Random;

/**
 * denna klass används för att skapa ankomsttider.
 * @author Fredrik Larsson
 */
public class ArrivalTime {

    private Random rand;
    private double lambda;

    /**
     * konstruktor för ankomsttid.
     * @param lambda frekvensen av ankomster
     * @param seed fröet för slumpgeneratorn
     */
    public ArrivalTime(double lambda, long seed){
        rand = new Random(seed);
        this.lambda = lambda;
    }

    /**
     * konstruktor för ankomsttid.
     * @param lambda frekvensen av ankomster
     */
    public ArrivalTime(double lambda){
        rand = new Random();
        this.lambda = lambda;
    }

    /**
     * @return nästa ankomsttid.
     */
    public double nextArrival() {
        return -Math.log(rand.nextDouble())/lambda;
    }

}
