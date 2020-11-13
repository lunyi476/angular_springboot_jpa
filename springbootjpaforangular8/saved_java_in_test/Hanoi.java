package com.springbootjpaangular2.domain;

public class Hanoi {

	/**  
	 *   Start  peg:  (s,m,e) repeating
	 *   Middle peg:  (m,e,s) repeating
	 *	 end    peg:  (e,s,m) repeating
	 *	 4th line changes:  ????

		start to end
		start to middle
		end to middle
		start to end
		
		middle to start
		middle to end
		start to end
		start to middle
		
		end to middle
		end to start
		middle to start
		end to middle
2		
		start to end
		start to middle
		end to middle
		start to end
		
		middle to start
		middle to end
		start to end
		---middle to start
		
		end to middle
		end to start
		middle to start
		---middle to end
3		
		start to end
		start to middle
		end to middle
		start to end
		
		middle to start
		middle to end
		start to end
		---start to middle
		
		end to middle
		end to start
		middle to start
		---end to middle
4		
		start to end
		start to middle
		end to middle
		*********end to start  ?????
		
		middle to start
		middle to end
		start to end
		---middle to start
		
		end to middle
		end to start
		middle to start
		---end to middle
5		
		start to end
		start to middle
		end to middle
		start to end
		
		middle to start
		middle to end
		start to end
		---start to middle
		
		end to middle
		end to start
		middle to start
		---end to middle
6		
		start to end
		start to middle
		end to middle
		start to end
		
		middle to start
		middle to end
		start to end
		--middle to start
		
		end to middle
		end to start
		middle to start
		---middle to end
7		
		start to end
		start to middle
		end to middle
		start to end
		
		middle to start
		middle to end
		start to end
		---middle to start
		
		end to middle
		end to start
		middle to start
		---end to middle
8		
		start to end
		start to middle
		end to middle
		********end to start
		
		middle to start
		middle to end
		start to end
		middle to start
		
		end to middle
		end to start
		middle to start
		middle to end
9		
		start to end
		start to middle
		end to middle
		start to end
		
		middle to start
		middle to end
		start to end
		---start to middle
		
		end to middle
		end to start
		middle to start
		---end to middle
10		
		start to end
		start to middle
		end to middle
		start to end
		
		middle to start
		middle to end
		start to end
		---middle to start
		
		end to middle
		end to start
		middle to start
		---middle to end
11		
		start to end
		start to middle
		end to middle
		start to end
		
		middle to start
		middle to end
		start to end

*************************************************************
1
		start 1 to end
		start 2 to middle
		end 1 to middle
		start 3 to end
		
		middle 1 to start
		middle 2 to end
		start 1 to end
		start 4 to middle
		
		end 1 to middle
		end 2 to start
		middle 1 to start
		end 3 to middle
2		
		start 1 to end
		start 2 to middle
		end 1 to middle
		start 5 to end
		
		middle 1 to start
		middle 2 to end
		start 1 to end
		middle 3 to start
		
		end 1 to middle
		end 2 to start
		middle 1 to start
		middle 4 to end
3		
		start 1 to end
		start 2 to middle
		end 1 to middle
		start 3 to end
		
		middle 1 to start
		middle 2 to end
		start 1 to end
		start 6 to middle
		
		end 1 to middle
		end 2 to start
		middle 1 to start
		end 3 to middle
4		
		start 1 to end
		start 2 to middle
		end 1 to middle
		*********end 4 to start
		
		middle 1 to start
		middle 2 to end
		start 1 to end
		middle 3 to start
		
		end 1 to middle
		end 2 to start
		middle 1 to start
		end 5 to middle
5		
		start 1 to end
		start 2 to middle
		end 1 to middle
		start 3 to end
		
		middle 1 to start
		middle 2 to end
		start 1 to end
		start 4 to middle
		
		end 1 to middle
		end 2 to start
		middle 1 to start
		end 3 to middle
6		
		start 1 to end
		start 2 to middle
		end 1 to middle
		start 7 to end
		
		middle 1 to start
		middle 2 to end
		start 1 to end
		middle 3 to start
		
		end 1 to middle
		end 2 to start
		middle 1 to start
		middle 4 to end
7		
		start 1 to end
		start 2 to middle
		end 1 to middle
		start 3 to end
		
		middle 1 to start
		middle 2 to end
		start 1 to end
		middle 5 to start
		
		end 1 to middle
		end 2 to start
		middle 1 to start
		end 3 to middle
8		
		start 1 to end
		start 2 to middle
		end 1 to middle
		end 4 to start
		
		middle 1 to start
		middle 2 to end
		start 1 to end
		middle 3 to start
		
		end 1 to middle
		end 2 to start
		middle 1 to start
		middle 6 to end
9		
		start 1 to end
		start 2 to middle
		end 1 to middle
		start 3 to end
		
		middle 1 to start
		middle 2 to end
		start 1 to end
		start 4 to middle
		
		end 1 to middle
		end 2 to start
		middle 1 to start
		end 3 to middle
10		
		start 1 to end
		start 2 to middle
		end 1 to middle
		start 5 to end
		
		middle 1 to start
		middle 2 to end
		start 1 to end
		middle 3 to start
		
		end 1 to middle
		end 2 to start
		middle 1 to start
		middle 4 to end
11		
		start 1 to end
		start 2 to middle
		end 1 to middle
		start 3 to end
		
		middle 1 to start
		middle 2 to end
		start 1 to end

		**/
	 /**
	  * @param n
	 * @param start
	 * @param middle
	 * @param end
	 */
	static void hanoiImp (int n, String start, String middle, String end) {
		if (n==1) {
			System.out.println(start + " " + n + " to "+ end);
		}
		else {
			hanoiImp(n-1, start, end, middle);
			System.out.println(start + " " + n +" to "+ end);
			hanoiImp(n-1, middle, start, end);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=7;
		String start ="start", middle="middle", end = "end";
		hanoiImp (n, start, middle, end );
	}

}


class TOH{
    
