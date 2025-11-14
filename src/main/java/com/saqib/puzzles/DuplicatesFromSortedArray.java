package com.saqib.puzzles;

import java.util.Arrays;

public class DuplicatesFromSortedArray {

	public static void main(String[] args) {
		int[] input = new int[] {0,0,0,0,1,1,1,1,2,3,3};
		//int[] input = new int[] {1,2,2};
		DuplicatesFromSortedArray dfsa = new DuplicatesFromSortedArray();
		int k = dfsa.removeDuplicates(input);
		System.out.println(k);
	}
	
	private int removeDuplicates(int[] nums) {
	        if (nums.length < 3) return nums.length;

	        int count = 0, k = 1;

	        for (int i = 1; i < nums.length; i++) {
	            if (nums[i] != nums[i - 1]) {
	                count = 0;
	                nums[k] = nums[i];
	                k++;
	            } else {
	                count++;
	                if (count < 2) {
	                    nums[k] = nums[i];
	                    k++;
	                }
	            }
	        }

	        return k;
	   }
	
/**	
	private int removeDuplicates(int[] nums) {
		int previous = nums[0];
		int count = 1;
		int replacementPointer = 1;
		boolean replacementRequired = false;
		int k = 1;
		
		if (nums.length < 3) return nums.length;
		
		for (int i=1;i<nums.length;i++) {
			if (nums[i] == previous) {
				count++;
				if (count < 3) k++;
				if (count == 3 && replacementRequired == Boolean.FALSE) {
					replacementPointer = i;
					replacementRequired = Boolean.TRUE;
				}
				if (replacementPointer != 1 && count < 3) {
					nums[replacementPointer] = nums[i];
					replacementPointer++;
				}
			}
			else {
				previous = nums[i];
				count = 1;
				k++;
				if (replacementPointer != 1) {
					nums[replacementPointer] = nums[i];
					replacementPointer++;
				}
				
			}
		}
		System.out.println(Arrays.toString(nums));
		return k;
	}
**/
	
}
