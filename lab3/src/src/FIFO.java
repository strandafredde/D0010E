
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
//		lägger till item i fifoList och ifall fifoList blir större än maxSize så ändras maxSize storlek till fifoLists storlek

	}

	public void removeFirst() {
		if (fifoList.size() == 0) {
			throw new NoSuchElementException();
		} else {
			fifoList.removeFirst();
		}
//		tar bort första saken i fifoList 
	}

	public Object first() {
		if (fifoList.size() == 0) {
			throw new NoSuchElementException();
		} else {

			return fifoList.getFirst();
		}
//		kollar vilket föremål som är först i listen och ifall den är tom kastar den ett error
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
//		returnerar false eller true beroende på ifall storleken av listan är 0 eller inte
	}

	public int size() {
		return fifoList.size();
//		returnerar storleken av listan
	}

	private Object get(int i) {
		return this.fifoList.get(i);
//		returnerar föremålet i positionen i
	}

	public String toString() {
		String objInList = " ";
		for (int i = 0; i < fifoList.size();) {
			objInList = objInList + "(" + String.valueOf(fifoList.get(i)) + ")" + " ";
			i += 1;
		}
		return "Queue:" + objInList;
//		skapar en tom sträng sen så länge i är mindre än fifoLists storlek så kommer värdet av föremålet på positon "i" i listan läggas till i listan som skapades i början
//		och sen när "i" är större än fifoList storleken så returneras en sträng med värdet av alla föremål i listan 
	}

	public boolean equals(Object f) {
		if ((f instanceof FIFO) && ((FIFO) f).size() == this.size()) {
// kollar så att f är av klassen FIFO och att fifo size är == this.size

			for (int i = 0; i < this.size(); i += 1) {
				if ((this.get(i) != null) && (((FIFO) f).get(i) != null)) {
//					kollar ifall det båda är null för "i"

					if (!(this.get(i).equals(((FIFO) f).get(i)))) {
//						kollar så nummret på platsen "i"  inte är likamed nummret på platsen "i" i f FIFO
						return false;
					}
				} else if (this.get(i) != ((FIFO) f).get(i)) {
					return false;
//						om det inte är det returnerar den false
				}
			}

			return true;
		}
		if (!(f instanceof FIFO)){
			throw new ClassCastException("f is not of the same type as this class");
		}
		return false;
		

	}
	
//	returnerar false ifall första if satsen inte är sann

}
