package com.saqib.puzzles;

import java.util.Arrays;

public class ReverseArray {

	public static void main(String[] args) {
		int[] testArray = new int[] {1,1,1,3,3};
		int[] reversedArray = new ReverseArray().reverse(testArray);
		System.out.println(Arrays.toString(reversedArray));
	}
	
	//reverse an array of integers
	private int[] reverse(int[] nums) {
		int start = 0;
		int end = nums.length - 1;
		int temp;
		
		while (start<end) {
			temp = nums[end];
			nums[end] = nums[start];
			nums[start] = temp;
			start++;
			end--;
		}
		return nums;
	}

}
