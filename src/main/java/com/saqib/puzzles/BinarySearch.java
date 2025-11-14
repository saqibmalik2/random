package com.saqib.puzzles;

public class BinarySearch {
	/**
	 * 
	 * The iterative solution to perform a binary search for a particular integer in a pre-sorted integer array.
	 * 
	 * @param inputArray
	 * @param target
	 * @return the index of the target value or -1 if it isn't in the array
	 */
	public int binarySearch(int[] inputArray, int target) {
		int left = 0;
		int right = inputArray.length - 1;
		int offset = 0;
		int mid = 0;
		
		while (left <= right) {
			// the offset required to get to the midpoint between left and right
			offset = (right - left) / 2; 
			// the actual midpoint between left and right
			mid = left + offset;
			
			// note we could do mid = (right + left) / 2 but this runs a risk of integer overflow when (right + left)
			// reaches into the billions. All but guaranteed never to happen in production (which single sorted array
			// contains several billion elements?) but for correctness use the formulation I have. We strictly speaking
			// could simplify the offset and mid step into one line but I kept them separate to make it clear what was happening.
			
			
			// we've found our value
			if (inputArray[mid] == target) return mid;
			
			// target value is to the right of the mid value so search that side of the array
			if (inputArray[mid] < target) {
				left = mid + 1;
			}
			
			// target value is to the left of the mid value so search that side of the array
			if (inputArray[mid] > target) {
				right = mid - 1;
			}
		}
		
		// if we got here then the target number isn't in the array
		return -1; 
	}
	
	public int binarySearchRecursive(int[] inputArray, int target) {
		return binarySearchRecursiveWorker(inputArray, target, 0, inputArray.length - 1);
	}
	
	
	/**
	 * 
	 * Recursive solution for binary search.
	 * Note we don't copy the array but reuse the original array except with different left and right values.
	 * 
	 * @param inputArray
	 * @param target
	 * @param begin
	 * @param end
	 * @return the index of the target value or -1 if it doesn't exist in the array
	 */
	private int binarySearchRecursiveWorker(int[] inputArray, int target, int begin, int end) {
		int left = begin;
		int right = end;
		int mid;
		
		if (left <= right) {
			mid = left + ((right - left) / 2);
			
			//base case is where we find the value
			if (inputArray[mid] == target) return mid;
			
			
			//the middle value is less than the target so it must be to the right of it
			//perform another search using all the values to the right of mid 
			// i.e. make the start point for the search equal to mid + 1.
			if (inputArray[mid] < target) {
				return binarySearchRecursiveWorker(inputArray, target, mid + 1, end);
			}
			
			//the middle value is greater than the target so it must be to the left of it
			//perform another search using all the values to the left of mid
			// i.e. make the end point for the search equal to mid - 1.
			if (inputArray[mid] > target) {
				return binarySearchRecursiveWorker(inputArray, target, left, mid - 1);
			}
		}
		
		return -1;
	}

	public static void main(String[] args) {
		int[] testArray = new int[] {1,2,5,7,8,16};
		int target = 9;
		System.out.println("Index of " + target + " in the array is: " + new BinarySearch().binarySearchRecursive(testArray, target));
	
		BinarySearch bs = new BinarySearch();
		int[] arr = {1, 3, 5, 7, 9, 11};

		System.out.println(bs.binarySearch(arr, 7));   // 3
		System.out.println(bs.binarySearch(arr, 1));   // 0
		System.out.println(bs.binarySearch(arr, 11));  // 5
		System.out.println(bs.binarySearch(arr, 4));   // -1
	}

}
