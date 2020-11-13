package com.springbootjpaangular2.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

class NodeA<T> {
    public T value;
    public NodeA left, right;

    public NodeA () { 
    	System.out.println("SSSS");
    }

    public NodeA(T value, NodeA left, NodeA right) {
    	System.out.println("ttttt");
        this.value = value;
        this.left = left;
        this.right = right;
    }
}

/**
 * The anonymous class expression consists of the following:

The new operator
The name of an interface to implement or a class to extend. In this example, the anonymous class is implementing the interface HelloWorld.
Parentheses that contain the arguments to a constructor, just like a normal class instance creation expression. Note: When you implement an interface, there is no constructor, so you use an empty pair of parentheses, as in this example.
A body, which is a class declaration body. More specifically, in the body, method declarations are allowed but statements are not.
Because an anonymous class definition is an expression, it must be part of a statement
 * @author Lun Yi
 *
 */
interface A {
	void print () ;
}

// Super class constructor will always be called in any case
class NodeAA<E, T> extends NodeA {
	public NodeAA () { //super();  automatically called
		// always provide default constructor if there are another constructor existing
	}
	// You can write a subclass constructor that invokes the constructor of the superclass, 
	// either implicitly or by using the keyword super.
	public NodeAA (E v, T t, NodeA l, NodeA r) {
		//super(t, l, r );  still call default constructor super();
	}
}

public class BinarySearch {
    public static int levelCount =0;
    public static final int SCREENWIDTH = 32;
    // screenwidth =32, level 1, offset = screenWidth/2,  indent1 = screenwidth -offset and indent2 = screenwidth + offset
    static class QInfor {
    	int indent;
    	int level;
    	
    	QInfor (int ind, int level) {
    		this.indent = ind;
    		this.level = level;
    	}
    }
    
	public static void levelScanPrint (Queue<NodeA> q, Queue<QInfor> qi, int level) {
		if (q.isEmpty() ) {
			return;
		}
		else {
			NodeA node = q.poll();
			NodeA nodeNext = null ;
			QInfor qt = qi.poll();
			QInfor qtNext = null;
			for (int i=0; i < qt.level; i++ ) {
				System.out.println();
			}
			
			// need to check all same level NodeA to print out such as: D,E,F,  while (true) { ......  break;}
			qtNext = qi.peek();
			System.out.print(printSpace(qt.indent) + node.value);
			// same level need to print
			if (qtNext != null && qtNext.level == qt.level) {
				qtNext = qi.poll();
				nodeNext = q.poll();
				System.out.print(printSpace(qtNext.indent - qt.indent) + nodeNext.value);
			}
			
			// next level of children
			if (node.left != null) {
				q.offer(node.left);	
				QInfor infor = new QInfor( SCREENWIDTH - SCREENWIDTH/(level+1), level+1);
				qi.offer(infor);
			}
			if (node.right != null) {
				q.offer(node.right);
				QInfor infor = new QInfor(SCREENWIDTH + SCREENWIDTH/(level+1), 1+level);
				qi.offer(infor);
			}
			
			if (nodeNext != null && nodeNext.left != null) {
				q.offer(nodeNext.left);	
				QInfor infor = new QInfor( SCREENWIDTH - SCREENWIDTH/(level+1)+ SCREENWIDTH/(level+1) , level+1);
				qi.offer(infor);
			}
			if (nodeNext != null && nodeNext.right != null ) {
				q.offer(nodeNext.right);
				QInfor infor = new QInfor(SCREENWIDTH + SCREENWIDTH/(level+1)+ SCREENWIDTH/(level+1), 1+level);
				qi.offer(infor);
			}
			
			levelScanPrint(q, qi, level+1);
		}
		
	}
	
    public static <T> boolean  contains  (NodeA root, T value) {
    	if (root != null) {
    		contains (root.left, value);
    		if (root.value == value)
    			return true;
    		
    		if (contains (root.right, value)) return true;
    	}
        
    	return false;
    }
    
    public static void printTree (NodeA node, int level) {
    	if (node == null) {
    		return;
    	}
    	else {
    		printTree (node.right, ++level);
    		System.out.println(printSpace(level) + node.value);
    		printTree (node.left, level);
    		
    		levelCount = levelCount > level? levelCount : level;
    	}
    }
    // duplicate a tree by copying
    public static NodeA copyTree (NodeA src) {
    	
    	NodeA left, right, newNode;
    	if (src == null) {
    		return null;
    	}
    	else {
    		
    		if (src.left != null)
    			left = copyTree (src.left);
    		else
    			left = null;
    		if (src.right != null)
    			right = copyTree (src.right);
    		else
    			right = null;
    		
    		newNode = new NodeA (src.value, left, right);
    		
    		return newNode;
    	}
    }
    
    // duplicate a tree by copying
    public static NodeA deleteTree (NodeA src) {
    	return src = null;
    	/**if (src == null) {
    		return null;
    	}
    	else {
    		deleteTree (src.left);
    		deleteTree (src.right);   	
    		src.left = null;
    		src.right = null;
    		src.value = null;
    		return src = null;
    	}**/
    }
    
    private static String printSpace (int n) {
    	StringBuffer stb = new StringBuffer(" ");
    	
    	for (int i=0; i < n; i++) {
    		stb.append(" ");
    	}
    	
    	return stb.toString();
    }
    
    public static void main(String[] args) {
    	
    	A a = () -> System.out.println("AAA");
    	a.print();
    	
    	Object aa = new NodeAA();
    	/**ExecutorService  execute  =  Executors.newFixedThreadPool(2);
    	String[] sta = {"a","b","c","d"};	
    	List<String> li = new ArrayList<String>(); 	
    	li = new ArrayList<String>(java.util.Arrays.asList(sta));
    	sta = li.toArray(new String[0]);    	
    	li = li.stream().filter( a -> a.contentEquals("a")).collect(Collectors.toList());
    	li = li.stream().sorted( (a, b) -> { if (a.charAt(0) > b.charAt(0)) return -1; else if (b.charAt(0) > a.charAt(0)) return 1; else return 0; }).collect(Collectors.toList());
    	**/
        NodeA n1 = new NodeA(1, null, null);
        NodeA n3 = new NodeA(3, null, null);
        NodeA n2 = new NodeA(2, n1, n3);
        
        Object aaa = new NodeAA(1, n2, n1, n3);
        
        //System.out.println(contains(n2, 3));
        
        NodeA ni = new NodeA("I", null, null);
        NodeA nh = new NodeA("H", null, null);
        NodeA ne = new NodeA("E", nh, ni);
        NodeA ng = new NodeA("G", null, null);
        NodeA nf = new NodeA("F", null, null);
        NodeA nd = new NodeA("D", null, ng);
        NodeA nb = new NodeA("B", nd, null);
        NodeA nc = new NodeA("C", ne, nf);
        NodeA na = new NodeA("A", nb, nc);
        
        Queue q = new LinkedList<NodeA>();
        q.add(na);
        Queue qi = new LinkedList<NodeA>();
        QInfor infor = new QInfor (32, 0);
        qi.add(infor);
        levelScanPrint(q, qi, 1);
        printTree (na.right, 1);
        System.out.println(printSpace(1) + na.value);
        printTree (na.left, 1);
        System.out.println("Level Count: " +levelCount);
        
        NodeA target = null;
        // passing in target is different from inside created new NodeA
        target = copyTree (na);
        printTree (target.right, 1);
        System.out.println(printSpace(1) + target.value);
        printTree (target.left, 1);    
        
        target = deleteTree (target);
        System.out.println(printSpace(1) + target);
    }
}
