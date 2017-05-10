
public class StackPlay {
	
	private static long nrOfTimesMoved = 0;

   /**
    * Returns the current number of times an element has been moved
    * @return the number of moves
    */   
   public static long getNumberOfMoves() {
	   return nrOfTimesMoved;
   }
   
   /**
    * Moves numbers from one stack to another using
    * the push and pop methods and a third help stack.
    * @param from the source stack
    * @param to the destination stack
    * @param help the help stack
    * @param n the number of integers to be moved
    */   
   public static void move(SpecialStack from, 
                           SpecialStack to, 
                           SpecialStack help, 
                           int n) {
	   if(n == 1){
	   to.push(from.pop());
	   nrOfTimesMoved++; 
	   }
	   else{
		 move(from,help,to,n-1); //Moves n-1 bricks from "from stack" to "help stack".
		 to.push(from.pop());
		   nrOfTimesMoved++; 
		 move(help,to,from,n-1); //Moves n-1 bricks from "help stack" to "to stack".
	   }
   } 
   
   public static void main(String[] args) {
      int size = 20;

      SpecialStack from = new SpecialStack(size);
      SpecialStack to = new SpecialStack();
      SpecialStack help = new SpecialStack();
      System.out.println("Start state");
      System.out.println("   From: " + from);
      System.out.println("   To:   " + to);
      System.out.println("   Help: " + help);
      System.out.println("   Number of moves: " + getNumberOfMoves());
      long startT = System.nanoTime();
      move(from, to, help, size);
      long endT = System.nanoTime();
      System.out.println("End state");
      System.out.println("   From: " + from);
      System.out.println("   To:   " + to);
      System.out.println("   Help: " + help);
      System.out.println("   Number of moves: " + getNumberOfMoves());
      System.out.println("Time to complete moves: " + (endT - startT));
   }
}
