package com.springbootjpaangular2.domain;

import java.util.*;
import java.lang.Cloneable;
import java.lang.Thread; //Object;
import java.util.concurrent.Executors;

public class RoutePlanner {
    
	private int yu =0;
	
	class Test 
	{ 
	    int x, y; 
	} 
	  
	  
	// Contains a reference of Test and implements 
	// clone with deep copy. 
	// static will means this class is Top level class and cannot directly access enclosing class member
	// actually, there is no enclosing class for static nested class.
	// without static, this class is inner class, which has enclosing class RoutePlanner
	static class Test2 implements Cloneable 
	{ 
	    int b =0;
	    int u =1;

	    int a = new RoutePlanner().yu;
	  
	    Test c = new RoutePlanner().new Test();  //new Test();
	    // Override clone of Object
	    public Object clone() throws
	                CloneNotSupportedException
	    { 
	        // Assign the shallow copy to new reference variable t 
	        Test2 t = (Test2)super.clone(); // super is Object which has native clone method
	  
	        t.c = new RoutePlanner().new Test();  //  new Test();
	  
	        // Create a new object for the field c 
	        // and assign it to shallow copy obtained, 
	        // to make it a deep copy 
	        return t; 
	    } 
	} 
	
    public static boolean routeExists2(int fr, int fc, int tr, int tc, 
            boolean[][] Mt, boolean path[], int idx) {

    	//path[idx] = Mt[fr][fc]; 
    	if (fr > Mt.length -1 || fc > Mt[0].length-1 || fr <0 || fc < 0) return false;
    	if (Mt[fr][fc] == false) return false;
    	if (fr == tr && fc == tc) return true;
    	
    	// column travel
    	if (fc+1 <= Mt[0].length ) {
    		if (routeExists2(fr,  fc+1, tr, tc,Mt,path, idx))
    			return true;
    	}
    	/// row travel
    	if (fr+1 <= Mt.length ) {
    		if( routeExists2(fr+1,  fc, tr, tc,Mt,path, idx))
    			return true;
    	}
    	/**
    	if (fc-1 >=0  ) {		
    		if (routeExists(fr+1,  fc-1, tr, tc,Mt,path, idx))
     		return true;
        }
    	
     	if (fr-1 >=0  ) {		
    		if (routeExists(fr-1,  fc, tr, tc,Mt,path, idx))
     		return true;
        }**/
         
         
        return false;
	}
     
    
    public static boolean routeExists(int start, int end, 
            List<Boolean> Mt, List<Boolean> results, int currentInd) {
    	
    	int i, j;
    	
    	if (Mt.size() -1 == currentInd) {
    		for (int k= start;  k <=end; k++) {
    			results.add(Mt.get(k));
    			if (Mt.get(k) == false) {
    				System.out.println(results.toString());
    				results.clear();
    				return false;
    			}
    		}
    		System.out.println(results.toString());
    		return true;
    	}
    	else {// permutation
    		for (i = currentInd; i < Mt.size(); i++) {
    			swap (Mt, currentInd, i);
    			if (routeExists(start, end, Mt, results,  i+1)) {
    				return true;
    			}
    			swap (Mt, currentInd, i);
    		}
    	}
          
        return false;
	}
    
    public static <T> void  swap (List<T> c, int i, int j) {
    	T t = c.get(i);
    	c.set(i, c.get(j));
    	c.set(j, t);
    	//Object[] a = new Object[10];
    	//Object[] b = {'a','b','c'};
    	//System.arraycopy(b, 0, a, 0, 3);
    }
    
 	static class Position {
		int x;
		int y;
		
		Position (int x, int y) {
			this.x =x;
			this.y = y;
		}
		
		public boolean equals ( Position e) {
			
			if (e == null ) return false;
			if (this.x == e.x  && this.y == e.y) return true;
			
			return false;
		}
	}
	
    // get all true index (i, j) and put in array/List, then search array to find possible path based on i, j
    // for example, same row and adjacent column or same column and adjacent row
    public static Boolean findPath (Boolean[][] path, int x, int y, int fx, int fy ) {
    	   
    	List<Position> pathHolder = new ArrayList<Position>();
    	
    	for (int i=0; i < path.length; i++) {
    		for (int j=0; j < path[i].length; j++) {
    			if (path[i][j] == true) {
    				pathHolder.add(new Position(i, j));
    			}
    		}
    	}
    	List<Position> visited = new ArrayList<Position>();
    	List<Position> realPath = new ArrayList<Position>();
    	Position start = new Position(x,y);
    	return searchPath (pathHolder, start, visited, realPath, fx, fy, path.length, path[0].length );	
    }
    
