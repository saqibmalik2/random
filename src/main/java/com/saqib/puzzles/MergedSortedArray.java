package com.saqib.puzzles;

import java.util.Arrays;

/**
 * Merges two sorted integer arrays {@code nums1} and {@code nums2} into one sorted array in place.
 *
 * <p>Both arrays are sorted, so we know the largest numbers are at the end of each.
 * We use three pointers: {@code i}, {@code j}, and {@code k}.</p>
 *
 * <ul>
 *   <li>{@code i} – initialised to {@code m - 1}, the last valid (unmerged) element in {@code nums1}</li>
 *   <li>{@code j} – initialised to {@code n - 1}, the last element in {@code nums2}</li>
 *   <li>{@code k} – initialised to {@code m + n - 1}, the final slot in {@code nums1} (the write position)</li>
 * </ul>
 *
 * <p>The values of {@code m} and {@code n} are provided as input parameters:
 * {@code m} represents the number of initialized elements in {@code nums1} (the portion containing real data),
 * and {@code n} represents the number of elements in {@code nums2}.
 * These values tell the algorithm where valid data ends and where the empty merge space begins.</p>
 *
 * <p>We compare {@code nums1[i]} and {@code nums2[j]}:</p>
 * <ul>
 *   <li>If {@code nums2[j]} is greater or equal, assign {@code nums1[k] = nums2[j]} and move {@code j} and {@code k} left.</li>
 *   <li>Otherwise, assign {@code nums1[k] = nums1[i]} and move {@code i} and {@code k} left.</li>
 * </ul>
 *
 * <p>This continues until all elements from {@code nums2} have been merged into {@code nums1}.
 * Any remaining {@code nums1} elements are already in the correct position because they’re smaller
 * than those already placed.</p>
 *
 * <p><b>Complexity:</b> O(m + n) time, O(1) space.</p>
 *
 * <p><b>Example:</b></p>
 * <pre>{@code
 * nums1 = [1, 2, 3, 0, 0, 0], m = 3
 * nums2 = [2, 5, 6],          n = 3
 *
 * Initial state:
 *   i = 2 (nums1[i] = 3)
 *   j = 2 (nums2[j] = 6)
 *   k = 5 (write position)
 *
 *   nums1: [1, 2, 3, 0, 0, 0]
 *                    ^     ^
 *                    i     k
 *
 *   nums2: [2, 5, 6]
 *                 ^
 *                 j
 *
 * Step 1 → compare 3 vs 6 → write 6 to nums1[5]
 * Step 2 → compare 3 vs 5 → write 5 to nums1[4]
 * Step 3 → compare 3 vs 2 → write 3 to nums1[3]
 *
 * Final result:
 *   nums1 = [1, 2, 2, 3, 5, 6]
 * }</pre>
 *
 * @author Saqib
 */
public final class MergedSortedArray {

    /**
     * Performs the in-place merge of two sorted arrays.
     *
     * @param nums1 first sorted array with size m + n
     * @param m number of initialized elements in nums1
     * @param nums2 second sorted array with size n
     * @param n number of elements in nums2
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;       // last valid element in nums1
        int j = n - 1;       // last element in nums2
        int k = m + n - 1;   // last index of nums1 (write position)

        // Fill nums1 from the back by comparing the largest remaining values
        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }

    /** Demonstration */
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        int m = 3, n = 3;

        new MergedSortedArray().merge(nums1, m, nums2, n);
        System.out.println("Merged array: " + Arrays.toString(nums1));
    }
}