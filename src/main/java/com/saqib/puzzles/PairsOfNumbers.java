package com.saqib.puzzles;


import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;


public class PairsOfNumbers {
	
	/**
	 * Given an input array of integers and an input integer, k, find the number of 
	 * distinct pairs of integers in the array that add up to k.
	 * 
	 */
	
	//private static final int [] inputArray = {3,3,3,3,2,3,3,3,4,3,3};
	//private static final int [] TEST_ARRAY = {3,5,2,-4,8,11};
	//private static final int total = 6;
	
	public int numberOfPairsNaive(int[] inputArray, int total) {
		if (inputArray.length == 0) return 0;
		int counter = 0;
		SortedSet<Integer> numbers = new TreeSet<>();
		
		//loop through the array
		for (int currentPosition = 0; currentPosition < (inputArray.length - 1) ; currentPosition++) {
			
			for (int i = currentPosition; i < inputArray.length; i++) {
				
				if (numbers.contains(inputArray[currentPosition]) || numbers.contains(inputArray[i])) continue;
				
				if (inputArray[currentPosition] + inputArray[i] == total) {
					counter++;
					numbers.add(inputArray[currentPosition]);
					numbers.add(inputArray[i]);
				}
			}
		
		}
		return counter;
	}
	
	public int numberOfPairsOptimised(int[] inputArray, int total) {
		HashSet<Integer> hashSetCopy = new HashSet<>();
		int counter = 0;
		
		//create a HashSet from the original array of numbers
		for (Integer i: inputArray) {
			hashSetCopy.add(i);
		}
		
		//only need to iterate once through the array. the hashset look ups and removals are fast
		for (Integer i: inputArray) {
			int diff = total - i;
			if (hashSetCopy.contains(diff)) {
				counter++;
				hashSetCopy.remove(i);
				hashSetCopy.remove(diff);
			}
		}
		
		return counter;
	}

}
