package com.saqib.puzzles;

import java.util.LinkedList;

/**
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return
 * the median of the two sorted arrays.
 * 
 * The overall run time complexity should be O(log (m+n)).
 * 
 * Example 1:
 * 
 * Input: nums1 = [1,3], nums2 = [2] Output: 2.00000 Explanation: merged array =
 * [1,2,3] and median is 2. Example 2:
 * 
 * Input: nums1 = [1,2], nums2 = [3,4] Output: 2.50000 Explanation: merged array
 * = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
 * 
 * 
 * Constraints:
 * 
 * nums1.length == m 
 * nums2.length == n 
 * 0 <= m <= 1000 
 * 0 <= n <= 1000 1 <= m + n <= 2000 
 * 
 * -10^6 <= nums1[i], nums2[i] <= 10^6
 * 
 */

public class MedianTwoSortedArrays {
	
	public static void main(String[] args) {
		MedianTwoSortedArrays testClass = new MedianTwoSortedArrays();
		// a few test cases (comment in and out as required)
		//double median = testClass.findMedianSortedArrays(new int[] {1,3,12,19,30,47}, new int[] {5,7,32});
		//double median = testClass.findMedianSortedArrays(new int[] {1,2}, new int[] {3,4});
		//double median = testClass.findMedianSortedArrays(new int[] {}, new int[] {});
		double median = testClass.findMedianSortedArrays(new int[] {}, new int[] {2,3});
		System.out.println(median);
	}
	
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		
		 LinkedList<Integer> mergedList = new LinkedList<>();
		 
		 // use this boolean if either of the two input arrays are empty in which case the usual
		 // procedure to build up the merged list can be skipped
		 boolean skip = false;
		 
		 // if both input arrays are empty return 0 as the median (I suppose null would also make sense)
		 if (nums1.length == 0 && nums2.length ==0) {
			 return 0.0d;
		 }
		 
		 // if the first input array is empty then the merged list will consist just of the elements of
		 // the second array (note the second array cannot also be empty as this scenario would have been
		 // caught in the first check above). If the second input array is empty then the same but in reverse.
		 if (nums1.length == 0) {
			 for (Integer value:nums2) {
				 mergedList.addFirst(value);
			 }
			 skip = true;
		 }
		 else if (nums2.length == 0) {
			 for (Integer value:nums1) {
				 mergedList.addFirst(value);
			 }
			 skip = true;
		 }
		 
		 // we start at the end of both input arrays (bear in mind both are pre-sorted)
		 int nums1Position = nums1.length - 1;;
		 int nums2Position = nums2.length - 1;;
		 
		 if (!skip) {
			 // while there are still elements in either of the two input arrays carry on processing
			 while ((nums1Position >= 0) && (nums2Position >= 0) && !skip) {
				 // if the number at the first array's position pointer is bigger than or equal to
				 // the number at the second array's position pointer then add this number to the start
				 // of the merged list and reduced the first array's position pointer by one (leave the second
				 // array's position pointer as it is). Otherwise do the same but in reverse.
				 if (nums1[nums1Position] >= nums2[nums2Position]) {
					 mergedList.addFirst(nums1[nums1Position]);
					 nums1Position--;
				 }
				 else {
					 mergedList.addFirst(nums2[nums2Position]);
					 nums2Position--;
				 }
			 }
			 
			 // at this point one of the two input arrays has been full copied into the merged list
			 // so all that remains is to copy in the remaining elements of the other array.
			 if (nums2Position < 0 ) {
				 for (int i=nums1Position; i>-1; i--) {
					 mergedList.addFirst(nums1[i]);
				 }
			 }
			 else {
				 for (int i=nums2Position; i>-1; i--) {
					 mergedList.addFirst(nums2[i]);
				 }
			 }
			 
		 }
		 
	
		 int middlePosition = Math.floorDiv(mergedList.size(), 2);
		 
		 //	the median value is the mean of the middle two values if the number of elements in the merged list 
		 // is an even number. if it's odd then it's the value which has the same number of elements on either side of it.
		 if (mergedList.size() % 2 == 0) {
			 double median = 0.0d;
			 median = (double) (mergedList.get(middlePosition) + mergedList.get(middlePosition - 1)) / 2;
			 return median;
		 }
		 else {
			 return (double) mergedList.get(middlePosition);
		 }
		 
	 }
	
	 
}
