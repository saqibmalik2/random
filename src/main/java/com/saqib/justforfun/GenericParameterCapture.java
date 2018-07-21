package com.saqib.justforfun;

import java.util.ArrayList;
import java.util.List;

public class GenericParameterCapture {
	
	
	public static void main(String[] args) {
		GenericParameterCapture gpc = new GenericParameterCapture();
		String aString = new String("a string");
		Integer anInteger = Integer.valueOf(2);
		List<String> aList = new ArrayList<String>();
		System.out.println(gpc.returnClassOfT(aString));
		System.out.println(gpc.returnClassOfT(anInteger));
		System.out.println(gpc.returnClassOfT(aList));
		
		Integer[] intArray = {Integer.valueOf(1)};
		gpc.printContents(intArray);
	}
	
	public  <T> Class<T> returnClassOfT(T input){
		return (Class<T>) input.getClass();
	}
	
	public <T> void printContents(T...input) {
		Object[] objectArray = input;
		Integer s = (Integer) input[0];
		System.out.println(s);
	}

}
