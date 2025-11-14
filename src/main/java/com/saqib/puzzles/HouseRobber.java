package com.saqib.puzzles;

/**
 * Solves the classic "House Robber" problem.
 *
 * <p>You are given a row of houses, each containing some amount of money. You cannot rob
 * two adjacent houses (doing so triggers an alarm). The goal is to compute the maximum
 * amount of money you can rob without robbing adjacent houses.</p>
 *
 * <p>This is a dynamic programming problem with the recurrence:</p>
 *
 * <pre>
 * best[i] = max(best[i-1], best[i-2] + nums[i])
 * </pre>
 *
 * <p>Where:
 * <ul>
 *   <li><code>best[i]</code> is the maximum money that can be robbed from houses [0..i].</li>
 *   <li><code>best[i-1]</code> corresponds to skipping house i.</li>
 *   <li><code>best[i-2] + nums[i]</code> corresponds to robbing house i.</li>
 * </ul>
 *
 * <p>To optimise space, we do not store the full DP array. Instead we only store the last
 * two computed values, because each state depends only on the previous two.</p>
 *
 * <p>Time complexity: O(n) — iterate once over the input</p>
 * <p>Space complexity: O(1) — constant extra memory</p>
 */
public class HouseRobber {

    /**
     * Computes the maximum money that can be robbed from a line of houses without
     * robbing two adjacent houses.
     *
     * @param nums array where nums[i] is the money in the i-th house (non-negative)
     * @return maximum money that can be robbed without triggering alarms
     */
    public static long rob(long[] nums) {
    	
    	// if array is null or empty then return 0
    	if (nums == null || nums.length == 0) {
    	    return 0;
    	}

        // prev2 = best result up to house i-2
        long prev2 = 0;

        // prev1 = best result up to house i-1
        long prev1 = 0;
        
        //current is the current result
        long current = 0;

        // Iterate over each house's money
        for (long money : nums) {

            // Option 1: skip this house → same result as previous house
            long skip = prev1;

            // Option 2: rob this house → add its money to best up to i-2
            long take = prev2 + money;

            // Best result at this house is the max of skipping vs taking
            current = Math.max(skip, take);

            // Shift state forward:
            // prev2 becomes previous prev1, prev1 becomes current
            prev2 = prev1;
            prev1 = current;
        }

        // return current
        return current;
    }

    public static void main(String[] args) {
        long[] nums = {5, 1, 3, 0, 2, 7, 1, 1};
        System.out.println(rob(nums)); // 16
    }
}
