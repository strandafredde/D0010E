package Main;

import State.ShoppingState;
import State.Events.*;
import java.util.Random;

/**
 * Denna klass används för att optimera simuleringen av shopping.
 * Klassen innehåller funktioner för att köra simuleringen en gång
 * med fasta värden, köra simuleringen ett antal gånger för att hitta
 * det optimala antalet kassor och köra simuleringen ett antal gånger
 * för att hitta det optimala antalet kassor med ett givet frö.
 * @author Fredrik Larsson
 */

public class Optimize {
    private double S;
    private int N;
    private int M;
    private double lambda;
    private double P_Min;
    private double P_Max;
    private double K_Min;
    private double K_Max;
    private long F;
    private Random rand;


    /**
     * Konstruktorn för optimeringsklassen för simuleringen.
     * @param N kassor
     * @param M kapacitet
     * @param S stängningstid
     * @param lambda ankomsthastighet
     * @param P_Min minsta plocktid
     * @param P_Max största plocktid
     * @param K_Min minsta betaltid
     * @param K_Max största betaltid
     * @param F frö
     */

    public Optimize(int N, int M, double S, double lambda, double P_Min, double P_Max, double K_Min, double K_Max, long F){
        this.S = S;
        this.N = N;
        this.M = M;
        this.lambda = lambda;
        this.P_Min = P_Min;
        this.P_Max = P_Max;
        this.K_Min = K_Min;
        this.K_Max = K_Max;
        this.F = F;
    }


    /**
     * Denna funktion används för att köra shopping
     * simuleringen en gång med fasta givna värden.
     * @param regs antalet kassor i
     * butiken i simuleringen.
     * @param seed fröet som används för slumpmässighet
     * inom simuleringen.
     * @return tillståndet för den skapade simuleringen.
     */
    public ShoppingState M1(int regs, long seed){
        ShoppingState State = new ShoppingState(regs, this.M, this.S, this.lambda, this.P_Min, this.P_Max, this.K_Min, this.K_Max, seed);
        EventQueue Queue = new EventQueue();

        StartEvent start = new StartEvent(State, Queue, 0.00);
        ClosingEvent closing = new ClosingEvent(State, Queue, this.S);
        StopEvent stop = new StopEvent(State, Queue, 999.00);

        Queue.add(start);
        Queue.add(closing);
        Queue.add(stop);

        Simulator sim = new Simulator(State, Queue);
        sim.run();
        return State;
    }


    /**
     * Denna funktion används för att köra M1() ett fast
     * antal gånger för att hitta det optimala antalet kassor
     * med det givna fröet.
     * @param seed fröet som används inom shopping simuleringen.
     * @return det optimala antalet kassor.
     */
    public int M2(long seed){
        int OpitmalNumberOfCheckouts = 0;
        int prev_lostCustomers = 999;
        for(int numberOfCheckouts = 2; numberOfCheckouts < M; numberOfCheckouts++) {
            ShoppingState S = (ShoppingState) M1(numberOfCheckouts, seed);
            int currentLostcustomers = S.getLostCustomers();
            if (currentLostcustomers < prev_lostCustomers) {
                OpitmalNumberOfCheckouts = numberOfCheckouts;
            }
            prev_lostCustomers = S.getLostCustomers();

            System.out.print("\n" + S.getLostCustomers());
        }

        return OpitmalNumberOfCheckouts;
    }

    /**
     * Denna funktion används för att köra M2() ett  antal
     * gånger för att hitta det optimala antalet kassor att använda
     * inom en shopping simulering givet de fasta parametrarna.
     * @param seed startfrö som ska slumpas för att
     * bestämma det optimala antalet kassor.
     * @return det slutliga optimala antalet kassor.
     */
    public int M3(int seed){
        rand = new Random(seed);
        int count = 0;
        int OptimalNumberOfCheckouts = 0;
        while(count <= 100){
            if (count >= 100) {
                break;
                } 
            int numberOfCheckouts = M2(rand.nextInt());
            if (numberOfCheckouts == OptimalNumberOfCheckouts){
                count++;
            }
            else if(numberOfCheckouts > OptimalNumberOfCheckouts) {
                OptimalNumberOfCheckouts = numberOfCheckouts;
                count = 0;
            }
            count++;
        }
        return OptimalNumberOfCheckouts;
    }

}
