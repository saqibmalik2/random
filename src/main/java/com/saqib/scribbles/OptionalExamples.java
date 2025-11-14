package com.saqib.scribbles;

import java.util.*;

public class OptionalExamples {
    public static void main(String[] args) {
        List<String> words = List.of("beta", "alpha", "gamma", "delta");

        // findFirst (deterministic in sequential/ordered)
        Optional<String> first = words.stream().findFirst();
        System.out.println("findFirst: " + first.orElse("<none>"));
        // -> beta

        // findAny (may return any element; useful in parallel)
        Optional<String> any = words.parallelStream().findAny();
        System.out.println("findAny: " + any.orElse("<none>"));
        // -> could be any of beta/alpha/gamma/delta

        // min (Optional<T>)
        Optional<String> shortest = words.stream()
                .min(Comparator.comparingInt(String::length));
        System.out.println("min: " + shortest.orElse("<none>"));
        // -> beta (length 4)

        // max (Optional<T>)
        Optional<String> longest = words.stream()
                .max(Comparator.comparingInt(String::length));
        System.out.println("max: " + longest.orElse("<none>"));
        // -> gamma (length 5)

        // reduce (no identity) -> Optional<T>
        Optional<String> concatenated = words.stream()
                .reduce((a, b) -> a + "-" + b);
        System.out.println("reduce(no identity): " + concatenated.orElse("<none>"));
        // -> beta-alpha-gamma-delta

        // average (primitive stream) -> OptionalDouble
        OptionalDouble avgLen = words.stream()
                .mapToInt(String::length)
                .average();
        System.out.println("average length: " + avgLen.orElse(0.0));
        // -> 4.5
    }
}

