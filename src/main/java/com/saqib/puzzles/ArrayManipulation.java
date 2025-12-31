package com.saqib.puzzles;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * <h1>Array Manipulation (Range Add + Maximum)</h1>
 *
 * <p>
 * You start with a 1-indexed array {@code A[1..n]} initialised to zero.
 * You are given {@code q} operations of the form {@code (a, b, k)} meaning:
 * add {@code k} to every element {@code A[i]} where {@code a <= i <= b}.
 * After all operations are applied, return the maximum value in the array.
 * </p>
 *
 * <h2>Core idea: Difference Array + Prefix Sum</h2>
 * <p>
 * We do <b>not</b> update the array directly. Instead, we track <i>change events</i>:
 * when an effect starts and when it stops.
 * </p>
 *
 * <ul>
 *   <li>{@code diff[a] += k}   → effect {@code +k} becomes active at index {@code a}</li>
 *   <li>{@code diff[b+1] -= k} → effect {@code +k} stops being active after index {@code b}</li>
 * </ul>
 *
 * <h2>Why {@code b + 1}?</h2>
 * <p>
 * The range is inclusive. If we placed the stop marker at {@code b}, the effect would be cancelled at {@code b}
 * (too early). Placing it at {@code b+1} cancels it starting from the next index, so {@code a..b} still include it.
 * </p>
 *
 * <h2>Negative diff entries are normal</h2>
 * <p>
 * A negative value in {@code diff[i]} does <b>not</b> mean the final array becomes negative.
 * It means: “at index {@code i}, some previously-active effects expire, reducing the running total.”
 * </p>
 *
 * <h2>Composability (overlaps)</h2>
 * <p>
 * Overlapping operations compose because effects are additive. If multiple effects start/end at the same index,
 * {@code diff[i]} is simply the <i>net change</i> to apply to the running total at {@code i}.
 * No special handling is required.
 * </p>
 *
 * <h2>Final summation step</h2>
 * <p>
 * The prefix sum pass answers:
 * </p>
 * <blockquote>
 *   “What is the total of all composed effects active at this index?”
 * </blockquote>
 *
 * <pre>{@code
 * running += diff[i];
 * A[i] = running;
 * max = max(max, running);
 * }</pre>
 *
 * <h2>Worked dry-run example (n=5, 4 operations)</h2>
 *
 * <p>Operations:</p>
 * <pre>
 * (1, 3, +10)
 * (2, 5, +5)      // overlaps with first
 * (3, 3, -15)     // cancels at index 3
 * (4, 5, -5)      // cancels the +5 on indices 4..5
 * </pre>
 *
 * <p>
 * Build {@code diff} with indices {@code 1..6} (where {@code 6 = n+1} is a buffer slot for stop markers. 
 * We need 6 slots, not 5 as if the operation specifies a range that stops at 5 then we need to place the stop marker for this at 6.
 * On a 5 slot array this would obviously result in an error.):
 * </p>
 *
 * <pre>
 * start:      [0,  0,   0,   0,   0,  0]
 *
 * (1,3,10):   [10, 0,   0, -10,   0,  0]
 *              +10@1        -10@4
 *
 * (2,5,5):    [10, 5,   0, -10,   0, -5]
 *                  +5@2             -5@6
 *
 * (3,3,-15):  [10, 5, -15,  +5,   0, -5]
 *                      -15@3   +15@4
 *
 * (4,5,-5):   [10, 5, -15,   0,   0,  0]
 *                          -5@4   +5@6  (cancels)
 * </pre>
 *
 * <p>Prefix sum over indices {@code 1..5}:</p>
 * <pre>
 * i=1: running = 10  → A[1] = 10
 * i=2: running = 15  → A[2] = 15
 * i=3: running =  0  → A[3] = 0   (cancellation)
 * i=4: running =  0  → A[4] = 0
 * i=5: running =  0  → A[5] = 0
 * max = 15
 * </pre>
 *
 * <h2>Complexity</h2>
 * <ul>
 *   <li>Time: {@code O(n + q)}</li>
 *   <li>Memory: {@code O(n)} for {@code diff}</li>
 * </ul>
 *
 * <h2>Overflow note</h2>
 * <p>
 * Values can reach roughly {@code q * k} (e.g., {@code 2e5 * 1e9 = 2e14}), exceeding {@code int}.
 * Use {@code long} for {@code diff}, {@code running}, and {@code max}.
 * </p>
 */
public final class ArrayManipulation {

