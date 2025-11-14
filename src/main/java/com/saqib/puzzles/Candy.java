package com.saqib.puzzles;

import java.util.Arrays;

/**
 * LeetCode Problem #135: Candy
 * 
 * <p>
 * There are n children standing in a line, each assigned a rating value.
 * You must distribute candies to the children according to these rules:
 * <ul>
 *   <li>Each child must have at least one candy.</li>
 *   <li>Children with a higher rating must receive more candies than their immediate neighbors.</li>
 * </ul>
 * 
 * The goal is to minimize the total number of candies distributed.
 * </p>
 * 
 * <p>
 * This implementation uses a greedy two-pass algorithm with O(n) time complexity
 * and O(n) space complexity.
 * </p>
 * 
 * <pre>
 * Example:
 * Input:  ratings = [1, 0, 2]
 * Output: 5
 * Explanation: candies = [2, 1, 2]
 * </pre>
 * 
 * <pre>
 * Example:
 * Input:  ratings = [1, 2, 2]
 * Output: 4
 * Explanation: candies = [1, 2, 1]
 * </pre>
 * 
 * @author Saqib
 */
public class Candy {

    public static void main(String[] args) {
        Candy candySolver = new Candy();
        int[] ratings = {1, 0, 2};
        System.out.println("Minimum candies required: " + candySolver.candy(ratings));
    }

    /**
     * Calculates the minimum number of candies needed to distribute among children
     * so that the rating and neighbor rules are satisfied.
     * 
     * @param ratings an array of integers representing the rating of each child
     * @return the minimum total number of candies required
     */
    public int candy(int[] ratings) {

        // Base case: if no children, no candies needed
        if (ratings == null || ratings.length == 0) {
            return 0;
        }

        // Step 1: Every child starts with 1 candy
        int[] candies = new int[ratings.length];
        Arrays.fill(candies, 1);

        // Step 2: Left-to-right sweep
        // If the current child has a higher rating than the left neighbor,
        // give them one more candy than the left neighbor.
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        // Step 3: Right-to-left sweep
        // If the current child has a higher rating than the right neighbor,
        // ensure they have at least one more candy than the right neighbor.
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        // Step 4: Sum up all candies to get the minimum total
        return Arrays.stream(candies).sum();
    }
    
    
    
    /**
     * Returns the minimum total number of candies required using the
     * O(1) extra-space single-pass (slopes) algorithm.
     *
     * <p>Rules enforced:
     * <ul>
     *   <li>Each child gets at least one candy.</li>
     *   <li>If ratings[i] &gt; ratings[i-1], then candies[i] &gt; candies[i-1].</li>
     *   <li>If ratings[i] &lt; ratings[i-1], then candies[i] &lt; candies[i-1].</li>
     * </ul>
     *
     * <p>Idea (no candies array): scan left→right and treat the sequence as alternating
     * increasing and decreasing slopes. Track four integers:
     * <ul>
     *   <li><b>total</b> — running sum of candies.</li>
     *   <li><b>up</b> — length (in edges) of current strictly increasing streak.</li>
     *   <li><b>down</b> — length (in edges) of current strictly decreasing streak.</li>
     *   <li><b>peak</b> — length of the last uphill at the peak (to ensure the peak stays
     *       strictly higher when a longer downhill appears).</li>
     * </ul>
     *
     * <p>Update rules per step i (i ≥ 1):
     * <ul>
     *   <li><b>Uphill</b> (ratings[i] &gt; ratings[i-1]): up++, peak = up, down = 0, total += 1 + up.</li>
     *   <li><b>Flat</b> (ratings[i] == ratings[i-1]): up = down = peak = 0, total += 1.</li>
     *   <li><b>Downhill</b> (ratings[i] &lt; ratings[i-1]): down++, up = 0, total += 1 + down; if (down &gt; peak) total++.</li>
     * </ul>
     *
     * <p>The extra {@code total++} when {@code down > peak} is the "peak bump" that
     * numerically accounts for raising the peak and all previously placed downhill
     * entries—without storing or mutating a candies array.
     *
     * <p>Time: O(n). Space: O(1).
     *
     * @param ratings array of child ratings (may be empty)
     * @return minimum total number of candies
     */
    public int candyOptimized(int[] ratings) {
        if (ratings == null || ratings.length == 0) return 0;
        int total = 1;  // first child gets at least one
        int up = 0;     // current uphill length (in edges)
        int down = 0;   // current downhill length (in edges)
        int peak = 0;   // uphill length at the last peak

        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                // Uphill: grow increasing streak
                up++;
                peak = up;
                down = 0;
                total += 1 + up;         // current child gets (1 + up)
            } else if (ratings[i] == ratings[i - 1]) {
                // Flat: reset both slopes
                up = down = peak = 0;
                total += 1;              // exactly one candy
            } else {
                // Downhill: grow decreasing streak
                down++;
                up = 0;
                total += 1 + down;       // current child gets (1 + down)
                if (down > peak) {
                    // If downhill exceeds prior uphill, bump the peak by 1
                    total += 1;
                }
            }
        }
        return total;
    }
    
}
