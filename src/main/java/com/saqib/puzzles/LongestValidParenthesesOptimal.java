package com.saqib.puzzles;

/**
 * Longest Valid Parentheses - Dynamic Programming Solution
 * 
 * <h2>The Core Idea:</h2>
 * Create a DP array where {@code dp[i]} represents the length of the longest valid
 * parentheses substring ending at index i.
 * <p>
 * <b>Why "ending at index i"?</b> Because this constraint makes it possible to build
 * up solutions incrementally. If a valid substring ends at position i, we can
 * use information about what came before it.
 * </p>
 *
 * <h2>Algorithm:</h2>
 * Iterate through the string from index 0 ascending. If we encounter a '(' then
 * place a 0 in the corresponding position in the dp array. <b>Why?</b> Because no
 * valid substring can end with a '('.
 * <p>
 * If we encounter a ')' then there are two cases to consider:
 * </p>
 *
 * <h3>CASE 1: s[i-1] = '('</h3>
 * This means we've got a valid () pair. This pair covers positions s[i-1] and s[i] 
 * so take the {@code dp[i-2]} value (the longest valid substring at the point 
 * immediately preceding the pair we just encountered) and add 2 to it and assign it 
 * to {@code dp[i]}. Obviously check to make sure i-2 doesn't go below 0 and if it 
 * does then the value to add defaults to 0.
 *
 * <h3>CASE 2: s[i-1] = ')'</h3>
 * Here we need to check whether it forms part of a nested bracket sequence. 
 * <b>How can we do that?</b>
 * <p>
 * Well we know {@code dp[i-1]} gives us the longest valid subsequence at s[i-1] so
 * let's go back to the character immediately before that subsequence and see
 * if it's a '('.
 * </p>
 * <p>
 * Since the longest valid subsequence that terminates at i-1 is given by {@code dp[i-1]}
 * and we need to check just before it, then the position we need to check is:
 * </p>
 * <pre>
 *     s[i - dp[i-1] - 1]
 * </pre>
 * <p>
 * Where {@code s[i - dp[i-1] - 1] = ')'} then {@code dp[i] = 0}, as the closing 
 * bracket we found isn't a valid character for that position.
 * </p>
 * <p>
 * If it is a '(' then we need to add two values to it: 2 for the valid
 * parentheses pair we just formed AND the dp value for what directly preceded
 * the opening bracket we just found.
 * </p>
 * <ul>
 *   <li>{@code dp[i-1] + 2} is for the new nested bracket pair we just formed.</li>
 *   <li>Adding in the value of the longest valid parentheses string terminating
 *       immediately before the opening of that bracket pair gives:</li>
 * </ul>
 * <pre>
 *     dp[i] = dp[i-1] + 2 + dp[i - dp[i-1] - 2]
 * </pre>
 *
 * <h2>Time and Space Complexity:</h2>
 * This is a one-pass O(n) dynamic programming solution: each position in the
 * string is processed once, with O(1) work per iteration. The dp array uses
 * O(n) auxiliary space.
 *
 * <h2>Dry Run Examples:</h2>
 *
 * <h3>1) s = "()()"</h3>
 * <pre>
 * Index:  0  1  2  3
 * Char:   (  )  (  )
 *
 * Initial state (all dp = 0):
 * dp:     0  0  0  0
 *
 * i = 1, s[1] = ')', s[0] = '(' → CASE 1
 *   dp[1] = 2 + dp[-1] (treated as 0) = 2
 * dp:     0  2  0  0
 *
 * i = 2, s[2] = '(' → cannot end a valid substring
 * dp:     0  2  0  0
 *
 * i = 3, s[3] = ')', s[2] = '(' → CASE 1
 *   dp[3] = 2 + dp[1] = 2 + 2 = 4
 * dp:     0  2  0  4
 *
 * Final dp for "()()":
 * dp:     0  2  0  4
 * </pre>
 *
 * <h3>2) s = "()((()))"</h3>
 * <pre>
 * Index:  0  1  2  3  4  5  6  7
 * Char:   (  )  (  (  (  )  )  )
 *
 * Initial state (all dp = 0):
 * dp:     0  0  0  0  0  0  0  0
 *
 * i = 1, s[1] = ')', s[0] = '(' → CASE 1
 *   dp[1] = 2 + dp[-1] (treated as 0) = 2
 * dp:     0  2  0  0  0  0  0  0
 *
 * i = 2, s[2] = '(' → cannot end a valid substring
 * dp:     0  2  0  0  0  0  0  0
 *
 * i = 3, s[3] = '(' → cannot end a valid substring
 * dp:     0  2  0  0  0  0  0  0
 *
 * i = 4, s[4] = '(' → cannot end a valid substring
 * dp:     0  2  0  0  0  0  0  0
 *
 * i = 5, s[5] = ')', s[4] = '(' → CASE 1
 *   dp[5] = 2 + dp[3] = 2 + 0 = 2
 * dp:     0  2  0  0  0  2  0  0
 *
 * i = 6, s[6] = ')', s[5] = ')' → CASE 2
 *   dp[5] = 2, so check s[6 - dp[5] - 1] = s[3] = '('
 *   dp[6] = dp[5] + 2 + dp[6 - dp[5] - 2]
 *          = 2 + 2 + dp[2]
 *          = 4
 * dp:     0  2  0  0  0  2  4  0
 * </pre>
 * 
 * <h3>IMPORTANT NOTE:</h3>
 * <p>
 * The 2 from {@code dp[1]} cannot yet be added to the overall total as at s[6] 
 * the opening bracket at s[2] hasn't been closed off and so considering s[0] to 
 * s[6] "()((())" you have two separate valid sequences. 
 * </p>
 * <p>
 * One is of length 2 "()". The other is of length 4 "(())". In between is an 
 * invalid '(' which will only become valid once we move to s[7] and match it 
 * with the ')' that is there.
 * </p>
 * <p>
 * When that bracket IS closed off at the next step (coming up) it not only adds 
 * another 2 to the value of 4 we have but also means that the 2 at {@code dp[1]} 
 * can be included as now s[0] to s[7] forms a contiguous valid sequence.
 * </p>
 * 
 * <pre>
 * i = 7, s[7] = ')', s[6] = ')' → CASE 2
 *   dp[6] = 4, so check s[7 - dp[6] - 1] = s[2] = '('
 *   dp[7] = dp[6] + 2 + dp[7 - dp[6] - 2]
 *          = 4 + 2 + dp[1]
 *          = 8
 * dp:     0  2  0  0  0  2  4  8
 *
 * Final dp for "()((()))":
 * dp:     0  2  0  0  0  2  4  8
 * </pre>
 */
class LongestValidParenthesesOptimal {
    public int longestValidParentheses(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }

        int n = s.length();
        int[] dp = new int[n];  // dp[i] = longest valid substring ending at i
        int maxLen = 0;

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {
                // Case 1: ...()
                if (s.charAt(i - 1) == '(') {
                    dp[i] = 2;
                    if (i >= 2) {
                        dp[i] += dp[i - 2];
                    }
                } else {
                    // Case 2: ...)) — try to link current ')' to a matching '('
                    // dp[i-1] is the valid block ending just before this ')'
                    // Check the character *before* that block to see if it's '('
                    if (i - dp[i - 1] - 1 >= 0 &&                      // ensure index is not before start of string
                        s.charAt(i - dp[i - 1] - 1) == '(') {         // found matching '('

                        dp[i] = dp[i - 1] + 2;                         // match outer pair + inner block

                        // Add dp[...] before that '(' if it exists
                        if (i - dp[i - 1] - 2 >= 0) {                  // ensure index stays within bounds
                            dp[i] += dp[i - dp[i - 1] - 2];
                        }
                    }
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        }
        return maxLen;
    }
}