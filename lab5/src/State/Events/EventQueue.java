package State.Events;


/**
 * Denna klass är en priokö för händelser som ska köras i simuleringen.
 * @author Fredrik Larsson
 */
public class EventQueue {
    Node head;

    /**
     * Inre klass för Noder
     * inom köns länkade lista.
     */
    private class Node{
        Node next;
        Event Event;
        public Node (Event Event){
            this.Event = Event;
        }

    }

    /**
     * Används för att lägga till element - med prioritet
     * i åtanke - till kön.
     * @param Event händelsen som ska läggas till
     * i kön.
     */
    public void add(Event Event){
        if(isEmpty()){
            head = new Node(Event);
            return;
        }
        if(head.Event.exTime > Event.exTime){
            Node input = new Node(Event);
            input.next = head;
            head = input;
            return;
        }
        Node current = head;
        while(current.next != null && (current.next.Event.exTime < Event.exTime)){
            current = current.next;
        }
        Node input = new Node(Event);
        input.next = current.next;
        current.next = input;
    }

    /**
     * Plockar den översta Noden i kön
     * och returnerar dess Händelse.
     * @return händelsen
     */
    public Event pop(){
        Event e = head.Event;
        head = head.next;
        return e;
    }

    /**
     * Kontrollerar om kön är tom eller inte.
     * @return om kön är tom eller inte
     */
    public boolean isEmpty(){
        return head == null;
    }


}