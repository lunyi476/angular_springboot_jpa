package com.springbootjpaangular2.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.*;
import org.junit.Assert;
import org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;


public class TestThread {  // deadlock example solution
	   public static Object Lock1 = new Object();
	   public static Object Lock2 = new Object();
	   
	   // two thread share one same Object
	   public static void main(String args[]) {
		   
	   try {	
	    givenHashMap_whenSumParallel_thenError();
	    givenConcurrentMap_whenSumParallel_thenCorrect();
	   } catch (Exception er ) {
		   
	   }
	    
	      ThreadDemo1 T1 = new ThreadDemo1();
	      // hash table index
	      int t = T1.getClass().hashCode() % 120;
	      ThreadDemo2 T2 = new ThreadDemo2();
	   // hash table index
	      int t2 = T2.getClass().hashCode() % 120;
	      T1.start();
	      T2.start();
	      
	
	    Map<Integer, String> 
            map1 = new ConcurrentHashMap<Integer, String>(); 
        map1.put(1, "L"); 
        map1.put(2, "M"); 
        map1.put(3, "N"); 
  
        HashMap<Integer, String> 
            map2 = new HashMap<>(); 
        map2.put(1, "B"); 
        map2.put(2, "G"); 
        map2.put(3, "R"); 
  
        // print map details 
        System.out.println("HashMap1: "
                           + map1.toString()); 
  
        System.out.println("HashMap2: "
                           + map2.toString()); 
  
        //public synchronized V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) 
	    // R apply(T t, U u);
        // provide value for new key which is absent 
        // using computeIfAbsent method 
        map2.forEach( 
            (key, value) // 1--B
                -> map1.merge( 
                    key, //same key 1
                    value, // same value: B
                    (v1, v2) // two values, B, L
                        -> v1.equalsIgnoreCase(v2) 
                               ? v1 
                               : v1 + ", " + v2)); 
  
        // print new mapping 
        /**HashMap1: {1=L, 2=M, 3=N}
			HashMap2: {1=B, 2=G, 3=R}
			New HashMap: {1=L, B, 2=M, G, 3=N, R}
			**/
        System.out.println("New HashMap: " + map1); 
    } 

	   
	   static String[] stringArray = { "Barbara", "James", "Mary", "John",
			    "Patricia", "Robert", "Michael", "Linda" };
	   static 
	   {
		   //  Comparator<? super String>  Functional Interface:  int compare(T o1, T o2);
		   //  public int compareToIgnoreCase(String str)
			Arrays.sort(stringArray, String::compareToIgnoreCase);
	   }
			
	   private static class ThreadDemo1 extends Thread {
	      public void run() {
	    	 
	         synchronized (Lock1) {
	            System.out.println("Thread 1: Holding lock 1...");
	            
	            try {
	               Thread.sleep(10);
	            } catch (InterruptedException e) {}
	            System.out.println("Thread 1: Waiting for lock 2...");
	            
	            synchronized (Lock2) {
	               System.out.println("Thread 1: Holding lock 1 & 2...");
	            }
	         }
	      }
	   }
	   
	   private static class ThreadDemo2 extends Thread {
	      public void run() {
	         synchronized (Lock1) {  
	            System.out.println("Thread 2: Holding lock 1...");
	           
	            try {
	               Thread.sleep(10);
	            } catch (InterruptedException e) {}
	            System.out.println("Thread 2: Waiting for lock 2...");
	            
	            synchronized (Lock2) { // lock1
	               System.out.println("Thread 2: Holding lock 1 & 2...");
	            }
	         }
	      }
	   }
	   
	   
	   //@Test
	   public static void givenHashMap_whenSumParallel_thenError() throws Exception {
	       Map<String, Integer> map = new HashMap<>();
	       List<Integer> sumList = parallelSum100(map, 100);
	       // total 100:
	       //[100, 92, 74, 100, 97, 83, 87, 89, 85, 100, 92, 94, 97, 87, 83, 85, 98, 100, 83, 89, 91, 100, 73, 100, 90, 100, 82, 100, 80, 79, 87, 100, 100, 100, 100, 100, 100, 87, 100, 91, 91, 100, 100, 
	       Assert.assertNotEquals(1, sumList
	         .stream()
	         .distinct()
	         .count());
	       long wrongResultCount = sumList
	         .stream()
	         .filter(num -> num != 100)
	         .count();
	       
	       assertTrue(wrongResultCount > 0);
	   }
	   
