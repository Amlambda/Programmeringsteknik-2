import java.util.ArrayList;

public class SpecialStack extends Object{
	private ArrayList<Integer> ss;	

	/*Creates an empty stack*/
	public SpecialStack(){
		ss = new ArrayList<Integer>();		
	} 

	/*Creates a stack with numbers n to 1*/
	public SpecialStack(int n){
		ss = new ArrayList<Integer>();				
		for(int i = 0; i<n ; i++){
			ss.add(n - i);
		}
	}

	/*Adds an element to the stack if it is smaller than the element
	 * on top.*/
	public void push(int x) throws RuntimeException{
		if(ss.size() == 0){
			ss.add(x);
		}
		else if (x <= ss.get(ss.size() - 1)){
			ss.add(x);
		}
		else{
			throw new RuntimeException("" +x+"Larger than element on top of stack!");
		}
	}

	/*Removes the top element of the stack and returns it.*/
	public int pop() throws RuntimeException{
		if (ss.size() != 0){
			int temp = ss.get(ss.size() - 1);
			ss.remove(ss.get(ss.size() - 1));
			return temp;
		}
		else{
			throw new RuntimeException("No elements in stack!");
		}
	}

	/*String representation of the stack.*/
	public String toString(){
		if(ss.size() != 0){
			String s = "[" + ss.get(0);
			for(int i=1; i<ss.size(); i++){
				s = s +", "+ ss.get(i);		
			}
			return s + "]";
		}
		else{
			return "";
		}
	}

	public static void main(String[] args) {
		SpecialStack stack = new SpecialStack(5);
		SpecialStack stack2 = new SpecialStack();
		stack2.push(stack.pop());
		stack.push(stack2.pop());
		System.out.println(stack.toString());
		System.out.println(stack2.toString());
	}
}
