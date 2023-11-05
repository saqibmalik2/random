package com.saqib.puzzles;

public class MinMaxDivision {

	public static void main(String[] args) {
		int[] A = new int[] {2,1,5,1,2,2,2};
		int K = 3;
		int M = 5;
		int minLargeSum = new MinMaxDivision().minLargeSum(K, M, A);
		
		System.out.println("Min large sum is: " + minLargeSum);
	}
	
	public int minLargeSum(int K, int M, int[] A){
		int minSum=0;
		int maxSum=0;
		for (int i: A) {
			maxSum += i;
			minSum = Math.max(minSum, i);
		}
		
		System.out.println(minSum);
		System.out.println(maxSum);
		return binarySearch(K, A, minSum, maxSum);
	}
	
	private int binarySearch(int K, int[] A, int minSum, int maxSum) {
		int result = maxSum;
		
		while (minSum <= maxSum) {
			int mid = (minSum + maxSum) / 2;
			
			if (isSplitValid(mid, K, A)) {
				maxSum = mid - 1;
				result = mid;
			} else {
				minSum = mid + 1;
			}
		}
		
		return result;
	}
	
	private boolean isSplitValid(int mid, int K, int[] A) {
		int cnt = 0;
		int sum = 0;
		
		for (int i:A) {
			if (sum + i > mid) {
				sum = i;
				cnt++;
			} else {
				sum += i;
			}
		}
		
		return cnt < K;
	}

}
