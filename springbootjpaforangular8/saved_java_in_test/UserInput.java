package com.springbootjpaangular2.domain;


public class UserInput {
	public static class TextInput {
	    String s = "";
	    public void add (char c) {
	       s = s +String.valueOf(c);
	    }
	    
	    public String getValue() {
	    	return s;
	    }
	}

	public static class NumericInput extends TextInput{	    
		    @Override
		    public void add (char c) {
		       if (Character.isDigit(c))
		    	   s = s +String.valueOf(c);
		    }
	}
		
	public static void main(String[] args) {
	    TextInput input = new NumericInput();
	    input.add('1');
	    input.add('a');
	    input.add('0');
	    System.out.println(input.getValue());
	}
}