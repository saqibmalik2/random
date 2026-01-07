package com.saqib.puzzles;

import java.util.HashMap;

/**
 * Longest Substring Without Repeating Characters
 * 
 * <p>Given a string s, find the length of the longest substring without repeating characters.
 * 
 * <h2>Examples:</h2>
 * <ul>
 *   <li>Input: "abcabcbb" → Output: 3 (substring "abc")</li>
 *   <li>Input: "bbbbb" → Output: 1 (substring "b")</li>
 *   <li>Input: "pwwkew" → Output: 3 (substring "wke")</li>
 *   <li>Input: "dvdf" → Output: 3 (substring "vdf")</li>
 * </ul>
 * 
 * <h2>Solution Approach - Sliding Window with HashMap:</h2>
 * 
 * <p>We use a sliding window technique with two pointers (left and right) to track the current
 * substring being evaluated. The right pointer (i) expands the window by iterating through
 * the string, while the left pointer contracts the window when we encounter duplicate characters.
 * 
 * <p>A HashMap tracks each character's most recent position. When we encounter a character that's
 * already in our current window (i.e., its stored position is >= leftPointer), we move the
 * left pointer to one position after the previous occurrence of that character.
 * 
 * <p><strong>Key insight:</strong> leftPointer only moves forward, never backward. This handles 
 * cases like "abba" where we might encounter an old duplicate that's no longer in our current window.
 * 
 * <p><strong>Time Complexity:</strong> O(n) where n is the length of the string - we visit each character once<br>
 * <strong>Space Complexity:</strong> O(min(n, m)) where m is the charset size (typically 128 for ASCII)
 * 
 * <h2>Dry Run Example 1: "abcabcbb"</h2>
 * <pre>
 * i=0, char='a': positions={a:0}, leftPointer=0, window="a", length=1, max=1
 * i=1, char='b': positions={a:0,b:1}, leftPointer=0, window="ab", length=2, max=2
 * i=2, char='c': positions={a:0,b:1,c:2}, leftPointer=0, window="abc", length=3, max=3
 * i=3, char='a': duplicate found! positions has 'a' at index 0
 *                leftPointer = max(0, 0+1) = 1
 *                positions={a:3,b:1,c:2}, leftPointer=1, window="bca", length=3, max=3
 * i=4, char='b': duplicate found! positions has 'b' at index 1
 *                leftPointer = max(1, 1+1) = 2
 *                positions={a:3,b:4,c:2}, leftPointer=2, window="cab", length=3, max=3
 * i=5, char='c': duplicate found! positions has 'c' at index 2
 *                leftPointer = max(2, 2+1) = 3
 *                positions={a:3,b:4,c:5}, leftPointer=3, window="abc", length=3, max=3
 * i=6, char='b': duplicate found! positions has 'b' at index 4
 *                leftPointer = max(3, 4+1) = 5
 *                positions={a:3,b:6,c:5}, leftPointer=5, window="cb", length=2, max=3
 * i=7, char='b': duplicate found! positions has 'b' at index 6
 *                leftPointer = max(5, 6+1) = 7
 *                positions={a:3,b:7,c:5}, leftPointer=7, window="b", length=1, max=3
 * 
 * Final result: 3
 * </pre>
 * 
 * <h2>Dry Run Example 2: "dvdf"</h2>
 * <pre>
 * i=0, char='d': positions={d:0}, leftPointer=0, window="d", length=1, max=1
 * i=1, char='v': positions={d:0,v:1}, leftPointer=0, window="dv", length=2, max=2
 * i=2, char='d': duplicate found! positions has 'd' at index 0
 *                leftPointer = max(0, 0+1) = 1
 *                positions={d:2,v:1}, leftPointer=1, window="vd", length=2, max=2
 * i=3, char='f': positions={d:2,v:1,f:3}, leftPointer=1, window="vdf", length=3, max=3
 * 
 * Final result: 3
 * </pre>
 * 
 * <h2>Why Math.max for leftPointer?</h2>
 * <p>Consider "abba": when we hit the second 'a' at index 3, the first 'a' is at index 0.
 * But our leftPointer is already at 2 (moved there when we found the duplicate 'b').
 * We shouldn't move leftPointer backward to 1, so we use Math.max(leftPointer, 0+1) = max(2,1) = 2
 */
public class LongestSubstring {

	public static void main(String args[]) {
		LongestSubstring ls = new LongestSubstring();
		System.out.println(ls.longestSubstring("abcabcbb"));
		System.out.println(ls.longestSubstring("abcabcd"));
		System.out.println(ls.longestSubstring("abcdef"));
		System.out.println(ls.longestSubstring("pwwkew"));
		System.out.println(ls.longestSubstring("bbbbb"));
		System.out.println(ls.longestSubstring("dvdf"));
		System.out.println(ls.longestSubstring("abcdefghijklmna"));
		System.out.println(ls.longestSubstring("tmmzuxt"));
		System.out.println(ls.longestSubstring("abba"));
	}

	public int longestSubstring(String inputString) {
		// HashMap to store each character and its most recent index position
		HashMap<Character, Integer> positions = new HashMap<>();
		
		// max tracks the longest substring length found so far
		// leftPointer marks the start of our current sliding window
		int max = 0, leftPointer = 0;

		// right pointer (i) expands the window by iterating through the string
		for (int i = 0; i < inputString.length(); i++) {
			char currentChar = inputString.charAt(i);
			
			// if we've seen this character before, it might be a duplicate in our current window
			if (positions.containsKey(currentChar)) {
				// move leftPointer to one position after the previous occurrence of this character
				// use Math.max to ensure leftPointer never moves backward (handles cases like "abba")
				leftPointer = Math.max(leftPointer, positions.get(currentChar) + 1);
			}
			
			// calculate current window size and update max if this window is larger
			// window size = (right - left) + 1 because indices are inclusive
			max = Math.max(max, (i - leftPointer) + 1);
			
			// update (or add) the current character's position in our HashMap
			// this handles both new characters and updating positions of duplicates
			positions.put(currentChar, i);
		}
		
		return max;
	}

}