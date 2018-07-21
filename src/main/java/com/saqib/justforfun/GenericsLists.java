package com.saqib.justforfun;

import java.util.ArrayList;
import java.util.List;

public class GenericsLists {
	
	static List<Object> listOfObjects = new ArrayList<>();
	static List<String> listOfStrings = new ArrayList<>();
	
	static {
		listOfObjects.add("Hello");
		listOfObjects.add("World!");
		listOfObjects.add(Long.valueOf(1000l));
		
		listOfStrings.add("Hello");
		listOfStrings.add("World!");
		listOfStrings.add("Saqib");
	}

	public static void main(String[] args) {
		printList(listOfStrings);	
	}
	
	public static void printList(List<?> list) {
	    for (Object elem : list) {
	        System.out.println(elem + " ");
	    }
	}
	
}
