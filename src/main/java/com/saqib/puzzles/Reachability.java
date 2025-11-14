package com.saqib.puzzles;

/**
 * Given a start coordinate (a, b) and a target (c, d),
 * determine if we can reach (c, d) from (a, b) using these moves any number of times, any order:
 *   (x, y) -> (x + y, y)   or   (x, y) -> (x, x + y)
 *
 * Key idea: work BACKWARDS from (c, d) toward (a, b).
 * Forward = "add smaller to larger". Backward = "subtract smaller from larger".
 * Using modulo ( % ) compresses many subtractions into one step.
 *
 * Assumes all inputs are positive integers (>= 1).
 */
public class Reachability {

    public static void main(String[] args) {
        System.out.println(isPossible(1, 1, 5, 2)); // sample run
    }

    /**
     * Returns "Yes" if (c, d) is reachable from (a, b), otherwise "No".
     */
    public static String isPossible(int a, int b, int c, int d) {

        // Monotonicity pruning:
        // Forward moves never decrease coordinates, so if target is already smaller in any coord → impossible.
        if (c < a || d < b) return "No";

        // Work backwards from (c, d) down toward (a, b).
        // Keep reducing while both coordinates are still at least as large as the start.
        while (c >= a && d >= b) {

            // Exact match → success.
            if (c == a && d == b) return "Yes";

            // Equal coordinates are a dead-end in the backward process:
            // subtracting would yield zero (not allowed for positive integers).
            // If we hit (k, k) and it's not (a, b), we must stop and fail later.
            if (c == d) break;

            if (c > d) {
                // Here the last forward move must have been (x, y) -> (x + y, y).
                // Backwards we should subtract multiples of d from c.

                if (d > b) {
                    // d hasn't been reduced down to its starting value yet, so both sides are still "free".
                    // Bulk-subtract d from c as many times as possible:
                    // c %= d is equivalent to repeated "c -= d" until c < d.
                    c %= d;
                } else {
                    // d is already locked at its start value b.
                    // From now on, only c can change by subtracting d repeatedly.
                    // We can reach a iff (c - a) is a non-negative multiple of d.
                    return (c - a) % d == 0 ? "Yes" : "No";
                }
            } else {
                // Symmetric case: d > c
                // Last forward move must have been (x, y) -> (x, x + y).
                // Backwards we should subtract multiples of c from d.

                if (c > a) {
                    // c hasn't been reduced down to its starting value yet.
                    // Bulk-subtract c from d as many times as possible:
                    d %= c;
                } else {
                    // c is locked at its start value a.
                    // Only d can change now, by subtracting c repeatedly.
                    // Reachable iff (d - b) is a non-negative multiple of c.
                    return (d - b) % c == 0 ? "Yes" : "No";
                }
            }
        }

        // Loop ended: either we broke on (c == d) or one coord fell below its start.
        // Success only if we landed exactly on (a, b).
        return (c == a && d == b) ? "Yes" : "No";
    }
}
