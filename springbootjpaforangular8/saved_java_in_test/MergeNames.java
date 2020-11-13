package com.springbootjpaangular2.domain;

import java.util.*;
import java.util.Arrays;

public class MergeNames {
    
    public static String[] uniqueNames(String[] names1, String[] names2) {
    	List<String> ls = new ArrayList<String> ();
    	
    	for (String s: names1) {
    		if (!ls.contains(s)) {
    			ls.add(s);
    		}
    	}
    	
     	for (String s: names2) {
    		if (!ls.contains(s)) {
    			ls.add(s);
    		}
    	}
    	
       return Arrays.copyOf(ls.toArray(), ls.toArray().length, String[].class);
    }
    
    public static void main(String[] args) {
        String[] names1 = new String[] {"Ava", "Emma", "Olivia"};
        String[] names2 = new String[] {"Olivia", "Sophia", "Emma"};
        System.out.println(String.join(", ", MergeNames.uniqueNames(names1, names2))); // should print Ava, Emma, Olivia, Sophia
    }
}
