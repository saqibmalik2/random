package com.saqib.puzzles;

/**
 * 
 * There is an integer array nums sorted in ascending order (with distinct
 * values).
 * 
 * Prior to being passed to your function, nums is possibly left rotated at an
 * unknown index k (1 <= k < nums.length) such that the resulting array is
 * [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]
 * (0-indexed). For example, [0,1,2,4,5,6,7] might be left rotated by 3 indices
 * and become [4,5,6,7,0,1,2].
 * 
 * Given the array nums after the possible rotation and an integer target,
 * return the index of target if it is in nums, or -1 if it is not in nums.
 * 
 * You must write an algorithm with O(log n) runtime complexity.
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [4,5,6,7,0,1,2], target = 0 Output: 4 Example 2:
 * 
 * Input: nums = [4,5,6,7,0,1,2], target = 3 Output: -1 Example 3:
 * 
 * Input: nums = [1], target = 0 Output: -1
 * 
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 5000 -104 <= nums[i] <= 104 All values of nums are
 * unique. nums is an ascending array that is possibly rotated. -104 <= target
 * <= 104
 * 
 */

public class RotatedArraySearch {

	public static void main(String[] args) {
		int[] nums = new int[] {7,0,1,2,4,5,6};
		RotatedArraySearch rotatedArraySearch = new RotatedArraySearch();
		System.out.println(rotatedArraySearch.search(nums, 3));
	}
	
	// Returns index of target in nums, or -1 if not present.
    public int search(int[] nums, int target) {
        int lpointer = 0, rpointer = nums.length - 1;

        while (lpointer <= rpointer) {
            int midpoint = lpointer + (rpointer - lpointer) / 2;
            if (nums[midpoint] == target) return midpoint;

            // At each step, one half is guaranteed to be monotonic (no pivot inside).
            // Perform check on left half first
            if (nums[lpointer] <= nums[midpoint]) {
                if (nums[lpointer] <= target && target < nums[midpoint]) {
                    rpointer = midpoint - 1;   // keep left half
                } else {
                    lpointer = midpoint + 1;   // keep right half (may contain the pivot)
                }
            } else {
                // Right half [midpoint..rpointer] is monotone.
                // If target lies within its CLOSED bounds, keep this half; otherwise keep the other.
                if (nums[midpoint] < target && target <= nums[rpointer]) {
                    lpointer = midpoint + 1;   // keep right half
                } else {
                    rpointer = midpoint - 1;   // keep left half (may contain the pivot)
                }
            }
        }
        return -1;
    }

}


/**
 * Let’s use the same example: nums = {4,0,1,2}, target = 3.
 * 
 * Step 1
 * 
 * Pointers: lpointer=0, rpointer=3
 * 
 * Active slice (indices 0..3): {4, 0, 1, 2}
 * 
 * midpoint: 0 + (3-0)/2 = 1 → midpoint=1, nums[midpoint]=0
 * 
 * Compare for monotone-half: nums[lpointer] <= nums[midpoint] → 4 <= 0 → false
 * ⇒ Right half is monotone (indices 1..3, values {0,1,2}).
 * 
 * Closed-interval check on monotone half: nums[midpoint] < target && target <=
 * nums[rpointer] 0 < 3 && 3 <= 2 → false
 * 
 * Decision: Keep left side (the other half). Update pointers: rpointer =
 * midpoint - 1 = 0
 * 
 * Step 2
 * 
 * Pointers: lpointer=0, rpointer=0
 * 
 * Active slice (indices 0..0): {4}
 * 
 * midpoint: 0 + (0-0)/2 = 0 → midpoint=0, nums[midpoint]=4 (≠ 3)
 * 
 * Compare for monotone-half: nums[lpointer] <= nums[midpoint] → 4 <= 4 → true ⇒
 * Left half is monotone (indices 0..0, values {4}).
 * 
 * Closed-interval check on monotone half: nums[lpointer] <= target && target <
 * nums[midpoint] 4 <= 3 && 3 < 4 → first part 4 <= 3 is false
 * 
 * Decision: Keep right side (the other half). Update pointers: lpointer =
 * midpoint + 1 = 1
 * 
 * Termination
 * 
 * Pointers: lpointer=1, rpointer=0 ⇒ window empty (lpointer > rpointer)
 * 
 * Result: return -1 (target not present).
 * 
 * You said: And again but for target = 2 this time. ChatGPT said:
 * 
 * Let’s dry-run nums = {4,0,1,2}, target = 2.
 * 
 * Step 1
 * 
 * Pointers: lpointer=0, rpointer=3
 * 
 * Active slice (indices 0..3): {4, 0, 1, 2}
 * 
 * midpoint: 0 + (3-0)/2 = 1 → midpoint=1, nums[midpoint]=0
 * 
 * Monotone-half check: nums[lpointer] <= nums[midpoint] → 4 <= 0 → false ⇒
 * Right half (indices 1..3, values {0,1,2}) is monotone.
 * 
 * Closed-interval on monotone half: nums[midpoint] < target && target <=
 * nums[rpointer] 0 < 2 && 2 <= 2 → true
 * 
 * Decision: Keep right side. Update: lpointer = midpoint + 1 = 2
 * 
 * Step 2
 * 
 * Pointers: lpointer=2, rpointer=3
 * 
 * Active slice (indices 2..3): {1, 2}
 * 
 * midpoint: 2 + (3-2)/2 = 2 → midpoint=2, nums[midpoint]=1
 * 
 * Check target at mid: 1 == 2 → no
 * 
 * Monotone-half check: nums[lpointer] <= nums[midpoint] → 1 <= 1 → true ⇒ Left
 * half (indices 2..2, values {1}) is monotone.
 * 
 * Closed-interval on that half: nums[lpointer] <= target && target <
 * nums[midpoint] 1 <= 2 && 2 < 1 → false
 * 
 * Decision: Keep right side. Update: lpointer = midpoint + 1 = 3
 * 
 * Step 3
 * 
 * Pointers: lpointer=3, rpointer=3
 * 
 * Active slice (indices 3..3): {2}
 * 
 * midpoint: 3 + (3-3)/2 = 3 → midpoint=3, nums[midpoint]=2
 * 
 * Check target at mid: 2 == 2 → found at index 3 ✅
 * 
 * Result: 3.
 **/
