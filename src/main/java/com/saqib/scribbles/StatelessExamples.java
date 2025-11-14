package com.saqib.scribbles;

import java.util.*;
import java.util.stream.*;

public class StatelessExamples {
    public static void main(String[] args) {
        List<String> words = List.of("alpha", "beta", "gamma", "delta");

		/*
		 * // map words.stream() .map(String::toUpperCase)
		 * .forEach(System.out::println);
		 * 
		 * // mapToInt words.stream() .mapToInt(String::length)
		 * .forEach(System.out::println);
		 * 
		 * // flatMap words.stream() .flatMap(w -> Arrays.stream(w.split(""))) // split
		 * into letters .forEach(System.out::print);
		 * 
		 * // flatMapToInt words.stream() .flatMapToInt(w -> w.chars()) // chars() gives
		 * IntStream .forEach(ch -> System.out.print((char) ch));
		 * 
		 * // filter words.stream() .filter(w -> w.length() > 4)
		 * .forEach(System.out::println);
		 * 
		 * // peek (for debugging/side-effects) words.stream() .peek(w ->
		 * System.out.println("before: " + w)) .map(String::toUpperCase) .peek(w ->
		 * System.out.println("after: " + w)) .toList();
		 * 
		 * // unordered words.stream() .unordered() // relaxes ordering guarantees
		 * .forEach(System.out::println);
		 * 
		 * // parallel words.parallelStream() .map(String::toUpperCase)
		 * .forEach(System.out::println);
		 * 
		 * // sequential words.parallelStream() .sequential() // force back to
		 * sequential .forEach(System.out::println);
		 */

        // onClose (meta — runs when stream closed)
        Stream<String> s = words.stream()
            .onClose(() -> System.out.println("Stream closed!"));
        s.forEach(System.out::println);
        s.close();

		/*
		 * // mapMulti (Java 16+) words.stream() .<String>mapMulti((w, consumer) -> {
		 * consumer.accept(w); consumer.accept(w.toUpperCase()); })
		 * .forEach(System.out::println);
		 * 
		 * // mapMultiToInt (Java 16+) words.stream() .mapMultiToInt((w, consumer) -> {
		 * consumer.accept(w.length()); consumer.accept(w.length() * 2); })
		 * .forEach(System.out::println);
		 */
        
    }
}

