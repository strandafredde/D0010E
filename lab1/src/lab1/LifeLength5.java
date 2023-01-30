package lab1;

public class LifeLength5 {
	
	public static int f1(int a0) {
		if (a0 == 1) {
			return 1;
		}
		else if(a0 % 2 == 0) {
			return (a0/2);
		}
		else {
			return (3 * a0 + 1);
		}
	}
	public static int f2(int a0) {
		return f1(f1(a0));
	}
	public static int f4(int a0) {
		return (f2(f2(a0)));
	}
	public static int f8(int a0) {
		return f4(f4(a0));
	}
	public static int f16(int a0) {
		return f8(f8(a0));
	}
	public static int f32(int a0) {
		return f16(f16(a0));
	}
	public static int iterateF(int a0, int n) {
		int res = a0;
		
		for(int n1=0; n1 < n; n1 += 1) {
			res = f1(res);
		}
		return res;
	}
	
	public static int iterLifeLength(int a0) {
		int count = 0;
		while (a0 !=1) {
			a0 = f1(a0);
			count += 1;
		}
		return count;
	}
	
	public static String intsToString(int X) {
		
		return "The life length of " + X + " is "+iterLifeLength(X) +".";
	}
	
	public static int recLifeLength(int x) {
		if (x==1) {
			return 0;
		}
		else if( x%2==0){
			return 1 + recLifeLength(f1(x));
		}
		else {
			return 1 +  recLifeLength(f1(x));
		}
	}
	public static void task1(int x) {
		System.out.println(f1(x));
	}
	
	public static void task2(int x) {
		System.out.print("f1="+f1(x)+" ");
		System.out.print("f2="+f2(x)+" ");
		System.out.print("f4="+f4(x)+" ");
		System.out.print("f8="+f8(x)+" ");
		System.out.print("f16="+f16(x)+" ");
		System.out.print("f32="+f32(x)+" ");
	}
	
	public static void task3() {
		System.out.println(iterateF(3,5));
		System.out.println(iterateF(42,3));
		System.out.println(iterateF(1,3));
	}
	
	public static void task4() {
		int i = 1;
		while (i <= 42) {
			System.out.println(intsToString(i));
			i+=1;
		}
	}
	public static void task6(int x) {
		int i = 1;
		while (i <= 15 ) { 
			System.out.println("Rec length for number "+ i+ " is " +recLifeLength(i));
			System.out.println("Iter length for number "+ i+ " is " +iterLifeLength(i));
			System.out.println();
			i+=1;
		}		
	}
	
	
	
	
	public static void main(String[] args) {
		int n;
		n = 6;
		switch(n) {
		case 1:
			task1(42);
			break;
		case 2:
			task2(42);
			break;
		case 3:
			task3();
			break;
		case 4:
			task4();
			break;
		case 6:
			task6(3);
			break;
		
		default:
			System.out.println("Error");
			break;
		}
		
		
		}
		
	}
