package com.saqib.puzzles;

/**
 * Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
 *
 * Solution uses Dynamic Programming approach where each cell stores the side length of the largest square
 * that has that cell as its bottom-right corner.
 *
 * Time Complexity: O(m * n) where m is number of rows and n is number of columns
 * Space Complexity: O(m * n) for the DP table
 *
 * Example 1:
 * Input: matrix = [
 *   ["1","0","1","0","0"],
 *   ["1","0","1","1","1"],
 *   ["1","1","1","1","1"],
 *   ["1","0","0","1","0"]
 * ]
 * Output: 4
 *
 * Example 2:
 * Input: matrix = [["0","1"],["1","0"]]
 * Output: 1
 *
 * Example 3:
 * Input: matrix = [["0"]]
 * Output: 0
 * 
 * Example 4:
 * Input: matrix = [
 *   ["1","0","1","0","0"],
 *   ["1","0","1","1","1"],
 *   ["1","1","1","1","1"],
 *   ["1","0","1","1","1"]
 * ]
 * Output: 9
 * 
 * 
 * 
 **/
public class MaximumSquare {

    public static void main(String[] args) {
        MaximumSquare ms = new MaximumSquare();
//        char[][] matrix = new char[][] {
//            {'1','0','1','0','0'},
//            {'1','0','1','1','1'},
//            {'1','1','1','1','1'},
//            {'1','0','0','1','0'}
//        };
        
        char[][] matrix = new char[][] {  
	          {'1','0','1','0','0'},
	          {'1','0','1','1','1'},
	          {'1','1','1','1','1'},
	          {'1','0','1','1','1'}
        };
        
        //int maxSquareValue = ms.findMaximumSquare(matrix);
        int maxSquareValue = ms.findMaximumSquareOptimized(matrix);
        System.out.println("Maximum Square that can be made from this input matrix is: " + maxSquareValue);
    }

    /**
     * Finds the area of the largest square containing only 1's in the given binary matrix.
     * 
     * Uses dynamic programming where dp[i][j] represents the side length of the largest square
     * with bottom-right corner at position (i, j).
     * 
     * The recurrence relation is:
     * - If matrix[i][j] == '0': dp[i][j] = 0
     * - If matrix[i][j] == '1': dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])
     * 
     * In plain English:
     * 
     * 1. Each cell stores a number that answers: "If this cell is the bottom-right corner of a square 
     *    made entirely of 1's, what's the side length of the biggest square I can make?"
     * 
     * 2. To calculate that number: Look at the three cells to your left, directly above, and top-left diagonal. 
     *    Find whichever one has the smallest number. Your number is that smallest number plus 1 
     *    (assuming your current cell is a '1'). If your cell is a '0', your number is 0.
     *    
     * 3. Why take the minimum? Because you can only extend a square if ALL three neighbors can support it. 
     *    Think of it like building upward and rightward from existing squares - if the cell above you can only support a 1×1 square, 
     *    then even if your left and diagonal neighbors support huge 5×5 squares, you're bottlenecked. 
     *    You can only be as good as your weakest neighbor. 
     *    The minimum represents the largest square size that all three directions can consistently support, 
     *    and you extend it by 1 to include your current cell.
     * 
     * @param matrix the input binary matrix with characters '0' and '1'
     * @return the area of the largest square (side length squared)
     */
    private int findMaximumSquare(char[][] matrix) {
        // Create DP table to store the side length of largest square ending at each position
        int[][] dp = new int[matrix.length][matrix[0].length];
        
        // Track the maximum side length seen so far
        int maxSideLength = 0;
        
        // Iterate through each cell in the matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
            	// If current cell is '0', it cannot be part of any square (dp value stays 0)
                // The dp array initializes to 0 values by default, so no need to explicitly set it
                if (matrix[i][j] == '0') continue;
                
                // Base case: cells in first row or first column can only form 1x1 squares
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
                    maxSideLength = Math.max(maxSideLength, 1);
                    continue;
                }
                
                // DP recurrence: current cell's square size is limited by smallest neighbor + 1
                // We check: cell above, cell to left, and cell diagonally above-left
                // The minimum of these three determines how large a square we can extend
                dp[i][j] = 1 + Math.min(
                    Math.min(dp[i][j-1], dp[i-1][j-1]), 
                    dp[i-1][j]
                );
                
                // Update maximum side length if current cell has larger square
                maxSideLength = Math.max(maxSideLength, dp[i][j]);
            }
        }
        
        // Return area (side length squared)
        return maxSideLength * maxSideLength;
    }
    
    private int findMaximumSquareOptimized(char[][] matrix) {
    	
        // Create DP table to store the side length of largest square ending at each position
    	// We only actually need one row as at any point we are only considering three positions:
    	// immediately to the left (same row) and top left and immediately above (both one row above).
        int[] dp = new int[matrix[0].length];
        
        // Track the maximum side length seen so far
        int maxSideLength = 0;
        
        // Iterate through each cell in the matrix
        for (int i = 0; i < matrix.length; i++) {
        	int previous = 0;  // Initialize diagonal tracker for this row
        	
            for (int j = 0; j < matrix[0].length; j++) {
            	int temp = dp[j];  // Save the value before overwriting
            	
            	// If current cell is '0', it cannot be part of any square (dp value stays 0)
                if (matrix[i][j] == '0') {
                	dp[j] = 0;
                	continue;
                }
                
                // Base case: cells in first row or first column can only form 1x1 squares
                if (i == 0 || j == 0) {
                    dp[j] = 1;
                    maxSideLength = Math.max(maxSideLength, 1);
                    continue;
                }
                
                // DP recurrence: current cell's square size is limited by smallest neighbor + 1
                // We check: cell above, cell to left, and cell diagonally above-left
                // The minimum of these three determines how large a square we can extend
                dp[j] = 1 + Math.min(
                    Math.min(dp[j-1], previous), 
                    temp
                );
                
                // Update maximum side length if current cell has larger square
                maxSideLength = Math.max(maxSideLength, dp[j]);
                previous = temp;  // This temp becomes the diagonal for next column
            }
        }
        
        // Return area (side length squared)
        return maxSideLength * maxSideLength;
    }
    
    
}