package com.saqib.scribbles;

import java.util.*;
import java.util.stream.*;

public class ReductionExamples {
    public static void main(String[] args) {
        List<String> words = List.of("alpha", "beta", "gamma", "delta");

        // reduce(identity, accumulator)
        int totalLength = words.stream()
                               .reduce(0,
                                       (sum, w) -> sum + w.length(),
                                       Integer::sum);
        System.out.println("Total length: " + totalLength);
        // -> sum of lengths

        // reduce(identity, accumulator) without combiner (works fine on sequential)
        String concatenated = words.stream()
                                   .reduce("", (a, b) -> a + "-" + b);
        System.out.println("Concatenated: " + concatenated);
        // -> "-alpha-beta-gamma-delta"

        // Primitive: sum
        int sum = words.stream()
                       .mapToInt(String::length)
                       .sum();
        System.out.println("Sum: " + sum);

        // Primitive: min
        int minLen = words.stream()
                          .mapToInt(String::length)
                          .min()
                          .orElse(0);
        System.out.println("Min length: " + minLen);

        // Primitive: max
        int maxLen = words.stream()
                          .mapToInt(String::length)
                          .max()
                          .orElse(0);
        System.out.println("Max length: " + maxLen);

        // Primitive: average
        double avgLen = words.stream()
                             .mapToInt(String::length)
                             .average()
                             .orElse(0);
        System.out.println("Average length: " + avgLen);

        // collect(Collector) — built-in collectors
        Map<Integer, List<String>> groupedByLength = words.stream()
                                                          .collect(Collectors.groupingBy(String::length));
        System.out.println(groupedByLength);

        // collect(Supplier, Accumulator, Combiner) — 3-arg form
        Set<String> upperSet = words.stream()
                                    .collect(HashSet::new,
                                             (set, w) -> set.add(w.toUpperCase()),
                                             Set::addAll);
        System.out.println(upperSet);
    }
}

