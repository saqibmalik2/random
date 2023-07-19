package com.saqib.puzzles.poker;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.saqib.puzzles.poker.Card;
import com.saqib.puzzles.poker.Hand;
import com.saqib.puzzles.poker.PokerHand;
import com.saqib.puzzles.poker.Suit;

public class PokerHandTest {
	
	static List<Card> deal = new ArrayList<>();
	
	
	@AfterEach
	void clearDeal() {
		deal.clear();
	}
	
	@Test
	public void testOnePair() {
		
		Card firstCard = new Card(7, Suit.DIAMONDS);
		Card secondCard = new Card(7, Suit.CLUBS);
		Card thirdCard = new Card(5, Suit.HEARTS);
		Card fourthCard = new Card(6, Suit.CLUBS);
		Card fifthCard = new Card(3, Suit.DIAMONDS);
		
		deal.add(firstCard);
		deal.add(secondCard);
		deal.add(thirdCard);
		deal.add(fourthCard);
		deal.add(fifthCard);
		
		Hand highestHand = new PokerHand().getHighestHand(deal);
		
		assertTrue(highestHand == Hand.ONEPAIR);
		
	}
	
	@Test
	public void testTwoPair() {
		Card firstCard = new Card(10, Suit.DIAMONDS);
		Card secondCard = new Card(10, Suit.CLUBS);
		Card thirdCard = new Card(5, Suit.HEARTS);
		Card fourthCard = new Card(5, Suit.CLUBS);
		Card fifthCard = new Card(3, Suit.HEARTS);
		
		deal.add(firstCard);
		deal.add(secondCard);
		deal.add(thirdCard);
		deal.add(fourthCard);
		deal.add(fifthCard);
		
		Hand highestHand = new PokerHand().getHighestHand(deal);
		
		assertTrue(highestHand == Hand.TWOPAIR);
		
	}
	
	@Test
	public void testRoyalFlush() {
		Card firstCard = new Card(10, Suit.HEARTS);
		Card secondCard = new Card(11, Suit.HEARTS);
		Card thirdCard = new Card(12, Suit.HEARTS);
		Card fourthCard = new Card(13, Suit.HEARTS);
		Card fifthCard = new Card(14, Suit.HEARTS);
		
		deal.add(firstCard);
		deal.add(secondCard);
		deal.add(thirdCard);
		deal.add(fourthCard);
		deal.add(fifthCard);
		
		Hand highestHand = new PokerHand().getHighestHand(deal);
		
		assertTrue(highestHand == Hand.ROYALFLUSH);
	}
	
	@Test
	public void testFourOfAKind() {
		Card firstCard = new Card(7, Suit.HEARTS);
		Card secondCard = new Card(7, Suit.DIAMONDS);
		Card thirdCard = new Card(7, Suit.CLUBS);
		Card fourthCard = new Card(7, Suit.SPADES);
		Card fifthCard = new Card(9, Suit.HEARTS);
		
		deal.add(firstCard);
		deal.add(secondCard);
		deal.add(thirdCard);
		deal.add(fourthCard);
		deal.add(fifthCard);
		
		Hand highestHand = new PokerHand().getHighestHand(deal);
		
		assertTrue(highestHand == Hand.FOUROFAKIND);
	}
	
	@Test
	public void testStraightFlush() {
		Card firstCard = new Card(6, Suit.HEARTS);
		Card secondCard = new Card(7, Suit.HEARTS);
		Card thirdCard = new Card(8, Suit.HEARTS);
		Card fourthCard = new Card(9, Suit.HEARTS);
		Card fifthCard = new Card(10, Suit.HEARTS);
		
		deal.add(firstCard);
		deal.add(secondCard);
		deal.add(thirdCard);
		deal.add(fourthCard);
		deal.add(fifthCard);
		
		Hand highestHand = new PokerHand().getHighestHand(deal);
		
		assertTrue(highestHand == Hand.STRAIGHTFLUSH);
	}
	
	@Test
	public void testFullHouse() {
		
		Card firstCard = new Card(2, Suit.DIAMONDS);
		Card secondCard = new Card(2, Suit.CLUBS);
		Card thirdCard = new Card(2, Suit.HEARTS);
		Card fourthCard = new Card(3, Suit.CLUBS);
		Card fifthCard = new Card(3, Suit.HEARTS);
		
		deal.add(firstCard);
		deal.add(secondCard);
		deal.add(thirdCard);
		deal.add(fourthCard);
		deal.add(fifthCard);
		
		Hand highestHand = new PokerHand().getHighestHand(deal);
		
		assertTrue(highestHand == Hand.FULLHOUSE);
	}
	
	@Test
	public void testFlush() {
		
		Card firstCard = new Card(3, Suit.HEARTS);
		Card secondCard = new Card(7, Suit.HEARTS);
		Card thirdCard = new Card(9, Suit.HEARTS);
		Card fourthCard = new Card(11, Suit.HEARTS);
		Card fifthCard = new Card(13, Suit.HEARTS);
		
		deal.add(firstCard);
		deal.add(secondCard);
		deal.add(thirdCard);
		deal.add(fourthCard);
		deal.add(fifthCard);
		
		Hand highestHand = new PokerHand().getHighestHand(deal);
		
		assertTrue(highestHand == Hand.FLUSH);
	}
	
	@Test
	public void testStraight() {
		
		Card firstCard = new Card(6, Suit.HEARTS);
		Card secondCard = new Card(7, Suit.CLUBS);
		Card thirdCard = new Card(8, Suit.HEARTS);
		Card fourthCard = new Card(9, Suit.DIAMONDS);
		Card fifthCard = new Card(10, Suit.CLUBS);
		
		deal.add(firstCard);
		deal.add(secondCard);
		deal.add(thirdCard);
		deal.add(fourthCard);
		deal.add(fifthCard);
		
		Hand highestHand = new PokerHand().getHighestHand(deal);
		
		assertTrue(highestHand == Hand.STRAIGHT);
	}
	
	@Test
	public void testThreeOfAKind() {
		
		Card firstCard = new Card(2, Suit.DIAMONDS);
		Card secondCard = new Card(2, Suit.SPADES);
		Card thirdCard = new Card(2, Suit.HEARTS);
		Card fourthCard = new Card(13, Suit.CLUBS);
		Card fifthCard = new Card(8, Suit.HEARTS);
		
		deal.add(firstCard);
		deal.add(secondCard);
		deal.add(thirdCard);
		deal.add(fourthCard);
		deal.add(fifthCard);
		
		Hand highestHand = new PokerHand().getHighestHand(deal);
		
		assertTrue(highestHand == Hand.THREEOFAKIND);
	}

}
