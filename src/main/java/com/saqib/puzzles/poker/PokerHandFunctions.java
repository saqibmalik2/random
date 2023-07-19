package com.saqib.puzzles.poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class PokerHandFunctions {
	
	Function<List<Card>, Hand> isStraightFlush =  hand -> {
		Suit firstCardSuit = hand.get(0).getSuit();
		for (int i=1;i<5;i++) {
			if ((hand.get(i).getValue() - hand.get(i-1).getValue() != 1 ) || hand.get(i).getSuit() != firstCardSuit) return Hand.DEFAULT;
		}
		return Hand.STRAIGHTFLUSH;
	};
	
	Function<List<Card>, Hand> isOnePair =  hand -> {
			if (countPairs(hand) == 1) return Hand.ONEPAIR;
			return Hand.DEFAULT;
		};
		
	Function<List<Card>, Hand> isTwoPair =  hand -> {
		if (countPairs(hand) == 2) return Hand.TWOPAIR;
		return Hand.DEFAULT;
	};
	
	
	Function<List<Card>, Hand> isRoyalFlush =  hand -> {
		Suit firstCardSuit = hand.get(0).getSuit();
		for (int i=1;i<5;i++) {
			if ((hand.get(i).getValue() - hand.get(i-1).getValue() != 1 ) || hand.get(i).getSuit() != firstCardSuit) return Hand.DEFAULT;
		}
		if (hand.get(0).getValue() == 10) return Hand.ROYALFLUSH;
		return Hand.DEFAULT;
	};
	
	Function<List<Card>, Hand> isFourOfAKind =  hand -> {
		HashMap<Integer, Integer> cardsMap= new HashMap<>();
		hand.stream().forEach(card -> cardsMap.merge(card.getValue(), 1, Integer::sum));
		if (cardsMap.values().stream().anyMatch(val -> val == 4)) return Hand.FOUROFAKIND;
		return Hand.DEFAULT;
	};
	
	Function<List<Card>, Hand> isFullHouse =  hand -> {
		HashMap<Integer, Integer> cardsMap= new HashMap<>();
		hand.stream().forEach(card -> cardsMap.merge(card.getValue(), 1, Integer::sum));
		if (cardsMap.values().contains(3) && cardsMap.values().contains(2)) return Hand.FULLHOUSE;
		return Hand.DEFAULT;
	};
	
	Function<List<Card>, Hand> isFlush =  hand -> {
		Suit firstCardSuit = hand.get(0).getSuit();
		boolean allSameSuit = hand.stream().allMatch(card -> card.getSuit() == firstCardSuit);
		boolean inSequence = true;
		for (int i=1;i<5;i++) {
			if ((hand.get(i).getValue() - hand.get(i-1).getValue() != 1 )) inSequence = false;
		}
		if (allSameSuit && !inSequence) return Hand.FLUSH;
		return Hand.DEFAULT;
	};
	
	Function<List<Card>, Hand> isStraight =  hand -> {
		Suit firstCardSuit = hand.get(0).getSuit();
		boolean allSameSuit = hand.stream().allMatch(card -> card.getSuit() == firstCardSuit);
		for (int i=1;i<5;i++) {
			if ((hand.get(i).getValue() - hand.get(i-1).getValue() != 1) && !allSameSuit) return Hand.DEFAULT;;
		}
		return Hand.STRAIGHT;
	};
	
	Function<List<Card>, Hand> isThreeOfAKind =  hand -> {
		HashMap<Integer, Integer> cardsMap= new HashMap<>();
		hand.stream().forEach(card -> cardsMap.merge(card.getValue(), 1, Integer::sum));
		if (cardsMap.values().contains(3) && !cardsMap.values().contains(2)) return Hand.THREEOFAKIND;
		return Hand.DEFAULT;
	};
	
	
	private long countPairs(List<Card> hand) {
		HashMap<Integer, Integer> cardsMap= new HashMap<>();
		hand.stream().forEach(card -> cardsMap.merge(card.getValue(), 1, Integer::sum));
		long returnValue = cardsMap.values().stream().filter(v -> v==2).count();
		System.out.println(returnValue);
		return returnValue;
	}
	

	
	public List<Function<List<Card>, Hand>> getHandEvaluationStrategies(){
		List<Function<List<Card>, Hand>> handEvaluationStrategies = new ArrayList<>();
		handEvaluationStrategies.add(isRoyalFlush);
		handEvaluationStrategies.add(isStraightFlush);
		handEvaluationStrategies.add(isFourOfAKind);
		handEvaluationStrategies.add(isFullHouse);
		handEvaluationStrategies.add(isFlush);
		handEvaluationStrategies.add(isStraight);
		handEvaluationStrategies.add(isThreeOfAKind);
		handEvaluationStrategies.add(isTwoPair);
		handEvaluationStrategies.add(isOnePair);
		return handEvaluationStrategies;
	}

}
