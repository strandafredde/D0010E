package Main;
import State.ShoppingState;
import State.Events.*;
/**
 * Denna klass sätter de initiala eventen och skapar instansen av
 * simuleringen och eventkön som ska användas i programmet.
 */

public class RunSim {
    private ShoppingState shoppingState;
    private EventQueue eventQueue;
    private double S;


    /**
     * Kör simuleringen.
     *
     * @param N Antalet kassor
     * @param M Max antal kunder i butiken
     * @param S Stängningstid
     * @param lambda Ankomsthastighet
     * @param P_Min Minsta plocktid
     * @param P_Max Största plocktid
     * @param K_Min Minsta betaltid
     * @param K_Max Största betaltid
     * @param F Frö för tidsberäkningar
     */

    public RunSim(int N, int M, double S, double lambda, double P_Min, double P_Max, double K_Min, double K_Max, long F){
        this.S = S;
        this.shoppingState = new ShoppingState(N, M, S, lambda, P_Min, P_Max, K_Min, K_Max, F);
        this.eventQueue = new EventQueue();

    }

    /**
     * Förbereder initiala händelser och sätter igång simuleringen.
     */
    public void start(){
        StartEvent start = new StartEvent(shoppingState, eventQueue, 0.00);
        ClosingEvent closing = new ClosingEvent(shoppingState, eventQueue, this.S);
        StopEvent stop = new StopEvent(shoppingState, eventQueue, 999.00);

        eventQueue.add(start);
        eventQueue.add(closing);
        eventQueue.add(stop);

        Simulator sim = new Simulator(shoppingState, eventQueue);

        sim.run();
    }

    /**
     * @return för huvudklassen att få en state.
     */
    public ShoppingState getState(){
        return shoppingState;
    }


}