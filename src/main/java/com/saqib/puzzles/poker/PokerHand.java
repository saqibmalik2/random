package com.saqib.puzzles.poker;

import java.util.List;
import java.util.function.Function;
public class PokerHand {
	
	public Hand getHighestHand(List<Card> hand) {
		
		List<Function<List<Card>, Hand>> listofEvaluationStrategies = new PokerHandFunctions().getHandEvaluationStrategies();
		
		for (Function<List<Card>, Hand> evaluationStrategy: listofEvaluationStrategies) {
			Hand resultantHand = evaluationStrategy.apply(hand);
			if (resultantHand != Hand.DEFAULT) return resultantHand;
		}
		
		return Hand.HIGHCARD;
	}
	
}
