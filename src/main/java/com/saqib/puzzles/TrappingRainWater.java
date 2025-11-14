package com.saqib.puzzles;

import static java.lang.System.*;

/**
 * <h1>Trapping Rain Water Problem</h1>
 *
 * <p>
 * This class provides an implementation of the classic <b>Trapping Rain Water</b> problem
 * (LeetCode #42), which asks:
 * </p>
 *
 * <blockquote>
 * Given <i>n</i> non-negative integers representing an elevation map
 * where the width of each bar is 1, compute how much water it can trap after raining.
 * </blockquote>
 *
 * <h2>Algorithm Overview</h2>
 *
 * <p>
 * The solution uses a <b>two-pointer</b> approach that runs in <b>O(n)</b> time
 * and uses <b>O(1)</b> extra space.
 * </p>
 *
 * <h3>Intuition</h3>
 * <p>
 * Water can only be trapped where there is a higher bar on both sides of a position.
 * For each position, the trapped water is:
 * </p>
 *
 * <pre>
 * water_at_i = min(max_left[i], max_right[i]) - height[i]
 * </pre>
 *
 * <p>
 * Instead of precomputing <code>max_left</code> and <code>max_right</code> arrays,
 * we can dynamically track them while traversing from both ends using two pointers.
 * </p>
 *
 * <h3>Two-Pointer Logic</h3>
 * <ul>
 *   <li>Maintain two pointers: <code>left</code> (starting from 0) and <code>right</code> (starting from n - 1).</li>
 *   <li>Maintain two variables: <code>leftMax</code> and <code>rightMax</code> to store the maximum heights seen so far from each side.</li>
 *   <li>At each step:
 *     <ul>
 *       <li>If <code>height[left] &lt;= height[right]</code>, then:
 *         <ul>
 *           <li>If <code>height[left] &gt;= leftMax</code>, update <code>leftMax</code>.</li>
 *           <li>Else, water trapped on this bar = <code>leftMax - height[left]</code>.</li>
 *           <li>Move <code>left++</code>.</li>
 *         </ul>
 *       </li>
 *       <li>Else (the right side is lower):
 *         <ul>
 *           <li>If <code>height[right] &gt;= rightMax</code>, update <code>rightMax</code>.</li>
 *           <li>Else, water trapped on this bar = <code>rightMax - height[right]</code>.</li>
 *           <li>Move <code>right--</code>.</li>
 *         </ul>
 *       </li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h3>Why It Works</h3>
 * <p>
 * The pointer on the smaller side always determines the limiting height for potential water trapping.
 * By moving inward from the smaller height, we ensure we never miss any possible trapped water,
 * since the water level at any position is bounded by the shorter of the two sides.
 * </p>
 *
 * <h3>Example</h3>
 * <pre>
 * height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output = 6
 * </pre>
 *
 * <h3>Complexity</h3>
 * <ul>
 *   <li>Time: O(n) — each index visited once.</li>
 *   <li>Space: O(1) — only constant extra variables used.</li>
 * </ul>
 *
 * @author Saqib
 * @see <a href="https://leetcode.com/problems/trapping-rain-water/">LeetCode Problem Description</a>
 */
public class TrappingRainWater {

	public static void main(String[] args) {
		// Example input: elevation map where each bar's height is represented by an integer
		int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
		//int[] height = {0,3,2,1,1,0,0,2,1,2,3}; // Alternate test case

		TrappingRainWater trw = new TrappingRainWater();
		out.println(trw.trap(height)); // Expected output: 6
	}

	private int trap(int[] height) {
		int right = height.length - 1;  // Pointer starting from the right end
		int left = 0;                   // Pointer starting from the left end
		int leftMax = 0;                // Tracks the maximum height seen so far from the left
		int rightMax = 0;               // Tracks the maximum height seen so far from the right
		int water = 0;                  // Accumulator for total trapped water

		// Two-pointer approach: move inward from both ends
		while (left < right) {
			// Compare current heights to decide which pointer to move
			if (height[left] <= height[right]) {
				// Left side is lower or equal — water trapped depends on leftMax
				if (height[left] >= leftMax) {
					// Update leftMax when a new higher bar is found
					leftMax = height[left];
				} else {
					// Otherwise, water is trapped over the current bar
					water += leftMax - height[left];
				}
				left++; // Move left pointer inward
			} else {
				// Right side is lower — water trapped depends on rightMax
				if (height[right] >= rightMax) {
					// Update rightMax when a new higher bar is found
					rightMax = height[right];
				} else {
					// Otherwise, water is trapped over the current bar
					water += rightMax - height[right];
				}
				right--; // Move right pointer inward
			}
		}

		// Return total water trapped
		return water;
	}

}
