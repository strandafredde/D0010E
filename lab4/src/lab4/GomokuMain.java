package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

/**
 * Starts the Gomoku game
 * port The port the program listens on
 * @author-Fredrik Justin 
 */
public class GomokuMain {
    public static void main(String args[]){

        /*
        *
        * This main method creates three things:
        *   A GomokuClient that passes a portNumber as an argument.
        *   A GomokuGameState that passes a GomokuClient instance as an argument
        *   A GomokuGUI that passes instances of GomokuGameState and GomokuClient as arguments.
        *
        */

        int DefaultPort = 4002;

        GomokuClient GC1 = new GomokuClient(DefaultPort);
        GomokuGameState GGS1 = new GomokuGameState(GC1);
        new GomokuGUI(GGS1, GC1);
        
        GomokuClient GC2 = new GomokuClient(DefaultPort+1);
        GomokuGameState GGS2 = new GomokuGameState(GC2);
        new GomokuGUI(GGS2, GC2);


    }
}