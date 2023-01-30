
import java.util.NoSuchElementException;
import java.util.LinkedList;

public class FIFO implements Queue {
	private LinkedList<Object> fifoList = new LinkedList<Object>();
	private int maxSize = 0;

//	skapar en LinkedList och en variabel maxSize
	public void add(Object item) {
		fifoList.add(item);
		if (fifoList.size() > maxSize) {
			maxSize = fifoList.size();
		}
//		l�gger till item i fifoList och ifall fifoList blir st�rre �n maxSize s� �ndras maxSize storlek till fifoLists storlek

	}

	public void removeFirst() {
		if (fifoList.size() == 0) {
			throw new NoSuchElementException();
		} else {
			fifoList.removeFirst();
		}
//		tar bort f�rsta saken i fifoList 
	}

	public Object first() {
		if (fifoList.size() == 0) {
			throw new NoSuchElementException();
		} else {

			return fifoList.getFirst();
		}
//		kollar vilket f�rem�l som �r f�rst i listen och ifall den �r tom kastar den ett error
	}

	public int maxSize() {
		return maxSize;
//		returnerar maxSize
	}

	public boolean isEmpty() {
		if (fifoList.size() == 0) {
			return true;
		}
		return false;
//		returnerar false eller true beroende p� ifall storleken av listan �r 0 eller inte
	}

	public int size() {
		return fifoList.size();
//		returnerar storleken av listan
	}

	private Object get(int i) {
		return this.fifoList.get(i);
//		returnerar f�rem�let i positionen i
	}

	public String toString() {
		String objInList = " ";
		for (int i = 0; i < fifoList.size();) {
			objInList = objInList + "(" + String.valueOf(fifoList.get(i)) + ")" + " ";
			i += 1;
		}
		return "Queue:" + objInList;
//		skapar en tom str�ng sen s� l�nge i �r mindre �n fifoLists storlek s� kommer v�rdet av f�rem�let p� positon "i" i listan l�ggas till i listan som skapades i b�rjan
//		och sen n�r "i" �r st�rre �n fifoList storleken s� returneras en str�ng med v�rdet av alla f�rem�l i listan 
	}

	public boolean equals(Object f) {
		if ((f instanceof FIFO) && ((FIFO) f).size() == this.size()) {
// kollar s� att f �r av klassen FIFO och att fifo size �r == this.size

			for (int i = 0; i < this.size(); i += 1) {
				if ((this.get(i) != null) && (((FIFO) f).get(i) != null)) {
//					kollar ifall det b�da �r null f�r "i"

					if (!(this.get(i).equals(((FIFO) f).get(i)))) {
//						kollar s� nummret p� platsen "i"  inte �r likamed nummret p� platsen "i" i f FIFO
						return false;
					}
				} else if (this.get(i) != ((FIFO) f).get(i)) {
					return false;
//						om det inte �r det returnerar den false
				}
			}

			return true;
		}
		if (!(f instanceof FIFO)){
			throw new ClassCastException("f is not of the same type as this class");
		}
		return false;
		

	}
	
//	returnerar false ifall f�rsta if satsen inte �r sann

}
