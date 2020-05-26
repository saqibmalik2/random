package com.saqib.puzzles;

/**
 * 
 * Codility coding problem
 * 
 * Rectangular map represented by a N (rows) x M (columns) (zero-indexed) array. Value at any position in the array represents the colour of that pixel.
 * 
 * Two areas on the map belong to the same country only under following conditions:
 * 
 * (i) have the same colour
 * (ii) it is possible to move from one area to the other orthogonally (north, east, south and west) without moving over any area(s) of a different colour.
 * 
 * Given an array write a function that return number of different countries on the map.
 * 
 * @author Saqib Malik
 *
 */

public class RectangularMapColours {

	public static void main(String[] args) {
		
		//should return 11 (draw a map on paper)
		//int[][] testMap = new int[][] {{5,4,4}, {4,3,4}, {3,2,4}, {2,2,2}, {3,3,4}, {1,4,4}, {4,1,1}};
		
		//should return 
		int[][] testMap = new int[][] {{1,2,2}, {3,2,2}, {4,2,5}, {6,5,5}, {7,7,7}};
		
		System.out.println(new RectangularMapColours().numberOfCountriesOnMap(testMap));

	}
	
	public int numberOfCountriesOnMap(int [][] inputArray) {
		int numberOfCountries = 0;
		int numOfRows = inputArray.length;
		int numOfColumns = inputArray[0].length;
		
		//traverse the array starting from the top left square (0,0) and moving along column by column and row by row
		for (int row=0; row < numOfRows; row++) {
			Integer currentColour = null;
			for (int column=0; column < numOfColumns; column++) {
				currentColour = inputArray[row][column];
				boolean isNewCountry = checkIfNewCountry(row, column, currentColour, inputArray);
				if (isNewCountry) numberOfCountries++;
			}
		}
		
		return numberOfCountries;
	}

	
	/**
	 * 
	 * Determine if we have a new country.
	 *  
	 * 
	 * @param row - the current row we're on
	 * @param column - the current column we're on
	 * @param currentColour - the colour of the current square
	 * @param inputArray - the entire array
	 * @return a boolean value indicating whether this square is part of a new country or now
	 */
	private boolean checkIfNewCountry(int row, int column, Integer currentColour, int[][] inputArray) {
		
		//very first square so just return true as by default it's a new country
		if (row == 0 && column == 0) {
			return true;
		}
		
		// we're on the first row
		if (row == 0) {
			if (inputArray[row][column - 1] != currentColour) {
				return true;
			}
			else {
				return false;
			}
		}
		
		// we are at the start of the row so can't test previous column so check the square directly above
		// and also the square directly in front as it could possibly provide a link to a previous square of the same colour
		// meaning it's not a new country
		if (column == 0) {
			if ((inputArray[row - 1][column] != currentColour)) {
				if (inputArray[row][column + 1] != currentColour) {
					return true;
				}
				else if (inputArray[row - 1][column + 1] != currentColour) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		
		//final column in the row so just check square above and square behind
		if (column == inputArray[0].length - 1) {
			if ((inputArray[row - 1][column] != currentColour) && (inputArray[row][column - 1] != currentColour)) return true;
		}
		
		//a square that's not the first or final column. first check square above and square before to see if they are both different colours.
		//then check the square ahead. if it's different then we definitely have a new country. if it's the same then we have to check the square above it.
		//if the square above it is the same colour then we already noted the start of this country so the function should return false.
		if ((inputArray[row - 1][column] != currentColour) && (inputArray[row][column - 1] != currentColour)) {
			if ((inputArray[row][column + 1] != currentColour)) {
				return true;
			}
			else if (inputArray[row - 1][column + 1] != currentColour) {
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;
	}

}
