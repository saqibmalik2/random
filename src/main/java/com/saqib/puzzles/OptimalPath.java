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
 * Space Complexity: O(m * n) for memoization table + O(m + n) for recursion stack
 */
@Component
public class OptimalPath {
    
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
        
        OptimalPath solution = new OptimalPath();
        out.println("Grid 1: " + solution.calculateOptimalPath(initialGrid1)); // Expected: 10
        out.println("Grid 2: " + solution.calculateOptimalPath(initialGrid2)); // Expected: 204
    }
    
    /**
     * Calculates the maximum number of rocks that can be collected on the optimal path
     * from bottom-left (So_Cal) to top-right (New_York).
     * 
     * Uses top-down dynamic programming (recursion with memoization).
     * 
     * @param grid A 2D integer array representing cities and rock counts
     * @return The maximum number of rocks collectible on the optimal path
     * @throws IllegalArgumentException if grid is null or empty
     */
    public int calculateOptimalPath(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid cannot be null or empty");
        }
        
        // Create memoization cache to store computed subproblems
        // memo[row][col] = max rocks from position (row, col) to destination
        Integer[][] memo = new Integer[grid.length][grid[0].length];
        
        // Start from bottom-left corner (So_Cal)
        return maxFrom(grid, grid.length - 1, 0, memo);
    }
    
    /**
     * Recursive helper with memoization.
     * 
     * Computes the maximum rocks collectible from position (row, col) to the destination.
     * 
     * @param grid The original grid
     * @param row Current row position
     * @param col Current column position
     * @param memo Memoization cache
     * @return Maximum rocks from this position to destination
     */
    private int maxFrom(int[][] grid, int row, int col, Integer[][] memo) {
        // Destination
        if (row == 0 && col == grid[0].length - 1) {
            return grid[row][col];
        }

        // Memo hit
        if (memo[row][col] != null) {
            return memo[row][col];
        }

        final int rowLength = grid[0].length - 1;

        int best;
        if (row == 0) {
            // Top row (can't go up): must go right
            best = grid[row][col] + maxFrom(grid, row, col + 1, memo);
        } else if (col == rowLength) {
            // Rightmost column (can't go right): must go up
            best = grid[row][col] + maxFrom(grid, row - 1, col, memo);
        } else {
            // Interior: choose the better of up/right
            int up = maxFrom(grid, row - 1, col, memo);
            int right = maxFrom(grid, row, col + 1, memo);
            best = grid[row][col] + Math.max(up, right);
        }

        memo[row][col] = best;
        return best;
    }
    
}