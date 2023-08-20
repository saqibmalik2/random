package com.saqib.puzzles;

import java.util.Collection;
import java.util.HashMap;

/**
 * A codility test question. Given an input array return the number of pairs of indices such that the values they point to 
 * are equal numbers. Additionally the index of the first number should is less than the index of the second.
 * So for example given the array {3,5,6,3,3,5} the result should be 4 as we would have index pairs: (0,3), (0,4), (3,4) and (1,5).
 * Note that the pairs (5,1), (4,0) etc. are not counted as the second index is less than the first.
 *
 * 
 * @author Saqib Malik (2018)
 *
 */

public class PairsOfArrays {

	public static void main(String[] args) {
		//int[] input = {0,0,0,1,1,1};
		int[] input = {3,5,6,3,3,5,6,3,6};
		System.out.println(calculateNoOfPairs(input));
	}
	
	public static int calculateNoOfPairs(int[] A) {
		if (A.length < 2) {
			return 0;
		}
		HashMap<Integer, Integer> appearanceCount = new HashMap<>();
		
		/* first of all iterate through the array and build up a hashmap for each integer value. Key is the integer and the value
		   will be the number of times that value is found in the array */
		for (int i=0;i<A.length;i++) {
			appearanceCount.merge(A[i], 1, Integer::sum);
		}
		
		Collection<Integer> values = appearanceCount.values();
		int count = 0;
		
		//if you work through some examples on paper you'll see that this is correct
		for (Integer val:values) {
			count = count + sumOfNumbers(val-1);
		}
		
		return count;
	}
	
	//sum of the first n natural numbers
	public static int sumOfNumbers(int n) {
		return (n*(n+1))/2;
	}

}
