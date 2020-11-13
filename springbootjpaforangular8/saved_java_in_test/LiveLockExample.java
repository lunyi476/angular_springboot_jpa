package com.springbootjpaangular2.domain;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;;


/**
 * Livelock is another concurrency problem and is similar to deadlock. In livelock, 
 * two or more threads keep on transferring states between one another instead of 
 * waiting infinitely as we saw in the deadlock example. Consequently, 
 * the threads are not able to perform their respective tasks.
 * 
 * 
 * 
 * When set true, under
 * contention, locks favor granting access to the longest-waiting
 * thread.  Otherwise this lock does not guarantee any particular
 * access order.
 *
 *  lock1 acquired, trying to acquire lock2. 
	lock2 acquired, trying to acquire lock1. 
	cannot acquire lock1, releasing lock2. 
	cannot acquire lock2, releasing lock1. 
	lock1 acquired, trying to acquire lock2. 
	lock2 acquired, trying to acquire lock1. 
	cannot acquire lock1, releasing lock2. 
	lock2 acquired, trying to acquire lock1. 
	cannot acquire lock2, releasing lock1. 
	lock1 acquired, trying to acquire lock2. 
	cannot acquire lock2, releasing lock1. 
	lock1 acquired, trying to acquire lock2. 
	cannot acquire lock1, releasing lock2. 
	lock2 acquired, trying to acquire lock1. 
	cannot acquire lock2, releasing lock1. 
	lock1 acquired, trying to acquire lock2. 
 **/
public class LiveLockExample {
	 
    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);
 
    public static void main(String[] args) {
    	// same Object share one Object
        LiveLockExample livelock = new LiveLockExample();
        //  method reference operator, same as Runnable, replaced lambda expression
        new Thread(livelock::operation1, "T1").start();
        new Thread(livelock::operation2, "T2").start();
    }
 
    public void operation1() {
        while (true) {
        	try {
            lock1.tryLock(1000, TimeUnit.MILLISECONDS);
            System.out.print("lock1 acquired, trying to acquire lock2. ");
            System.out.println();
            Thread.sleep(50);
        	} catch (Exception er) {
        		
        	}
 
            if (lock2.tryLock()) { // same Object, lock2 was acquired by operation2
            	System.out.print("lock2 acquired.");
            	System.out.println();
            } else {  // release lock1, so nothing locked, but cannot progress
            	System.out.print("cannot acquire lock2, releasing lock1. ");
            	System.out.println();
                lock1.unlock();
                continue;
            }
 
            System.out.print("executing first operation.");
            System.out.println();
            break;
        }
        lock2.unlock();
        lock1.unlock();
    }
 
    public void operation2() {
        while (true) {
        	try {
	            lock2.tryLock(1000, TimeUnit.MILLISECONDS);
	            System.out.print("lock2 acquired, trying to acquire lock1. ");
	            System.out.println();
	            Thread.sleep(50);
        	} catch (Exception er) {
        		
        	}
 
            if (lock1.tryLock()) {
            	System.out.print("lock1 acquired. ");
            	System.out.println();
            } else {
            	System.out.print("cannot acquire lock1, releasing lock2. ");
            	System.out.println();
                lock2.unlock();
                continue;
            }
 
            System.out.print("executing second operation.");
            System.out.println();
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }
 
    // helper methods
 
}
