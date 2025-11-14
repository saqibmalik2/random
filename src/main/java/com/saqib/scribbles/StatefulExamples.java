package com.saqib.scribbles;

import java.util.*;

public class StatefulExamples {
    public static void main(String[] args) {
        List<String> words = List.of("beta", "alpha", "beta", "gamma", "alpha", "delta");

        // distinct (removes duplicates; keeps encounter order)
        words.stream()
             .distinct()
             .forEach(System.out::println);
        // -> beta, alpha, gamma, delta

        // sorted() (natural order)
        words.stream()
             .sorted()
             .forEach(System.out::println);
        // -> alpha, alpha, beta, beta, delta, gamma

        // sorted(Comparator) (custom order; e.g., length then lexicographic)
        words.stream()
             .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
             .forEach(System.out::println);
        // -> beta, alpha, gamma, delta, alpha, beta

        // limit(n) (short-circuiting; may stop early)
        words.stream()
             .filter(w -> w.length() >= 4)
             .limit(3)
             .forEach(System.out::println);
        // -> first 3 with length >= 4 in encounter order

        // skip(n) (skip first n elements)
        words.stream()
             .skip(2)
             .forEach(System.out::println);
        // -> from the 3rd element onward

        // Combine skip + limit for paging (page 2, size 3)
        int page = 2, size = 3;
        words.stream()
             .sorted()
             .skip((page - 1L) * size)
             .limit(size)
             .forEach(System.out::println);

        // Note: in parallel, distinct/sorted may buffer; limit’s semantics can differ with unordered()
        words.parallelStream()
             .unordered()
             .limit(3)
             .forEach(System.out::println); // any 3 elements (order not guaranteed)
    }
}

