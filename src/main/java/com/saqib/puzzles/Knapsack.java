package com.saqib.puzzles;

import java.util.Arrays;

/**
 * Solves the 0/1 Knapsack Problem using dynamic programming.
 *
 * <h2>Problem Definition</h2>
 * <p>
 * The 0/1 Knapsack Problem is a classic combinatorial optimisation problem. Given a knapsack
 * with a fixed weight capacity and a set of items—each with an associated weight and value—the
 * goal is to determine the subset of items that maximises total value without exceeding the
 * knapsack's capacity.
 * </p>
 *
 * <p>
 * The "0/1" designation indicates that each item presents a binary choice: include it entirely (1)
 * or exclude it entirely (0). Fractional items are not permitted, which distinguishes this problem
 * from the Fractional Knapsack variant (solvable via a greedy algorithm).
 * </p>
 *
 * <h2>Formal Definition</h2>
 * <pre>
 * Given:
 *   - A knapsack with capacity W
 *   - n items where item i has weight w[i] and value v[i]
 *
 * Find:
 *   A subset S of items that maximises Σ v[i] for i ∈ S
 *   subject to the constraint Σ w[i] ≤ W for i ∈ S
 * </pre>
 *
 * <h2>Computational Complexity</h2>
 * <p>
 * The 0/1 Knapsack Problem is NP-hard. No polynomial-time algorithm is known. However, dynamic
 * programming provides a pseudo-polynomial time solution with complexity O(nW), where n is the
 * number of items and W is the knapsack capacity. This is termed "pseudo-polynomial" because
 * the complexity depends on the numeric value of W rather than the number of bits required to
 * represent it.
 * </p>
 *
 * <h2>Dynamic Programming Approach</h2>
 * <p>
 * The problem exhibits two properties that make it amenable to dynamic programming:
 * </p>
 * <ul>
 *   <li><b>Optimal Substructure</b>: The optimal solution to the whole problem contains optimal
 *       solutions to its subproblems.</li>
 *   <li><b>Overlapping Subproblems</b>: The same subproblems are encountered repeatedly during
 *       a naive recursive solution.</li>
 * </ul>
 *
 * <h2>The Recurrence Relation</h2>
 * <p>
 * Define dp[i][w] as the maximum value achievable using items 0 to i-1 with capacity w.
 * For each item i with weight w[i] and value v[i]:
 * </p>
 * <pre>
 * dp[i][w] = max(dp[i-1][w], v[i] + dp[i-1][w - w[i]])   if w[i] ≤ w
 * dp[i][w] = dp[i-1][w]                                   if w[i] &gt; w
 * </pre>
 *
 * <h2>This Class</h2>
 * <p>
 * This class provides two canonical implementations:
 * </p>
 * <ul>
 *   <li>{@link #solveRecursiveWithMemoisation(int[], int[], int)} - Top-down recursive approach
 *       with memoisation</li>
 *   <li>{@link #solveBottomUp(int[], int[], int)} - Bottom-up iterative approach using tabulation</li>
 * </ul>
 * <p>
 * Both approaches have identical time and space complexity: O(nW) time and O(nW) space.
 * </p>
 *
 * @author Generated for educational purposes
 * @version 1.0
 */
public class Knapsack {

    /**
     * Solves the 0/1 Knapsack Problem using top-down recursion with memoisation.
     *
     * <p>
     * This approach starts from the full problem (all items, full capacity) and recursively
     * breaks it down into smaller subproblems. Memoisation stores the results of solved
     * subproblems in a cache, ensuring each unique (itemCount, remainingCapacity) pair is
     * computed only once.
     * </p>
     *
     * <h3>How It Works</h3>
     * <ol>
     *   <li>Create a 2D memo array initialised to -1 (indicating "not yet computed")</li>
     *   <li>For each recursive call, check if the result is already cached</li>
     *   <li>If cached, return immediately; otherwise, compute the result recursively</li>
     *   <li>Before returning, store the computed result in the cache</li>
     * </ol>
     *
     * <h3>Without Memoisation</h3>
     * <p>
     * A naive recursive implementation would have O(2^n) time complexity because it recomputes
     * the same subproblems repeatedly. Memoisation reduces this to O(nW) by ensuring each
     * subproblem is solved exactly once.
     * </p>
     *
     * <h3>Complexity</h3>
     * <ul>
     *   <li><b>Time:</b> O(nW) where n = number of items, W = capacity</li>
     *   <li><b>Space:</b> O(nW) for the memo table + O(n) for the recursion call stack</li>
     * </ul>
     *
     * @param weights  array of item weights, where weights[i] is the weight of item i
     * @param values   array of item values, where values[i] is the value of item i
     * @param capacity the maximum weight capacity of the knapsack
     * @return the maximum total value achievable without exceeding the capacity
     * @throws IllegalArgumentException if weights and values arrays have different lengths
     */
    public static int solveRecursiveWithMemoisation(int[] weights, int[] values, int capacity) {
        // Validate that weights and values arrays are aligned
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Weights and values arrays must have the same length");
        }

