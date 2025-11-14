package com.saqib.puzzles;

import java.util.stream.IntStream;

/**
 * Computes the minimum edit distance (Levenshtein distance) between two strings.
 * 
 * <p>The edit distance is the minimum number of single-character operations 
 * (insertions, deletions, or replacements) required to transform one string into another.
 * 
 * <p><b>Algorithm:</b> Dynamic Programming with a 2D table where dp[i][j] represents 
 * the minimum operations needed to convert the first i characters of word1 into 
 * the first j characters of word2.
 * 
 * <p><b>Example:</b> Converting "horse" to "ros"
 * <pre>
 * DP Table:
 *         ""  r   o   s
 *     ""   0   1   2   3
 *     h    1   1   2   3
 *     o    2   2   1   2
 *     r    3   2   2   2
 *     s    4   3   3   2
 *     e    5   4   4   3
 * 
 * Result: dp[5][3] = 3 operations
 * One possible sequence:
 *   horse -> rorse (replace 'h' with 'r')
 *   rorse -> rose  (delete 'r')
 *   rose  -> ros   (delete 'e')
 * </pre>
 * 
 * <p><b>Time Complexity:</b> O(m * n) where m = word1.length, n = word2.length
 * <br><b>Space Complexity:</b> O(m * n) for the DP table
 * 
 * @author Saqib
 */
public class MinimumDistance {

    /**
     * Calculates the minimum number of operations to convert word1 to word2.
     * 
     * <p>Available operations:
     * <ul>
     *   <li><b>Insert</b> a character</li>
     *   <li><b>Delete</b> a character</li>
     *   <li><b>Replace</b> a character</li>
     * </ul>
     * 
     * @param word1 the source string to transform
     * @param word2 the target string
     * @return the minimum number of operations required
     * 
     * @example
     * <pre>
     * minDistance("horse", "ros")  returns 3
     * minDistance("intention", "execution")  returns 5
     * minDistance("abc", "abc")  returns 0
     * </pre>
     */
    public int minDistance(String word1, String word2) {
        // Trivial case: if strings are identical, no operations needed
        if (word1.equals(word2)) return 0;

        // Create DP table with dimensions (word1.length + 1) x (word2.length + 1)
        // Extra row/column for empty string base cases
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        // Base case: Initialize first column (converting word1 prefixes to empty string)
        // dp[i][0] = i because we need i deletions to convert i characters to empty string
        // Examples: "h" -> "" costs 1, "ho" -> "" costs 2, etc.
        IntStream.range(0, word1.length() + 1).forEach(i -> dp[i][0] = i);

        // Base case: Initialize first row (converting empty string to word2 prefixes)
        // dp[0][j] = j because we need j insertions to convert empty string to j characters
        // Examples: "" -> "r" costs 1, "" -> "ro" costs 2, etc.
        IntStream.range(0, word2.length() + 1).forEach(i -> dp[0][i] = i);

        // Fill the DP table iteratively
        // i represents position in word1 (1-indexed in table, 0-indexed in string)
        // j represents position in word2 (1-indexed in table, 0-indexed in string)
        for (int i = 1; i < word1.length() + 1; i++) {
            for (int j = 1; j < word2.length() + 1; j++) {
                
                // Compare current characters (i-1 and j-1 because table is 1-indexed)
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // Characters match: no operation needed
                    // Take the value from diagonal (converting previous prefixes)
                    // Example: "hor" -> "ror" when 'r' == 'r', take cost from "ho" -> "ro"
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Characters don't match: try all three operations and pick minimum
                    
                    // Operation 1: REPLACE
                    // dp[i-1][j-1] + 1: Convert prefixes, then replace current char
                    // Example: "" -> "" costs 0, replace 'h' with 'r' costs 1, total = 1
                    int replace = dp[i - 1][j - 1] + 1;
                    
                    // Operation 2: INSERT
                    // dp[i][j-1] + 1: Already handled word1 prefix, insert char from word2
                    // Example: "h" -> "" costs 1, insert 'r' costs 1, total = 2
                    int insert = dp[i][j - 1] + 1;
                    
                    // Operation 3: DELETE
                    // dp[i-1][j] + 1: Already made word2 prefix, delete char from word1
                    // Example: "" -> "r" costs 1, delete 'h' costs 1, total = 2
                    int delete = dp[i - 1][j] + 1;
                    
                    // Take minimum of all three operations
                    dp[i][j] = Math.min(replace, Math.min(insert, delete));
                }
            }
        }

        // The bottom-right cell contains the answer: 
        // minimum operations to convert entire word1 to entire word2
        return dp[word1.length()][word2.length()];
    }

    public static void main(String[] args) {
        MinimumDistance solution = new MinimumDistance();
        
        // Test cases
        System.out.println(solution.minDistance("horse", "ros"));           // Expected: 3
        System.out.println(solution.minDistance("intention", "execution")); // Expected: 5
        System.out.println(solution.minDistance("abc", "abc"));             // Expected: 0
    }
}