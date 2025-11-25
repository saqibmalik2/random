package com.saqib.puzzles;

import java.util.Arrays;

/**
 * PLEASE UNDERSTAND THIS FIRST
 * <p>
 * Why -x = ~x + 1 works mathematically.
 * <p>
 * <b>The Key Insight</b>
 * <p>
 * In n-bit two's complement, all arithmetic is modulo 2^n.
 * For 8-bit numbers: everything wraps around at 256.
 * <p>
 * <b>Why ~x + 1 = -x</b>
 * <p>
 * We want to find what value, when added to x, gives us 0 (mod 2^n):
 * <pre>
 * x + (-x) = 0 (that's the definition of negation)
 * </pre>
 * Let's see what happens with ~x:
 * <pre>
 * x + ~x = ?
 * </pre>
 * When you add a number and its bitwise complement, every bit position has:
 * <ul>
 * <li>Either 0 + 1 = 1</li>
 * <li>Or 1 + 0 = 1</li>
 * </ul>
 * So you get all 1s:
 * <p>
 * Example with x = 5 (8-bit):
 * <pre>
 *   0000 0101  (x = 5)
 * + 1111 1010  (~x)
 * ───────────
 *   1111 1111  (all 1s = 2^8 - 1 = 255)
 * </pre>
 * In general: x + ~x = 2^n - 1
 * <p>
 * Now add 1 to both sides:
 * <pre>
 * x + ~x + 1 = 2^n - 1 + 1
 * x + (~x + 1) = 2^n
 * x + (~x + 1) = 0  (mod 2^n, since 2^n wraps to 0)
 * Therefore: ~x + 1 = -x ✓
 * </pre>
 * <b>Concrete Example</b>
 * <p>
 * Let's verify with x = 5 (8-bit):
 * <pre>
 * x       = 0000 0101  (5)
 * ~x      = 1111 1010  (255 - 5 = 250)
 * ~x + 1  = 1111 1011  (251)
 * 
 * Check: 5 + 251 = 256 = 0 (mod 256) ✓
 * In signed interpretation, 1111 1011 = -5 ✓
 * </pre>
 * <b>The Formula</b>
 * <p>
 * Another way to see it:
 * <pre>
 * ~x = 2^n - 1 - x (flipping bits gives you the complement with respect to all 1s)
 * ~x + 1 = 2^n - 1 - x + 1 = 2^n - x
 * 2^n - x ≡ -x (mod 2^n)
 * </pre>
 */
public class TwoMissingNumbersXor {

