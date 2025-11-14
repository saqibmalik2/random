package com.saqib.puzzles;

import java.util.Arrays;
import java.util.HashMap;


/**
 * Classic algorithm question. Optimal solution that runs in O(n) time.
 * We set up a HashMap which will hold the difference between the array value and the target value.
 * Start iterating through the array and check if the current value is contained in the HashMap. If it is then...
 * ...we have our pairs. If not place the difference between the current value and the target value into the HashMap.
 * 
 */
public class TwoSum {

	public static void main(String[] args) {
		TwoSum twoSum = new TwoSum();
		int[] result = twoSum.twoSum(new int[] {2,7,11,15}, 9);
		System.out.println(Arrays.toString(result));
	}
	
	public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> difference = new HashMap<>();
        for (int i=0;i<nums.length;i++){
            if (difference.containsKey(nums[i])) {
            	return new int[] {difference.get(nums[i]), i};
            }
            else {
            	difference.put(target-nums[i], i);
            }
        }
        return null;
    }

}
