package com.saqib.puzzles;

import static java.lang.System.*;


/**
 * 
 * A leet code question. 
 * 
 * You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.
 *
 *	Return true if you can reach the last index, or false otherwise.
 *	
 *	Example 1:
 *
 *	Input: nums = [2,3,1,1,4]
 *	Output: true
 *	Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *	Example 2:
 *	
 *	Input: nums = [3,2,1,0,4]
 *	Output: false
 *	Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
 * 
 */

public class JumpGame {

	public static void main(String[] args) {
		//int[] jumps = {3,2,1,0,4};
		int[] jumps = {2,3,1,1,4};
		JumpGame jumpGame = new JumpGame();
		out.println(jumpGame.canJump(jumps));
	}
	
	
	/**
	 * 
	 * Start at the penultimate position in the array. Can we get to the end from there? 
	 * Yes - set the currentEndPoint to the current positon and see if we can reach it from any of the
	 * earlier positions.
	 * No - move along and see if we can reach the end from there. If so then follow the procedure for Yes (above)
	 * from there.
	 * 
	 * Notice how we reset canJump to false each time. It's the final iteration of the loop which determines
	 * whether we have found a complete path to the end. When i=0 if canJump is set to true that means we can
	 * reach the currentEndPoint from the start. The currentEndPoint is either the final position or some earlier
	 * position which has a path to the final position.
	 * 
	 * @param nums
	 * @return boolean canJump
	 */
	public boolean canJump(int[] nums) {
		//edge case, trivially true
		if (nums.length == 1) return true;
		
		boolean canJump = false;
		int currentEndPoint = nums.length-1;
		for (int i=nums.length-2;i>=0;i--) {
			canJump = false;
			if (nums[i] >= currentEndPoint - i) {
				canJump = true;
				currentEndPoint = i;
			}
		}
		return canJump;
	}
	
}
