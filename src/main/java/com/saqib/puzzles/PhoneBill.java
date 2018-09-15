package com.saqib.puzzles;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Codility test. This question, or some variant thereof, is one of the stock questions in the Codility test suite.
 * 
 *  Given a phone calls log in the form of a string with each record separated by \n (ascii code 10) calculate the total phone bill.
 *  Each record is correctly formed as "HH:MM:SS,nnn-nnn-nnn" e.g. "00:01:07,400-234-090" refers to a phone call to phone number 
 *  400-234-090 and lasted a duration of 1 minute and 7 seconds.
 *  Rules for calculating charges:
 *		   (i) For a phone call less than 5 minutes long charge 3 cents per second
 *		   (ii) For a phone call 5 minutes and longer charge 150 cents per started minute, so for a call of 5 minutes 2 seconds would be
 *		        charged as a 6 minute call i.e. 6 * 150 = 900 cents
 *		   (iii) All calls to the phone number with the longest total call duration are free. In the event that two phone numbers have the same total call duration use the one
 * 
 * @author Saqib Malik (2018)
 *
 */

public class PhoneBill {
	
	public static void main(String[] args) {
		String phoneLog = "00:01:07,400-234-090"+ "\n" +
				  "00:05:01,701-080-080" + "\n" +
				  "00:05:00,400-234-090";
		System.out.println(new PhoneBill().totalCost(phoneLog));
	}
	
	public int totalCost(String log) {
		
		//split the phoneLog into its individual rows
		String[] logEntries = log.split("\\n");
		
		// create a hashmap to store the phoneNumber and the total phone call duration for it
		HashMap<String, Integer> phoneNumberDetails = new HashMap<String, Integer>();
		
		for (String record: logEntries) {
			String[] details = record.split(",");
			int durationOfHours = Integer.valueOf(details[0].substring(0, 2)) * 3600;
			int durationOfMinutes = Integer.valueOf(details[0].substring(3, 5)) * 60;
			int durationOfSeconds = Integer.valueOf(details[0].substring(6, 8));
			int totalDuration = durationOfHours + durationOfMinutes + durationOfSeconds;
	
			// if we already have an entry for this phone number then add this total duration to the existing one else just add it
			if (phoneNumberDetails.containsKey(details[1])){
				totalDuration += phoneNumberDetails.get(details[1]);
				phoneNumberDetails.put(details[1], totalDuration);
			}
			else {
				phoneNumberDetails.put(details[1], totalDuration);
			}
		}
		
		/* Find the phone call with the maximum duration in event of a tie use the one with least numerical value  
		   (NOTE: I think this is redundant as we don't need to bother with the tie break given that they all have the same total duration and it's that value we will use
		    to calculate the total bill)
		*/
		int maxDuration = 0;
		int maxPhoneNumberValue = 0;
		String maxPhoneNumber = "";
		
		for (Map.Entry<String, Integer> entry : phoneNumberDetails.entrySet()) {
			int duration = entry.getValue();
			
			if (duration > maxDuration) {
				maxPhoneNumber = entry.getKey();
				maxPhoneNumberValue = Integer.valueOf(entry.getKey().replaceAll("-", ""));
				maxDuration = duration;
			}
			else {
				
				if (duration == maxDuration) {
					if (Integer.valueOf(entry.getKey().replaceAll("-", "")) < maxPhoneNumberValue) {
						maxPhoneNumber = entry.getKey();
						maxPhoneNumberValue = Integer.valueOf(entry.getKey().replaceAll("-", ""));
					}
				}
				
			}
		}
		
		/* Finally we can evaluate the total cost of the phone bill.
		   Rules:
		   (i) For a phone call less than 5 minutes long charge 3 cents per second
		   (ii) For a phone call 5 minutes and longer charge 150 cents per started minute, so for a call of 5 minutes 2 seconds would be
		        charged as a 6 minute call i.e. 6 * 150 = 900 cents
		   (iii) All calls to the phone number with the longest total call duration are free. In the event that two phone numbers have the same total call duration use the one
		*/
		int totalCost = 0;
		
		for (Map.Entry<String, Integer> entry : phoneNumberDetails.entrySet())
		{
			if (entry.getKey().equals(maxPhoneNumber)) continue;
			
			if (entry.getValue() > 300) {
				totalCost += ((entry.getValue()/60) * 150);
				if (entry.getValue() % 60 != 0) totalCost += 150;
			}
			else {
				totalCost += (entry.getValue() * 3);
			}
		}
		
		return totalCost;
	}

}
