package com.saqib.puzzles;

/**
 * Solves the Codility "MinMaxDivision" problem.
 *
 * Problem:
 * --------
 * Given:
 * - An integer K  (number of blocks to divide into),
 * - An integer M  (upper bound on element values),
 * - A non-empty array A of length N, where each A[i] is in [0..M],
 *
 * You must split A into exactly K blocks of consecutive elements
 * (some blocks may be empty) so that:
 *
 *    largeSum = max( sum(block_1), ..., sum(block_K) )
 *
 * is as small as possible.
 *
 * Return this minimal possible largeSum.
 *
 * Notes about M:
 * --------------
 * M is the *theoretical* upper bound on any A[i]:
 * - We are guaranteed that A[i] <= M for all i.
 * - But we are NOT guaranteed that any element actually equals M.
 *   The true max element could be smaller.
 * The algorithm therefore uses the *actual* max(A[i]) as a lower
 * bound in the search, not M itself.
 *
 * Algorithm (high level):
 * -----------------------
 * 1) Any block must at least contain the largest element in A.
 *    So the minimal possible largeSum is:
 *
 *       lowerBound = max(A[i])
 *
 * 2) One block could contain all elements.
 *    So the maximal possible largeSum is:
 *
 *       upperBound = sum(A[i])
 *
 * 3) The true answer lies in [lowerBound, upperBound].
 *    We binary-search that range. Each mid represents a candidate
 *    "max allowed block sum".
 *
 * 4) For a given mid, we greedily pack elements from left to right
 *    into blocks:
 *       - keep adding to the current block while sum + A[i] <= mid
 *       - if adding A[i] would exceed mid, start a new block
 *    This greedy rule always produces the *fewest* blocks possible
 *    for that mid (we never start a block earlier than we must).
 *
 * 5) If this greedy packing uses <= K blocks, mid is feasible.
 *    Then we try a smaller mid. Otherwise we need a larger mid.
 *
  * Example + Dry Run:
 * ------------------
 * K = 3, M = 5, A = [2,1,5,1,2,2,2]
 *
 * lowerBound = max(A) = 5
 * upperBound = sum(A) = 15
 * Search range = [5, 15]
 *
 * mid = 10:
 *   Greedy grouping:
 *   [2,1,5,1] = 9
 *   [2,2,2]   = 6
 *   → 2 blocks ≤ 3 → feasible → search left side → new range [5, 9]
 *
 * mid = 7:
 *   [2,1] = 3
 *   [5,1] = 6
 *   [2,2,2] = 6
 *   → 3 blocks ≤ 3 → feasible → new range [5, 6]
 *
 * mid = 5:
 *   [2,1] = 3
 *   [5]   = 5
 *   [1,2] = 3
 *   [2,2] = 4
 *   → 4 blocks > 3 → NOT feasible → raise lower bound → new range [6, 6]
 *
 * mid = 6:
 *   [2,1] = 3
 *   [5,1] = 6
 *   [2,2,2] = 6
 *   → 3 blocks ≤ 3 → feasible → new range [6, 5] (search ends)
 *
 * Result = 6.
 *
 */
public class MinMaxDivision {

    public static void main(String[] args) {
        int[] A = new int[] {2, 1, 5, 1, 2, 2, 2};
        int K = 3;
        int M = 5;
        int minLargeSum = new MinMaxDivision().minLargeSum(K, M, A);

        System.out.println("Min large sum is: " + minLargeSum);
    }

    /**
     * Computes the minimal possible "large sum" when splitting A into K blocks.
     *
     * @param K number of blocks to split into
     * @param M upper bound on element values (0 <= A[i] <= M).
     *          Not used directly in the algorithm, but part of the original signature.
     * @param A input array
     * @return the minimal feasible maximum block sum
     */
    public int minLargeSum(int K, int M, int[] A) {
        int minSum = 0;  // will become the maximum single element in A
        int maxSum = 0;  // will become the total sum of A

        // Compute:
        // - maxSum = sum(A) → worst-case "one big block"
        // - minSum = max(A) → smallest allowed upper bound (must fit the largest element)
        for (int i : A) {
            maxSum += i;
            minSum = Math.max(minSum, i);
        }

        // Now the true answer is guaranteed to lie in [minSum, maxSum].
        return binarySearch(K, A, minSum, maxSum);
    }

    /**
     * Binary searches in the numeric range [minSum, maxSum] to find the
     * smallest value X such that A can be split into at most K blocks
     * with each block's sum <= X.
     */
    private int binarySearch(int K, int[] A, int minSum, int maxSum) {
        // Start with the pessimistic answer: all elements in one block.
        int result = maxSum;

        // Standard binary search on integers: invariant is that the
        // answer (minimal feasible X) lies in [minSum, maxSum].
        while (minSum <= maxSum) {

            // Candidate maximum block sum.
            // Conceptually: "assume no block is allowed to exceed 'mid'".
            int mid = (minSum + maxSum) / 2;

            // Check if 'mid' is a feasible maximum block sum.
            if (isSplitValid(mid, K, A)) {
                // Feasible: we can split into <= K blocks under this limit.
                // Try to shrink the maximum allowed block sum further.
                result = mid;
                maxSum = mid - 1;
            } else {
                // Not feasible: even with greedy splitting we need > K blocks.
                // So 'mid' is too small; we must allow larger block sums.
                minSum = mid + 1;
            }
        }

        return result;
    }

    /**
     * Checks whether A can be split into at most K blocks such that the sum
     * of each block is <= mid.
     *
     * Greedy argument ("why" this works):
     * -----------------------------------
     * - We always keep adding elements to the current block as long as
     *   sum + A[i] <= mid.
     * - Only when sum + A[i] > mid are we *forced* to start a new block.
     * - Any valid partition must also start a new block at or before that point,
     *   because including A[i] would exceed mid.
     *
     * Therefore, this greedy procedure:
     * - Never starts a new block earlier than necessary.
     * - Produces the minimum possible number of blocks for that 'mid'.
     *
     * If even this minimum number of blocks is > K, then no other splitting
     * can satisfy the constraint with this mid.
     *
     * @param mid proposed maximum allowed sum for any single block
     * @param K   maximum number of blocks allowed
     * @param A   input array
     * @return true if A can be split into at most K blocks with block sums <= mid
     */
    private boolean isSplitValid(int mid, int K, int[] A) {

        int blocks = 1; // We start with the first block.
        int sum = 0;    // Running sum of the current block.

        for (int x : A) {

            // If adding x would exceed the proposed limit mid,
            // we must start a new block at x.
            if (sum + x > mid) {
                blocks++;
                sum = x;

                // If we already exceed K blocks, mid is too small.
                // Early exit: no need to process the rest.
                if (blocks > K) {
                    return false;
                }
            } else {
                // Otherwise, keep accumulating in the current block.
                sum += x;
            }
        }

        // If we used <= K blocks, then mid is a feasible max block sum.
        return blocks <= K;
    }
}
