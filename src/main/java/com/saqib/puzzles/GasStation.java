package com.saqib.puzzles;

/**
 * Gas Station Circuit Problem (LeetCode 134)
 *
 * <p><b>Problem Statement:</b></p>
 * There are n gas stations arranged along a circular route. At station i,
 * {@code gas[i]} units of gas are available, and it costs {@code cost[i]} units
 * of gas to travel from station i to station (i + 1) mod n.
 * The car starts with an empty tank and has unlimited capacity.
 *
 * <p>Return the index of the unique gas station from which the circuit can be
 * completed once clockwise, or -1 if no such station exists.</p>
 *
 * <p><b>Solution Overview:</b></p>
 * This solution uses a greedy, single-pass algorithm based on prefix-sum
 * invariants over the net gas balance {@code gas[i] - cost[i]}.
 *
 * <p><b>Key Observations:</b></p>
 * <ul>
 *   <li>If the total gas available is less than the total cost required, the
 *       circuit is impossible from any starting point.</li>
 *   <li>If the total gas is sufficient, there exists exactly one valid starting
 *       station.</li>
 * </ul>
 *
 * <p><b>Invariant:</b></p>
 * As we traverse the stations from index 0 to n−1, we track the running
 * cumulative balance:
 *
 * <pre>
 * balance(k) = Σ (gas[i] − cost[i]) for i = 0..k
 * </pre>
 *
 * The minimum value of this running balance represents the point at which the
 * accumulated deficit is greatest.
 *
 * <p><b>Greedy Choice:</b></p>
 * If we start immediately <em>after</em> the position where the cumulative
 * balance is minimal, then all prefix balances of the rotated traversal are
 * guaranteed to be non-negative.
 *
 * <p><b>Why This Works:</b></p>
 * <ul>
 *   <li>Any start at or before the minimum prefix sum would necessarily
 *       encounter a negative balance at that minimum point.</li>
 *   <li>Starting immediately after the minimum avoids inheriting that deficit.</li>
 *   <li>If the total balance over the full circuit is non-negative, this start
 *       will never run out of gas.</li>
 * </ul>
 *
 * <p>This choice is greedy and irreversible: once a lower cumulative balance is
 * observed, all earlier starting positions are permanently invalidated.</p>
 *
 * <p><b>Algorithm:</b></p>
 * <ol>
 *   <li>Traverse the stations once, tracking the cumulative balance.</li>
 *   <li>Record the position where this balance reaches its minimum.</li>
 *   <li>If the final balance is negative, return -1.</li>
 *   <li>Otherwise, return the index immediately after the minimum position.</li>
 * </ol>
 *
 * <p><b>Time Complexity:</b> O(n) - single pass through the arrays</p>
 * <p><b>Space Complexity:</b> O(1) - only constant extra space</p>
 * 
 * <p><b>Example Walkthrough:</b></p>
 * <pre>
 * Input: gas  = [1, 2, 3, 4, 5]
 *        cost = [3, 4, 5, 1, 2]
 * 
 * Traversal from station 0:
 * i=0: net = 1-3 = -2,  balance = -2  (worst = -2, minPos = 0)
 * i=1: net = 2-4 = -2,  balance = -4  (worst = -4, minPos = 1)
 * i=2: net = 3-5 = -2,  balance = -6  (worst = -6, minPos = 2)
 * i=3: net = 4-1 = +3,  balance = -3  (no change)
 * i=4: net = 5-2 = +3,  balance =  0  (no change)
 * 
 * Final balance = 0 (>= 0), so solution exists
 * Return (2 + 1) % 5 = 3
 * 
 * Verification starting from station 3:
 * 3→4: start=0, gain=4, cost=1, end=3 ✓
 * 4→0: start=3, gain=5, cost=2, end=6 ✓
 * 0→1: start=6, gain=1, cost=3, end=4 ✓
 * 1→2: start=4, gain=2, cost=4, end=2 ✓
 * 2→3: start=2, gain=3, cost=5, end=0 ✓ (completes circuit)
 * </pre>
 */
public class GasStation {

	public static void main(String[] args) {
		GasStation gasStation = new GasStation();
		
		// Test case: gas stations with varying surplus/deficit
		int result = gasStation.canCompleteCircuit(
			new int[] {1, 2, 3, 4, 5},  // gas available at each station
			new int[] {3, 4, 5, 1, 2}   // cost to reach next station
		);
		System.out.println("Starting station index: " + result);  // Expected: 3
	}

	/**
	 * Determines if there exists a starting station from which the circuit can be completed,
	 * and returns that station's index if it exists.
	 * 
	 * @param gas  array where gas[i] is the amount of gas available at station i
	 * @param cost array where cost[i] is the gas needed to travel from station i to i+1
	 * @return the starting station index (0-based) if a solution exists, -1 otherwise
	 */
	public int canCompleteCircuit(int[] gas, int[] cost) {
		// Track the worst (most negative) cumulative balance encountered
		int worstDeficit = 0;
		
		// Track the cumulative balance as if starting from station 0
		int balance = 0;
		
		// Track the position where the worst deficit occurs
		// Initialize to -1 to handle the case where balance never goes negative
		int minPosition = -1;

		// Single pass: calculate net balance at each station and track the minimum
		for (int i = 0; i < cost.length; i++) {
			// Net gain/loss at station i
			int netGas = gas[i] - cost[i];
			balance += netGas;
			
			// If we've hit a new low (or equal low), update our tracking
			// We use <= to always capture the *last* position if there are ties
			if (balance <= worstDeficit) {
				worstDeficit = balance;
				minPosition = i;
			}
		}

		// If total balance is negative, it's impossible to complete the circuit
		// (not enough total gas to cover total cost)
		if (balance < 0) {
			return -1;
		}

		// Start from the position immediately after the worst deficit point
		// The modulo handles wraparound (if minPosition is the last station)
		// This works because:
		// 1. Total balance is non-negative (we have enough gas overall)
		// 2. Starting after the minimum ensures we never go negative during traversal
		return (minPosition + 1) % gas.length;
	}
}