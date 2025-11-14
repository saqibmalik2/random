package com.saqib.puzzles;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.saqib.puzzles.utils.TestConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class OptimalPathTest {
	
	@Autowired
	OptimalPath optimalPath;
	
	@Test
	public void testOptimalPathWithEmptyGrid() {
		int[][] intialGrid = {};
		assertThrows(IllegalArgumentException.class, () -> optimalPath.calculateOptimalPath(intialGrid));
	}
	
	@Test
	public void testOptimalPathExpectTen() {
		int[][] intialGrid = { {0,0,0,0,5}, 
				   {0,1,1,1,0},
				   {2,0,0,0,0}
				 };
		assertEquals(10,optimalPath.calculateOptimalPath(intialGrid));
	}
	
	@Test
	public void testOptimalPathExpectAThousand() {
		int[][] intialGrid = { {995,0,0,0,5}, 
				   {0,1,1,1,0},
				   {0,0,0,0,0}
				 };
		assertEquals(1000,optimalPath.calculateOptimalPath(intialGrid));
	}
	
	@Test
	public void testOptimalPathExpectTwoHundred() {
		int[][] intialGrid = { {40,0,94,0,5}, 
				   {0,100,1,1,0},
				   {0,0,0,0,0}
				 };
		assertEquals(200,optimalPath.calculateOptimalPath(intialGrid));
	}
	
	@Test
	public void testOptimalPathExpectSix() {
		int[][] intialGrid = { {3}, 
				   {2},
				   {1}
				 };
		assertEquals(6,optimalPath.calculateOptimalPath(intialGrid));
	}

}
