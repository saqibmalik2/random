package com.saqib.puzzles;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.saqib.puzzles.utils.TestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OptimalPathTest {
	
	@Autowired
	OptimalPath optimalPath;
	
	@Test(expected = IllegalArgumentException.class)
	public void testOptimalPathWithNullGrid() {
		optimalPath.calculateOptimalPath(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOptimalPathWithEmptyGrid() {
		int[][] intialGrid = {};
		optimalPath.calculateOptimalPath(intialGrid);
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

}
