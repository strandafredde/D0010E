package State;
import State.Events.Event;
import State.Time.ArrivalTime;
import State.Time.PayTime;
import State.Time.PickTime;


/**
 * Denna klass används för att hantera shoppingtillståndet.
 * Det är den själva hjärnan i simuleringen och innehåller
 * alla nödvändiga metoder och variabler för att simulera
 * @Author Fredrik Larsson
 */
public class ShoppingState extends State {

    /* ============= KÄLLVARIABLER =============*/
    private FIFOQueue registerQueue = new FIFOQueue();
    private CustomerFactory CustomerFactory = new CustomerFactory();
    private ArrivalTime ArrivalTime;
    private PickTime PickTime;
    private PayTime PayTime;

    /* ============= FASTA VARIABLER ============= */
    private final int N;            /* Kassaregister */
    private final int M;            /* Butikskapacitet */
    private final double lambda;    /* Ankomstfrekvens */
    private final double K_Min;     /* Min betalningstid */
    private final double K_Max;     /* Max betalningstid */
    private final double P_Min;     /* Min plocktid */
    private final double P_Max;     /* Max plocktid */
    private final double S;         /* Stängningstid */
    private final long F;           /* frö för tid */

    /*============= JUSTERBARA VARIABLER =============*/
    private int finishedCustomers;          /* Kunder som är klara */
    private int lostCustomers;              /* Förlorade kunder */
    private double inactiveRegisterTime;    /* Tid som register har varit inaktiva */
    private double TotalQueueTime;          /* Den totala tiden kunder tillbringade i kö */
    private int queuedCustomers;            /* Antal kunder för närvarande i kö */
    private int occupancy;                  /* Antal kunder för närvarande i butiken */
    private int occupiedRegisters;          /* Antal för närvarande upptagna register */
    private boolean storeEntry = true;      /* Butiksinträdesbehörighet */
    private double eventTimeDelta;          /* Tid mellan händelser */
    private double lastPayTime;             /* Tid för sista betalningshändelsen */


    /**
     * Konstruktören för den specifika shoppingtillståndet.
     * @param N kassaregister
     * @param M kapacitet
     * @param S stängningstid
     * @param lambda ankomstfrekvens
     * @param P_Min plockning min tid
     * @param P_Max plockning max tid
     * @param K_Min betalning min tid
     * @param K_Max betalning max tid
     * @param F frö
     */
    public ShoppingState(int N, int M, double S, double lambda, double P_Min, double P_Max, double K_Min, double K_Max, long F) {
        super();

        if (N <= 0 || M <= 0 || S <= 0 || lambda <= 0 || P_Min < 0 || P_Max <= 0 || K_Min < 0 || K_Max <= 0) {
            throw new IllegalArgumentException("Olagliga argument i shoppingkonstruktören.");
        }

        this.ArrivalTime = new ArrivalTime(lambda, F);
        this.PickTime = new PickTime(P_Min, P_Max, F);
        this.PayTime = new PayTime(K_Min, K_Max, F);

        this.N = N;
        this.M = M;
        this.lambda = lambda;
        this.K_Min = K_Min;
        this.K_Max = K_Max;
        this.P_Min = P_Min;
        this.P_Max = P_Max;
        this.S = S;
        this.F = F;

    }

    /*================================================*/
    /*               MODIFIERANDE METODER             */
    /*================================================*/

    /*------- BUTIKSSTATUS -------*/

    /**
     * Används för att lägga till det totala antalet färdiga
     * kundräkning.
     */
    public void addFinishedCustomers(){ this.finishedCustomers++; }

    /**
     * Används för att lägga till det totala antalet förlorade kunder.
     */
    public void addLostCustomers() { this.lostCustomers++; }

    /**
     * Används för att lägga till tid som slösas bort för inaktiva register.
     * @param addedTime ytterligare inaktiv tid.
     */
    public void addInactiveRegisterTime(double addedTime) { this.inactiveRegisterTime += addedTime; }

    /**
     * Används för att lägga till ytterligare en ockupant till butikens ockupanträkning.
     */
    public void addOccupant(){ if(storeEntry) this.occupancy++; }

    /**
     * Används för att ta bort en ockupant från butikens ockupanträkning.
     */
    public void removeOccupant(){ this.occupancy--; }

    /**
     * Används för att lägga till ett upptaget register till det totala antalet.
     */
    public void addOccupiedRegister(){ this.occupiedRegisters++; }

    /**
     * Används för att ta bort ett upptaget register från det totala antalet.
     */
    public void removeOccupiedRegister(){ this.occupiedRegisters--; }

    /**
     * Används för att neka ytterligare kundinträde i butiken.
     */
    public void denyEntry(){ this.storeEntry = false; }

    /**
     * Används av externa klasser för att få kunder för händelser.
     * @return en kund som ska tilldelas en händelse.
     */
    public Customer getCustomer(){ return CustomerFactory.getCustomer(); }

    /**
     * Kontrollerar och lägger till tid som slösas bort av inaktiva register.
     * @param addedTime ytterligare slösad tid som register har varit inaktiva.
     */
    public void checkInactiveRegisterTime(double addedTime){
        for(int i = 1; i <= (getN() - getOccupiedRegisters()); i++){
            addInactiveRegisterTime(addedTime);
        }
    }

    /**
     * Används för att ställa in exekveringstiden för den sista betalningshändelsen i simuleringen.
     * @param time exekveringstid för sista betalningshändelsen.
     */
    public void setLastPayTime(double time){this.lastPayTime = time;}