	   public static void givenConcurrentMap_whenSumParallel_thenCorrect() 
			   throws Exception {
			     Map<String, Integer> map = new ConcurrentHashMap<>();
			     List<Integer> sumList = parallelSum100(map, 1000);
			     // Total 100
			     // [100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 10
			     assertEquals(1, sumList
			       .stream()
			       .distinct()
			       .count());
			     long wrongResultCount = sumList
			       .stream()
			       .filter(num -> num != 100)
			       .count();
			     
			     assertEquals(0, wrongResultCount);
	 }

	    
	   private static List<Integer> parallelSum100(Map<String, Integer> map, 
	     int executionTimes) throws InterruptedException {
	       List<Integer> sumList = new ArrayList<>(1000);
	       for (int i = 0; i < executionTimes; i++) { // total 100 map and every: "Test"/100
	           map.put("test", 0);
	           ExecutorService executorService = 
	             Executors.newFixedThreadPool(4);
	           for (int j = 0; j < 10; j++) {
	               executorService.execute(() -> {
	                   for (int k = 0; k < 10; k++)  // there 4 threads in 10*10 loops to calculate and put in same map
	                       map.computeIfPresent(
	                         "test", 
	                         (key, value) -> value + 1
	                       );
	               });
	           }
	           executorService.shutdown();
	           executorService.awaitTermination(5, TimeUnit.SECONDS);
	           // for every "Test" key should get 100
	           sumList.add(map.get("test"));
	       }
	       return sumList;
	   }

	}

class DeadlockExample2 {
	 
    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);
 
    /**
     * Sometimes, however, a lambda expression does nothing but call an existing method. 
     * In those cases, it's often clearer to refer to the existing method by name. 
     * 
     * Method references enable you to do this; they are compact, 
     * easy-to-read lambda expressions for methods that already have a name.
     * @param args
     */
    public static void main(String[] args) {

    	// same Object in two threads
        DeadlockExample2 deadlock = new DeadlockExample2();
        //  (1) Runnable (public abstract void run(); matched public void operation1() ) is Functional Interface 
        // 		( any function (no matter name but abstract function signature) meet this interface could be used), so we can use LambDa expression
         // (2) LambDa: Because this lambda expression invokes an existing method, you can use a method reference instead of a lambda expression:
    	//  (3) deadlock::operation1
        //  (4) deadlock.operation1()  not working because it calling function and return void instead of Runnable
        new Thread(()-> { 
        	deadlock.lock1.lock();
             System.out.print("lock1 acquired, waiting to acquire lock2.");
             
             try {
             	Thread.sleep(50);
             } catch (InterruptedException er) {
             	
             }
      
             deadlock.lock2.lock();
             System.out.print("lock2 acquired");
      
             System.out.print("executing first operation.");
      
             deadlock.lock2.unlock();
             deadlock.lock1.unlock();
        }, "T1").start();
        // Method reference: 
        new Thread(deadlock::operation2, "T2").start();
    }
 
    public void operation1() {
        lock1.lock();
        System.out.print("lock1 acquired, waiting to acquire lock2.");
        
        try {
        	Thread.sleep(50);
        } catch (InterruptedException er) {
        	
        }
 
        lock2.lock(); // was holden by operation2
        System.out.print("lock2 acquired");
 
        System.out.print("executing first operation.");
 
        lock2.unlock();
        lock1.unlock();
    }
 
    public void operation2() {
        lock2.lock();
        System.out.print("lock2 acquired, waiting to acquire lock1.");
        try {
        	Thread.sleep(50);
        } catch (InterruptedException er) {
        	
        }
 
        lock1.lock(); // was holden by operation1
        System.out.print("lock1 acquired");
 
        System.out.print("executing second operation.");
 
        lock1.unlock();
        lock2.unlock();
    }
 
    // helper methods
 
}