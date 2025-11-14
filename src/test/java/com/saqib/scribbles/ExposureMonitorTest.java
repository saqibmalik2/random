package com.saqib.scribbles;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExposureMonitorTest {
	
	ExposureMonitor monitor;
	
	@BeforeEach
	void setup() {
		monitor = new ExposureMonitor(Duration.ofMinutes(5));
	    long now = System.currentTimeMillis();
	    monitor.addTrade(new Trade(now - 600000, "AAPL", 30_000)); // should be evicted
	    monitor.addTrade(new Trade(now - 300000, "AAPL", 5_000));
	    monitor.addTrade(new Trade(now - 200000, "AAPL", 15_000));
	    monitor.addTrade(new Trade(now - 100000, "AAPL", 10_000));
	}
	

	@Test
	void testExposureWindow() {
		Runnable r1 = () -> {};

	    assertEquals(3, monitor.getSizeOfTradeList());
	    assertEquals(30_000, monitor.getMaxExposure(),0.01);
	}
	
	@Test
	void testExposureWindowConcurrent() {
		ExposureMonitor monitor = new ExposureMonitor(Duration.ofMinutes(5));
		
	}
	

}
