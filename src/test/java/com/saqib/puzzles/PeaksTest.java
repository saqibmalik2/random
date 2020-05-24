package com.saqib.puzzles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Codility test - Peaks")
public class PeaksTest {
	
	static Peaks peaksTestInstance = new Peaks();
	
	@Test
	void firstInputArray() {
		int[] inputArrayOne = new int[] {1,2,3,4,3,4,1,2,3,4,6,2};
		int maxNoOfBlocks = peaksTestInstance.determineMaxNoOfBlocks(inputArrayOne);
		assertEquals(3, maxNoOfBlocks);
	}
	
	@Test
	void secondInputArray() {
		int[] inputArrayTwo = new int[] {5,7,4,2,3,6,1,6,9,7};
		int maxNoOfBlocks = peaksTestInstance.determineMaxNoOfBlocks(inputArrayTwo);
		assertEquals(2, maxNoOfBlocks);
	}
	
	@Test
	void thirdInputArray() {
		int[] inputArrayThree = new int[] {5,7,4,2,8,6,8,6,9,7,8,3};
		int maxNoOfBlocks = peaksTestInstance.determineMaxNoOfBlocks(inputArrayThree);
		assertEquals(4, maxNoOfBlocks);
	}
	
	@Test
	void fourthInputArray() {
		int[] inputArrayFour = new int[] {5,6,6,7,8,8,9};
		int maxNoOfBlocks = peaksTestInstance.determineMaxNoOfBlocks(inputArrayFour);
		assertEquals(0, maxNoOfBlocks);
	}
	
	@Test
	void fifthInputArray() {
		int[] inputArrayFive = new int[] {1};
		assertThrows(IllegalArgumentException.class, () -> peaksTestInstance.determineMaxNoOfBlocks(inputArrayFive));
	}

}