        int totalItems = weights.length;

        // Create memoisation table with dimensions (totalItems + 1) x (capacity + 1).
        // The +1 accounts for the base cases: 0 items considered and 0 capacity.
        // Initialise all cells to -1 to indicate "not yet computed".
        int[][] memo = new int[totalItems + 1][capacity + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        // Begin recursion from the full problem: all items available, full capacity
        return recurseWithMemo(weights, values, totalItems, capacity, memo);
    }

    /**
     * Private recursive helper method that implements the memoised recursion.
     *
     * @param weights           array of item weights
     * @param values            array of item values
     * @param itemCount         number of items being considered (items 0 to itemCount-1)
     * @param remainingCapacity current remaining capacity of the knapsack
     * @param memo              memoisation table storing previously computed results
     * @return maximum value achievable with the given items and capacity
     */
    private static int recurseWithMemo(int[] weights, int[] values, int itemCount,
                                       int remainingCapacity, int[][] memo) {
        // BASE CASE: No items left to consider, or no capacity remaining.
        // In either case, we cannot add any more value.
        if (itemCount == 0 || remainingCapacity == 0) {
            return 0;
        }

        // MEMOISATION CHECK: If we've already solved this subproblem, return the cached result.
        // This is the key optimisation that reduces time complexity from O(2^n) to O(nW).
        if (memo[itemCount][remainingCapacity] != -1) {
            return memo[itemCount][remainingCapacity];
        }

        // Extract the weight and value of the current item.
        // We use (itemCount - 1) because itemCount represents "how many items we're considering"
        // while the arrays are 0-indexed.
        int currentItemWeight = weights[itemCount - 1];
        int currentItemValue = values[itemCount - 1];

        int result;

        // CASE 1: Current item is too heavy to fit in the remaining capacity.
        // We have no choice but to exclude it and move to the next item.
        if (currentItemWeight > remainingCapacity) {
            result = recurseWithMemo(weights, values, itemCount - 1, remainingCapacity, memo);
        } else {
            // CASE 2: Current item can fit. We must decide whether to include or exclude it.
            // We try both options and take the maximum.

            // Option A: EXCLUDE the current item.
            // The maximum value is whatever we can achieve with the remaining items
            // at the same capacity.
            int valueIfExcluded = recurseWithMemo(weights, values, itemCount - 1,
                    remainingCapacity, memo);

            // Option B: INCLUDE the current item.
            // We gain the item's value, but must reduce our remaining capacity by its weight.
            // The total value is the item's value plus the best we can do with the remaining
            // items and reduced capacity.
            int valueIfIncluded = currentItemValue + recurseWithMemo(weights, values,
                    itemCount - 1, remainingCapacity - currentItemWeight, memo);

            // Take the better of the two options
            result = Math.max(valueIfExcluded, valueIfIncluded);
        }

        // CACHE THE RESULT: Store the computed result before returning.
        // This ensures we never recompute this subproblem.
        memo[itemCount][remainingCapacity] = result;

        return result;
    }

    /**
     * Solves the 0/1 Knapsack Problem using bottom-up dynamic programming (tabulation).
     *
     * <p>
     * This approach builds the solution iteratively, starting from the smallest subproblems
     * (no items, no capacity) and progressively solving larger subproblems until we reach
     * the full problem. Unlike the recursive approach, this method fills the entire DP table
     * systematically without recursion.
     * </p>
     * <p>
     *  The DP array is 2-dimensional. The columns represent the various possible total capacities leading up to the maximal capacity specified.
     *  The rows represent the addition (or not) of each particular item when the total capacity is specified by the column value. 
     *  Where the item's weight is too great for that particular maximal capacity then it can't be added. 
     *  Otherwise we see whether including it or excluding it gives a higher value. 
     *  If we exclude it then we take the value from the row directly above but the same column (which is the maximal value for that capacity without considering this item). 
     *  If we include it then we take its value and add the best value for all the items in the knapsack before considering this one and at capacity (current maximal capacity - item's weight).
	 *	Once we reach the bottom right corner we have the maximum possible value of the knapsack.
     * </p>
     *
     * <h3>How It Works</h3>
     * <ol>
     *   <li>Create a 2D DP table where dp[i][w] represents the maximum value achievable
     *       using items 0 to i-1 with capacity w</li>
     *   <li>Initialise base cases: dp[0][*] = 0 (no items means zero value)</li>
     *   <li>For each item, for each capacity, compute the optimal choice (include or exclude)</li>
     *   <li>The final answer is in dp[totalItems][capacity]</li>
     * </ol>
     *
     * <h3>Advantages Over Recursive Approach</h3>
     * <ul>
     *   <li>No recursion overhead or risk of stack overflow for large inputs</li>
     *   <li>Can be optimised to O(W) space by using a 1D array (not shown here)</li>
     *   <li>More cache-friendly memory access pattern</li>
     * </ul>
     *
     * <h3>Complexity</h3>
     * <ul>
     *   <li><b>Time:</b> O(nW) where n = number of items, W = capacity</li>
     *   <li><b>Space:</b> O(nW) for the DP table</li>
     * </ul>
     *
     * @param weights  array of item weights, where weights[i] is the weight of item i
     * @param values   array of item values, where values[i] is the value of item i
     * @param capacity the maximum weight capacity of the knapsack
     * @return the maximum total value achievable without exceeding the capacity
     * @throws IllegalArgumentException if weights and values arrays have different lengths
     */
    public static int solveBottomUp(int[] weights, int[] values, int capacity) {
        // Validate that weights and values arrays are aligned
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Weights and values arrays must have the same length");
        }

