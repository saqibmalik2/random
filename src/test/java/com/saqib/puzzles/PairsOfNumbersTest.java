package com.saqib.puzzles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PairsOfNumbersTest {
	
	@Test
	public void pairsOfNumbersTest1() {
		int [] inputArray = {3,3,3,3,2,3,3,3,4,3,3};
		int total = 6;
		
		assertEquals(2, new PairsOfNumbers().numberOfPairsOptimised(inputArray, total));
	}
	
	@Test
	public void pairsOfNumbersTest2() {
		int [] inputArray = {3,5,2,-4,8,11};
		int total = 7;
		assertEquals(2, new PairsOfNumbers().numberOfPairsOptimised(inputArray, total));
	}
	
	@Test
	public void pairsOfNumbersTest3() {
		int [] inputArray = {3,5,2,-4,8,12};
		int total = 7;
		assertEquals(1, new PairsOfNumbers().numberOfPairsOptimised(inputArray, total));
	}
	
	@Test
	public void pairsOfNumbersTest4() {
		int [] inputArray = {3,3,3,2,1,1,1,1};
		int total = 7;
		assertEquals(0, new PairsOfNumbers().numberOfPairsOptimised(inputArray, total));
	}
	
	

}
