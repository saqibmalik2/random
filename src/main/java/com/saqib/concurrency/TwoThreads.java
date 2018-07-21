package com.saqib.concurrency;

import java.util.Arrays;
import java.util.Random;
import com.saqib.concurrency.utils.Computations;

import static java.lang.System.*;

public class TwoThreads {
	
	private static Random randomIntegerGenerator = new Random();
	private static Random randomLongGenerator = new Random();
	
	private static Runnable runnableOne = () -> {
		int total = 1250;
		for (int i=0;i < 50; i++) {
			int nextValue = randomIntegerGenerator.nextInt(50);
			total = total - nextValue;
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			out.println(Thread.currentThread().getName()+ " -- " + "Random value [" + i + "]: " + nextValue + "  Current total: " + total);
			if (total < 0) {
				out.println("Breaking out...");
				break;
			}
		}
	};
	
	private static Runnable runnableTwo = () -> {
		long[] longArray = new long[50];
		for (int i=0; i < 50; i++) {
			longArray [i] = randomLongGenerator.nextInt(50);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			out.println(Thread.currentThread().getName()+ " -- " + "Array of longs: " + Arrays.toString(longArray) + "  Average value: " + Computations.average(longArray));
		}
	};

	public static void main(String[] args) {
		new Thread(runnableTwo).start();
		new Thread(runnableOne).start();
	}
}