	/**
	 * Finds two missing numbers from an array containing numbers from 0 to n with exactly two missing.
	 * <p>
	 * <b>Algorithm Overview:</b>
	 * <p>
	 * This extends the single missing number XOR approach. For one missing number, we XOR all numbers
	 * from 0 to n with all array elements - duplicates cancel out, leaving only the missing number.
	 * <p>
	 * For TWO missing numbers (a and b):
	 * <p>
	 * Example: nums = {0, 1, 3, 4, 6}, n = 6 (complete sequence: 0,1,2,3,4,5,6), missing: 2 and 5
	 * <p>
	 * <b>Step 1: XOR all numbers 0 to n and all array elements</b>
	 * <ul>
	 * <li>XOR of 0,1,2,3,4,5,6: 0^1^2^3^4^5^6 = 7 (binary: 0111)</li>
	 * <li>XOR of array: 0^1^3^4^6 = 4 (binary: 0100)</li>
	 * <li>Combined XOR: 7^4 = 3 (binary: 0011)</li>
	 * <li>Alternatively: xorResult = (0^0)^(1^1)^2^(3^3)^(4^4)^5^(6^6) = 2^5 = 7 (binary: 0111)</li>
	 * <li>Result: xorResult = 2 ^ 5 = 7 (binary: 0111)</li>
	 * <li>We now have the XOR of our two missing numbers (2 and 5), but can't separate them yet</li>
	 * </ul>
	 * <b>Step 2: Find any bit position where the missing numbers differ</b>
	 * <ul>
	 * <li>xorResult = 7 (binary: 0111)</li>
	 * <li>Rightmost set bit: position 0 (rightmost bit = 1, binary: 0001)</li>
	 * <li>This means 2 and 5 differ at bit position 0</li>
	 * <li>Indeed: 2 = 0010 (bit 0 is 0), 5 = 0101 (bit 0 is 1) ✓</li>
	 * </ul>
	 * <b>Step 3: Partition all numbers into two groups based on bit position 0</b>
	 * <ul>
	 * <li>Group 1 (bit 0 = 1): from [0..6]: {1,3,5} and from array: {1,3}
	 *     XOR: 1^3^5^1^3 = 5 (the 1s and 3s cancel)</li>
	 * <li>Group 2 (bit 0 = 0): from [0..6]: {0,2,4,6} and from array: {0,4,6}
	 *     XOR: 0^2^4^6^0^4^6 = 2 (the 0s, 4s, and 6s cancel)</li>
	 * <li>Result: missing numbers are 2 and 5 ✓</li>
	 * </ul>
	 * <b>Complexity:</b>
	 * <ul>
	 * <li>Time Complexity: O(n)</li>
	 * <li>Space Complexity: O(1)</li>
	 * </ul>
	 * 
	 * @param nums array of n-1 distinct integers from range [0, n] with two numbers missing
	 * @return array containing the two missing numbers
	 */
	public int[] findTwoMissingNumbers(int[] nums) {
	    // Calculate n: if array has n-1 elements from range [0, n], then n = arr.length + 1
	    int n = nums.length + 1;
	    
	    // Step 1: XOR all numbers from 0 to n with all array elements
	    // This gives us xorResult = a ^ b (where a and b are the two missing numbers)
	    // All numbers present in both sequences cancel out due to XOR property: x ^ x = 0
	    int xorResult = 0;
	    
	    // XOR all numbers in the complete sequence [0, n]
	    for (int i = 0; i <= n; i++) {
	        xorResult ^= i;
	    }
	    
	    // XOR all numbers actually present in the array
	    // After this, xorResult = a ^ b (only missing numbers remain)
	    for (int num : nums) {
	        xorResult ^= num;
	    }
	    
	    // Step 2: Find the rightmost set bit in xorResult
	    // This bit position has different values in a and b (one has 0, the other has 1)
	    // Integer.lowestOneBit() returns a number with only the rightmost 1-bit set
	    // Example: if xorResult = 12 (1100), rightmostBit = 4 (0100)
	    // Alternative implementation: int rightmostBit = xorResult & (-xorResult);
	    int rightmostBit = Integer.lowestOneBit(xorResult);
	    
	    // Step 3: Partition all numbers into two groups and XOR each group separately
	    // Group 1 (xor1): numbers with rightmostBit set to 1
	    // Group 2 (xor2): numbers with rightmostBit set to 0
	    // One missing number will be in group 1, the other in group 2
	    int xor1 = 0, xor2 = 0;
	    
	    // Partition the complete sequence [0, n] into two groups
	    for (int i = 0; i <= n; i++) {
	        // Check if the bit at rightmostBit position is set in i
	        if ((i & rightmostBit) != 0) {
	            xor1 ^= i;  // Add to group 1
	        } else {
	            xor2 ^= i;  // Add to group 2
	        }
	    }
	    
	    // Partition the array elements into the same two groups
	    // Numbers present in both sequences will cancel out within their respective groups
	    for (int num : nums) {
	        // Check if the bit at rightmostBit position is set in num
	        if ((num & rightmostBit) != 0) {
	            xor1 ^= num;  // Add to group 1
	        } else {
	            xor2 ^= num;  // Add to group 2
	        }
	    }
	    
	    // After XOR operations, all duplicate numbers in each group have cancelled out
	    // xor1 contains the first missing number, xor2 contains the second missing number
	    return new int[]{xor1, xor2};
	}

    /**
     * Simple test method to demonstrate the algorithm.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
    	TwoMissingNumbersXor solver = new TwoMissingNumbersXor();
        int[] nums = {0, 2, 4, 5};
        System.out.println("Missing numbers: " + Arrays.toString(solver.findTwoMissingNumbers(nums))); // Output: {3, 1}
    }
}