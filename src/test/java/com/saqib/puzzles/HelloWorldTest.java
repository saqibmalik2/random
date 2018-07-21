package com.saqib.puzzles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class HelloWorldTest {
	
	static Logger log = LogManager.getLogger(HelloWorldTest.class);

	
	@DisplayName("Single test successful")
	@Test
	void testSingleSuccessTest() {
	    log.error("Success");
	}
	 
	@Test
	@Disabled("Not implemented yet")
	void testShowSomething() {
	}
	
}
