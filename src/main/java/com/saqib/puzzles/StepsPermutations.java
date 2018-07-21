package com.saqib.puzzles;

public class StepsPermutations {
	
	/**
	 * Tribonacci sequence puzzle
	 */
	
	private static int numberOfPermutations(int totalNumberOfSteps) {
		
		//base cases first
		if (totalNumberOfSteps < 0) throw new IllegalArgumentException();
		if (totalNumberOfSteps == 0 || totalNumberOfSteps == 1) return 1;
		if (totalNumberOfSteps == 2) return 2;
		
		//maximum number of steps we can take at a time is 3
		return numberOfPermutations(totalNumberOfSteps - 3) + numberOfPermutations(totalNumberOfSteps - 2) + numberOfPermutations(totalNumberOfSteps - 1);
	}

	public static void main(String[] args) {
		System.out.println(numberOfPermutations(13));
	}
	
}
