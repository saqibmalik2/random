package com.saqib.puzzles;

/**
 * Finds the median of two sorted arrays in O(log(min(m,n))) time.
 * <p>
 * <b>Problem Statement:</b>
 * <p>
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return
 * the median of the two sorted arrays. The overall run time complexity should be O(log(m+n)).
 * <p>
 * <b>Key Insight:</b>
 * <p>
 * Instead of merging the arrays (which would be O(m+n)), we use binary search to find the correct
 * partition point. We conceptually divide the combined elements into two partitions:
 * <ul>
 * <li>Left partition: Contains the smaller half of all elements</li>
 * <li>Right partition: Contains the larger half of all elements</li>
 * </ul>
 * The median lies at the boundary between these partitions.
 * <p>
 * <b>Algorithm Overview:</b>
 * <p>
 * We perform binary search on the smaller array to find how many elements from each array
 * should go into the left partition. For a valid partition:
 * <ol>
 * <li>Left partition size = (m + n + 1) / 2 (the +1 handles both odd and even cases)</li>
 * <li>max(left partition) ≤ min(right partition)</li>
 * </ol>
 * <b>Important: The partitions are conceptual, not physical data structures.</b>
 * We never create new arrays or copy elements. Instead, we use indices (i1 and i2) to
 * conceptually divide nums1 and nums2. The "partitions" exist only through these index
 * pointers - we access boundary values directly from the original arrays using these indices.
 * <p>
 * We only need to check 4 boundary values to verify condition 2:
 * <ul>
 * <li>left1: last element of nums1's left partition (nums1[i1-1])</li>
 * <li>right1: first element of nums1's right partition (nums1[i1])</li>
 * <li>left2: last element of nums2's left partition (nums2[i2-1])</li>
 * <li>right2: first element of nums2's right partition (nums2[i2])</li>
 * </ul>
 * Valid partition requires: left1 ≤ right2 AND left2 ≤ right1
 * <p>
 * <b>Complete Dry Run Example:</b>
 * <pre>
 * nums1 = [1, 3, 5, 7, 9, 11, 13, 15] (length = 8)
 * nums2 = [8, 11, 14, 17, 18, 21, 23, 26] (length = 8)
 * Total elements = 16 (even)
 * Target left partition size = (8 + 8 + 1) / 2 = 8
 * 
 * ITERATION 1:
 * -----------
 * Binary search bounds: start = 0, end = 8
 * 
 * Calculate partition indices:
 *   i1 = (0 + 8) / 2 = 4 (take 4 elements from nums1)
 *   i2 = 8 - 4 = 4 (take 4 elements from nums2)
 * 
 * Partitions:
 *   nums1: [1, 3, 5, 7] | [9, 11, 13, 15]
 *   nums2: [8, 11, 14, 17] | [18, 21, 23, 26]
 * 
 * Conceptual partitions:
 *   Left:  [1, 3, 5, 7, 8, 11, 14, 17] (8 elements)
 *   Right: [9, 11, 13, 15, 18, 21, 23, 26] (8 elements)
 * 
 * Boundary values:
 *   left1 = nums1[3] = 7 (max of nums1's left partition)
 *   right1 = nums1[4] = 9 (min of nums1's right partition)
 *   left2 = nums2[3] = 17 (max of nums2's left partition)
 *   right2 = nums2[4] = 18 (min of nums2's right partition)
 * 
 * Validity check (Why we actually need only 2 checks as opposed to the four we originally listed):
 *   Since nums1 is already sorted: left1 ≤ right1 is GUARANTEED (7 ≤ 9) ✓
 *   Since nums2 is already sorted: left2 ≤ right2 is GUARANTEED (17 ≤ 18) ✓
 *   
 *   We only need to check the CROSS-ARRAY conditions:
 *   (i)  Is left1 ≤ right2? Is 7 ≤ 18? YES ✓
 *        (Can nums1's left coexist with nums2's right?)
 *   (ii) Is left2 ≤ right1? Is 17 ≤ 9? NO ✗
 *        (Can nums2's left coexist with nums1's right?)
 * 
 * Diagnosis: left2 (17) > right1 (9) means we took too many elements from nums2.
 * We need MORE from nums1 and FEWER from nums2.
 * 
 * Adjust: start = i1 + 1 = 5
 * 
 * ITERATION 2:
 * -----------
 * Binary search bounds: start = 5, end = 8
 * 
 * Calculate partition indices:
 *   i1 = (5 + 8) / 2 = 6 (take 6 elements from nums1)
 *   i2 = 8 - 6 = 2 (take 2 elements from nums2)
 * 
 * Partitions:
 *   nums1: [1, 3, 5, 7, 9, 11] | [13, 15]
 *   nums2: [8, 11] | [14, 17, 18, 21, 23, 26]
 * 
 * Conceptual partitions:
 *   Left:  [1, 3, 5, 7, 8, 9, 11, 11] (8 elements)
 *   Right: [13, 14, 15, 17, 18, 21, 23, 26] (8 elements)
 *   
 *   Note: The conceptual left partition happens to be sorted here, but this is NOT
 *   required! What matters is that max(left) ≤ min(right) AND that THESE TWO boundary
 *   elements are correctly positioned at the partition edges. The internal ordering
 *   of the rest of the elements within each partition is irrelevant for finding the median.
 * 
 * Boundary values:
 *   left1 = nums1[5] = 11 (max of nums1's left partition)
 *   right1 = nums1[6] = 13 (min of nums1's right partition)
 *   left2 = nums2[1] = 11 (max of nums2's left partition)
 *   right2 = nums2[2] = 14 (min of nums2's right partition)
 * 
 * Validity check (Why only 2 checks needed):
 *   Since nums1 is already sorted: left1 ≤ right1 is GUARANTEED (11 ≤ 13) ✓
 *   Since nums2 is already sorted: left2 ≤ right2 is GUARANTEED (11 ≤ 14) ✓
 *   
 *   We only need to check the CROSS-ARRAY conditions:
 *   (i)  Is left1 ≤ right2? Is 11 ≤ 14? YES ✓
 *        (Can nums1's left coexist with nums2's right?)
 *   (ii) Is left2 ≤ right1? Is 11 ≤ 13? YES ✓
 *        (Can nums2's left coexist with nums1's right?)
 * 
 * SUCCESS! Valid partition found.
 * 
 * Why this partition is valid:
 *   - Both cross-array conditions are satisfied
 *   - This means: max(left partition) ≤ min(right partition)
 *   - Specifically: max(11, 11) = 11 and min(13, 14) = 13, and 11 ≤ 13 ✓
 *   - The boundary values are in correct order, which is all that matters
 *   - Internal ordering within each partition is irrelevant
 * 
 * Calculate median (even total):
 *   median = (max(left1, left2) + min(right1, right2)) / 2.0
 *   median = (max(11, 11) + min(13, 14)) / 2.0
 *   median = (11 + 13) / 2.0 = 12.0
 * </pre>
 * <b>Time Complexity:</b> O(log(min(m, n))) - binary search on the smaller array
 * <br>
 * <b>Space Complexity:</b> O(1) - only using a constant amount of extra space
 */
