package com.saqib.scribbles;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;

public class FlatMapMapMultiExercises1to3 {

    /**
     * Exercise 1
     * Input:
     *   [
     *     ["alpha", "beta"],
     *     ["gamma", "delta"]
     *   ]
     * Task:
     *   Flatten the nested lists using flatMap.
     */
    public static List<String> exercise1_flattenNestedLists(List<List<String>> input) {
        return input.stream()
                    .flatMap(List::stream)
                    .toList();
    }

    /**
     * Exercise 2
     * Input:
     *   [
     *     [2, 4],
     *     [6],
     *     [8, 10, 12]
     *   ]
     * Task:
     *   Flatten and convert each integer to its negative using mapMultiToInt.
     */
    public static List<Integer> exercise2_flattenAndNegate(List<List<Integer>> input) {
        BiConsumer<List<Integer>, IntConsumer> negatingEmitter = (list, sink) -> {
            for (int n : list) {
                sink.accept(-n);
            }
        };

        return input.stream()
                    .mapMultiToInt(negatingEmitter)
                    .boxed()
                    .toList();
    }

    /**
     * Exercise 3
     * Input:
     *   [ Optional["A"], Optional.empty, Optional["B"], Optional.empty, Optional["C"] ]
     * Task:
     *   Extract only the present values using flatMap(Optional::stream).
     */
    public static List<String> exercise3_flattenOptionals(List<Optional<String>> input) {
        return input.stream()
                    .flatMap(Optional::stream)
                    .toList();
    }

    public static void main(String[] args) {
    	
    	List<Integer> input = List.of(0, 1, 2, 3);
    	
    	/**
    	 * Using mapMulti, produce a List<Integer> where, for each n:

				0 produces no values,
				
				1 produces 1,
				
				2 produces 1, 2,
				
				3 produces 1, 2, 3.
    	 */
    	
    	List<Integer> listOfIntegers = input.stream()
        .<Integer>mapMulti((n, sink) -> {
            for (int i = 1; i <= n; i++) {
                sink.accept(i);
            }
        })
        .toList();
    	
    	System.out.println(listOfIntegers);
    	
        // Exercise 1 demo
        List<List<String>> ex1Input = List.of(
                List.of("alpha", "beta"),
                List.of("gamma", "delta")
        );
        System.out.println("Exercise 1 input : " + ex1Input);
        System.out.println("Exercise 1 output: " + exercise1_flattenNestedLists(ex1Input));

        // Exercise 2 demo
        List<List<Integer>> ex2Input = List.of(
                List.of(2, 4),
                List.of(6),
                List.of(8, 10, 12)
        );
        System.out.println("Exercise 2 input : " + ex2Input);
        System.out.println("Exercise 2 output: " + exercise2_flattenAndNegate(ex2Input));

        // Exercise 3 demo
        List<Optional<String>> ex3Input = List.of(
                Optional.of("A"),
                Optional.empty(),
                Optional.of("B"),
                Optional.empty(),
                Optional.of("C")
        );
        System.out.println("Exercise 3 input : " + ex3Input);
        System.out.println("Exercise 3 output: " + exercise3_flattenOptionals(ex3Input));
    }
}
