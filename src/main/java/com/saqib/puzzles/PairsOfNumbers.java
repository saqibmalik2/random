package com.saqib.puzzles;


import java.util.Arrays;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Given an input array of integers and an input integer, k, find the number of 
 * distinct pairs of integers in the array that add up to k.
 * 
 */
public class PairsOfNumbers {
	
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
		
		//create a HashSet from the original array of numbers. 
		//could have done this in a slightly simpler fashion but wanted to use streams
		Integer[] boxedInputArray = Arrays.stream(inputArray).boxed().toArray(Integer[]::new);
		Stream.of(boxedInputArray).forEach(hashSetCopy::add);
		
		
		//only need to iterate once through the array. the hashset look ups and removals are fast - O(1)
		for (Integer i: inputArray) {
			//find the difference between the target total and the current value
			int diff = total - i;
			// if the hashset contains that difference value then we have a matching pair so increase the counter.
			// remove both integers from the Hashset and carry on.
			// NOTE: we are iterating the original input array, not the hashset derived from it, therefore removing items
			// will not trigger a ConcurrentModificationException.
			if (hashSetCopy.contains(diff)) {
				counter++;
				hashSetCopy.remove(i);
				hashSetCopy.remove(diff);
			}
			// if we've removed all the elements from the hashset then no need to iterate anymore as
			// no more pairs will ever be found
			if (hashSetCopy.isEmpty()) break;
		}
		
		return counter;
	}

}