    /*------- REGISTERKÖ -------*/

    /**
     * Används för att lägga till tid för total kötid.
     * @param addedTime ytterligare kötid.
     */
    public void addTotalQueueTime(double addedTime){ this.TotalQueueTime += addedTime; }


    /**
     * Används för att lägga till en ny händelse/kund till registerkön.
     * @param e händelse/kund som ska läggas till i registerkön.
     */
    public void addRegisterQueue(Event e){
        registerQueue.add(e);
        this.queuedCustomers++;
    }

    /**
     * Används för att hämta och ta bort den första händelsen/kunden
     * i registerkön.
     * @return händelse/kund hämtad från registerkön.
     */
    public Event popRegisterQueue(){ return registerQueue.pop(); }

    /**
     * Används för att kontrollera om registerkön är tom eller inte
     * @return sant om den är tom.
     */
    public boolean registerIsEmpty(){ return registerQueue.isEmpty(); }

    /**
     * Används för att få längden/storleken på registerkön.
     * @return antal kunder för närvarande i kö.
     */
    public int registerLength(){ return registerQueue.length(); }

    /**
     * används för att få registerkön som en sträng
     * @return köstruktur som sträng.
     */
    public String registerString(){ return registerQueue.printQ(); }


    /**
     * Kontrollerar och lägger till tid som spenderas i registerkön
     * av kunder.
     * @param addedTime ytterligare tid som spenderas i kö.
     */
    public void checkQueueTime(double addedTime){
        for(int i = 1; i <= registerLength(); i++){
            addTotalQueueTime(addedTime);
        }
    }

    /*------- HÄNDELSESTATUS -------*/

    /**
     * Används för att ställa in tidsdeltat mellan händelsernas exekveringstid.
     * @param newDeltaTime tidsdeltat mellan händelser.
     */
    public void setEventTimeDelta(double newDeltaTime){this.eventTimeDelta = newDeltaTime;}

    /*================================================*/
    /*                 HJÄLPMETODER                   */
    /*================================================*/

    /*------- FASTA VARIABLER -------*/

    /**
     * @return antal kassaregister
     */
    public int getN(){ return this.N; }

    /**
     * @return Maximal butikskapacitet.
     */
    public int getM(){ return this.M; }

    /**
     * @return ankomstfrekvensfaktor.
     */
    public double getLambda(){ return this.lambda; }

    /**
     * @return Minsta plocktid.
     */
    public double getP_Min(){ return this.P_Min; }

    /**
     * @return Maximal plocktid.
     */
    public double getP_Max(){ return this.P_Max; }

    /**
     * @return Minsta betalningstid.
     */
    public double getK_Min(){ return this.K_Min; }

    /**
     * @return Maximal betalningstid.
     */
    public double getK_Max(){ return this.K_Max; }

        /**
     * @return Tidsberäkningsfrö.
     */
    public long getF(){ return this.F; }

    /*------- BUTIKENS STATUS -------*/

    /**
     * Används för att kontrollera om butiken är vid maxkapacitet.
     * @return sant om kapaciteten har nåtts.
     */
    public boolean atCapacity(){ return (getM() <= getOccupancy());}

    /**
     * @return antal upptagna kassor.
     */
    public int getOccupiedRegisters(){ return occupiedRegisters; }

    /**
     * @return antal lediga kassor.
     */
    public int getUnoccupiedRegisters(){ return (getN() - getOccupiedRegisters()); }

    /**
     * @return Aktuell beläggning i butiken.
     */
    public int getOccupancy(){ return this.occupancy; }

    /**
     * @return Sant om det finns tillgängliga kassor.
     */
    public boolean availableRegisters(){ return (this.occupiedRegisters < getN()); }

    /**
     * @return Total tid slösad från inaktiva kassor.
     */
    public double getInactiveRegisterTime(){ return this.inactiveRegisterTime; }

    /**
     * @return Antal avslutade kunder.
     */
    public int getFinishedCustomers(){ return this.finishedCustomers; }

    /**
     * @return Antal förlorade kunder.
     */
    public int getLostCustomers(){ return this.lostCustomers; }

    /**
     * @return Nästa tid för en betalningshändelse.
     */
    public double AddedPayTime(){ return PayTime.nextPay(); }

    /**
     * @return Nästa tid för en plockhändelse.
     */
    public double AddedPickTime(){ return PickTime.nextPick(); }

    /**
     * @return Nästa tid för en kundankomst.
     */
    public double AddedArrivalTime(){ return ArrivalTime.nextArrival(); }

    /**
     * @return Butikens ingångsstatus.
     */
    public boolean getStoreEntry(){ return this.storeEntry; }

    /**
     * @return Exekveringstid för sista betalningshändelsen i simuleringen.
     */
    public double getLastPayTime(){return this.lastPayTime; }

    /*------- KÖ TILL KASSA -------*/

    /**
     * @return Antal kunder för närvarande i kassakön.
     */
    public int getQueuedCustomers(){ return this.queuedCustomers; }

    /**
     * @return Total tid kunder tillbringade i kassakön.
     */
    public double getTotalQueueTime(){ return this.TotalQueueTime; }

    /*------- HÄNDELSENS STATUS -------*/

    /**
     * @return Tid mellan händelser.
     */
    public double getEventTimeDelta(){ return this.eventTimeDelta; }


}