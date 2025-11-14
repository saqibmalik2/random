package com.saqib.scribbles;

import java.util.*;
import static java.util.stream.Collectors.*;



public class Example {

    record Trade(String trader, double notional, boolean internal) {
	}
	
  public static void main(String[] args) {
    List<Trade> trades = List.of(
        new Trade("Alice", 1_000.0, true),
        new Trade("Alice", 2_500.0, false),
        new Trade("Bob",   700.0,  true),
        new Trade("Bob",   300.0,  false),
        new Trade("Bob", 1_200.0,  true)
    );

    // 1) Basic: stats of some characteristic per key
    Map<String, DoubleSummaryStatistics> statsByTrader =
        trades.stream().collect(
            groupingBy(Trade::trader, summarizingDouble(Trade::notional))
        );

    // 2) With a filter on the bucketed stream (Java 9+)
    Map<String, DoubleSummaryStatistics> internalStatsByTrader =
        trades.stream().collect(
            groupingBy(Trade::trader,
                filtering(Trade::internal,
                    summarizingDouble(Trade::notional)))
        );

    // 3) If you already *have* Map<K, List<T>>, summarize afterward
    Map<String, List<Trade>> grouped =
        trades.stream().collect(groupingBy(Trade::trader));

    Map<String, DoubleSummaryStatistics> statsFromPreGrouped =
        grouped.entrySet().stream().collect(toMap(
            Map.Entry::getKey,
            e -> e.getValue().stream()
                    .mapToDouble(Trade::notional)
                    .summaryStatistics()
        ));

    // Use: statsByTrader.get("Alice").getAverage(), getSum(), getCount(), getMin(), getMax()
  }
}