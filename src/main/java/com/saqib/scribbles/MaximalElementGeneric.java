package com.saqib.scribbles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Date;


/**
 * 
 * A class with two methods for obtaining the maximum element from a list either between two positions in the list or
 * the list as a whole.
 * 
 * @author Saqib Malik
 *
 */
public class MaximalElementGeneric {

	public static void main(String[] args) {
		
		ArrayList<String> listOfString = new ArrayList<String>();
		listOfString.add("Ziyad");
		listOfString.add("Nazneen");
		listOfString.add("Saqib");
		listOfString.add("Niayyir");
		listOfString.add("Sabure");
		listOfString.add("Zara");
		
		ArrayList<Long> listOfNumbers = new ArrayList<Long>();
		listOfNumbers.add(Long.valueOf(50));
		listOfNumbers.add(Long.valueOf(500));
		listOfNumbers.add(Long.valueOf((byte)4));
		listOfNumbers.add(Long.valueOf((byte)8));
		listOfNumbers.add(Long.valueOf(-123));
		listOfNumbers.add(Long.valueOf(9123));
		
				
		System.out.println(new MaximalElementGeneric().findMaximalElement(listOfString, 1, 3));
		System.out.println(new MaximalElementGeneric().findMaximalElement(listOfNumbers, 2, 4));
		System.out.println(new MaximalElementGeneric().findMaximalElement(listOfString));
		System.out.println(new MaximalElementGeneric().findMaximalElement(listOfNumbers));
	}
	
	public <T extends Object & Comparable<? super T>> T findMaximalElement(List<? extends T> inputList) {
		if (inputList.isEmpty() || inputList == null) throw new IllegalArgumentException("Supplied list mustn't be empty or null.");
		
		// start from beginning of list
		int begin = 0;
		// the final position in the list
		int end = inputList.size() - 1;
		
		return findMaximalElement(inputList, begin, end);
	}
	
	public <T extends Object & Comparable<? super T>> T findMaximalElement(List<? extends T> inputList, int startPosition, int endPosition) {
		T maxElement = inputList.get(startPosition);
		
		for (++startPosition; startPosition <= endPosition; startPosition++) {
			if (inputList.get(startPosition).compareTo(maxElement) > 0) maxElement = inputList.get(startPosition);
		}
		
		return maxElement;
	}

}
