package com.saqib.puzzles;

import java.util.HashMap;
import java.util.Objects;

public class MinimumWindowSubstring {
	
	
	public static void main(String[] args) {
		System.out.println(minimumWindowSubstring("ADOBECODEBANC", "ABC"));  // "BANC"
		System.out.println(minimumWindowSubstring("a", "a"));                // "a"
		System.out.println(minimumWindowSubstring("a", "aa"));               // ""
		System.out.println(minimumWindowSubstring("ab", "b"));               // "b"
		System.out.println(minimumWindowSubstring("abc", "cba"));            // "abc" or "bca" or "cab" (any valid)
		System.out.println(minimumWindowSubstring("ADOBECODEBANC", "AABC"));  // Should handle duplicates in t
		System.out.println(minimumWindowSubstring("aa", "aa"));              // "aa"
	}

	private static String minimumWindowSubstring(String s, String t) {
		if (s.isEmpty() || Objects.isNull(s) || Objects.isNull(t)) throw new IllegalArgumentException("Bad input");
		
		String output = "";
		int smallestWindowLength = s.length();
		int formed = 0;
		int basePointer = 0;
		
		
		HashMap<Character, Integer> requiredCount = new HashMap<>();
		HashMap<Character, Integer> characterCount = new HashMap<>();
		
		for (int i = 0; i < t.length(); i++) {
			requiredCount.merge(t.charAt(i), 1, Integer::sum);
		}
		
		int requiredCountSize = requiredCount.size();
		
		for (int i=0; i < s.length(); i++) {
			char currentCharacter = s.charAt(i);
			if (requiredCount.containsKey(currentCharacter)) {
				int currentCount = characterCount.merge(currentCharacter, 1, Integer::sum);
				if (currentCount == requiredCount.get(currentCharacter)) formed++;
			}
			//check if we have formed the string
			while (formed == requiredCountSize) {
				
				if (((i - basePointer) + 1) <= smallestWindowLength) {
					output = s.substring(basePointer, i+1);
					smallestWindowLength = (i - basePointer) + 1;
				}
				
				char leftChar = s.charAt(basePointer);
				if (requiredCount.containsKey(leftChar)) {
			        // It's a character we care about - update counts
			        characterCount.merge(leftChar, -1, Integer::sum);
			        if (characterCount.get(leftChar) < requiredCount.get(leftChar)) {
			            formed--;
			        }
			    }
				basePointer++;
				
			    // Skip to next character of interest
			    while (basePointer < s.length() && !requiredCount.containsKey(s.charAt(basePointer))) {
			        basePointer++;
			    }
			}
		}
		return output;
	}

}
