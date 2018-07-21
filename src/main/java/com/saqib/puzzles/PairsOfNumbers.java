package com.saqib.puzzles;

import java.util.SortedSet;
import java.util.TreeSet;

public class PairsOfNumbers {
	
	/**
	 * Given an input array of integers and an input integer, k, find the number of 
	 * distinct pairs of integers in the array that add up to k.
	 * 
	 */
	
	private static final int [] TEST_ARRAY = {3,3,3,3,2,3,3,3,4,3,3};
	private static final int TOTAL_TARGET = 6;
	
	private static int numberOfPairs() {
		if (TEST_ARRAY.length == 0) return 0;
		int counter = 0;
		SortedSet<Integer> numbers = new TreeSet<>();
		for (int startPosition = 0; startPosition < (TEST_ARRAY.length - 1) ; startPosition++) {
			for (int i = startPosition; i < TEST_ARRAY.length; i++) {
				if (numbers.contains(TEST_ARRAY[startPosition]) || numbers.contains(TEST_ARRAY[i])) continue;
				if (TEST_ARRAY[startPosition] + TEST_ARRAY[i] == TOTAL_TARGET) {
					counter++;
					numbers.add(TEST_ARRAY[startPosition]);
					numbers.add(TEST_ARRAY[i]);
				}
			}
		}
		return counter;
	}
	
	public static void main(String[] args) {
		System.out.println(numberOfPairs());
	}

}
