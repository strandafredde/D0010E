package Main;

import State.State;
import State.Events.EventQueue;
import State.Events.Event;

/**
 * Detta är programmets kärna, och det håller
 * programmet igång och tar händelser ur kön
 * för exekvering.
 * @author Fredrik Larsson
 */
public class Simulator {

    private EventQueue queue;
    private State state;

    /**
     * Konstruktorn för simulatorklassen.
     * @param state tillståndet som simuleringen ska köras för.
     * @param queue kön som används av tillståndet för denna simulering.
     */
    public Simulator(State state, EventQueue queue) {
        this.state = state;
        this.queue = queue;

    }

    /**
     * Programmets kärna. Startar simuleringens
     * händelsekedja och stoppar den när
     * den uppmanas att göra det.
     */
    public void run() {
        while (!state.getBrake()) {
            Event currentEvent = queue.pop();
            currentEvent.effect(state);
        }
    }

}