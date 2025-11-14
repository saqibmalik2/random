package com.saqib.puzzles;

import java.util.TreeSet;

/**
 * Longest Increasing Subsequence (LIS) using Patience Sorting Algorithm.
 * 
 * <p>This algorithm finds the length of the longest strictly increasing subsequence
 * in an array. A subsequence is derived by deleting some or no elements without 
 * changing the order of the remaining elements.</p>
 * 
 * <p><b>Algorithm:</b> Patience Sorting - inspired by the card game "Patience"</p>
 * <p>The algorithm maintains a set of "pile tops" where each pile represents a 
 * potential increasing subsequence. When we encounter a new number:</p>
 * <ul>
 *   <li>If it's larger than all pile tops, it extends the longest subsequence (new pile)</li>
 *   <li>Otherwise, we replace the smallest pile top that's >= the new number</li>
 * </ul>
 * 
 * <p><b>Time Complexity:</b> O(n log n) - n iterations, each with O(log n) TreeSet operations</p>
 * <p><b>Space Complexity:</b> O(n) - TreeSet can grow up to size n in worst case</p>
 * 
 * <p><b>Example:</b></p>
 * <pre>
 * Input: [0,1,0,3,2,3]
 * Process:
 *   0 → set = {0}
 *   1 → set = {0,1}
 *   0 → replace 0, set = {0,1}
 *   3 → set = {0,1,3}
 *   2 → replace 3, set = {0,1,2}
 *   3 → set = {0,1,2,3}
 * Output: 4 (subsequence could be [0,1,2,3])
 * </pre>
 * 
 * <pre>
 *  * DETAILED WALKTHROUGH with nums = [0,1,0,3,2,3]:
 * 
 * Step 1: v=0, set={}, ceil=null
 *         → No ceiling found, 0 is the first element
 *         → set = {0}
 *         → LIS length = 1
 * 
 * Step 2: v=1, set={0}, ceil=null
 *         → 1 > 0, no ceiling found
 *         → Extends the subsequence
 *         → set = {0,1}
 *         → LIS length = 2 (subsequence: [0,1])
 * 
 * Step 3: v=0, set={0,1}, ceil=0
 *         → Found ceiling 0 (equal to v)
 *         → Remove 0, add 0 back (no change, but algorithm is consistent)
 *         → set = {0,1}
 *         → LIS length = 2
 * 
 * Step 4: v=3, set={0,1}, ceil=null
 *         → 3 > all elements, no ceiling found
 *         → Extends the subsequence
 *         → set = {0,1,3}
 *         → LIS length = 3 (subsequence: [0,1,3])
 * 
 * Step 5: v=2, set={0,1,3}, ceil=3
 *         → Found ceiling 3 (smallest >= 2)
 *         → Replace 3 with 2 (keeps tail smaller for future extensions)
 *         → set = {0,1,2}
 *         → LIS length = 3 (subsequence: [0,1,2])
 * 
 * Step 6: v=3, set={0,1,2}, ceil=null
 *         → 3 > all elements, no ceiling found
 *         → Extends the subsequence
 *         → set = {0,1,2,3}
 *         → LIS length = 4 (subsequence: [0,1,2,3])
 * 
 * Final Answer: 4
 * 
 * WHY REPLACING WORKS:
 * When we replace ceil with v (where v < ceil), we're not changing the length
 * of the subsequence that pile represents. We're just optimizing it by keeping
 * the tail value as small as possible. A smaller tail value means we have more
 * opportunities to extend this subsequence with future numbers.
 * 
 * Example: If we have [0,1,10] and encounter 2:
 * - Replace 10 with 2 → [0,1,2]
 * - Now if we see 3, we can extend to [0,1,2,3]
 * - If we hadn't replaced, [0,1,10] couldn't be extended by 3
 * 
 * </pre>
 * 
 * @author Saqib Malik
 */
public class LongestIncreasingSubsequence {

	public static void main(String[] args) {
		int[] nums = {0,1,0,3,2,3};
		//int[] nums = {4,10,4,3,8,9};
		LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();
		System.out.println(lis.lengthOfLIS(nums));
	}

	/**
	 * Calculates the length of the longest strictly increasing subsequence.
	 * 
	 * <p>Uses the Patience Sorting algorithm which maintains a TreeSet representing
	 * the smallest possible tail elements for all increasing subsequences of 
	 * different lengths found so far.</p>
	 * 
	 * <p><b>Key Insight:</b> The TreeSet size represents the length of the LIS because:</p>
	 * <ul>
	 *   <li>Each element in the set represents a potential "pile top"</li>
	 *   <li>The number of piles equals the length of the longest increasing subsequence</li>
	 *   <li>We greedily replace larger values to keep subsequences as small as possible,
	 *       maximizing our chances of extending them later</li>
	 * </ul>
	 * 
	 * @param nums the input array of integers
	 * @return the length of the longest strictly increasing subsequence
	 * 
	 * @throws NullPointerException if nums is null
	 */
	public int lengthOfLIS(int[] nums) {
		// TreeSet maintains sorted order and allows efficient ceiling() operations
		// Each element represents the smallest tail value for a subsequence of that length
		TreeSet<Integer> set = new TreeSet<>();
		
		// Process each number in the array
		for (int v : nums) {
			// Find the smallest number in set that is >= v
			// This is the pile we should place v on (or null if v is larger than all)
			Integer ceil = set.ceiling(v);
			
			// If we found a ceiling value, remove it
			// We're replacing it with v (a smaller or equal value)
			// This keeps our subsequence tails as small as possible for future extensions
			if (ceil != null) {
				set.remove(ceil);
			}
			// If ceil is null, v is larger than all elements in set
			// This means v extends the longest subsequence (adds a new pile)
			
			// Add v to the set
			// Either: 1) Replacing a larger value (optimizing for future)
			//     Or: 2) Adding to the end (extending the longest subsequence)
			set.add(v);
		}
		
		// The size of the set equals the number of piles,
		// which equals the length of the longest increasing subsequence
		return set.size();
	}

}
