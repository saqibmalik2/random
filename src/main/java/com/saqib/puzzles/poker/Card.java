package com.saqib.puzzles.poker;

public class Card {
	
	// A card has a value of between 1 and 14 (Jack =11, Queen = 12, King = 13, Ace = 14) as well as a Suit (HEARTS, DIAMONDS, SPADES, CLUBS).
	Integer value;
	Suit suit;
	
	public Card(Integer value, Suit suit) {
		this.value = value;
		this.suit = suit;
	}
	
	public Integer getValue() {
		return this.value;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	

}
