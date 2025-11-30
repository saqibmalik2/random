package com.saqib.puzzles;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * Solution for LeetCode Problem #456: 132 Pattern
 * 
 * Problem: Given an array of n integers nums, a 132 pattern is a subsequence of three integers 
 * nums[i], nums[j] and nums[k] such that i < j < k and nums[i] < nums[k] < nums[j].
 * Return true if there is a 132 pattern in nums, otherwise return false.
 * 
 * Algorithm Overview:
 * This solution uses a monotonic stack with backward traversal to efficiently find the pattern.
 * The key insight is that by traversing from right to left, we can:
 * 1. Track potential "3" candidates (largest values) in our stack
 * 2. Identify the best "2" candidate (rightmost value with middle magnitude) when we find larger numbers
 * 3. Check if any subsequent number is a valid "1" (leftmost and smallest, less than our "2" candidate)
 * 
 * Why Reverse Traversal is Necessary:
 * =====================================
 * 
 * The Core Problem with Forward Traversal:
 * When you traverse forwards (left to right), you encounter the "1" first, then the "3", then the "2".
 * The critical issue: YOU DON'T KNOW WHICH "3" TO PICK!
 * 
 * Example: [0, 5, 1, 4, 2]
 * Going FORWARD:
 * - You see 0 (potential "1")
 * - You see 5 (potential "3"?)
 * - You see 1... wait, should you pair 0 with 5? Or start fresh with 1?
 * - You see 4... is this the "3" for the 0? Or for the 1?
 * - You see 2... does this complete a pattern with 0 and 5? Or 0 and 4? Or 1 and 4?
 * 
 * You'd need to track MULTIPLE possible "1" and "3" combinations simultaneously - very complex!
 * 
 * Why Reverse Traversal Solves This:
 * Going BACKWARDS (right to left), you encounter the "2" first, then the "3", then the "1":
 * - When you find a "2" and "3" pair, you KNOW they're valid - the "3" is to the left of "2" ✓
 * - Now you just need to find ANY number less than "2" to the left
 * - You don't need to track multiple possibilities - just one twoCandidate
 * 
 * Same example [0, 5, 1, 4, 2] going BACKWARDS:
 * - See 2, push it → stack=[2]
 * - See 4 (bigger!), pop 2, set twoCandidate=2, push 4 → stack=[4], twoCandidate=2
 * - See 1 (smaller than 4), push 1 → stack=[4,1]
 * - See 5 (bigger!), pop 1, pop 4, set twoCandidate=4, push 5 → stack=[5], twoCandidate=4
 * - See 0: Is 0 < twoCandidate(4)? YES! Pattern found: [0, 5, 4] ✓
 * 
 * The Key Insight:
 * Reverse traversal lets you GREEDILY maintain the best "2" candidate as you go. Once you have 
 * a valid "2" and "3" pair, you just need to find ONE value less than "2" - and you're guaranteed 
 * any such value will be at the correct position (to the left of both "2" and "3").
 * 
 * Performance Optimization Notes:
 * ================================
 * This implementation prioritizes code clarity and readability. For maximum performance (1ms vs 13ms),
 * consider these optimizations:
 * 
 * 1. **Use raw int[] array instead of ArrayDeque**
 *    - ArrayDeque has object creation overhead and method call costs
 *    - Direct array access with manual index tracking (int top = -1) is faster
 *    - Example: int[] stack = new int[n]; then use stack[++top] and stack[top--]
 * 
 * 2. **Use Integer.MIN_VALUE instead of boolean flag**
 *    - Initialize: int second = Integer.MIN_VALUE;
 *    - Eliminates the twoCandidateSet boolean check on every iteration
 *    - The condition nums[i] < second naturally handles the "not set" case since any valid
 *      number will be >= Integer.MIN_VALUE
 * 
 * 3. **Reduce conditional branching**
 *    - Fewer if statements = better CPU branch prediction
 *    - Simplify logic flow to minimize separate cases
 *    - Always push to stack after the while loop (unconditional operation)
 * 
 * 4. **Eliminate continue statements**
 *    - Restructure logic to avoid early loop exits
 *    - Allows for more predictable control flow
 * 
 * Trade-off: The optimized version is faster but less explicit about the algorithm's logic.
 * For interviews and learning, this readable version is preferred. For production performance-critical
 * code (embedded systems, real-time systems, competitive programming), the optimized version wins.
 * 
 * Optimized version runs the LeetCode test cases in ~1ms vs this version's ~13ms, though both are O(n) time complexity.
 * 
 * Time Complexity: O(n) - single pass through array, each element pushed/popped at most once
 * Space Complexity: O(n) - stack can hold at most n elements
 * 
 * Key Variables:
 * - deque: Monotonic stack maintaining potential "3" candidates in descending order
 * - twoCandidate: The best "2" value found (rightmost position, middle magnitude - largest value popped when finding a bigger "3")
 * - twoCandidateSet: Flag indicating whether we've found a valid "2" and "3" pair
 * 
 * Dry Run Example 1 - TRUE CASE: [0,1,2,0,1,2,0]
 * ================================================================
 * Going backwards (right to left):
 * 
 * i=6: nums[6]=0
 *   - Stack empty, push 0
 *   - stack = [0], twoCandidate = unset
 * 
 * i=5: nums[5]=2
 *   - 2 > 0 (top of stack) → Found a potential "3"!
 *   - Pop 0, set twoCandidate = 0 (this is our "2")
 *   - Push 2 (our "3")
 *   - stack = [2], twoCandidate = 0, twoCandidateSet = true
 * 
 * i=4: nums[4]=1
 *   - twoCandidateSet=true, but 1 < twoCandidate(0)? NO (1 > 0)
 *   - 1 < 2 (top of stack) → push 1
 *   - stack = [2, 1], twoCandidate = 0
 * 
 * i=3: nums[3]=0
 *   - twoCandidateSet=true, 0 < twoCandidate(0)? NO (0 == 0)
 *   - 0 < 1 (top of stack) → push 0
 *   - stack = [2, 1, 0], twoCandidate = 0
 * 
 * i=2: nums[2]=2
 *   - twoCandidateSet=true, 2 < twoCandidate(0)? NO
 *   - 2 > 0 (top of stack) → Found bigger "3"!
 *   - Pop 0, pop 1, set twoCandidate = 1 (largest popped)
 *   - 2 == 2? Stop popping
 *   - Push 2
 *   - stack = [2, 2], twoCandidate = 1
 * 
 * i=1: nums[1]=1
 *   - twoCandidateSet=true, 1 < twoCandidate(1)? NO (1 == 1)
 *   - 1 < 2 (top of stack) → push 1
 *   - stack = [2, 2, 1], twoCandidate = 1
 * 
 * i=0: nums[0]=0
 *   - twoCandidateSet=true, 0 < twoCandidate(1)? YES! ✓
 *   - PATTERN FOUND: [0, 2, 1] at positions (0, 2, 4)
 *   - Return TRUE
 * 
 * Dry Run Example 2 - FALSE CASE: [1,0,1,-4,-3]
 * ================================================================
 * Going backwards (right to left):
 * 
 * i=4: nums[4]=-3
 *   - Stack empty, push -3
 *   - stack = [-3], twoCandidate = unset
 * 
 * i=3: nums[3]=-4
 *   - -4 < -3 (top of stack) → push -4
 *   - stack = [-3, -4], twoCandidate = unset
 * 
 * i=2: nums[2]=1
 *   - 1 > -4 (top of stack) → Found potential "3"!
 *   - Pop -4, pop -3, set twoCandidate = -3 (largest popped)
 *   - Push 1
 *   - stack = [1], twoCandidate = -3, twoCandidateSet = true
 * 
 * i=1: nums[1]=0
 *   - twoCandidateSet=true, 0 < twoCandidate(-3)? NO (0 > -3)
 *   - 0 < 1 (top of stack) → push 0
 *   - stack = [1, 0], twoCandidate = -3
 * 
 * i=0: nums[0]=1
 *   - twoCandidateSet=true, 1 < twoCandidate(-3)? NO (1 > -3)
 *   - 1 > 0 (top of stack) → Found bigger "3"!
 *   - Pop 0, pop 1, set twoCandidate = 1 (largest popped)
 *   - Push 1
 *   - stack = [1], twoCandidate = 1
 * 
 * End of array - no value was less than twoCandidate
 * Return FALSE - No 132 pattern exists
 */
