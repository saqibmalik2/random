package com.saqib.puzzles;

import java.util.Arrays;


/**
 * 
 * NOTE: This solution works and is accepted by LeetCode but isn't the optimal one.
 * 
 */

public class FirstMissingPositive {

	public static void main(String[] args) {
		FirstMissingPositive firstMissingPositive = new FirstMissingPositive();
		int lowestMissingInteger;
		//lowestMissingInteger = firstMissingPositive.firstMissingPositive(new int[] {2,1});
		//lowestMissingInteger = firstMissingPositive.firstMissingPositive(new int[] {1,2,0});
		lowestMissingInteger = firstMissingPositive.firstMissingPositive(new int[] {7,8,9,11,12});
		System.out.println(lowestMissingInteger);
	}
	
	public int firstMissingPositive(int[] nums) {
		Arrays.sort(nums);
		int lowestMissing = 1;
		
		lowestMissing = (nums[0] == 1) ? 2:1;
		
		for (int i=1;i<nums.length;i++) {
			if (nums[i] <= 0) {
				continue;
			}
			if (nums[i] == lowestMissing) lowestMissing++;
		}
		
		
		return lowestMissing;
	}

}
