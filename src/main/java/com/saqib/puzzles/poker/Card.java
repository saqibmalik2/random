package com.saqib.puzzles.poker;

public class Card {
	
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
