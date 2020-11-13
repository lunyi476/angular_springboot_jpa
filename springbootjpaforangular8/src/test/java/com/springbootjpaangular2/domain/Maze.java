package com.springbootjpaangular2.domain;

public class Maze {
	// total intersections
	int mazeSize;
	// reached maze exit
	final int EXIT;
	Intersection[] intersections;
	
	Maze (int mazeSize, int exit) {
		this.mazeSize = mazeSize;
		this.EXIT = exit;
		this.intersections = new Intersection[mazeSize];
	}
	

	// 1,2,3,4,5,6,7,  0 means deadend
	class Intersection {
		public int left;
		public int right;
		public int forward;
		
		Intersection ( int l, int r, int f) {
			this.left =l;
			this.right = r;
			this.forward = f;
		}
	}
	
	public static int traverseMaze (Intersection[] insec, int current, int previous, int exit) {
		
		
		// base case, reached exit
		if (current == exit) return 7;
		// should not happen
		if (current < 0  ) return -1;
		// dead end
		if (insec[current].left ==0 && insec[current].right==0 && insec[current].forward ==0) {
			return -1;
		}
		
		System.out.println(current + "  "+ insec[current].left + "  "+ 
				insec[current].right +"  "+ insec[current].forward);
		// backtrack from dead end
		if ( previous != insec[current].left && -1 == traverseMaze (insec, insec[current].left, current, exit)) {
			if (previous != insec[current].right && -1== traverseMaze (insec, insec[current].right, current, exit)) {
				if (previous != insec[current].forward && -1== traverseMaze (insec, insec[current].forward, current, exit)) {
					return -1;
				}
				return -1;
			}		
			return -1;
		}
			
		return 7;	
	}
	
	public static void main (String[] args) {
		Maze mz = new Maze(7, 7);
		mz.intersections[0] = mz.new Intersection (-1,-1,1);
		mz.intersections[1] = mz.new Intersection (2,5,4);
		mz.intersections[2] = mz.new Intersection (-1,-1,3);
		mz.intersections[3] = mz.new Intersection (-1,-1,-1);
		mz.intersections[4] = mz.new Intersection (-1,-1,-1);
		mz.intersections[5] = mz.new Intersection (1,-1,6);
		mz.intersections[6] = mz.new Intersection (-1,-1,6);
		
		if ( 7 == traverseMaze (mz.intersections, 0, 0, 7)) {
			System.out.println("EXITE Succeed!");
		};
	}

}
