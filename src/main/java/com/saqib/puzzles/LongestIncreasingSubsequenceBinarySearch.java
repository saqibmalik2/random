package com.saqib.puzzles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Longest Increasing Subsequence (LIS) using Patience Sorting.
 *
 * <p><b>ALGORITHM INTUITION - THE CARD PILE MODEL:</b></p>
 * 
 * <p>Imagine playing a solitaire game where you build piles of cards following these rules:
 * <ul>
 *   <li>For each new card, find the <b>leftmost pile</b> whose top card is ≥ the new card</li>
 *   <li>Place the new card on that pile (it becomes the new top)</li>
 *   <li>If no such pile exists, create a new pile with this card</li>
 * </ul>
 * </p>
 *
 * <p><b>KEY THEOREM:</b> The number of piles equals the length of the LIS.</p>
 * 
 * <p><b>WHY THIS WORKS:</b>
 * <ul>
 *   <li>Each pile top is strictly smaller than the next pile's top (increasing sequence)</li>
 *   <li>When we place a card on pile i, we can trace back through piles 0...i-1 
 *       to find a valid increasing subsequence of length i+1</li>
 *   <li>The tops of all piles form a valid increasing subsequence</li>
 * </ul>
 * </p>
 *
 * <p><b>OPTIMIZATION:</b> Instead of storing full piles, we only track the tops in the 
 * {@code tails} array, and use binary search to find the correct pile in O(log n) time.</p>
 *
 * <p><b>EXAMPLE:</b> For input array {@code [2, 5, 3, 7, 11, 8, 10, 13, 6]}:
 * <pre>
 * After processing all elements:
 *   Pile 0: [2]        (top: 2)
 *   Pile 1: [5, 3]     (top: 3)
 *   Pile 2: [7]        (top: 7)
 *   Pile 3: [11, 8]    (top: 8)
 *   Pile 4: [10]       (top: 10)
 *   Pile 5: [13]       (top: 13)
 * 
 * tails = [2, 3, 7, 8, 10, 13]  (the pile tops)
 * size = 6  (6 piles = LIS length of 6)
 * 
 * One valid LIS: [2, 3, 7, 8, 10, 13]
 * </pre>
 * </p>
 *
 * <p><b>CRITICAL NOTE:</b> The {@code tails} array does NOT represent the actual LIS!
 * It only tracks the smallest top value of each pile. For example, {@code tails[1] = 3}
 * means "pile 1 has top value 3", but pile 1 actually contains [5, 3] where 5 appeared
 * before 3 in the input. The {@code tails} values are from different positions in the
 * original array and may not form a contiguous subsequence themselves.</p>
 *
 * <p>Time Complexity: O(n log n)
 * <br>Space Complexity: O(n)</p>
 */
public class LongestIncreasingSubsequenceBinarySearch {
    
    /**
     * Returns the length of the longest strictly increasing subsequence.
     *
     * <p>The {@code tails} array represents pile tops:
     * <ul>
     *   <li>{@code tails[i]} = the smallest top card on a pile representing 
     *       a subsequence of length i+1</li>
     *   <li>{@code size} = the number of piles we've created so far</li>
     * </ul>
     * </p>
     *
     * @param nums input array of integers
     * @return length of LIS (= number of piles)
     */
    public int lengthOfLIS(int[] nums) {
        return lengthOfLISWithTails(nums, null);
    }
    
    /**
     * Internal method that optionally returns the tails array for debugging.
     *
     * @param nums input array of integers
     * @param outTails if not null, will be filled with the final tails array (pile tops)
     * @return length of LIS (= number of piles)
     */
    private int lengthOfLISWithTails(int[] nums, int[] outTails) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // tails[i] = top of pile i (smallest ending value for subsequence of length i+1)
        int[] tails = new int[nums.length];
        int size = 0; // Current number of piles
        
        for (int v : nums) {
            /*
             * BINARY SEARCH: Find the leftmost pile whose top is >= v
             * 
             * We maintain the invariant that tails[0] < tails[1] < ... < tails[size-1]
             * Goal: Find the first index where tails[index] >= v
             * 
             * Search range: [lo, hi) where lo is inclusive, hi is exclusive
             */
            
            int lo = 0;        // Left boundary: start of search range
            int hi = size;     // Right boundary: one past the last valid pile
            
            // Standard binary search for "first element >= target"
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;  // Midpoint (avoids overflow)
                
                if (tails[mid] < v) {
                    // Pile 'mid' has a top smaller than v
                    // v cannot go here or any pile to the left
                    // Search in the right half: [mid+1, hi)
                    lo = mid + 1;
                } else {
                    // tails[mid] >= v
                    // v could go on pile 'mid', but maybe there's an earlier pile
                    // Search in the left half (including mid): [lo, mid)
                    hi = mid;
                }
            }
            
            // After binary search: lo is the leftmost pile where tails[lo] >= v
            // (or lo == size if v is greater than all pile tops)
            
            /*
             * PLACE THE CARD:
             * - If lo < size: replace the top of pile 'lo' with v (better/smaller top)
             * - If lo == size: create a new pile (v extends all existing piles)
             */
            tails[lo] = v;
            
