package com.saqib.scribbles;

import java.util.List;

public class FlatMapMapMultiExercises4to6 {

    // Exercise 4
    //	Task:
    //	Using mapMulti, emit:
    //		nothing for 0
    //		1 for 1
    //		1, 2 for 2
    //		1, 2, 3 for 3
	// Example - 
    // Input:  [0, 1, 2, 3]
    // Output: [1, 1, 2, 1, 2, 3]
    public static List<Integer> exercise4(List<Integer> input) {
        return input.stream()
                    .<Integer>mapMulti((n, sink) -> {
                        for (int i = 1; i <= n; i++) {
                            sink.accept(i);
                        }
                    })
                    .toList();
    }

    // Exercise 5
    // Task: Using mapMultiToInt, emit each word’s length twice, then collect to List<Integer>.
    // Input:  ["one", "three", "seven"]
    // Output: [3, 3, 5, 5, 5, 5, 6, 6]
    public static List<Integer> exercise5(List<String> input) {
        return input.stream()
                    .mapMultiToInt((word, sink) -> {
                        int len = word.length();
                        sink.accept(len);
                        sink.accept(len);
                    })
                    .boxed()
                    .toList();
    }

    // Exercise 6
    // Task: Using flatMapToInt, emit the integer character codes of all characters in order, 
    //       collected into List<Integer>.
    // Input:  ["AB", "CD", "E"]
    // Output: [65, 66, 67, 68, 69]  // assuming standard Unicode/ASCII
    public static List<Integer> exercise6(List<String> input) {
    	return input.stream().<Integer>mapMulti( (w,s) -> {
    		w.chars().forEach(c -> s.accept(c));
    	}).toList();
    }

    public static void main(String[] args) {
        List<Integer> ex4Input = List.of(0, 1, 2, 3);
        System.out.println("Exercise 4 input : " + ex4Input);
        System.out.println("Exercise 4 output: " + exercise4(ex4Input));

        List<String> ex5Input = List.of("one", "three", "seven");
        System.out.println("Exercise 5 input : " + ex5Input);
        System.out.println("Exercise 5 output: " + exercise5(ex5Input));

        List<String> ex6Input = List.of("AB", "CD", "E");
        System.out.println("Exercise 6 input : " + ex6Input);
        System.out.println("Exercise 6 output: " + exercise6(ex6Input));
    }
}