    /**
     * Applies all range-add operations and returns the maximum final value.
     *
     * @param n   size of the 1-indexed array
     * @param ops operations, each {@code [a, b, k]}
     * @return maximum value in the array after all operations
     */
    public static long arrayManipulation(int n, int[][] ops) {
        /*
         * We will write to diff[b+1]. If b == n, then b+1 == n+1 must be in-bounds.
         * Using indices 1..n+1 means we allocate n+2 and ignore index 0.
         */
        long[] diff = new long[n + 2];

        // Record start/stop "events" for each operation.
        for (int[] op : ops) {
            int a = op[0];
            int b = op[1];
            long k = op[2];

            diff[a] += k;       // start effect at a
            diff[b + 1] -= k;   // stop effect after b (i.e., starting at b+1)
        }

        // Prefix sum: evaluate total active effects at each index and track the maximum.
        long running = 0;
        long max = Long.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            running += diff[i];      // apply net change events at i
            if (running > max) {
                max = running;       // running is the value of A[i]
            }
        }
        return max;
    }

    /**
     * HackerRank-style entry point.
     *
     * <p>Input format:</p>
     * <pre>
     * n q
     * a b k
     * a b k
     * ...
     * </pre>
     */
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        int n = fs.nextInt();
        int q = fs.nextInt();

        int[][] ops = new int[q][3];
        for (int i = 0; i < q; i++) {
            ops[i][0] = fs.nextInt();
            ops[i][1] = fs.nextInt();
            ops[i][2] = fs.nextInt();
        }

        System.out.println(arrayManipulation(n, ops));
    }

    /**
     * <h2>FastScanner</h2>
     *
     * <p>
     * A minimal, high-performance input reader for reading integers from standard input.
     * </p>
     *
     * <p><b>Why this exists</b></p>
     * <p>
     * Java's {@link java.util.Scanner} is convenient but can be slow for very large inputs because:
     * </p>
     * <ul>
     *   <li>It uses regular expressions internally</li>
     *   <li>It creates many temporary objects</li>
     *   <li>It prioritises flexibility over raw throughput</li>
     * </ul>
     *
     * <p>
     * Competitive programming inputs can be large enough that {@code Scanner} times out.
     * This class avoids that by:
     * </p>
     * <ul>
     *   <li>Reading raw bytes in large chunks into a buffer</li>
     *   <li>Parsing integers manually, digit by digit</li>
     *   <li>Avoiding intermediate Strings and regex</li>
     * </ul>
     *
     * <p><b>Important:</b> this is an <i>I/O optimisation only</i>. It is not required to understand the algorithm.</p>
     */
    private static final class FastScanner {

        /**
         * Buffered input stream wrapping {@code System.in}.
         * Buffering reduces expensive system calls.
         */
        private final BufferedInputStream in = new BufferedInputStream(System.in);

        /**
         * Internal byte buffer used to hold a chunk of input.
         * 64KB is a common, reasonable size to amortise read overhead.
         */
        private final byte[] buffer = new byte[1 << 16];

        /** Pointer to the current read position within {@link #buffer}. */
        private int ptr = 0;

        /** Number of valid bytes currently stored in {@link #buffer}. */
        private int len = 0;

        /**
         * Returns the next byte from the buffer, refilling the buffer as needed.
         *
         * <p>
         * When the buffer has been fully consumed ({@code ptr >= len}), we read another chunk
         * from the underlying stream into {@code buffer}, reset {@code ptr}, and continue.
         * </p>
         *
         * @return next byte as an int in range 0..255, or -1 if end-of-stream
         * @throws IOException if an I/O error occurs
         */
        private int read() throws IOException {
            // If we've consumed the current buffer, refill it.
            if (ptr >= len) {
                len = in.read(buffer); // Read a new chunk of bytes into buffer.
                ptr = 0;               // Reset pointer to the start.

                // If no bytes were read, input has ended.
                if (len <= 0) {
                    return -1;
                }
            }

            // Return the next buffered byte and advance the pointer.
            return buffer[ptr++];
        }

        /**
         * Reads the next integer from input.
         *
         * <p>Algorithm:</p>
         * <ol>
         *   <li>Skip leading whitespace</li>
         *   <li>Read optional '-' sign</li>
         *   <li>Accumulate digit characters into an integer</li>
         * </ol>
         *
         * <p>
         * This method avoids creating Strings. Digits are parsed using ASCII codes:
         * {@code '0'..'9'} map to {@code 48..57}, so {@code (c - '0')} yields the digit value.
         * </p>
         *
         * @return the parsed integer
         * @throws IOException if an I/O error occurs
         */
        int nextInt() throws IOException {
            int c;

            // 1) Skip whitespace (spaces, newlines, tabs, etc.).
            // ASCII space is 32; treating any char <= ' ' as whitespace is common in CP.
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            // 2) Optional sign handling.
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            // 3) Parse digits until the next whitespace.
            int value = 0;
            while (c > ' ') {
                value = value * 10 + (c - '0');
                c = read();
            }

            return value * sign;
        }
    }
}

