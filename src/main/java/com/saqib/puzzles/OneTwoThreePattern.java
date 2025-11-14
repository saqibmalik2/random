package com.saqib.puzzles;

import java.util.Objects;

public class OneTwoThreePattern {

	public static void main(String[] args) {
		OneTwoThreePattern oneTwoThreePattern = new OneTwoThreePattern();
		//int[] nums = new int[] {1,2,3,4};
		//int[] nums = new int[] {3,5,0,3,4};
        //int[] nums = new int[] {4,9,10,7,8,5,0,3,4};
		//int[] nums = new int[] {-1,3,2,0};
		//int[] nums = new int[] {1,0,1,-4,-3};
		//int[] nums = new int[] {1,3,2,4,5,6};
		//int[] nums = new int[] {-2,1,1,-2,1,1};
		//int[] nums = new int[] {3,2,4,1};
		int[] nums = new int[] {10,12,6,8,3,11};
		//int[] nums = new int[] {26,26,16,16,36,36,36,36,16,16,16,36,36,36,36,10,10,10,80,80,80,80,10,10,10,10,6,6,6,6,6};
		System.out.println(oneTwoThreePattern.find132pattern(nums));
	}
	


	 public static boolean find132pattern(int[] nums) {
	        int n = nums.length;
	        if (Objects.isNull(nums) || n < 3) return false;
	        int second = Integer.MIN_VALUE;
	        int k = n;
	        for(int i = n - 1 ; i >= 0 ; i--){
	            if(nums[i] < second) {
	            	return true;
	            }
	            while (k < n && nums[i] > nums[k]) {
	            	second = nums[k++];
	            }
	            nums[--k] = nums[i];
	        }
	        return false;
	 }
	
	
//	public boolean find132pattern(int[] nums) {
//        if (nums == null || nums.length < 3) {
//            return false;
//        }
//        
//        int depth=0;
//        int[] min = new int[nums.length];
//        min[0] = nums[nums.length - 1];
//        
//        for (int i=(nums.length - 1);i > 0;i--) {
//        	if (nums[i-1] > nums[i]) {
//        		depth++;
//        		min[depth] = nums[i-1];
//        	}
//        	if (depth > 0 && nums[i-1] < min[depth-1] && min[depth] > min[depth-1]) return true;
//        }
//        
//        return false; // No 132 pattern found
//    }
//	
//	public boolean find132pattern(int[] nums) {
//  if (nums == null || nums.length < 3) {
//      return false;
//  }
//
//  Stack<Integer> stack = new Stack<>();
//  int s3 = Integer.MIN_VALUE; // s3 will store the potential nums[k]
//
//  // Traverse the array from the end to the start
//  for (int i = nums.length - 1; i >= 0; i--) {
//      if (nums[i] < s3) {
//          return true; // We found a valid 132 pattern
//      }
//
//      // While the current number is greater than the top of the stack,
//      // update s3 to be the top of the stack (potential nums[j])
//      while (!stack.isEmpty() && nums[i] > stack.peek()) {
//          s3 = stack.pop();
//      }
//
//      // Push the current number onto the stack
//      stack.push(nums[i]);
//  }
//  return false; // No 132 pattern found
//}

	

}
