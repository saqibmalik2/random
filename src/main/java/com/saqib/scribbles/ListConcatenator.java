package com.saqib.scribbles;

import java.util.List;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;

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
		
		// alternative method which doesn't use flatMap
		//List<E> concatenatedList = new ArrayList<>();
		//lists.stream().forEach(concatenatedList::addAll);
		
		return concatenatedList;
	}

}
