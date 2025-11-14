package com.saqib.scribbles;

import java.util.List;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;

import static java.util.stream.Collectors.*;



public class ListConcatenator {

	public static void main(String[] args) {
		ListConcatenator listConcatenator = new ListConcatenator();
		List<String> stringList1 = new ArrayList<>();
		stringList1.add("one");
		stringList1.add("two");
		stringList1.add("three");
		stringList1.add("four");
		
		List<String> stringList2 = new ArrayList<>();
		stringList2.add("five");
		stringList2.add("six");
		stringList2.add("seven");
		stringList2.add("eight");
		
		List<String> stringList3 = new ArrayList<>();
		stringList3.add("nine");
		stringList3.add("ten");

		List<List<String>> listOfListOfStrings = new ArrayList<>();
		
		listOfListOfStrings.add(stringList1);
		listOfListOfStrings.add(stringList2);
		listOfListOfStrings.add(stringList3);
		
		System.out.println(listConcatenator.concatenateLists(listOfListOfStrings));
	}
	
	public <E> List<E> concatenateLists(List<List<E>> lists){
		List<E> concatenatedList = lists.stream().flatMap(List::stream).collect(toList());
		
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
	    List<Integer> numbersSquared = numbers.stream().map(x -> x * x).collect(toList());
	    
	    System.out.println(numbersSquared);
	    
	 
	    
	    List<String> list = Arrays.asList("Geeks", "for", "gfg", 
                "GeeksforGeeks", "GeeksQuiz"); 
	    list.stream().map(str -> str.length()).forEach(System.out::println); 
	    
	    List<String> list1 = Arrays.asList("3", "6", "8",  
                "14", "15"); 


		list1.stream().map(num -> Integer.parseInt(num)) 
		.filter(num -> num % 3 == 0) 
		.forEach(System.out::println); 
		
		// alternative method which doesn't use flatMap
		//List<E> concatenatedList = new ArrayList<>();
		//lists.stream().forEach(concatenatedList::addAll);
		System.out.println(null == null);
		return concatenatedList;
	}

}
