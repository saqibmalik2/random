package com.saqib.concurrency.utils;

import java.util.stream.LongStream;

public class Computations {

	public static long average(long ...numbers) {
		long total = LongStream.of(numbers).sum();
		return total/numbers.length;
	}

}
