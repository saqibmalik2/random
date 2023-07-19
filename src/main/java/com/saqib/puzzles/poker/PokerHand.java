package com.saqib.puzzles.poker;

import java.util.List;
import java.util.function.Function;


/** 
 * Please see the PokerPuzzle.png image for a description of the puzzle.
 * 
 */
public class PokerHand {
	
	// We are supplied with a list of 5 cards. We can assume the cards are valid and that there are only 5 of them.
	public Hand getHighestHand(List<Card> hand) {
		
		List<Function<List<Card>, Hand>> listofEvaluationStrategies = new PokerHandFunctions().getHandEvaluationStrategies();
		
		//the iterator of an ArrayList proceeds in the order of insertion. So we will be evaluating first against Royal Flush
		//and proceeding down the scale from there and will return the first match we get (the highest possible hand).
		for (Function<List<Card>, Hand> evaluationStrategy: listofEvaluationStrategies) {
			Hand resultantHand = evaluationStrategy.apply(hand);
			if (resultantHand != Hand.DEFAULT) return resultantHand;
		}
		
		// Note we don't have a specific function for Highcard as it's the lowest one and will be returned automatically if we can't
		// match against any of the others.
		return Hand.HIGHCARD;
	}
	
}