            // If we placed the card beyond all existing piles, increment pile count
            if (lo == size) {
                size++;
            }
        }
        
        // The number of piles = length of LIS
        
        // Copy tails to output if requested
        if (outTails != null && outTails.length >= size) {
            System.arraycopy(tails, 0, outTails, 0, size);
        }
        
        return size;
    }
    
    /**
     * Returns the actual longest increasing subsequence (one possible solution).
     *
     * <p>This extends the basic algorithm to track:
     * <ul>
     *   <li>{@code prev[i]} = index of the predecessor of element i in the LIS</li>
     *   <li>{@code tailIndices[i]} = array index of the current top of pile i</li>
     * </ul>
     * These allow us to reconstruct the actual sequence by backtracking.
     * </p>
     *
     * @param nums input array of integers
     * @return list containing one LIS
     */
    public List<Integer> findLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        
        int[] tails = new int[nums.length];
        int[] prev = new int[nums.length];        // Predecessor index for reconstruction
        int[] tailIndices = new int[nums.length]; // Original array index of pile top
        Arrays.fill(prev, -1);
        
        int size = 0;
        
        for (int i = 0; i < nums.length; i++) {
            int v = nums[i];
            
            // Binary search (same as above)
            int lo = 0, hi = size;
            
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (tails[mid] < v) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            
            // Place card and record its position
            tails[lo] = v;
            tailIndices[lo] = i;  // Remember which array index is on top of pile 'lo'
            
            // Link to predecessor: the element on top of the previous pile
            if (lo > 0) {
                prev[i] = tailIndices[lo - 1];
            }
            
            if (lo == size) {
                size++;
            }
        }
        
        // Reconstruct the LIS by backtracking through predecessors
        List<Integer> result = new ArrayList<>();
        int current = tailIndices[size - 1];  // Start from top of last pile
        
        while (current != -1) {
            result.add(0, nums[current]);     // Add to front (building backwards)
            current = prev[current];           // Move to predecessor
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        LongestIncreasingSubsequenceBinarySearch lis = 
            new LongestIncreasingSubsequenceBinarySearch();
        
        // Test case 1
        int[] nums1 = {0, 1, 0, 3, 2, 3};
        int[] tails1 = new int[nums1.length];
        int length1 = lis.lengthOfLISWithTails(nums1, tails1);
        System.out.println("Array: " + Arrays.toString(nums1));
        System.out.println("LIS Length: " + length1);
        System.out.println("Pile Tops (tails): " + Arrays.toString(Arrays.copyOf(tails1, length1)));
        System.out.println("Actual LIS: " + lis.findLIS(nums1));
        System.out.println();
        
        // Test case 2
        int[] nums2 = {10, 9, 2, 5, 3, 7, 101, 18};
        int[] tails2 = new int[nums2.length];
        int length2 = lis.lengthOfLISWithTails(nums2, tails2);
        System.out.println("Array: " + Arrays.toString(nums2));
        System.out.println("LIS Length: " + length2);
        System.out.println("Pile Tops (tails): " + Arrays.toString(Arrays.copyOf(tails2, length2)));
        System.out.println("Actual LIS: " + lis.findLIS(nums2));
        System.out.println();
        
        // Test case 3: Single element
        int[] nums3 = {7};
        int[] tails3 = new int[nums3.length];
        int length3 = lis.lengthOfLISWithTails(nums3, tails3);
        System.out.println("Array: " + Arrays.toString(nums3));
        System.out.println("LIS Length: " + length3);
        System.out.println("Pile Tops (tails): " + Arrays.toString(Arrays.copyOf(tails3, length3)));
        System.out.println("Actual LIS: " + lis.findLIS(nums3));
        System.out.println();
        
        // Test case 4: Decreasing array
        int[] nums4 = {7, 6, 5, 4, 3};
        int[] tails4 = new int[nums4.length];
        int length4 = lis.lengthOfLISWithTails(nums4, tails4);
        System.out.println("Array: " + Arrays.toString(nums4));
        System.out.println("LIS Length: " + length4);
        System.out.println("Pile Tops (tails): " + Arrays.toString(Arrays.copyOf(tails4, length4)));
        System.out.println("Actual LIS: " + lis.findLIS(nums4));
        System.out.println();
        
        // Test case 5: The example from documentation
        int[] nums5 = {2, 5, 3, 7, 11, 8, 10, 13, 6};
        int[] tails5 = new int[nums5.length];
        int length5 = lis.lengthOfLISWithTails(nums5, tails5);
        System.out.println("Array: " + Arrays.toString(nums5));
        System.out.println("LIS Length: " + length5);
        System.out.println("Pile Tops (tails): " + Arrays.toString(Arrays.copyOf(tails5, length5)));
        System.out.println("Actual LIS: " + lis.findLIS(nums5));
        
        // Test case 6: The example from documentation
        // NOTE: this example illustrates why you can't rely on the tails array for a valid LIS
        int[] nums6 = {3, 5, 6, 2, 4, 7};
        int[] tails6 = new int[nums5.length];
        int length6 = lis.lengthOfLISWithTails(nums6, tails6);
        System.out.println("Array: " + Arrays.toString(nums6));
        System.out.println("LIS Length: " + length6);
        System.out.println("Pile Tops (tails): " + Arrays.toString(Arrays.copyOf(tails6, length6)));
        System.out.println("Actual LIS: " + lis.findLIS(nums6));
        
    }
}