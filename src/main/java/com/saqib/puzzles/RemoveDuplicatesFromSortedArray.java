package com.saqib.puzzles;

/*Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that each unique element appears only once. The relative order of the elements should be kept the same. Then return the number of unique elements in nums.

Consider the number of unique elements of nums to be k, to get accepted, you need to do the following things:

Change the array nums such that the first k elements of nums contain the unique elements in the order they were present in nums initially. The remaining elements of nums are not important as well as the size of nums.
Return k.
Custom Judge:

The judge will test your solution with the following code:

int[] nums = [...]; // Input array
int[] expectedNums = [...]; // The expected answer with correct length

int k = removeDuplicates(nums); // Calls your implementation

assert k == expectedNums.length;
for (int i = 0; i < k; i++) {
    assert nums[i] == expectedNums[i];
}
If all assertions pass, then your solution will be accepted.

 

Example 1:

Input: nums = [1,1,2]
Output: 2, nums = [1,2,_]
Explanation: Your function should return k = 2, with the first two elements of nums being 1 and 2 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).
Example 2:

Input: nums = [0,0,1,1,1,2,2,3,3,4]
Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
Explanation: Your function should return k = 5, with the first five elements of nums being 0, 1, 2, 3, and 4 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).*/

public class RemoveDuplicatesFromSortedArray {
	
	
	/**
	 * 
	 * Two positions: currentPosition (self-explanatory) and nextUniquePosition (the position in the array containing...
	 * ...the next distinct number in the array).
	 * We start from currentPosition = 0 and proceed through the array. 
	 * 
	 * On each pass we first find the position of the next
	 * unique number. If we can't then we are finished and we've counted all the unique numbers.
	 * Otherwise we move the currentPosition along one and change the value at that position to the next distinct number we've found.
	 * 
	 * We won't ever reach the second of the two return statements.
	 * 
	 * @param nums
	 * @return k - the number of unique numbers in the array
	 */
	public int removeDuplicates(int[] nums) {
		
		int currentPosition = 0;
		int nextUniquePosition = 0;
		int k=1; //there has to be at least one unique number
		
		while (currentPosition < nums.length) {
			boolean newUniqueNumberFound = false;
			for (int i=nextUniquePosition; i < nums.length; i++) {
				if (nums[i] != nums[currentPosition]) {
					nextUniquePosition = i;
					newUniqueNumberFound = true;
					break;
				}
			}
			if (!newUniqueNumberFound) {
				return k;
			}
			currentPosition++;
			nums[currentPosition] = nums[nextUniquePosition];
			k++;
		}

		return k;
	}
	

	public static void main(String[] args) {
		//int[] nums = {0,0,1,1,1,2,2,3,3,4};
		int[] nums = {0,1,2,3,4};
		RemoveDuplicatesFromSortedArray rdfsa = new RemoveDuplicatesFromSortedArray();
		System.out.println(rdfsa.removeDuplicates(nums));
	}

}
