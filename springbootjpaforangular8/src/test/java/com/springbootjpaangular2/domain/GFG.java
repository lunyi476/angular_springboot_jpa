package com.springbootjpaangular2.domain;

import java.util.*;
import java.util.stream.IntStream;  
import java.lang.*;
import java.util.LinkedList;

class Test {  
    String str=null; 
    
    Test(String s) 
    { 
        this.str=s; 
    } 
    // instance function to be called  
    void someFunction()  
    {  
        System.out.println(this.str);  
    }  
}  



public class GFG {  
  
	private int y =0;
	
	public static List<int[]> generate1 (int n, int r) {
	    List<int[]> combinations = new ArrayList<>();
	    helper(combinations, new int[r], 0, n-1, 0);
	    return combinations;
	}
	
	public static void helper(List<int[]> combinations, int data[], int start, int end, int index) {
	    if (index == data.length) {
	        int[] combination = data.clone();
	        combinations.add(combination);
	    } else if (start <= end) {
	        data[index] = start;
	        helper(combinations, data, start + 1, end, index + 1);
	        helper(combinations, data, start + 1, end, index);
	    }
	}
	

	
	public static void mergeSort (int[] src, int l, int h) {
		
		if (h-l == 1 ) {	//reached base case	
			if (src[l] > src[h]) { 
				//swap(src, l,h);
				int temp = src[l]; 
				src[l] =src[h]; 
				src[h] =temp;
			}

			return;
		}
		
		if ( l ==h) return;
		int m = (l+h)/2;
		
		// if (l < h) {  int m = (l+h)/2;
		mergeSort (src, l, m);	
		//merge(src, l, m, h);
		mergeSort (src, m+1, h);
		merge(src, l, m+1, h);				
	}
	
	static void merge(int[] src, int p, int q, int r) {
		// use insertion sort
		int i =q;
		int j =0;
		int temp;
		
		while (i <= r) {
			temp = src[i];
			j =i;

			while ( j > 0 && src[j-1] > temp) {		
					src[j] = src[j-1];
					j--;
			}
			src[j] =temp;
			i++;
		}
	}
	

	 void merge2(int arr[], int l, int m, int r) 
	    { 
	        // Find sizes of two subarrays to be merged 
	        int n1 = m - l + 1; 
	        int n2 = r - m; 
	  
	        /* Create temp arrays */
	        int L[] = new int[n1]; 
	        int R[] = new int[n2]; 
	  
	        /*Copy data to temp arrays*/
	        for (int i = 0; i < n1; ++i) 
	            L[i] = arr[l + i]; 
	        for (int j = 0; j < n2; ++j) 
	            R[j] = arr[m + 1 + j]; 
	  
	        /* Merge the temp arrays */
	  
	        // Initial indexes of first and second subarrays 
	        int i = 0, j = 0; 
	  
	        // Initial index of merged subarry array 
	        int k = l; 
	        while (i < n1 && j < n2) { 
	            if (L[i] <= R[j]) { 
	                arr[k] = L[i]; 
	                i++; 
	            } 
	            else { 
	                arr[k] = R[j]; 
	                j++; 
	            } 
	            k++; 
	        } 
	  
	        /* Copy remaining elements of L[] if any */
	        while (i < n1) { 
	            arr[k] = L[i]; 
	            i++; 
	            k++; 
	        } 
	  
	        /* Copy remaining elements of R[] if any */
	        while (j < n2) { 
	            arr[k] = R[j]; 
	            j++; 
	            k++; 
	        } 
	    } 
  public static void insertion(Integer[] src) {
		
		int i =1;
		int j =0;
		int temp;
		
		while (i < src.length) {
			temp = src[i];
			j =i;

			//while ( j > 0 && src[j-1] > temp) {
				for (j=i; j > 0; j--) {
					if (src[j-1] > temp) //{ //shift to right
					src[j] = src[j-1];
					else break;
					//j--;
				}
			//}
			src[j] =temp;
			i++;
		}
	}

	public static void selection(Integer[] src) {

		int i, j =0;
		int smallIndex =0;
		
		for (i =0; i  < src.length -1; i++) {
			smallIndex = i;
			for (j=i+1; j< src.length; j++) {
				if (src[j] < src[smallIndex]) {
					smallIndex = j;
				}
			}
			swap (src, i, smallIndex);
			j++;
		}
	}
	