    private static Boolean searchPath (List<Position> holder, Position start,
    		List<Position> visited, List<Position> realPath, int fx, int fy, int m, int n ) {
    	
    	for (Position pos : holder) {	
    		if (pos.equals(start)) {
    			if (traverseNode(holder, start, visited, realPath, fx, fy, m, n))
    				return true;
    			else 
    				return false;
    		}
    	}
    	return false;
    }
    
    public static Boolean traverseNode(List<Position> holder, Position start, 
    		List<Position> visited, List<Position> realPath, int fx, int fy, int m, int n ) {
    	LinkedList<Position> nodeToExplore = new LinkedList<Position>();
    	nodeToExplore.addLast(start);
    	
    	while (!nodeToExplore.isEmpty()) {
    		Position temp = nodeToExplore.pollFirst();
    		if (temp.x == fx && temp.y ==fy) {
    			// get realPath from visited
    			/** to do , put path in two binary tree (right/upper and left/down) after complete to print path **/
    			return true;
    		}
    		
    		if (!visited.contains(temp)) {
    			visited.add(temp);
    			LinkedList<Position> nabors =  getUnvisitedNabors (holder, temp, m, n);
    			
    			for (Position s: nabors) {
    				if (!visited.contains(s)) {
    					nodeToExplore.addLast(s);
    				}
    			}
    		}
    	}
    	
    	return false;
    }
    
    private static LinkedList<Position> getUnvisitedNabors (
    		List<Position> holder, Position p, int m, int n) {
    	
    	LinkedList<Position> ll = new LinkedList<Position>();
    	
    	int x = p.x;
    	int y = p.y;
    	
    	if (x < m  && y < n) {
    		if (findNode(holder, new Position(x, y)) != null) { 			
    			// right
    			if (y+1 < n) {
    			 Position tp = findNode (holder, new Position(x, y+1));
    			 if (tp !=null) {
    				 ll.addLast(tp);
    			 }
    			}
    			// upper
    			if (x-1 >0) {
       			 Position tp = findNode (holder, new Position(x-1, y));
       			 if (tp !=null) {
       				 ll.addLast(tp);
       			 }
       			}
    			
    			// down 
    			if (x+1 < m) {
          			 Position tp = findNode (holder, new Position(x+1, y));
          			 if (tp !=null) {
          				 ll.addLast(tp);
          			 }
          			}
    			
    			// left
    			if (y-1 >0) {
          			 Position tp = findNode (holder, new Position(x, y-1));
          			 if (tp !=null) {
          				 ll.addLast(tp);
          			 }
          		}
    		}
    	}
    	
    	return ll;
    }
    
    private static Position findNode (List<Position> holder, Position p) {
    	
    	for (Position i: holder) {
    		if (i.equals(p))
    			return i;
    	}
    	return null;
    }
    
    public static void main(String[] args) {
        Boolean[][] mapMatrix = {
            {true,  true, true, true},
            {true,  false,  false, true},
            {true, false,  true, false},
            {true, true,  true, false}
        };
        
        // not true or false, but cannot store realPath
        System.out.println (findPath (mapMatrix, 0, 0,  2,2 ));
        
        Boolean[] orig = new Boolean[mapMatrix[0].length*mapMatrix.length];
        List<Boolean> origL = new ArrayList<Boolean>();
        List<Boolean> results = new ArrayList<Boolean>();
        
        for (int i =0; i < mapMatrix.length; i++) {
        	for (int j =0; j < mapMatrix[0].length; j++)
        		origL.add(mapMatrix[i][j]);
        }
        
        // row and column start count at 0
        int fr =0, fc =0, tr=2, tc=2;
        int start, end;
        if (fr ==0 && fc ==0)  
        	start = 0;
        else start = (fr)*mapMatrix[0].length + fc+1;
        
        if (tr ==0 && tc ==0)  
        	end = 0;
        else end = (tr)*mapMatrix[0].length + tc+1;
        
        // always start at first for flat array or list
        routeExists(start, end, origL, results,  0);
        
        int maxLengthOfPath = mapMatrix.length +  mapMatrix[0].length - 1; 
        //System.out.println(routeExists2(0, 0, 2, 2,  
        		//mapMatrix, new boolean[maxLengthOfPath], 0));
        
        //System.out.println(routeExists2(mapMatrix.length-1, mapMatrix[0].length-1, 2, 2, mapMatrix.length, mapMatrix[0].length, 
        		// mapMatrix, new boolean[maxLengthOfPath], 0));
    }
}


