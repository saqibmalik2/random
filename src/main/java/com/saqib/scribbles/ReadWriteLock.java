package com.saqib.scribbles;


public class ReadWriteLock {
	
	private int numberOfReaders = 0;
	private int numberOfWaitingWriters = 0;
	private Boolean writeLocked = false;

	public static void main(String[] args) {
		
	}
	
	public synchronized void acquireReadLock() {
		while (writeLocked || numberOfWaitingWriters != 0)
			try {
				wait();
			} catch (InterruptedException e) {
				
			}
		numberOfReaders++;
	}
	
	public synchronized void releaseReadLock() {
		numberOfReaders--;
		if (numberOfReaders == 0) notifyAll();
	}
	
	public synchronized void acquireWriteLock() {
		numberOfWaitingWriters++;
		while (numberOfReaders != 0 || writeLocked == true)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		writeLocked = true;
		numberOfWaitingWriters--;
	}
	
	public synchronized void releaseWriteLock() {
		writeLocked = false;
		notifyAll();
	}
}