    public static void main(String[] args)  
    {
    	int N=5, R=3;
    	List<int[]> combinations = generate(N, R);
    	for (int[] combination : combinations) {
    	    System.out.println(Arrays.toString(combination));
    	}
    	System.out.printf("generated %d combinations of %d items from %d ", combinations.size(), R, N);

    	int[] sort = {5,3,2,4,1,6,0};
    	//selection(sort);
    	//insertion(sort);
    	
    	//mergeSort(sort, 0, 6);
    	
    	//System.out.println(sort.toString());
    	char[] a = new char[10];
    	char[] b = {'a','b','c'};
    	System.arraycopy(b, 0, a, 0, 3);
    	
    	List<int[]> lar = generate(5,3);
    	
    	Character [] c = {'X','Y','Z', 'W', 'K'}; //, 'W', 'K'
    	
    	/**for (int[] l: lar) {
    		for (int i: l) {
    			//System.out.print(i);
    			System.out.print(c[i]);
    		}
    		System.out.println();
    	}**/
    	
    	List<Character> inputSet = new ArrayList<Character>(Arrays.asList(c));
    	// <E> type must be the same
    	List<List<Character>> results = new ArrayList<List<Character>>();
    	List<Character> accumulator =  new ArrayList<Character>();
    	
    	combinations(inputSet, 3, results, accumulator, 0);
    	
    	for (List<Character> l: results) {
    		for (Character i: l) {
    			System.out.print(i);
    		}
    		System.out.println();
    	}
    	//char[] c2 = c.clone();
    	
        printMe (c, 0, new ArrayList<Character[]>());
        
        Integer[] c1 = {5,4,2,6,10};
        
    	List<Integer> inputSet1 = new ArrayList<Integer>(Arrays.asList(c1));
    	// <E> type must be the same
    	List<List<Integer>> results1 = new ArrayList<List<Integer>>();
    	List<Integer> accumulator1 =  new ArrayList<Integer>();
    	
    	for ( int i =1; i <= c1.length; i++) {
    		sumOfSubset (inputSet1, i, results1, accumulator1, 0, 16);
    		for (List<Integer> l: results1) {
        		for (Integer j: l) {
        			System.out.print(j);
        		}
        		System.out.println();
        	}
    		results1.clear();
    	}
    	
    	
    	//char[] c2 = c.clone();
    	
       
  
        List<Test> list = new ArrayList<Test>();  
        list.add(new Test("Geeks"));  
        list.add(new Test("For"));  
        list.add(new Test("GEEKS"));  
 
        // call the instance method  
        // using double colon operator  
        //list.forEach(Test::someFunction);  
    } 
    
    public static <T> void  swap (Object[] c, int i, int j) {
    	Object t = c[i];
    	c[i] = c[j];
    	c[j] =t;
    	//Object[] a = new Object[10];
    	//Object[] b = {'a','b','c'};
    	//System.arraycopy(b, 0, a, 0, 3);
    }
    
    // combination possible index
    public static List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[r];
     
