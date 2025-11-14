package com.saqib.scribbles;

public class Trade {

	private final long timestamp; // epoch milliseconds
    private final String instrument;
    private final double notional;

    public Trade(long timestamp, String instrument, double notional) {
        this.timestamp = timestamp;
        this.instrument = instrument;
        this.notional = notional;
    }

	public long getTimestamp() {
		return timestamp;
	}

	public String getInstrument() {
		return instrument;
	}

	public double getNotional() {
		return notional;
	}
}
