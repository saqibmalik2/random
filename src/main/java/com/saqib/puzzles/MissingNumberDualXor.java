package com.saqib.puzzles;

/**
 * LeetCode 268 - Missing Number
 *
 * <p>
 * This implementation uses two XOR accumulators — one for the array values
 * and one for the full numeric range [0, n] — computed within a single loop.
 * The final missing number is obtained by XORing the two results.
 *
 * <p><b>Why this works:</b>
 * <ul>
 *   <li>Every number from 0 to n should appear exactly once.</li>
 *   <li>XOR has two key properties:
 *       <ul>
 *         <li>a ^ a = 0 → identical values cancel each other out.</li>
 *         <li>a ^ 0 = a → XOR with zero leaves a unchanged.</li>
 *       </ul>
 *   </li>
 *   <li>Because XOR is associative and commutative, the order of operations doesn’t matter.</li>
 *   <li>Therefore, when we XOR all array values (a) and all numbers in [0, n] (b),
 *       every number that exists in both cancels out — leaving only the missing one.</li>
 * </ul>
 *
 * <p><b>Example:</b>
 * nums = [3, 0, 1], n = 3
 * <pre>
 * a = 3 ^ 0 ^ 1 = 2
 * b = 0 ^ 1 ^ 2 ^ 3 = 0
 * a ^ b = 2 ^ 0 = 2 → missing number is 2
 * </pre>
 *
 * <p><b>Complexity:</b>
 * <ul>
 *   <li>Time: O(n)</li>
 *   <li>Space: O(1)</li>
 * </ul>
 */
public class MissingNumberDualXor {

    /**
     * Returns the missing number from an array containing n distinct integers
     * taken from the range [0, n].
     *
     * @param nums the input array of distinct integers in the range [0, n]
     * @return the missing integer
     */
    public int missingNumber(int[] nums) {
        int a = 0;  // XOR accumulator for all array elements
        int b = 0;  // XOR accumulator for all integers in [0, n]
        int n = nums.length;

        // Perform both XOR accumulations in a single pass
        for (int i = 0; i < n; i++) {
            a ^= nums[i];  // XOR with the current array value
            b ^= i;        // XOR with the current index (part of [0, n])
        }

        // Include n in the range XOR since indices go from 0 to n-1
        b ^= n;

        // The XOR of (all in array) and (all in range) gives the missing number
        return a ^ b;
    }

    // Simple test
    public static void main(String[] args) {
        MissingNumberDualXor solver = new MissingNumberDualXor();
        int[] nums = {3, 0, 1};
        System.out.println("Missing number: " + solver.missingNumber(nums)); // Output: 2
    }
}