	// A structure to represent a stack
	class Stack
	{
	    int capacity;
	    int top;
	    int array[];
	}
	 
	// Function to create a stack of given capacity.
	Stack createStack(int capacity)
	{
	    Stack stack = new Stack();
	    stack.capacity = capacity;
	    stack.top = -1;
	    stack.array = new int[capacity];
	    return stack;
	}
	 
	// Stack is full when the top is equal
	// to the last index
	boolean isFull(Stack stack)
	{
	    return (stack.top == stack.capacity - 1);
	}
	 
	// Stack is empty when top is equal to -1
	boolean isEmpty(Stack stack)
	{
	    return (stack.top == -1);
	}
	 
	// Function to add an item to stack.It 
	// increases top by 1
	void push(Stack stack, int item)
	{
	    if (isFull(stack))
	        return;
	         
	    stack.array[++stack.top] = item;
	}
	 
	// Function to remove an item from stack.It
	// decreases top by 1
	int pop(Stack stack)
	{
	    if (isEmpty(stack))
	        return Integer.MIN_VALUE;
	         
	    return stack.array[stack.top--];
	}
	 
	// Function to implement legal movement between
	// two poles
	void moveDisksBetweenTwoPoles(Stack src, Stack dest, 
	                              char s, char d) 
	{
	    int pole1TopDisk = pop(src);
	    int pole2TopDisk = pop(dest);
	 
	    // When pole 1 is empty
	    if (pole1TopDisk == Integer.MIN_VALUE) 
	    {
	        push(src, pole2TopDisk);
	        moveDisk(d, s, pole2TopDisk);
	    }
	     
	    // When pole2 pole is empty
	    else if (pole2TopDisk == Integer.MIN_VALUE) 
	    {
	        push(dest, pole1TopDisk);
	        moveDisk(s, d, pole1TopDisk);
	    }
	     
	    // When top disk of pole1 > top disk of pole2
	    else if (pole1TopDisk > pole2TopDisk) 
	    {
	        push(src, pole1TopDisk);
	        push(src, pole2TopDisk);
	        moveDisk(d, s, pole2TopDisk);
	    }
	    // When top disk of pole1 < top disk of pole2
	    else
	    {
	        push(dest, pole2TopDisk);
	        push(dest, pole1TopDisk);
	        moveDisk(s, d, pole1TopDisk);
	    }
	}
	 
	// Function to show the movement of disks
	void moveDisk(char fromPeg, char toPeg, int disk)
	{
	    System.out.println("Move the disk " + disk + 
	                            " from " + fromPeg + 
	                              " to " + toPeg);
	}
	 
	// Function to implement TOH puzzle
	void tohIterative(int num_of_disks, Stack
	                  src, Stack aux, Stack dest)
	{
	    int i, total_num_of_moves;
	    char s = 'S', d = 'D', a = 'A';
	  
	    // If number of disks is even, then 
	    // interchange destination pole and
	    // auxiliary pole
	    if (num_of_disks % 2 == 0)
	    {
	        char temp = d;
	        d = a;
	        a  = temp;
	    }
	    total_num_of_moves = (int)(Math.pow(
	                         2, num_of_disks) - 1);
	  
	    // Larger disks will be pushed first
	    for(i = num_of_disks; i >= 1; i--)
	        push(src, i);
	  
	    for(i = 1; i <= total_num_of_moves; i++)
	    {
	        if (i % 3 == 1)
	          moveDisksBetweenTwoPoles(src, dest, s, d);
	  
	        else if (i % 3 == 2)
	          moveDisksBetweenTwoPoles(src, aux, s, a);
	  
	        else if (i % 3 == 0)
	          moveDisksBetweenTwoPoles(aux, dest, a, d);
	    }
	}
	 
	// Driver code
	public static void main(String[] args)
	{
	     
	    // Input: number of disks
	    int num_of_disks = 3;
	     
	    TOH ob = new TOH();
	    Stack src, dest, aux;
	     
	    // Create three stacks of size 'num_of_disks'
	    // to hold the disks
	    src = ob.createStack(num_of_disks);
	    dest = ob.createStack(num_of_disks);
	    aux = ob.createStack(num_of_disks);
	     
	    ob.tohIterative(num_of_disks, src, aux, dest);
	}
}
