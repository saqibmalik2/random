package com.saqib.scribbles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;

public class FlatMapLowerToUpperCase {

	public static void main(String[] args) {
		
		class Helper {
			int summation(int a, int b) {
				return a+b;
			}
		}
		
		Helper helper = new Helper();
		String[][] array = new String[][]{{"a", "b"}, {"a", "d"}, {"e", "f"}};
		Stream.of(array).flatMap(Stream::of).map(c -> c.toUpperCase()).forEach(System.out::print);
		
		System.out.println("\n" + Stream.of(array).flatMap(a -> Stream.of(a)).filter(c -> c.equalsIgnoreCase("a")).count());
		List<Integer> fibbonacciList = new ArrayList<>();
		Stream.iterate(new int[] {0, 1}, n -> new int[] {n[1], n[0] + n[1]}).limit(20).map(n -> n[1]).forEach(fibbonacciList::add);
		//fibbonacciList.stream().forEach(System.out::println);
		
		int sum = Stream.iterate(new int[]{0, 1}, n -> new int[]{n[1], n[0] + n[1]})
                .limit(10)
                .map(n -> n[0]) // Stream<Integer>
                .mapToInt(n -> n)
                .sum();
		
		
		List<Integer> integerList = new ArrayList<>();
		integerList.add(1);
		integerList.add(2);
		integerList.add(3);
		integerList.add(4);
		integerList.add(5);
		
		int sum1 = integerList.stream().mapToInt(c -> c).reduce(0, helper::summation);
		
		long sum2 = integerList.stream().mapToLong(num -> num).sum();
		
		System.out.println(sum1);
		System.out.println(sum2);
		
		System.out.println("Fibonacci 10 sum : " + sum);
	}

}
