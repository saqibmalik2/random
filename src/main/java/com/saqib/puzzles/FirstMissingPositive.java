package com.saqib.puzzles;

/**
 * Finds the smallest missing positive integer in an unsorted array.
 * 
 * Problem: Given an unsorted integer array, find the smallest missing positive integer (1, 2, 3, ...).
 * 
 * Time Complexity: O(n) - Three passes through the array, O(3n) = O(n)
 * Space Complexity: O(1) - Only uses a few variables, modifies input in-place
 * 
 * Explanation of the algorithm:
 * 
 * We know the answer lies in the range 1 to n+1 where n is the length of the provided array.
 * Why? Because we are looking for the first (i.e. the smallest) positive integer not appearing 
 * in the array. Suppose the array contains every positive integer it could possibly hold, then 
 * for an array of length n that would give: [1,2,3,...,n] and so the first missing positive 
 * integer would be n+1. Hence we know the missing number must be n+1 or less and since it's a 
 * positive integer it must be in the range 1 to n+1.
 * 
 * We know the array has indices 0 to n-1. What if we could mark the presence of an integer in 
 * the range 1 to n by using these indices somehow? Well we can.
 * 
 * We will iterate through the array and every time we encounter an integer in the range 1 to n 
 * (we define a variable val to hold it) we mark its presence by doing something to the index 
 * val - 1. Why -1? Because arrays are zero indexed and so if we encounter the integer n we need 
 * to mark its presence in index n - 1 (attempting to place it in index n would result in a 
 * runtime error). And how do we mark the presence? By changing the value at that location to 
 * negative. NOTE: as our array can contain duplicates (e.g. {1,2,2,4,5}) we must guard against 
 * making an already negative value negative again (i.e. positive again). The code contains a 
 * comment to that effect. So for example if we have an array {1,3,4} then the indices are 0,1,2. 
 * As we iterate through we encounter the 1 and mark its presence by making the value at index 0 
 * negative. The next value is 3 so we make the value at index 2 negative. And lastly the value 4 
 * is out of range so we don't consider it. The result of this processing is {-1,3,-4}.
 * 
 * Once we have processed the array in this manner the answer will be determined by the index of 
 * the first non-negative value we encounter. The actual answer will be the index of this value + 1 
 * or in the case we don't encounter any (e.g. an array such as {1,2,3,4}) then it will be the 
 * length of the array + 1. In the example above we can see that in {-1,3,-4} the first non-negative 
 * number we encounter is 3 which is at index 1. Hence the first missing positive integer is 2.
 * 
 * Finally we should explain the "pre-processing" stage. We must eliminate any zeroes or already 
 * negative numbers present in the array that will interfere with our use of negatives to mark the 
 * presence of positive integers. We do that by changing them to a positive number outside of the 
 * range 1 to n. Any value greater than n is fine as when we process the array we ignore any values 
 * not in the range 1 to n. In the code I used n + 2 as in an interview process we should "play it 
 * safe" rather than go for the exact boundary values. But in truth any value n + 1 (or greater) 
 * works fine.
 * 
 * Below is a dry run of this algorithm with an example input array.
 * 
 * Example Dry Run with input [3, 4, -1, 1]:
 * 
 * Initial state: [3, 4, -1, 1]
 * 
 * After Pass 1 (preprocessing): [3, 4, 6, 1]
 * - Replaced -1 with 6 (n+2) since -1 is non-positive
 * 
 * After Pass 2 (marking):
 * - i=0: val=3, mark index 2 → [3, 4, -6, 1]
 * - i=1: val=4, mark index 3 → [3, 4, -6, -1]
 * - i=2: val=6, skip (6 > n) [note that although the actually value is -6 we consider the absolute value]
 * - i=3: val=1, mark index 0 → [-3, 4, -6, -1]
 * Result: [-3, 4, -6, -1]
 * Index:   0   1   2    3
 * Means:   1   ?   3    4  are present
 * 
 * After Pass 3 (finding first positive):
 * - i=0: nums[0]=-3 (negative, skip)
 * - i=1: nums[1]=4 (positive! First unmarked index)
 * - lowestMissing = 1
 * - Return 1 + 1 = 2 ✓
 */
public class FirstMissingPositive {

    public static void main(String[] args) {
        FirstMissingPositive firstMissingPositive = new FirstMissingPositive();
        int lowestMissingInteger;
        lowestMissingInteger = firstMissingPositive.firstMissingPositive(new int[] {3, 4, -1, 1});
        System.out.println(lowestMissingInteger);
    }

    /**
     * Finds the first missing positive integer.
     * 
     * @param nums the input array (will be modified in-place)
     * @return the smallest positive integer not present in the array
     */
    public int firstMissingPositive(int[] nums) {
        // Default to n if all numbers [1, n] are present, answer will be n+1
        int lowestMissing = nums.length;

        // PASS 1: Preprocessing - Replace all non-positive numbers with a safe placeholder
        // We only care about numbers in range [1, n], so anything <= 0 is irrelevant
        // We replace them with n+2, which is safely outside our range of interest
        // 
        // Example: [3, 4, -1, 1] becomes [3, 4, 6, 1]
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0) {
                nums[i] = nums.length + 2;  // Safe placeholder (could be any value > n)
            }
        }

        // PASS 2: Marking phase - Use sign bit to mark which numbers exist
        // For each number k in range [1, n], we mark index (k-1) as negative
        // This means: "number k exists in the array"
        // We use Math.abs() when reading because previous iterations may have already made values negative
        //
        // Example trace for [3, 4, 6, 1]:
        // i=0: val=3, mark nums[2] → [3, 4, -6, 1]
        // i=1: val=4, mark nums[3] → [3, 4, -6, -1]
        // i=2: val=6, skip (6 > n=4, out of range) [as per above, although the actually value is -6 we consider the absolute value]
        // i=3: val=1, mark nums[0] → [-3, 4, -6, -1]
        for (int i = 0; i < nums.length; i++) {
            var val = Math.abs(nums[i]);  // Get absolute value (in case already marked negative)
            
            // Only process numbers in our range of interest [1, n]
            if (val <= nums.length) {
                // Mark index (val-1) as negative to indicate "val exists"
                // Use Math.abs() on target in case it's already negative from a duplicate
                nums[val - 1] = -Math.abs(nums[val - 1]);
            }
        }

        // PASS 3: Find the first index that's still positive
        // A positive value at index i means number (i+1) was never marked, so it's missing
        //
        // Example for [-3, 4, -6, -1]:
        // i=0: nums[0]=-3 (negative, so 1 exists)
        // i=1: nums[1]=4 (positive! So 2 is missing)
        // lowestMissing = 1, return 1+1 = 2
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                lowestMissing = i;  // Found first unmarked index
                break;
            }
        }

        // If no positive value found, all numbers [1, n] exist, so answer is n+1
        // Otherwise, answer is (first positive index) + 1
        return lowestMissing + 1;
    }
}