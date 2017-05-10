import java.util.Scanner;

public class Recursion {

	/* Method that returns double with value 
	 * x to the power of n*/
	public static double power(double x, int n){
		if (n == 0){
			return 1;
		}
		else{
			return x*power(x,n-1);
		}
	}

	/* Method that returns int with value 
	 * m times n*/
	public static int multiply(int m, int n){
		if (n == 0){
			return 0;
		}
		else{
			return m + multiply(m,n-1);
		}
	}

	/* Method that returns double with value 
	 of the harmonic sum of n*/
	public static double harmonic(int n){
		if(n == 0){
			return 0;
		}
		else{
			return  1./n + harmonic(n-1) ;
		}
	}

	/* Method that returns int with value of the
	 largest array element in a from index 0 to i*/
	public static int largest(int[] a, int i){
		if(i == 0){
			return a[0];
		}
		else{
			if(a[i]>=largest(a,i-1)){
				return a[i];
			}
			else{
				return largest(a,i-1);
			}
		}
	}
	
	/* Method that returns String with numbers from 
	 * the scanner object reversed*/
	public static String reverseNumbers(Scanner scan){
		String s = "";
		
		if(scan.hasNextInt()){
		s = " " + scan.next();	}
		
		while (scan.hasNextInt()){
			s = reverseNumbers(scan) + s;		
		}					
		return s;
	}

	public static void reverseNumbers(){
		Scanner scan = new Scanner(System.in);
		String s = "";	
		while(scan.hasNextInt()){	
			s = scan.nextInt() + s;	
		}

			scan.close();	
			System.out.println(s);
		
	}

	//ALGORITHMS
	//---------------------------------------------------------
	public static long fib(int n){
		if (n==0){
			return 0;
		}
		else if (n==1){
			return 1;
		}
		else{
			return fib(n-1) + fib(n-2);
		}
	}

	public static long fibtime(int n){
		if (n==0){
			return 0;
		}
		else if (n==1){
			return 0;
		}
		else{
			return 1 + fibtime(n-1) + fibtime(n-2);
		}
	}


	public static void main(String[] args) {
		int[] a = {12,1,17,18};
		System.out.println(power(2,4));
		System.out.println(multiply(8,2));
		System.out.println(harmonic(2));
		System.out.println(largest( a, 1));
		System.out.println(reverseNumbers(new Scanner("11 23 31 49 56 611")));
		reverseNumbers();
		//System.out.println(fibtime(50));takes t(50)=20.365011073s, c=0.72436721652
		//System.out.println(fibtime(100));would take 5.7254617*10^11s
	}
}
