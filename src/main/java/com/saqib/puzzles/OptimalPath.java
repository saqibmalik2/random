package com.saqib.puzzles;

import static java.lang.System.*;

import java.util.Arrays;

import org.springframework.stereotype.Component;


/**
 * You are an avid rock collector who lives in southern California. Some rare and desirable rocks just became
 * available in New York, so you are planning a cross-country road trip. There are several other rare rocks that you
 * could pick up along the way.
 * 
 * You have been given a grid filled with numbers, representing the number of rare rocks available in various cities across
 * the country. Your objective is to find the optimal path from So_Cal to New_York that would allow you to accumulate the most rocks
 * along the way.
 * 
 * Note: You can only travel either north (up) or east (right).
 * 
 * Example: 
 * 
 * { {0,0,0,0,5}, (New York)
 *   {0,1,1,1,0},
 *   {2,0,0,0,0}  (So_Cal)
 *   }
 *   
 * So total for the optimal path here would be: 2 + 1 + 1 + 1 + 5 = 10
 * 
 *
 */

@Component
public class OptimalPath {
	
	int total = 0;
	
	
	public static void main(String...args) {
		int[][] intialGrid = { {0,0,0,0,5}, 
				   			   {0,1,1,1,0},
				   			   {2,0,0,0,0}
				 			 };
		out.println(new OptimalPath().calculateOptimalPath(intialGrid));
	}
	
	/**
	 * @param A two dimensional integer array. This is a grid representing the all the cities, and positions at which rocks 
	 * 		  are located along with the number of rocks at each point.
	 * 
	 * @return An int givng the total number of rocks that can be collected along the optimal path as per the rules specified above.
	 */
	public int calculateOptimalPath(int[][] grid) {
			if (grid == null || grid.length == 0) throw new IllegalArgumentException("grid cannot be empty");
			total = grid[grid.length - 1][0];
			if (grid.length == 1 && grid[0].length == 1) {
				return total;
			}
			if (grid.length == 1) {
				int[] newIntArray;
				newIntArray = Arrays.copyOfRange(grid[0], 1, grid[0].length);
				int [][] newGrid = {newIntArray};
				return total += calculateOptimalPath(newGrid);
			}
			if (grid[0].length == 1) {
				int [][] newGrid = new int[1][1];
				newGrid[0][0] = grid[0][0];
				return total += calculateOptimalPath(newGrid);
			}
			else {
				int[][] upPathGrid = new int[grid.length - 1][];
				for (int i = grid.length - 2; i >= 0; i--) {
					upPathGrid[i] = Arrays.copyOfRange(grid[i], 0, grid[0].length);
				}
				int [][] alongPathGrid = new int[grid.length][grid[0].length - 1];
				for (int i = grid.length -1; i >= 0; i--) {
					alongPathGrid[i] = Arrays.copyOfRange(grid[i], 1, grid[0].length);
				}
				return total += Math.max(calculateOptimalPath(upPathGrid), calculateOptimalPath(alongPathGrid));
			}
	}

}

