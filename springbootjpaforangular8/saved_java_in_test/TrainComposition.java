package com.springbootjpaangular2.domain;

import java.util.LinkedList;

public class TrainComposition {
	LinkedList<Integer> link = new LinkedList<Integer>();
	
    public void attachWagonFromLeft(int wagonId) {
    	link.addLast(wagonId);
        
    }

    public void attachWagonFromRight(int wagonId) {
    	link.addFirst(wagonId);
    }

    public int detachWagonFromLeft() {
    	return link.removeLast();
    }

    public int detachWagonFromRight() {
    	return link.removeFirst();
    }

    public static void main(String[] args) {
        TrainComposition train = new TrainComposition();
        train.attachWagonFromLeft(7);
        train.attachWagonFromLeft(13);
        System.out.println(train.detachWagonFromRight()); // 7 
        System.out.println(train.detachWagonFromLeft()); // 13
    }
}