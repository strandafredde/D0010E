package State.View;
import State.View.View;
import State.ShoppingState;
import State.Events.*;
import java.util.Observable;


public class ShoppingView extends View {
    private ShoppingState state;

    /**
     * Konstruktorn för shoppingvyn.
     * @param state state:n som vyn ska representera.
     */
    public ShoppingView(ShoppingState state) {
        this.state = state;
        state.addObserver(this);
    }

    /**
     * Kallas när ett nytt event har skapats och en utskrift
     * ska göras för att vidarebefordra informationen om det aktuella eventet.
     * @param state det observerbara objektet(i det här fallet shoppingstatet).
     * @param e ett argument som skickas till {@code notifyObservers}
     * metoden(i det här fallet Eventet).
     */
    @Override
    public void update(Observable state, Object e) {
        super.update(state, e);
        if (e instanceof StartEvent){
            PrintStart((Event) e);
        }
        else if (e instanceof StopEvent){
            PrintStop((Event) e);
        }
        else {
            PrintEvent((Event) e);
        }
    }

    /**
     * Används för att skriva ut StartEventets information
     * och annan relaterad information.
     * @param e StartEvent-objektet.
     */
    public void PrintStart(Event e){
        System.out.printf("PARAMETRAR\n");
        System.out.printf("==========\n");
        System.out.printf("Antal kassor, N..........: %d\n", state.getN());
        System.out.printf("Max som ryms, M..........: %d\n", state.getM());
        System.out.printf("Ankomsthastigheten, lamda: %.2f\n", state.getLambda());
        System.out.printf("Plocktider, [P_min..P_max]: [%.2f..%.2f]\n", state.getP_Min(), state.getP_Max());
        System.out.printf("Betaltider, [K_min..K_max]: [%.2f..%.2f]\n", state.getK_Min(), state.getK_Max());
        System.out.printf("Frö, f...................: %d\n\n", state.getF());
        System.out.printf("Tid  Händelse    Kund   ?   led     ledT    I   $  :-(    köat   köT     köar   [Kassakö..]\n");
        System.out.printf("%.2f %s\n", e.getExTime(), e.EventName());
    }

    /**
     * Generell utskriftsfunktion för alla Events
     * som inte är ett Closing -eller StartEvent.
     * @param e Eventet som ska ha sin information utskriven.
     */
    public void PrintEvent(Event e) {
        if (e instanceof ArrivalEvent){
            System.out.printf("%.2f %s\t\t%s\t%s\t%d\t\t%.2f\t%d\t%d\t%d\t\t%d\t%.2f\t\t%d\t%s\n", e.getExTime(),
                    e.EventName(), CustomerNum(e), StoreOpen(), state.getUnoccupiedRegisters(), state.getInactiveRegisterTime(),
                    state.getOccupancy(), state.getFinishedCustomers(), state.getLostCustomers(), state.getQueuedCustomers(),
                    state.getTotalQueueTime(), state.registerLength(), state.registerString());
        }
        else if (e instanceof PickEvent){
            System.out.printf("%.2f %s   \t\t%s\t%s\t%d\t\t%.2f\t%d\t%d\t%d\t\t%d\t%.2f\t\t%d\t%s\n", e.getExTime(),
                    e.EventName(), CustomerNum(e), StoreOpen(), state.getUnoccupiedRegisters(), state.getInactiveRegisterTime(),
                    state.getOccupancy(), state.getFinishedCustomers(), state.getLostCustomers(), state.getQueuedCustomers(),
                    state.getTotalQueueTime(), state.registerLength(), state.registerString());
        }
        else{
            System.out.printf("%.2f %s \t\t%s\t%s\t%d\t\t%.2f\t%d\t%d\t%d\t\t%d\t%.2f\t\t%d\t%s\n", e.getExTime(),
                    e.EventName(), CustomerNum(e), StoreOpen(), state.getUnoccupiedRegisters(), state.getInactiveRegisterTime(),
                    state.getOccupancy(), state.getFinishedCustomers(), state.getLostCustomers(), state.getQueuedCustomers(),
                    state.getTotalQueueTime(), state.registerLength(), state.registerString());
        }

    }

    /**
     * Används för att skriva ut utdatainformationen
     * för StopEventet.
     * @param e eventet som ska skrivas ut.
     */
    public void PrintStop(Event e) {
        System.out.printf("%.2f %s\n", e.getExTime(), e.EventName());
        Result();
    }

    /**
     * Används för att skriva ut resultatet av simuleringen.
     */
    public void Result(){
        System.out.print("\nRESULTAT\n");
        System.out.print("========\n");
        System.out.printf("1) Av %d kunder handlade %d medan %d missades \n\n",
                (state.getFinishedCustomers() + state.getLostCustomers()),
                state.getFinishedCustomers(),
                state.getLostCustomers());
        System.out.printf("2) Total tid %d kassor varit lediga: %.2f te. \n" +
                        "Genomsnittlig ledig kassatid: %.2f te (dvs %.2f%% av " +
                        "tiden från öppning tills sista kunden betalat).\n\n",
                state.getUnoccupiedRegisters(),
                state.getInactiveRegisterTime(),(state.getInactiveRegisterTime()/state.getN()),
                (((state.getInactiveRegisterTime()/state.getN())/state.getLastPayTime())*100));
        System.out.printf("3) Total tid %d kunder tvingats köa: %.2f te. \n" +
                        "Genomsnittlig kötid: %.2f te \n\n",
                state.getQueuedCustomers(),
                state.getTotalQueueTime(),
                (state.getTotalQueueTime()/state.getQueuedCustomers()));
    }

    /**
     * Används för att korrekt konvertera kund-ID
     * för att användas i utskrift.
     * @param e ett event.
     * @return kund-ID som en sträng.
     */
    private String CustomerNum(Event e){
        if (e instanceof StartEvent){
            return " ";
        }
        else if (e instanceof StopEvent){
            return " ";
        }
        else if (e instanceof ClosingEvent){
            return "-";
        }
        else if (e instanceof ArrivalEvent){
            return "" + ((ArrivalEvent) e).getCustomerId();
        }
        else if (e instanceof PayEvent){
            return "" + ((PayEvent) e).getCustomerId();
        }
        else{
            return "" + ((PickEvent) e).getCustomerId();
        }
    }

    /**
     * Används för att få butikens
     * öppna/stängda status.
     * @return butikens status.
     */
    private String StoreOpen(){
        if(state.getStoreEntry()){
            return "Ö";
        }
        return "S";
    }

}
