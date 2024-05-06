package State;

import State.Events.Event;
import State.Events.PayEvent;

public class FIFOQueue {
    Node head;


    /**
     * Inre klass för noder
     */
    private class Node{
        Node next;
        Event Event;
        public Node (Event Event){
            this.Event = Event;
        }

    }

    /**
     * Används för att lägga till en händelse i slutet av kön.
     * @param Event händelsen som ska läggas till.
     */
    public void add(Event Event){
        if(isEmpty()){
            head = new Node(Event);
            return;
        }
        Node current = head;
        while(current.next != null){
            current = current.next;
        }
        Node input = new Node(Event);
        current.next = input;
    }

    /**
     * Tar bort den översta noden i kön
     * och returnerar dess händelse.
     * @return händelse
     */
    public Event pop(){
        Event e = head.Event;
        head = head.next;
        return e;
    }

   /**
     * Används för att få längden på registerkön.
     * @return antal kunder i kön.
     */
    public int length(){
        int l = 1;
        if(isEmpty()){
            return 0;
        }
        Node current = head;
        while(current.next != null){
            current = current.next;
            l++;
        }
        return l;
    }

    /**
     * Används för att få en sträng av registerkön.
     * @return kön.
     */
    public String printQ(){
        String Q = "[";
        if(isEmpty()){
            Q += "]";
            return Q;
        }
        Node current = head;
        Q += "" + ((PayEvent) current.Event).getCustomerId();
        while(current.next != null){
            current = current.next;
            Q += ", " + ((PayEvent) current.Event).getCustomerId();
        }
        return (Q += "]");
    }

    /**
     * Kontrollerar om kön är
     * tom.
     * @return om kön är tom eller inte.
     */
    public boolean isEmpty(){
        return head == null;
    }


}



