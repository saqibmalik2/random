package com.saqib.puzzles;

import java.util.Arrays;

/**
 * Optimal solution for squaring a sorted array. Time Complexity: O(n), Space: O(n)
 * Beats 100%.
 *
 * A two pointer methodology that exploits the property that in a sorted array,
 * the largest squared values must come from either end of the array.
 *
 * Key insight: When the input contains negative numbers, the largest absolute values
 * (which produce the largest squares) are at the extremes of the array.
 * Example: [-5,-2,-1,0,1,3,7,8]
 *          - Left end: -5 squared = 25
 *          - Right end: 8 squared = 64
 *          - The largest square (64) comes from the right
 *
 * By comparing absolute values at both ends and always taking the larger one,
 * we can build the result array from right to left (largest to smallest).
 * 
 * EXAMPLE WALKTHROUGH with nums = [-5,-2,-1,0,1,3,7,8]:
 * 
 * Initial: first=0 (at -5), second=7 (at 8)
 * 
 * Iteration 1 (i=7): |8| > |-5| → result[7] = 64, second=6
 * Iteration 2 (i=6): |7| > |-5| → result[6] = 49, second=5
 * Iteration 3 (i=5): |-5| > |3| → result[5] = 25, first=1
 * Iteration 4 (i=4): |3| > |-2| → result[4] = 9, second=4
 * Iteration 5 (i=3): |-2| > |1| → result[3] = 4, first=2
 * Iteration 6 (i=2): |-1| = |1| → result[2] = 1, first=3
 * Iteration 7 (i=1): |1| > |0| → result[1] = 1, second=3
 * Iteration 8 (i=0): |0| (only one left) → result[0] = 0
 * 
 * Result: [0, 1, 1, 4, 9, 25, 49, 64] ✓
 * 
 * WHY THIS WORKS:
 * - Input is sorted, so negative numbers are on the left, positive on the right
 * - Largest absolute values are at the ends (most negative or most positive)
 * - By always picking the larger absolute value, we get squares in descending order
 * - Filling from right to left gives us ascending sorted order in the result
 * 
 */
public class SquaresOfASortedArray {

	public static void main(String[] args) {
		int[] nums = {-5,-2,-1,0,1,3,7,8};
		SquaresOfASortedArray soasa = new SquaresOfASortedArray();
		System.out.println(Arrays.toString(soasa.sort(nums)));
	}

	private int[] sort(int[] nums) {
		// Base case: single element array
		if (nums.length == 1) return new int [] {nums[0] * nums[0]};
		
		// Result array to store squared values in sorted order
		int[] result = new int[nums.length];

		// Two pointers: one at the start (leftmost), one at the end (rightmost)
		// These track the elements we're comparing
		int first = 0, second = nums.length - 1;

		// Fill result array from right to left (largest squared values first)
		// We work backwards because we're finding the LARGEST squares at each step
		for (int i = nums.length - 1; i >= 0; i--) {
			
			// Compare absolute values at both ends
			// The larger absolute value will produce the larger square
			if (Math.abs(nums[second]) > Math.abs(nums[first])) {
				// Right end has larger absolute value
				// Square it and place in current position
				result[i] = nums[second] * nums[second];
				
				// Move right pointer inward (we've used this element)
				second--;
			}
			else {
				// Left end has larger or equal absolute value
				// Square it and place in current position
				result[i] = nums[first] * nums[first];
				
				// Move left pointer inward (we've used this element)
				first++;
			}
		}

		return result;
	}

}
