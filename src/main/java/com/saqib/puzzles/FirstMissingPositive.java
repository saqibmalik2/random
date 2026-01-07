package com.saqib.puzzles;

/**
 * Finds the smallest missing positive integer in an unsorted array.
 * <p>
 * <b>Problem:</b> Given an unsorted integer array, find the smallest missing positive integer (1, 2, 3, ...).
 * <p>
 * <b>Time Complexity:</b> O(n) - Three passes through the array, O(3n) = O(n)<br>
 * <b>Space Complexity:</b> O(1) - Only uses a few variables, modifies input in-place
 * <p>
 * <b>Explanation of the algorithm:</b>
 * <p>
 * We know the answer lies in the range 1 to n+1 where n is the length of the provided array.
 * Why? Because we are looking for the first (i.e. the smallest) positive integer not appearing 
 * in the array. Suppose the array contains every positive integer it could possibly hold, then 
 * for an array of length n that would give: [1,2,3,...,n] and so the first missing positive 
 * integer would be n+1. Hence we know the missing number must be n+1 or less and since it's a 
 * positive integer it must be in the range 1 to n+1.
 * <p>
 * We know the array has indices 0 to n-1. What if we could mark the presence of an integer in 
 * the range 1 to n by using these indices somehow? Well we can.
 * <p>
 * We will iterate through the array and every time we encounter an integer in the range 1 to n 
 * (we define a variable val to hold it) we mark its presence by doing something to the index 
 * val - 1. Why -1? Because arrays are zero indexed and so if we encounter the integer n we need 
 * to mark its presence in index n - 1 (attempting to place it in index n would result in a 
 * runtime error). And how do we mark the presence? By changing the value at that location to 
 * negative.
 * <p>
 * <b>NOTE:</b> as our array can contain duplicates (e.g. {1,2,2,4,5}) we must guard against 
 * making an already negative value negative again (i.e. positive again). The code contains a 
 * comment to that effect.
 * <p>
 * <b>Example:</b> Suppose we have the array {1,3,4} with indices 0,1,2 (where n=3).
 * <ul>
 * <li>We encounter 1: mark its presence by making the value at index (1-1)=0 negative → {-1,3,4}</li>
 * <li>We encounter 3: mark its presence by making the value at index (3-1)=2 negative → {-1,3,-4}</li>
 * <li>We encounter 4: this is out of range (4 &gt; n) so we ignore it → {-1,3,-4}</li>
 * </ul>
 * <p>
 * The result of this processing is {-1,3,-4}. Notice that index 0 is negative (1 is present), 
 * index 2 is negative (3 is present), but index 1 remains positive (2 is NOT present). When we 
 * later scan for the first positive value, we'll find it at index 1, telling us that 2 is the 
 * first missing positive integer (index + 1 = 1 + 1 = 2).
 * </p>
 * <p>
 * Once we have processed the array in this manner the answer will be determined by the index of 
 * the first non-negative value we encounter. The actual answer will be the index of this value + 1 
 * or in the case we don't encounter any (e.g. an array such as {1,2,3,4}) then it will be the 
 * length of the array + 1. In the example above we can see that in {-1,3,-4} the first non-negative 
 * number we encounter is 3 which is at index 1. Hence the first missing positive integer is 2.
 * <p>
 * Finally we should explain the "pre-processing" stage. We must eliminate any zeroes or already 
 * negative numbers present in the array that will interfere with our use of negatives to mark the 
 * presence of positive integers. We do that by changing them to a positive number outside of the 
 * range 1 to n. Any value greater than n is fine as when we process the array we ignore any values 
 * not in the range 1 to n. In the code I used n + 2 as in an interview process we should "play it 
 * safe" rather than go for the exact boundary values. But in truth any value n + 1 (or greater) 
 * works fine.
 * <p>
 * Below is a dry run of this algorithm with an example input array.
 * <p>
 * <b>Example Dry Run with input [3, 4, -1, 1]:</b>
 * <p>
 * Initial state: [3, 4, -1, 1]
 * <p>
 * <b>After Pass 1 (preprocessing):</b> [3, 4, 6, 1]
 * <ul>
 * <li>Replaced -1 with 6 (n+2) since -1 is non-positive</li>
 * </ul>
 * <p>
 * <b>After Pass 2 (marking):</b>
 * <ul>
 * <li>i=0: val=3, mark index 2 → [3, 4, -6, 1]</li>
 * <li>i=1: val=4, mark index 3 → [3, 4, -6, -1]</li>
 * <li>i=2: val=6, skip (6 &gt; n) [note that although the actually value is -6 we consider the absolute value]</li>
 * <li>i=3: val=1, mark index 0 → [-3, 4, -6, -1]</li>
 * </ul>
 * Result: [-3, 4, -6, -1]<br>
 * Index:&nbsp;&nbsp; 0&nbsp;&nbsp; 1&nbsp;&nbsp; 2&nbsp;&nbsp;&nbsp; 3<br>
 * Means:&nbsp;&nbsp; 1&nbsp;&nbsp; ?&nbsp;&nbsp; 3&nbsp;&nbsp;&nbsp; 4&nbsp; are present
 * <p>
 * <b>After Pass 3 (finding first positive):</b>
 * <ul>
 * <li>i=0: nums[0]=-3 (negative, skip)</li>
 * <li>i=1: nums[1]=4 (positive! First unmarked index)</li>
 * <li>lowestMissing = 1</li>
 * <li>Return 1 + 1 = 2 ✓</li>
 * </ul>
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