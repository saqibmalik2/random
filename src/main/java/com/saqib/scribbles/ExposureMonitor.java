package com.saqib.scribbles;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ExposureMonitor {
	
	private Duration windowSize;
	private ConcurrentLinkedDeque<Trade> tradeList;
	
    public ExposureMonitor(Duration windowSize) {
    	this.windowSize = windowSize;
    	this.tradeList = new ConcurrentLinkedDeque<>();
    }

    public void addTrade(Trade trade) {
    	long currentTime = trade.getTimestamp();
		Trade head;
		while ((head = tradeList.peekFirst()) != null &&
			currentTime - head.getTimestamp() > windowSize.toMillis()) {
		    tradeList.removeFirst();
		}
    	tradeList.addLast(trade);
    }
    
    public int getSizeOfTradeList() {
    	return tradeList.size();
    }

    public double getMaxExposure() {
    	return tradeList.stream().mapToDouble(Trade::getNotional).sum();
    }
    
    public double getMaxExposure(String instrument) {
    	return tradeList.stream().filter(t -> t.getInstrument().equals(instrument)).
    			mapToDouble(Trade::getNotional).sum();
    }
}
