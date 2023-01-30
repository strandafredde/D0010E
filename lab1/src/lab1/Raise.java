package lab1;

public class Raise {
	public static int counter = 0;
	static double recRaiseHalf(double x, int k) {

		int Z = k / 2;
		if (k == 0) {
			return 1;		
		}
		else if (k % 2 == 0) {
			counter += 1;
			double Y = recRaiseHalf(x, k / 2);
			return (Y * Y);
		}
		else {
			counter += 1;
			double Y = recRaiseHalf(x, k / 2);
			return (x * Y * Y);
		
			
		}
	}		
	
	
	public static int counter2 = 0;
	static double recRaiseOne(double x, int k) {;
		counter2 += 1;
		if (k==0) {
			return 1.0;
			
		} 
		else {
			return x * recRaiseOne (x, k-1);
		}
	}


	public static void main(String[] args) {
		int k = 1;
		while (k <= 15) {
			System.out.println("For k = "+k + " ResultRasieOne " + recRaiseOne(1.5, k)+ " Counter " + counter2);
			counter2 = 0;
			System.out.println("For k = "+k + " ResultRasieHalf " + recRaiseHalf(1.5, k)+ " Counter " + counter);
			counter = 0;
			
			System.out.println();
			k+=1;
		}
	}
}
