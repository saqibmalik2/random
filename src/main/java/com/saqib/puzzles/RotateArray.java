package com.saqib.puzzles;

import java.util.Arrays;

/**
 * 
 * 
 * A zero-indexed array A consisting of N integers is given. 
 * Rotation of the array means that each element is shifted right by one index, and the last element of the array 
 * is also moved to the first place. 
 * 
 * For example, the rotation of array A = [3, 8, 9, 7, 6] is [6, 3, 8, 9, 7]. 
 * The goal is to rotate array A K times; that is, each element of A will be shifted to the right by K indexes.
 * 
 * Write a function: 
 * 
 * 	public int[] rotateArray(int[] A, int K); 
 * 
 * that, given a zero-indexed array A consisting of N integers and an integer K, returns the array A rotated K times. 
 * For example, given array A = [3, 8, 9, 7, 6] and K = 3, the function should return [9, 7, 6, 3, 8]. 
 * Assume that: N and K are integers within the range [0..100]; 
 * each element of array A is an integer within the range [−1,000..1,000]. 
 * In your solution, focus on correctness. The performance of your solution will not be the focus of the assessment.
 * 
 * 
 * @author Saqib
 *
 */

public class RotateArray {

	public static void main(String[] args) {
		int[] A = {1,2,3,4,5,6,7};
		int K = 3;
		RotateArray rotateArray = new RotateArray();
		rotateArray.rotateArray(A, K);
	}
	
	private void rotateArray(int[] nums, int k) {
	    int length = nums.length;
		int[] rotatedArray = new int[length];

		for (int i=0;i<length;i++) {
			int position = i + k > (length - 1) ? (i + k) % length : i + k;
			rotatedArray[position] = nums[i];
		}
		System.arraycopy(rotatedArray, 0, nums, 0, length);
		System.out.println(Arrays.toString(nums));
	}
	
	/**
	 * A more efficient solution: 
	 * 
	 * 		(i) first calculate the effective number of places to rotate by, k. Obtained from the modulo after
	 * 			division by the length of the array.
	 * 		(ii) reverse the entire array
	 * 		(iii) reverse the first portion of the array up to the point k
	 * 		(iv) reverse the second portion of the array starting at k up to the end
	 */
	
	public void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
	

}
