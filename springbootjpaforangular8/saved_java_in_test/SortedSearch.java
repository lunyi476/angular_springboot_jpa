package com.springbootjpaangular2.domain;

public class SortedSearch {
    public static int countNumbers(int[] sa, int lt) {
    	int count =0;
    	
    	for (int i=0; i <sa.length; i++) {
    		if (sa[i] < lt) {
    			count++;
    		}
    	}

    	return count;
    }
    
    public static void main(String[] args) {
        System.out.println(SortedSearch.countNumbers(new int[] { 1, 3, 5, 7 }, 4));
    }
}