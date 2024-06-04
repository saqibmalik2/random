package com.saqib.puzzles;

import java.util.Arrays;

/**
 * 
 * Codility coding problem
 * 
 * Rectangular map represented by a N (rows) x M (columns) (zero-indexed) array.
 * Value at any position in the array represents the colour of that pixel.
 * 
 * Two areas on the map belong to the same country only under following
 * conditions:
 * 
 * (i) have the same colour (ii) it is possible to move from one area to the
 * other orthogonally (north, east, south and west) without moving over any
 * area(s) of a different colour.
 * 
 * Given an array write a function that return number of different countries on
 * the map.
 * 
 * @author Saqib Malik
 *
 */

public class RectangularMapColours {
	
	int [] [] traversedSquares;
	int rowLength;
	int count = 0;

	public static void main(String[] args) {

		// should return 7
		int[][] testMap1 = new int[][] { { 1, 2, 2 }, { 3, 2, 2 }, { 4, 2, 5 }, { 6, 5, 5 }, { 7, 7, 7 } };
		
		// should return 6
		int[][] testMap2 = new int[][] { { 1, 2, 2 }, { 3, 2, 2 }, { 4, 2, 5 }, { 5, 5, 5 }, { 7, 7, 7 } };
		
		//should return 10
		int[][] testMap3 = new int[][] { { 1, 2, 2, 2 }, { 3, 2, 5, 2 }, { 6, 6, 7, 2 }, { 5, 6, 3, 1 }, { 7, 7, 7, 7 } };
		
		//should return 10
		int[][] testMap4 = new int[][] { { 1, 2, 5, 2 }, { 3, 2, 2, 2 }, { 6, 6, 7, 2 }, { 5, 6, 3, 1 }, { 7, 7, 7, 7 } };
		
		//should return 3
		int[][] testMap5 = new int[][] { { 1, 1, 1, 7 }, { 1, 7, 7, 1 }, { 1, 7, 7, 1  }, { 1, 7, 7, 1  }, { 1, 1, 1, 1} };
		
		//should return 2
		int[][] testMap6 = new int[][] { { 1, 1, 1, 1 }, { 1, 7, 7, 1 }, { 1, 7, 7, 1  }, { 1, 7, 7, 1  }, { 1, 1, 1, 1} };
		
		System.out.println(new RectangularMapColoursAlternative().numberOfCountriesOnMap(testMap1));
		System.out.println(new RectangularMapColoursAlternative().numberOfCountriesOnMap(testMap2));
		System.out.println(new RectangularMapColoursAlternative().numberOfCountriesOnMap(testMap3));
		System.out.println(new RectangularMapColoursAlternative().numberOfCountriesOnMap(testMap4));
		System.out.println(new RectangularMapColoursAlternative().numberOfCountriesOnMap(testMap5));
		System.out.println(new RectangularMapColoursAlternative().numberOfCountriesOnMap(testMap6));
		
	}
	
	/**
	 * 
	 * The solution works by performing a recursive 'walk' from each position. If a position has already been
	 * processed as part of a country then it is skipped over. The total number of countries will be equal to 
	 * the total number of walks performed. The best way to understand this solution would be to do a dry run using
	 * the example input maps in the main method.
	 * 
	 * @param inputArray - the provided map
	 * @return count - the number of distinct countries on this map
	 */
	public int numberOfCountriesOnMap(int[][] inputArray) {
		rowLength = inputArray[0].length;
		traversedSquares = new int [inputArray.length] [rowLength];
		
		/*
		 * create a matrix of the same dimensions as the original input matrix and fill
		 * it with 0s. As we 'walk' from each position, every position we walk to we cross off
		 * by setting it to 1.
		 * 
		 */
	
		for (int i=0; i < inputArray.length; i++) { 
			for (int j=0; j < rowLength; j++) { 
				traversedSquares [i] [j] = 0; 
			} 
		}
		
		// traverse the matrix, starting from position [0][0]
		for (int i=0; i < inputArray.length; i++) { 
			for (int j=0; j < rowLength; j++) { 
				/*
				 * if we've already done the walk from this position then no need to do it
				 * again. Keep advancing until we arrive at a position that hasn't already been traversed
				 * in a previous walk (i.e. a new country).
				 * The total number of countries will equal the total number of walks we did,
				 * as each walk represents an entire country.
				 * 
				 */
				if (traversedSquares [i] [j] == 0) {
					count++;
					doWalk(inputArray, i, j); 
				}
			} 
		}
		
		return count;
	}
	
	private void doWalk(int [] [] inputArray, int row, int column) {
		
		/*
		 * From each position there are potentially four ways we can move. Up, down, left and right.
		 * However, we need to check for the limits of the map (we can't move off the edges).
		 * If the the next square in the relevant direction has the same colour and hasn't already been traversed
		 * then we move to it.
		 */
		
		// moving right
		if (column+1 < rowLength && inputArray[row][column +1] == inputArray[row][column] && traversedSquares[row][column + 1] == 0) {
			traversedSquares[row][column] = 1;
			doWalk(inputArray, row, column+1);
		}
		
		// moving left
		if (column-1 >= 0 && inputArray[row][column-1] == inputArray[row][column] && traversedSquares[row][column - 1] == 0) {
			traversedSquares[row][column] = 1;
			doWalk(inputArray, row, column-1);
		}
		
		// moving down
		if (row-1 >= 0 && inputArray[row-1][column] == inputArray[row][column] && traversedSquares[row-1][column] == 0) {
			traversedSquares[row][column] = 1;
			doWalk(inputArray, row-1, column);
		}
		
		// moving up
		if (row+1 < inputArray.length && inputArray[row+1][column] == inputArray[row][column] && traversedSquares[row+1][column] == 0) {
			traversedSquares[row][column] = 1;
			doWalk(inputArray, row+1, column);
		}
		
		// if we can't move anywhere then the walk is finished and we can mark this position as traversed
		traversedSquares[row][column] = 1;
		return;
	}
	
}
