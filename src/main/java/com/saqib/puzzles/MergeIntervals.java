package com.saqib.puzzles;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

/*****
 
Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, 
and return an array of the non-overlapping intervals that cover all the intervals in the input.

Example 1:

Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].

Example 2:

Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 
Constraints:

1 <= intervals.length <= 104
intervals[i].length == 2
0 <= starti <= endi <= 104 

******/

public class MergeIntervals {
	
	
	public static void main(String[] args) {
//		int [][] mergedArray = new MergeIntervals().merge(new int[][]{{1,3},{2,6},{8,10},{15,18}});
//		System.out.println(Arrays.deepToString(mergedArray));
//		int [][] mergedArray1 = new Solution().merge(new int[][]{{1,4},{4,5}});
//		System.out.println(Arrays.deepToString(mergedArray1));
//		int [][] mergedArray2 = new Solution().merge(new int[][]{{1,4},{2,3}});
//		System.out.println(Arrays.deepToString(mergedArray2));
		int [][] mergedArray3 = new MergeIntervals().merge(new int[][]{{1,4},{0,2},{3,5}});
		System.out.println(Arrays.deepToString(mergedArray3));
//		int [][] mergedArray5 = new Solution().merge(new int[][]{{2,3},{2,2},{3,3},{1,3},{5,7},{2,2},{4,6}});
//		System.out.println(Arrays.deepToString(mergedArray5));
	}
	
	public int[][] merge(int[][] intervals){
		
		// trivial case 
		if (intervals.length == 1) return intervals;
		
		// sort the array according to the interval start time
	    Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
	    
	    Stack<int[]> mergedStack = new Stack<>();
	    
	    mergedStack.push(intervals[0]);
	    
	    for (int[] interval:intervals) {
	    	int[] topStack = mergedStack.peek();
	    	if (interval[0] <= topStack[1]) {
	    		mergedStack.pop();
	    		int[] mergedValue = new int[2];
	    		mergedValue[0] = topStack[0];
	    		mergedValue[1] = Math.max(topStack[1], interval[1]);
	    		mergedStack.push(mergedValue);
	    		continue;
	    	}
	    	mergedStack.push(interval);
	    }
	    
	    int stackSize = mergedStack.size();
	    int[][] mergedArray = new int[stackSize][2];
	    
	    for (int i=0; i < stackSize; i++) {
	    	mergedArray[i] = mergedStack.elementAt(i);
	    }
	    return mergedArray;
	   
	}
	
}
