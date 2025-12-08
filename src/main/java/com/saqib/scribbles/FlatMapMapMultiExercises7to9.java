package com.saqib.scribbles;

import java.util.List;
import java.util.stream.Stream;

public class FlatMapMapMultiExercises7to9 {

    /**
     * Exercise 7
     * <p>
     * Task:
     * Using {@code flatMapToInt}, emit each character of each input word
     * as a separate {@code String}, collected into a {@code List<String>}.
     * </p>
     *
     * <p>Example input:</p>
     * <pre>
     * ["hi", "java", "stream"]
     * </pre>
     *
     * <p>Expected output:</p>
     * <pre>
     * ["h", "i", "j", "a", "v", "a", "s", "t", "r", "e", "a", "m"]
     * </pre>
     *
     * @param input list of words
     * @return list of single-character strings, in order
     */
    public static List<String> exercise7(List<String> input) {
        return input.stream()
                    .flatMapToInt(s -> s.chars())
                    .mapToObj(c -> String.valueOf((char) c))
                    .toList();
    }

    /**
     * Exercise 8
     * <p>
     * Task:
     * Using {@code mapMultiToLong}, for each input long {@code n}, emit
     * {@code n} and {@code n * 100}, collected into a {@code List<Long>}.
     * </p>
     *
     * <p>Example input:</p>
     * <pre>
     * [5, 10, 15]
     * </pre>
     *
     * <p>Expected output:</p>
     * <pre>
     * [5, 500, 10, 1000, 15, 1500]
     * </pre>
     *
     * @param input list of long values
     * @return list containing, for each n, n then n * 100
     */
    public static List<Long> exercise8(List<Long> input) {
        return input.stream()
                    .mapMultiToLong((l, s) -> {
                        s.accept(l);
                        s.accept(l * 100);
                    })
                    .boxed()
                    .toList();
    }

    /**
     * Exercise 9
     * <p>
     * Task:
     * Using {@code flatMap}, flatten a 2D array of strings into a single
     * {@code List<String>}.
     * </p>
     *
     * <p>Example input:</p>
     * <pre>
     * [
     *   ["A", "B"],
     *   ["C", "D", "E"],
     *   ["F"]
     * ]
     * </pre>
     *
     * <p>Expected output:</p>
     * <pre>
     * ["A", "B", "C", "D", "E", "F"]
     * </pre>
     *
     * @param input 2D array of strings
     * @return flattened list of all strings, in row-major order
     */
    public static List<String> exercise9(String[][] input) {
        return Stream.of(input)
                     .flatMap(Stream::of)
                     .toList();
    }

    public static void main(String[] args) {
        // Exercise 7 demo
        List<String> ex7Input = List.of("hi", "java", "stream");
        List<String> ex7Output = exercise7(ex7Input);
        System.out.println("Exercise 7 input : " + ex7Input);
        System.out.println("Exercise 7 output: " + ex7Output);

        // Exercise 8 demo
        List<Long> ex8Input = List.of(5L, 10L, 15L);
        List<Long> ex8Output = exercise8(ex8Input);
        System.out.println("Exercise 8 input : " + ex8Input);
        System.out.println("Exercise 8 output: " + ex8Output);

        // Exercise 9 demo
        String[][] ex9Input = {
                {"A", "B"},
                {"C", "D", "E"},
                {"F"}
        };
        List<String> ex9Output = exercise9(ex9Input);
        System.out.println("Exercise 9 input : [[A, B], [C, D, E], [F]]");
        System.out.println("Exercise 9 output: " + ex9Output);
    }
}