        // initialize with lowest lexicographic combination
        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }
     
        while (combination[r - 1] < n) { // [2,3,5] stop
            combinations.add(combination.clone());
     
             // generate next combination in lexicographic order
            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) { // n-1
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }
     
        return combinations;
    }
    
    public static int comm (int n, int k) {
    	if (k>n) return 0;
    	else if (k == n || k ==0) {
    		return 1;
    	}
    	else {
    		return comm(n-1, k) + comm (n-1, k-1);
    	}			
    }
    
    private static void sumOfSubset (
  		  List<Integer> inputSet, int k, List<List<Integer>> results, 
  		  List<Integer> accumulator, int index, int sum) { // index ==0; k>=1
      	
  		  int needToAccumulate = k - accumulator.size();
  		  int canAcculumate = inputSet.size() - index;
  		 
  		  if (accumulator.size() == k) {
  			  int m=0;
  			  for (Integer s: accumulator) {
  				  m = m+ s;
  			  }		  
  			  if (m == sum) 
  				  results.add(new ArrayList<>(accumulator));
  		  } else if (needToAccumulate <= canAcculumate) {// need to fill size must be same or small than available size
  		      sumOfSubset(inputSet, k, results, accumulator, index + 1, sum);
  		      accumulator.add(inputSet.get(index)); // when fill size small than available size, we have to stop going inside
  		      sumOfSubset(inputSet, k, results, accumulator, index + 1, sum); // fill size decreased and try again
  		      accumulator.remove(accumulator.size() - 1); // clear and reuse it
  		  }
     }
    /** from right most back to begin:  XYZWK
     * Keep the order from left to right, but not adjacent
      	ZWK
		YWK
		YZK
		YZW
		XWK
		XZK
		XZW
		XYK
		XYW
		XYZ
		**/
    private static void combinations( // XYZWK--> ZYZ get Z ---> get W --> get K (canAccumulate keep same as needed from Z to K), ZWK
    								  // back to: XY --> get Y --> ZWK --> 			 WK --> get W --> get K, YWK
    								  // back to: XY --> get Y --> ZWK --> get Z --> WK --> get W --> get K, YZK, YZW
    								// 	 back to: X  --> get X --> YZWK--> ZWK   --> WK --> get W --> get K, XWK
    								 //  back to: X  --> get X --> YZWK--> Z --> get Z -->WK --> get W --> get K, XZK, XZW
    								//   back to: X  --> get X --> YZWK--> Y --> get Y -->ZWK--> WK --> get W --> get K, XYK, XYW
    		                        //   back to: X  --> get X --> YZWK--> Y --> get Y -->Z --> get Z, XYZ
		  List<Character> inputSet, int k, List<List<Character>> results, 
		  List<Character> accumulator, int index) {
    	
		  int needToAccumulate = k - accumulator.size();
		  int canAcculumate = inputSet.size() - index;
		 
		  if (accumulator.size() == k) {
		      results.add(new ArrayList<>(accumulator));
		  } // index: 2, get(2) ==Z, index:3, get(3) ==W,  index:4, get(4) == K, accumulator.size == K == 3, print/add
		    // remove from accumulator when back index to 4,3,2 
		    // back to index: 1, get(1) == Y, index: 2 => index:3, get(3) ==W,  index:4, get(4) == K, accumulator.size == K == 3, print/add
		    // remove from accumulator when back index to 4,3,2 
		    // index: 2, get(2) ==Z, index: 3, index: 4, get(4) ==K, print/add (YZK)
		    // remove K and back to index: 3
		  else if (needToAccumulate <= canAcculumate) {// need to fill size must be same or small than available size
		      combinations(inputSet, k, results, accumulator, index + 1);
		      accumulator.add(inputSet.get(index)); 
		      combinations(inputSet, k, results, accumulator, index + 1); // after get first, re-entry as condition changed, canAccumulate also decrease and needed decrease
		      accumulator.remove(accumulator.size() - 1); // clear accumulator
		  }
   }
    
    private static List<List<Character>> combinations2(
  		  List<Character> inputSet, int k, int n) { 
  		
    	List<List<Character>> accumulator1 = combinations2(inputSet, k, n-1);
    	List<List<Character>> accumulator2 =combinations2(inputSet, k-1, n-1);
 
    	return combMerge (accumulator1, accumulator2);
     }
    
    public static List<List<Character>> combMerge (List<List<Character>> a, List<List<Character>> b) {
    	return null;
    }
    // X,Y,Z,W,K   permutation:  List<Character[]> to hold every permutation
    public static void printMe (Character [] c, int start,  List<Character[]> results) {
    	// start == level
    	//Character[]  tempArray;  // =new Character[c.length];
    	if (start == c.length -1) {
    		for (int i=0; i < c.length; i++) {
    			results.add(c);
    			//for (char ch: c)
    			System.out.print(c[i]); 	
	    	}
    		System.out.println();
    	}
    	else { // start: 3, i:3 => print XYZWK;  start: 3, i: 4 =>  switch W and K and print XYZKW, switch K and W back to XYZWK
    		   // start: 2, i:2 => switch Z and Z; start: 2; i:3 => switch Z and W (XYZWK to XYWZK), then do above line 1, print XYWZK, XYWKZ
    		   // start: 2, i:4 => switch Z and K (XYZWK to XYKWZ); start:3, i: 3 do above line 1, print XYKWZ, XYKZW
    		   // start: 1, i:1 => switch Y and Z (XYZWK to XZYWK); start:2, i:2 do above line 2
	    	for (int i=start; i < c.length; i++) { // 0-->1,2,3,4;  1-->2,3,4;  2-->3,4;  3-->4
	    		swap(c, start, i); // begin
	    		//tempArray = Arrays.copyOf(c, 0, Character[].class);  avoid next swap call
	    		//printMe (tempArray, start +1, lchars);
	    		printMe (c, start +1, results);
	    		swap(c, start, i);// must have this to back to begin for next switch: for same start and its every i, its begin c will be same
	    	}
    	}
    }
} 