public class MedianTwoSortedArraysOptimal {

    /**
     * Finds the median of two sorted arrays.
     * 
     * @param nums1 first sorted array
     * @param nums2 second sorted array
     * @return the median of the combined arrays
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        
        // Optimization: Always perform binary search on the smaller array
        // This ensures O(log(min(m,n))) complexity rather than O(log(max(m,n)))
        if(nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        
        // Binary search bounds for nums1
        // We're searching for the correct number of elements to take from nums1 for the left partition
        int start = 0;
        int end = nums1.length;
        
        while(start <= end) {
            
            // i1: Number of elements to take from nums1 for the left partition
            // This is our binary search midpoint
            int i1 = (start + end) / 2;
            
            // i2: Number of elements to take from nums2 for the left partition
            // Total left partition size is (m + n + 1) / 2
            // The +1 ensures correct behavior for both odd and even total lengths:
            //   - For even (e.g., 16): (16+1)/2 = 8 (we want 8 on left)
            //   - For odd (e.g., 15): (15+1)/2 = 8 (we want 8 on left, median is max of left)
            int i2 = (nums1.length + nums2.length + 1) / 2 - i1;
            
            // Get boundary values for nums1's partition
            // left1: Maximum value in nums1's left partition (last element)
            // right1: Minimum value in nums1's right partition (first element)
            // Use Integer.MIN_VALUE/MAX_VALUE for edge cases when partition is at array boundaries
            int left1 = i1 == 0 ? Integer.MIN_VALUE : nums1[i1-1];
            int right1 = i1 == nums1.length ? Integer.MAX_VALUE : nums1[i1];
            
            // Get boundary values for nums2's partition
            // left2: Maximum value in nums2's left partition (last element)
            // right2: Minimum value in nums2's right partition (first element)
            int left2 = i2 == 0 ? Integer.MIN_VALUE : nums2[i2-1];
            int right2 = i2 == nums2.length ? Integer.MAX_VALUE : nums2[i2];
            
            // Check if current partition is valid
            // A valid partition requires:
            //   1. left1 ≤ right2 (nums1's left boundary ≤ nums2's right boundary)
            //   2. left2 ≤ right1 (nums2's left boundary ≤ nums1's right boundary)
            // Note: left1 ≤ right1 and left2 ≤ right2 are already guaranteed by sorted arrays
            
            if(left1 > right2) {
                // left1 is too large - we took too many elements from nums1
                // Move partition point to the LEFT (take fewer from nums1)
                end = i1 - 1;
            } else if(left2 > right1) {
                // left2 is too large - we took too many elements from nums2
                // This means we need MORE from nums1 (and automatically fewer from nums2)
                // Move partition point to the RIGHT (take more from nums1)
                start = i1 + 1;
            } else {
                // Valid partition found!
                // The median is at the boundary between left and right partitions
                
                if((nums1.length + nums2.length) % 2 == 0) {
                    // Even total: median is average of the two middle elements
                    // These are: max(left partition) and min(right partition)
                    return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                } else {
                    // Odd total: median is the max of the left partition
                    // (the left partition has one more element due to the +1 in our formula)
                    return Math.max(left1, left2);
                }
            }
        }
        
        // Should never reach here with valid input
        // This would only occur if the input arrays are not properly sorted
        return -1;
    }
    
    /**
     * Test method demonstrating the algorithm with the dry run example.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        MedianTwoSortedArraysOptimal solver = new MedianTwoSortedArraysOptimal();
        
        // Example from the dry run
        int[] nums1 = {1, 3, 5, 7, 9, 11, 13, 15};
        int[] nums2 = {8, 11, 14, 17, 18, 21, 23, 26};
        System.out.println("Median: " + solver.findMedianSortedArrays(nums1, nums2)); // Output: 12.0
        
        // Additional test cases
        int[] test1a = {1, 3};
        int[] test1b = {2};
        System.out.println("Median: " + solver.findMedianSortedArrays(test1a, test1b)); // Output: 2.0
        
        int[] test2a = {1, 2};
        int[] test2b = {3, 4};
        System.out.println("Median: " + solver.findMedianSortedArrays(test2a, test2b)); // Output: 2.5
    }
}