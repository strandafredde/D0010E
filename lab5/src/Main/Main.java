package Main;
import State.View.ShoppingView;
import State.View.View;

/**
 * Denna klass initierar simuleringen
 * @author Fredrik Larsson
 */
public class Main {

    /**
     * Konstruktorn för programmets huvudklass.
     * @param args ingen.
     */


    private static int whichSim = 4;
    private static boolean optimizing = true;
    public static void main(String[] args) {
        int N = 0, M = 0, F = 0;
        double S = 0.0, lambda = 0.0, P_Min = 0.0, P_Max = 0.0, K_Min = 0.0, K_Max = 0.0;
    
        if (whichSim == 1) {
            /* ============ Simuleringsexempel 1 ============*/
            N = 2;              /* Antalet kassor */
            M = 5;              /* Max kunder i butiken */
            S = 10;         /* Stängningstid */
            lambda = 1.0;    /* Ankomsthastighet  */
            P_Min = 0.5;     /* Minsta plocktid */
            P_Max = 1.0;     /* Största plocktid */
            K_Min = 2.0;    /* Minsta betaltid */
            K_Max = 3.0;     /* Största betaltid */
            F = 1234;             /* Frö för tidsberäkningar */
        } else if (whichSim == 2) {
            /* ============ Simuleringsexempel 2 ============*/
            N = 2;              /* Antalet kassor */
            M = 7;              /* Max kunder i butiken */
            S = 8;         /* Stängningstid */
            lambda = 3.0;    /* Ankomsthastighet  */
            P_Min = 0.6;     /* Minsta plocktid */
            P_Max = 0.9;     /* Största plocktid */
            K_Min = 0.35;    /* Minsta betaltid */
            K_Max = 0.6;     /* Största betaltid */
            F = 13;             /* Frö för tidsberäkningar */
        } 
        else if (whichSim == 3) { 
            /* ============ Optimeringexempel 2 ============*/
            N = 0;              /* Antalet kassor */
            M = 7;              /* Max kunder i butiken */
            S = 10;         /* Stängningstid */
            lambda = 2.0;    /* Ankomsthastighet  */
            P_Min = 0.5;     /* Minsta plocktid */
            P_Max = 1.0;     /* Största plocktid */
            K_Min = 2.0;    /* Minsta betaltid */
            K_Max = 3.0;     /* Största betaltid */
            F = 1234;             /* Frö för tidsberäkningar */
        }
        else if (whichSim == 4) { 
            /* ============ Optimeringexempel 4 ============*/
            N = 0;              /* Antalet kassor */
            M = 100;              /* Max kunder i butiken */
            S = 20;         /* Stängningstid */
            lambda = 50.0;    /* Ankomsthastighet  */
            P_Min = 0.45;     /* Minsta plocktid */
            P_Max = 0.65;     /* Största plocktid */
            K_Min = 0.2;    /* Minsta betaltid */
            K_Max = 0.3;     /* Största betaltid */
            F = 42;             /* Frö för tidsberäkningar */
        }
        /*============ Originalsimulering ============*/
        if (!optimizing) {
            RunSim Sim = new RunSim(N, M, S, lambda, P_Min, P_Max, K_Min, K_Max, F);
            View view = new ShoppingView(Sim.getState());
            Sim.start();
            }
        /*============ Optimerad simulering ============*/
        if (optimizing) {
            Optimize SIM = new Optimize(N, M, S, lambda, P_Min, P_Max, K_Min, K_Max, F);
            System.out.print("===== Optimize result =====\n\n");
            System.out.printf("initial seed: %d \n", 1234);
            System.out.printf("\nThe optimal register amount for given fixed values is: %d \n", SIM.M2(F));
           // System.out.printf("\nThe optimal register amount across multiple random seeds: %d \n", SIM.M3(F));
            
        }

    }
}