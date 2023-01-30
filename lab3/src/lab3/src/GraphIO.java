import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;


public class GraphIO {
	static public void readFile(Graph g, String filename) throws IOException {
		try {
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			int numOfNodes = scanner.nextInt();
			int i = 0;
//			skapar en fil från filen filename sen en scanner på filen man skapade
//			skapar sedan en varaibel som är antal nodes som representerar första nummret i 
			while(i < numOfNodes) {
				int space = scanner.nextInt();
				int x = scanner.nextInt();
				int y = scanner.nextInt();
				g.addNode(space, x, y);
				i += 1;
//			medans i är mindre än num of nodes så kommer den att ge space, x, y ett värde
//			måste vara u space,x,y ordningen eftersom det var denna ordning den var i texten
	
			}
			while(scanner.hasNext()) {
				int space1 = scanner.nextInt();
				int space2 = scanner.nextInt();
				int weight = scanner.nextInt();
				g.addEdge(space1, space2, weight);
//				medans det finns nånitng mer i scanner så kommer den att tilldela space1, space2, weight
//				detta göra så de blir anslutna till varandra
//				måste vara space1, space2 weight ordningen eftersom det var denna ordning den var i texten
			}
		}	
		catch (FileNotFoundException e) {
			System.out.println("Går inte att läsa filen");
			throw new IOException();  
		}
	}
}
