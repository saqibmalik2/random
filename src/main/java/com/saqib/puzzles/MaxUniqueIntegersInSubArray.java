package com.saqib.puzzles;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;


/*
 * Input Format
 * 
 * The first line of input contains two integers N and M representing the total
 * number of integers and the size of the subarray, respectively. The next line
 * contains space separated integers.
 * 
 * Read input from STDIN. Print output to STDOUT.
 * 
 * 
 * Output Format
 * 
 * Print the maximum number of unique integers among all possible contiguous
 * subarrays of size .
 * 
 * Sample Input 6 3 5 3 5 2 3 2
 * 
 * Sample Output 3
 * 
 * Explanation
 * 
 * In the sample testcase, there are 4 subarrays of contiguous numbers.
 * 
 * (5,3,5) - Has 2 unique numbers.
 * 
 * (3,5,2) - Has 3 unique numbers.
 * 
 * (5,2,3) - Has 3 unique numbers.
 * 
 * (2,3,2) - Has 2 unique numbers.
 * 
 * In these subarrays, there are unique numbers, respectively. The maximum
 * amount of unique numbers among all possible contiguous subarrays is 3.
 * 
 */

public class MaxUniqueIntegersInSubArray {
	
	public static void main(String[] args) {
		
		try (Scanner in = new Scanner(System.in)) {
			int sizeOfArray = in.nextInt();
			int sizeOfSubArray = in.nextInt();
			int maxSize = 0;
			LinkedList<Integer> subArray = new LinkedList<>();
			HashSet<Integer> set = new HashSet<>();
			
			for (int i=0; i < sizeOfSubArray; i++) {
				int nextNumber = in.nextInt();
				set.add(nextNumber);
				subArray.add(nextNumber);
			}
			
			maxSize = set.size();
			
			for (int i=sizeOfSubArray; i < sizeOfArray; i++) {
				int nextNumber = in.nextInt();
				int head = subArray.removeFirst();
				if (!subArray.contains(head)) set.remove(head);
				subArray.offer(nextNumber);
				set.add(nextNumber);
				maxSize = Math.max(maxSize, set.size());
			}
			
			System.out.println(maxSize);
		}
	}
	

}
