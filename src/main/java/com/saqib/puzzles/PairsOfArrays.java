package com.saqib.puzzles;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A codility test question. Given an input array return the number of pairs of indices such that the values they point to 
 * are equal numbers. Additionally the index of the first number should is less than the index of the second.
 * So for example give the array {3,5,6,3,3,5} the result should be 4 as we would have index pairs: (0,3), (0,4), (3,4) and (1,5).
 * Note that the pairs (5,1), (4,0) etc. are not counted as the second index is larger than the first.
 *
 * 
 * @author Saqib Malik (2018)
 *
 */

public class PairsOfArrays {

	public static void main(String[] args) {
		int[] input = {3,5,6,3,3,5};
		System.out.println(calculateNoOfPairs(input));
	}
	
	public static int calculateNoOfPairs(int[] input) {
		HashMap<Integer, ArrayList<Integer>> indexPositions = new HashMap<>();
		
		/* first of all iterate through the array and build up a hashmap for each integer value. Key is the integer and the value
		   will be an arraylist of all the index position at which this integer can be found in the array. */
		for (int i=0; i< input.length; i++) {
			if (indexPositions.containsKey(input[i])){
				indexPositions.get(input[i]).add(i);
			}
			else {
				ArrayList<Integer> listOfIndexPositions = new ArrayList<>();
				listOfIndexPositions.add(i);
				indexPositions.put(input[i], listOfIndexPositions);
			}
		}
		
		int count = 0;
		
		for (Integer integerValue: indexPositions.keySet()) {
			ArrayList<Integer> alist = indexPositions.get(integerValue);
			count += sumOfNumbers(alist.size() - 1);
		}
		
		return (count <= 1000000) ? count : 1000000;
	}
	
	//sum of the first n natural numbers
	public static int sumOfNumbers(int n) {
		return (n*(n+1))/2;
	}

}