        int totalItems = weights.length;

        // Create DP table with dimensions (totalItems + 1) x (capacity + 1).
        // The +1 on both dimensions accommodates the base cases:
        //   - Row 0: zero items considered (value is 0 for any capacity)
        //   - Column 0: zero capacity (value is 0 for any number of items)
        // Java initialises int arrays to 0, so base cases are implicitly handled.
        int[][] dp = new int[totalItems + 1][capacity + 1];

        // MAIN DP LOOP: Iterate through each item, then each possible capacity.
        // We build up solutions from smaller subproblems to larger ones.
        for (int itemCount = 1; itemCount <= totalItems; itemCount++) {

            // Extract the weight and value of the current item.
            // We use (itemCount - 1) because itemCount represents "how many items we're
            // considering" (1-indexed for the DP table) while arrays are 0-indexed.
            int currentItemWeight = weights[itemCount - 1];
            int currentItemValue = values[itemCount - 1];

            // For each possible capacity from 1 to the maximum
            for (int currentCapacity = 1; currentCapacity <= capacity; currentCapacity++) {

                // DEFAULT: Exclude the current item.
                // The best value is whatever we achieved with the previous items
                // at the same capacity.
                int valueIfExcluded = dp[itemCount - 1][currentCapacity];

                // CHECK: Can we include the current item?
                // Only possible if its weight doesn't exceed the current capacity.
                if (currentItemWeight <= currentCapacity) {

                    // Calculate the value if we include this item:
                    // The item's value PLUS the best value achievable with the remaining
                    // capacity (after subtracting this item's weight) using previous items.
                    int valueIfIncluded = currentItemValue +
                            dp[itemCount - 1][currentCapacity - currentItemWeight];

                    // Take the maximum of excluding vs including
                    dp[itemCount][currentCapacity] = Math.max(
                    		valueIfExcluded,
                            valueIfIncluded
                    );
                }
                else {
                    // Item too heavy—exclusion is the only option
                    dp[itemCount][currentCapacity] = valueIfExcluded;
                }
            }
        }

        // The answer is in the bottom-right cell: all items considered, full capacity
        return dp[totalItems][capacity];
    }

    /**
     * Demonstrates both solution methods using the fruit example.
     *
     * <p>
     * Example problem:
     * </p>
     * <pre>
     * Items:
     *   Item 0 (Apple):      weight = 10, value = 50
     *   Item 1 (Banana):     weight = 20, value = 120
     *   Item 2 (Grapes):     weight = 30, value = 150
     *   Item 3 (Pineapple):  weight = 40, value = 210
     *   Item 4 (Watermelon): weight = 50, value = 240
     *
     * Capacity: 50
     *
     * Optimal Solution: Banana + Grapes
     *   Total weight: 20 + 30 = 50
     *   Total value:  120 + 150 = 270
     * </pre>
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Define the problem: weights and values for each item
        int[] weights = {10, 20, 30, 40, 50};
        int[] values = {50, 120, 150, 210, 240};
        int capacity = 50;

        // Solve using both methods
        int resultMemoised = solveRecursiveWithMemoisation(weights, values, capacity);
        int resultBottomUp = solveBottomUp(weights, values, capacity);

        // Display results
        System.out.println("0/1 Knapsack Problem - Fruit Example");
        System.out.println("=====================================");
        System.out.println();
        System.out.println("Items:");
        System.out.println("  Apple:      weight = 10, value = 50");
        System.out.println("  Banana:     weight = 20, value = 120");
        System.out.println("  Grapes:     weight = 30, value = 150");
        System.out.println("  Pineapple:  weight = 40, value = 210");
        System.out.println("  Watermelon: weight = 50, value = 240");
        System.out.println();
        System.out.println("Knapsack capacity: " + capacity);
        System.out.println();
        System.out.println("Results:");
        System.out.println("  Recursive with memoisation: " + resultMemoised);
        System.out.println("  Bottom-up DP:               " + resultBottomUp);
        System.out.println();
        System.out.println("Optimal solution: Banana (20, 120) + Grapes (30, 150) = 270");
    }
}
