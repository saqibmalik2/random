package com.saqib.puzzles;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A codility question.
 * 
 * A non-empty array A consisting of N integers is given.
 * 
 * A peak is an array element which is larger than its neighbors. More precisely, it is an index P such that 0 < P < N − 1,  A[P − 1] < A[P] and A[P] > A[P + 1].
  For example, the following array A:

    A[0] = 1
    A[1] = 2
    A[2] = 3
    A[3] = 4
    A[4] = 3
    A[5] = 4
    A[6] = 1
    A[7] = 2
    A[8] = 3
    A[9] = 4
    A[10] = 6
    A[11] = 2
has exactly three peaks: 3, 5, 10.

We want to divide this array into blocks containing the same number of elements. More precisely, we want to choose a number K that will yield the following blocks:

A[0], A[1], ..., A[K − 1],
A[K], A[K + 1], ..., A[2K − 1],
...
A[N − K], A[N − K + 1], ..., A[N − 1].
What's more, every block should contain at least one peak. Notice that extreme elements of the blocks (for example A[K − 1] or A[K]) can also be peaks, but only if they have both neighbours (including one in an adjacent blocks).

The goal is to find the maximum number of blocks into which the array A can be divided.

Array A can be divided into blocks as follows:

one block (1, 2, 3, 4, 3, 4, 1, 2, 3, 4, 6, 2). This block contains three peaks.
two blocks (1, 2, 3, 4, 3, 4) and (1, 2, 3, 4, 6, 2). Every block has a peak.
three blocks (1, 2, 3, 4), (3, 4, 1, 2), (3, 4, 6, 2). Every block has a peak. Notice in particular that the first block (1, 2, 3, 4) has a peak at A[3], because A[2] < A[3] > A[4], even though A[4] is in the adjacent block.
However, array A cannot be divided into four blocks, (1, 2, 3), (4, 3, 4), (1, 2, 3) and (4, 6, 2), because the (1, 2, 3) blocks do not contain a peak. Notice in particular that the (4, 3, 4) block contains two peaks: A[3] and A[5].

The maximum number of blocks that array A can be divided into is three.

Write a function:

class Solution { public int solution(int[] A); }

that, given a non-empty array A consisting of N integers, returns the maximum number of blocks into which A can be divided.

If A cannot be divided into some number of blocks, the function should return 0.

For example, given:

    A[0] = 1
    A[1] = 2
    A[2] = 3
    A[3] = 4
    A[4] = 3
    A[5] = 4
    A[6] = 1
    A[7] = 2
    A[8] = 3
    A[9] = 4
    A[10] = 6
    A[11] = 2
the function should return 3, as explained above.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [1..100,000];
each element of array A is an integer within the range [0..1,000,000,000].
 * 
 * 
 * 
 * 
 * 
 * @author Saqib Malik
 *
 */

public class Peaks {

	public static void main(String[] args) {
		int[] inputArrayOne = new int[] {1,2,3,4,3,4,1,2,3,4,6,2};
		int[] inputArrayTwo = new int[] {5,7,4,2,3,6,1,6,9,7};
		int[] inputArrayThree = new int[] {5,7,4,2,8,6,8,6,9,7,8,3};
		int[] inputArrayFour = new int[] {5,6,8,7,8,8,9};
		
		System.out.println(new Peaks().determineMaxNoOfBlocks(inputArrayThree));
	}
	
	public int determineMaxNoOfBlocks(int[] inputArray){
		
		if (inputArray == null || inputArray.length < 3) throw new IllegalArgumentException("Input array can't be null and must have at least three elements.");
		
		List<Integer> peaks = new ArrayList<>();
		int size = inputArray.length;
		int currentBlockSize;
		
		//find all the peaks in the given array
		for (int i=1; i < inputArray.length - 1; i++) {
			if (inputArray[i] > inputArray[i-1] && inputArray[i] > inputArray[i+1]) {
				peaks.add(i);
			}
		}
		
		System.out.println(peaks);
		
		//max possible no. of blocks cannot exceed no. of peaks so start from there and work down
		for (int noOfBlocks=peaks.size(); noOfBlocks > 0; noOfBlocks--) {
			
			// if we can't divide it evenly then skip
			if (size % noOfBlocks !=0) continue;
			
			//we already checked that it divides evenly
			currentBlockSize = size / noOfBlocks;
			
			//best to sketch this out on a piece of paper. basically we're checking that each block has at least one peak
			boolean foundPeakInAllGroups = true;
			boolean foundInCurrentGroup = false;
			
			//so if we're dealing with a block size of say 4 then we start checking for a peak between positions 0 and 3 and then move on
			int currentMarker = currentBlockSize - 1;
			
			//iterate through the list of peaks
			for (int currentPeak:peaks) {
				
				// we've found the first peak in this block and can ignore any subsequent ones
				if (currentPeak <= currentMarker && foundInCurrentGroup == false) {
					foundInCurrentGroup = true;
					continue;
				}
				
				// it means we couldn't find a peak in this group so this block size doesn't work - move onto the next one down
				if (currentPeak > currentMarker && foundInCurrentGroup == false) {
					foundPeakInAllGroups = false;
					break;
				}
				
				// we found at least one peak in this group so let's move onto the next group and check in there
				currentMarker = currentMarker + currentBlockSize;
			}
			
			// if the number of blocks returned by this block size worked then return this figure
			if (foundPeakInAllGroups) return noOfBlocks;
		}
		
		// if we reach here then we couldn't split the array up into fixed length blocks containing at least one peak in each
		return 0;
	}

}
