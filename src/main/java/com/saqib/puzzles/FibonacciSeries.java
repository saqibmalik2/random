package com.saqib.puzzles;

import java.util.Arrays;

public class FibonacciSeries {
	
	public static void main(String[] args) {
		FibonacciSeries fs = new FibonacciSeries();
		System.out.println(Arrays.toString(fs.printFibonacciSeries(Integer.parseInt(args[0]))));
	}
	
	public int[] printFibonacciSeries (int seriesNumber) {
		
		if (seriesNumber < 1) throw new IllegalArgumentException("Term for Fibonacci series must be greater than 1");
		
		int[] series = new int[seriesNumber];
		series[0] = 0;
		series[1] = 1;
		int result = 0;
		
		if (seriesNumber < 3) return series;
		
		for (int count = 2; count < seriesNumber; count++) {
			result = series[count-1] + series[count-2];
			series[count] = result;
		}
		
		return series;
	}

}
