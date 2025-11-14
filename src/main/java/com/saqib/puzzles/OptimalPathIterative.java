package com.saqib.puzzles;

import static java.lang.System.*;

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
 * }
 *
 * Total for the optimal path: 2 + 1 + 1 + 1 + 5 = 10
 *
 * Time Complexity: O(m * n) where m = rows, n = columns
 * Space Complexity: O(m * n) but reducible to O(n)
 */
@Component
public class OptimalPathIterative {

    public static void main(String... args) {
        int[][] initialGrid1 = {
            {0, 0, 0, 0, 5},
            {0, 1, 1, 1, 0},
            {2, 0, 0, 0, 0}
        };

        int[][] initialGrid2 = {
            {  1, 100,   1, 1},
            {  1,   1, 100, 1},
            {100,   1,   1, 1}
        };

        OptimalPathIterative solution = new OptimalPathIterative();
        out.println("Grid 1: " + solution.calculateOptimalPath(initialGrid1)); // Expected: 10
        out.println("Grid 2: " + solution.calculateOptimalPath(initialGrid2)); // Expected: 204
    }

    /**
     * Calculates the maximum number of rocks collectible using space-optimized DP.
     * 
     * <p><b>Space Optimization:</b> Uses only O(n) space instead of O(m*n) by maintaining 
     * a single row that gets updated as we process each row of the grid from bottom to top.
     * 
     * <p><b>Key Insight:</b> At any point, we only need the current row and the row 
     * below it. So instead of a 2D table, we reuse a single array, updating it in-place.
     * 
     * <p><b>Example progression for grid:</b>
     * <pre>
     * Input:
     * {0, 0, 0, 0, 5}
     * {0, 1, 1, 1, 0}
     * {2, 0, 0, 0, 0}
     * 
     * 
     * After top row:         [2, 3, 4, 5, 10]
     * After middle row:      [2, 3, 4, 5, 5]
     * Initial (bottom row):  [2, 2, 2, 2, 2]
     * 
     * Result: 10
     * </pre>
     *
     * @param grid A 2D integer array where grid[i][j] represents the number of rocks 
     *             at position (i,j). Grid coordinates: (0,0) is top-left, 
     *             (rows-1, 0) is bottom-left (start), (0, cols-1) is top-right (destination).
     * @return The maximum number of rocks collectible on any valid path from bottom-left 
     *         to top-right, moving only up or right
     * @throws IllegalArgumentException if grid is null, empty, or has no columns
     */
    public int calculateOptimalPath(int[][] grid) {
        // Validate input
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid cannot be null or empty");
        }

        // Cache dimensions for readability
        int noOfRows = grid.length;
        int noOfCols = grid[0].length;

        // Single DP array - will be reused and updated for each row
        // Initially represents the bottom row, then gets updated row by row
        int[] dp = new int[noOfCols];
        
        // Initialize to the bottom left corner (starting position / So_Cal)
        dp[0] = grid[noOfRows - 1][0];

        // Fill the bottom row (base case: can only move RIGHT from starting position)
        // Starting from index 1 since dp[0] is already initialized
        // Each position accumulates: previous position's total + current position's rocks
        // Example: If bottom row is [2, 0, 0, 0, 0], dp becomes [2, 2, 2, 2, 2]
        for (int n = 1; n < noOfCols; n++) {
            dp[n] = dp[n - 1] + grid[noOfRows - 1][n];
        }
        
        int valueAtCurrentPosition;

        // Process each row from second-to-last up to the top
        for (int i = noOfRows - 2; i >= 0; i--) {
            // Update leftmost column: can only come from below
            // Add current row's value to the existing dp[0] (which holds value from row below)
            dp[0] += grid[i][0];
            
            // Process remaining columns left to right
            for (int j = 1; j < noOfCols; j++) {
                valueAtCurrentPosition = grid[i][j];
                
                // Two choices:
                // 1. dp[j-1]: coming from LEFT (already updated for current row)
                // 2. dp[j]: coming from BELOW (still holds value from previous row)
                // Take maximum and add current position's rocks
                dp[j] = Math.max(valueAtCurrentPosition + dp[j - 1], 
                                valueAtCurrentPosition + dp[j]);
            }
        }
        
        // The rightmost position contains the answer (destination / New_York)
        return dp[noOfCols - 1];
    }

}