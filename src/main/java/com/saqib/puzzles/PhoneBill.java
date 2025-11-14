package com.saqib.puzzles;

import java.util.*;

/**
 * Codility test - Phone Bill Calculator
 * 
 * Given a phone calls log, calculate the total phone bill.
 * Each record: "HH:MM:SS,nnn-nnn-nnn"
 * 
 * Charging rules:
 * (i) Calls < 5 minutes: 3 cents per second
 * (ii) Calls >= 5 minutes: 150 cents per started minute
 * (iii) All calls to the phone number with longest total duration are free
 *       (tie-breaker: use numerically smallest phone number)
 * 
 * @author Saqib Malik (2019)
 */
public class PhoneBill {
	
	public static void main(String[] args) {
		String phoneLog = "00:01:07,400-234-090" + "\n" +
				  "00:05:01,701-080-080" + "\n" +
				  "00:05:00,400-234-090";
		System.out.println(new PhoneBill().totalCost(phoneLog));
	}
	
	public int totalCost(String log) {
		String[] logEntries = log.split("\\n");
		
		// Store all call records with their durations
		List<CallRecord> calls = new ArrayList<>();
		// Track total duration per phone number
		HashMap<String, Integer> totalDurationPerNumber = new HashMap<>();
		
		// Parse all call records
		for (String record : logEntries) {
			String[] details = record.split(",");
			int durationSeconds = parseDuration(details[0]);
			String phoneNumber = details[1];
			
			calls.add(new CallRecord(phoneNumber, durationSeconds));
			totalDurationPerNumber.merge(phoneNumber, durationSeconds, Integer::sum);
		}
		
		// Find the phone number with maximum total duration
		String freePhoneNumber = findPhoneNumberWithMaxDuration(totalDurationPerNumber);
		
		// Calculate total cost
		int totalCost = 0;
		for (CallRecord call : calls) {
			if (!call.phoneNumber.equals(freePhoneNumber)) {
				totalCost += calculateCallCost(call.durationSeconds);
			}
		}
		
		return totalCost;
	}
	
	private int parseDuration(String time) {
		int hours = Integer.parseInt(time.substring(0, 2));
		int minutes = Integer.parseInt(time.substring(3, 5));
		int seconds = Integer.parseInt(time.substring(6, 8));
		return hours * 3600 + minutes * 60 + seconds;
	}
	
	private String findPhoneNumberWithMaxDuration(HashMap<String, Integer> durations) {
		int maxDuration = 0;
		String maxPhoneNumber = "";
		long minPhoneNumberValue = Long.MAX_VALUE;
		
		for (Map.Entry<String, Integer> entry : durations.entrySet()) {
			int duration = entry.getValue();
			long phoneValue = Long.parseLong(entry.getKey().replaceAll("-", ""));
			
			if (duration > maxDuration || 
			    (duration == maxDuration && phoneValue < minPhoneNumberValue)) {
				maxDuration = duration;
				maxPhoneNumber = entry.getKey();
				minPhoneNumberValue = phoneValue;
			}
		}
		
		return maxPhoneNumber;
	}
	
	private int calculateCallCost(int durationSeconds) {
		if (durationSeconds < 300) {
			// Less than 5 minutes: 3 cents per second
			return durationSeconds * 3;
		} else {
			// 5 minutes or more: 150 cents per started minute
			int minutes = durationSeconds / 60;
			if (durationSeconds % 60 != 0) {
				minutes++; // Round up for partial minutes
			}
			return minutes * 150;
		}
	}
	
	private static class CallRecord {
		String phoneNumber;
		int durationSeconds;
		
		CallRecord(String phoneNumber, int durationSeconds) {
			this.phoneNumber = phoneNumber;
			this.durationSeconds = durationSeconds;
		}
	}
}