public class OneThreeTwo {

    public static void main(String[] args) {
        OneThreeTwo oneThreeTwo = new OneThreeTwo();
        
        // Test cases
        int[] nums = new int[] {0,1,2,0,1,2,0};  // Expected: true
        // int[] nums = new int[] {1,0,1,-4,-3};  // Expected: false
        
        System.out.println(oneThreeTwo.find132pattern(nums));
    }

    /**
     * Finds whether a 132 pattern exists in the given array.
     * A 132 pattern is three numbers at positions i < j < k where nums[i] < nums[k] < nums[j].
     * 
     * @param nums Array of integers to search
     * @return true if a 132 pattern exists, false otherwise
     */
    public static boolean find132pattern(int[] nums) {
        int n = nums.length;
        
        // twoCandidate represents the "2" in our 132 pattern
        // In terms of position: rightmost of the three (position k)
        // In terms of value: middle magnitude (between "1" and "3")
        // This will be the largest value we pop from the stack when we find a bigger number
        int twoCandidate = 0;
        
        // Flag to track whether we've found a valid "2" and "3" pair
        // We can only look for "1" after we have this pair
        boolean twoCandidateSet = false;
        
        // Base case: need at least 3 elements for a pattern
        if (Objects.isNull(nums) || n < 3) return false;
        
        // Monotonic stack to maintain potential "3" candidates (largest values)
        // The stack will be in descending order from bottom to top
        Deque<Integer> deque = new ArrayDeque<>();
        
        // Traverse the array backwards (right to left)
        // This allows us to build up potential "3" and "2" candidates before finding "1"
        for (int i = n - 1; i >= 0; i--) {
            
            if (!deque.isEmpty()) {
                
                // CASE 1: We have a valid "2" candidate, check if current number is our "1"
                // If nums[i] < twoCandidate, we've found: nums[i] < twoCandidate < (some number in stack)
                // This satisfies i < k < j with nums[i] < nums[k] < nums[j]
                if (twoCandidateSet && (nums[i] < twoCandidate)) {
                    return true;  // Found a valid 132 pattern!
                }
                
                // CASE 2: Current number is smaller than top of stack
                // Push it onto stack as a potential "3" candidate for future iterations
                // This maintains our descending monotonic stack property
                if (nums[i] < deque.peek()) {
                    deque.push(nums[i]);
                    continue;  // Move to next element
                }
                
                // CASE 3: Current number is larger than top of stack
                // This means we've found a new, larger "3" candidate
                // We need to pop all smaller values and track the largest popped value as our "2"
                if (nums[i] > deque.peek()) {
                    
                    // Pop all values smaller than current number
                    // The last (largest) value popped becomes our best "2" candidate
                    // Why? Because a larger "2" gives us more chances to find a valid "1"
                    while (!deque.isEmpty() && deque.peek() < nums[i]) {
                        twoCandidate = deque.pop();
                    }
                    
                    // Mark that we now have a valid "2" candidate
                    twoCandidateSet = true;
                    
                    // Push current number as our new "3" candidate
                    deque.push(nums[i]);
                }
                
                // Note: If nums[i] == deque.peek(), none of the conditions trigger
                // and we simply continue without modifying the stack
                
            } else {
                // Stack is empty - this is the first element we're processing (rightmost in array)
                // Push it as our initial potential "3" candidate
                deque.push(nums[i]);
            }
        }
        
        // If we've traversed the entire array without finding a pattern, return false
        return false;
    }
}