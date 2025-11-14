package com.saqib.puzzles;

import java.util.Arrays;
import java.util.Comparator;

public class ArrayComparator {
	
	static int[][] matrix = new int[][] {{3,2}, {1,7}, {4,5}, {1,9}, {6,0}};

	public static void main(String[] args) {
		ArrayComparator arrayComparator = new ArrayComparator();
		int[][] sortedArray = arrayComparator.sortMultiDimensionalArray(matrix);
		System.out.println(Arrays.deepToString(sortedArray));
	}
	
	public int[][] sortMultiDimensionalArray(int[][] inputArray) {
		
		Comparator<int[]> compareBySecondValue = Comparator.comparingInt((int[] a) -> a[1]);
		
		int[][] sortedArray = Arrays.stream(inputArray)
									.map(int[]::clone)
									.sorted(compareBySecondValue)
									.toArray(int[][]::new);
		
		return sortedArray;
	}

}
