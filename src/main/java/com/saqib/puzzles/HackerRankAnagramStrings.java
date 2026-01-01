package com.saqib.puzzles;

import java.util.HashMap;
import java.util.Objects;


/**
 * Anagram Substring Minimum Changes Problem (HackerRank)
 * 
 * <p><b>Problem Statement:</b></p>
 * Two words are anagrams of one another if their letters can be rearranged to form the other word.
 * Given a string, split it into two contiguous substrings of equal length. Determine the minimum 
 * number of characters to change to make the two substrings into anagrams of one another.
 * 
 * <p><b>Requirements:</b></p>
 * <ul>
 *   <li>Split the string exactly in half into two contiguous substrings</li>
 *   <li>Both substrings must have equal length</li>
 *   <li>Calculate minimum character changes needed in one substring to make it an anagram of the other</li>
 *   <li>If the string has odd length, return -1 (impossible to split into equal halves)</li>
 * </ul>
 * 
 * <p><b>Function Signature:</b></p>
 * <pre>
 * public static int anagram(String s)
 * </pre>
 * 
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li><code>s</code> - a string consisting only of lowercase letters (a-z)</li>
 * </ul>
 * 
 * <p><b>Returns:</b></p>
 * <ul>
 *   <li><code>int</code> - the minimum number of characters to change, or -1 if impossible</li>
 * </ul>
 * 
 * <p><b>Constraints:</b></p>
 * <ul>
 *   <li>1 ≤ q ≤ 100 (number of test cases)</li>
 *   <li>1 ≤ |s| ≤ 10⁴ (string length)</li>
 *   <li>s consists only of characters in the range ascii[a-z]</li>
 * </ul>
 * 
 * <p><b>Example 1:</b></p>
 * <pre>
 * Input:  "abccde"
 * Split:  "abc" and "cde"
 * Output: 2
 * 
 * Explanation: Change 'a' and 'b' in the first substring to 'd' and 'e' to have 'dec' 
 * and 'cde' which are anagrams. Two changes were necessary.
 * </pre>
 * 
 * <p><b>Example 2:</b></p>
 * <pre>
 * Input:  "aaabbb"
 * Split:  "aaa" and "bbb"
 * Output: 3
 * 
 * Explanation: Replace all three characters from the first string with 'b' to make 
 * the strings anagrams.
 * </pre>
 * 
 * <p><b>Example 3:</b></p>
 * <pre>
 * Input:  "ab"
 * Split:  "a" and "b"
 * Output: 1
 * 
 * Explanation: Replace 'a' with 'b', which will generate "bb".
 * </pre>
 * 
 * <p><b>Example 4:</b></p>
 * <pre>
 * Input:  "abc"
 * Split:  Cannot split into equal halves (odd length)
 * Output: -1
 * 
 * Explanation: It is not possible for two strings of unequal length to be anagrams 
 * of one another.
 * </pre>
 * 
 * <p><b>Example 5:</b></p>
 * <pre>
 * Input:  "mnop"
 * Split:  "mn" and "op"
 * Output: 2
 * 
 * Explanation: Replace both characters of first string ("mn") to make it an anagram 
 * of the other one.
 * </pre>
 * 
 * <p><b>Example 6:</b></p>
 * <pre>
 * Input:  "xyyx"
 * Split:  "xy" and "yx"
 * Output: 0
 * 
 * Explanation: The two substrings are already anagrams of one another.
 * </pre>
 * 
 * <p><b>Example 7:</b></p>
 * <pre>
 * Input:  "xaxbbbxx"
 * Split:  "xaxb" and "bbxx"
 * Output: 1
 * 
 * Explanation: S1 = "xaxb" and S2 = "bbxx". Replace 'a' from S1 with 'b' so that 
 * S1 = "xbxb", which is an anagram of S2.
 * </pre>
 * 
 * <p><b>Time Complexity:</b> O(n) where n is the length of the string</p>
 * <p><b>Space Complexity:</b> O(1) - at most 26 entries for lowercase English letters</p>
 */

public class HackerRankAnagramStrings {

	public static void main(String[] args) {
			int result = HackerRankAnagramStrings.anagram("xaxbbbxx");
			System.out.println(result);
	}
	
	/**
	 * Calculates the minimum number of character changes needed to make two halves of a string
	 * into anagrams using a HashMap-based frequency counting approach.
	 * 
	 * <p><b>Algorithm:</b></p>
	 * <ol>
	 *   <li>Validate input and check if string length is even (required for equal split)</li>
	 *   <li>Split string into two equal halves at the midpoint</li>
	 *   <li>Build frequency map of all characters in the first half</li>
	 *   <li>Iterate through second half, decrementing counts in the frequency map</li>
	 *   <li>Remove characters from map when their count reaches zero (fully matched)</li>
	 *   <li>Sum remaining counts in the map - these represent unmatched characters</li>
	 * </ol>
	 * 
	 * <p><b>Key Insight:</b></p>
	 * After processing the second half against the frequency map, any remaining entries represent
	 * characters in the first half that have no counterpart in the second half. The sum of these
	 * remaining frequencies equals the minimum changes needed.
	 * 
	 * <p><b>Example:</b></p>
	 * <pre>
	 * Input: "xaxbbbxx"
	 * First half:  "xaxb" → Map: {x:2, a:1, b:1}
	 * Second half: "bbxx"
	 *   Process 'b' → Map: {x:2, a:1} (b removed, count reached 0)
	 *   Process 'b' → Map: {x:2, a:1} (b not in map, ignore)
	 *   Process 'x' → Map: {x:1, a:1} (x decremented)
	 *   Process 'x' → Map: {a:1} (x removed, count reached 0)
	 * Result: Sum of map values = 1 (need to change 'a')
	 * </pre>
	 * 
	 * <p><b>Advantages:</b></p>
	 * <ul>
	 *   <li>More readable and intuitive than array-based approach</li>
	 *   <li>Generalizable to any character set (Unicode, special characters)</li>
	 *   <li>Modern Java idioms (merge, computeIfPresent, streams)</li>
	 * </ul>
	 * 
	 * <p><b>Time Complexity:</b> O(n) where n is the length of the string</p>
	 * <p><b>Space Complexity:</b> O(k) where k is the number of distinct characters (max 26 for lowercase)</p>
	 * 
	 * @param s the input string to analyze
	 * @return minimum number of character changes needed, or -1 if string has odd length
	 */
	public static int anagram(String s) {
		// Edge cases: null, empty, or odd-length strings cannot be split into equal halves
		if (Objects.isNull(s) || (s.length() == 0) || (s.length() % 2 != 0)) return -1;
		
		// Split string exactly in half
		int mid = s.length() / 2;
		String firstHalf = s.substring(0, mid);
		String secondHalf = s.substring(mid);
		
		// Build frequency map for first half
		HashMap<Character, Integer> firstHalfCharacters = new HashMap<>();
		
		// Count frequency of each character in the first half
		// merge(key, 1, Integer::sum) adds 1 if new, otherwise adds to existing count
		firstHalf.chars().forEach(c -> firstHalfCharacters.merge((char)c, 1, Integer::sum));
		
		// Process second half: decrement counts for matching characters
		for (char c: secondHalf.toCharArray()) {
			// computeIfPresent only operates if key exists (ignores chars not in first half)
			// If count reaches 0, return null to remove the key (fully matched)
			firstHalfCharacters.computeIfPresent(c, (key, oldValue) -> {
				return (oldValue - 1 == 0) ? null : oldValue - 1;
			});
		}
		
		// Sum all remaining counts - these are characters that need to be changed
		return firstHalfCharacters.values().stream().mapToInt(Integer::intValue).sum();
	}
	
	/**
	 * Calculates the minimum number of character changes using an optimized array-based
	 * frequency counting approach for lowercase English letters only.
	 * 
	 * <p><b>Algorithm:</b></p>
	 * <ol>
	 *   <li>Validate input and check if string length is even</li>
	 *   <li>Create a frequency array of size 26 (one slot per lowercase letter)</li>
	 *   <li>Traverse first half: increment frequency for each character</li>
	 *   <li>Traverse second half: decrement frequency for each character</li>
	 *   <li>Sum all positive frequencies - these represent unmatched characters from first half</li>
	 * </ol>
	 * 
	 * <p><b>Key Insight:</b></p>
	 * By incrementing for the first half and decrementing for the second half, characters that
	 * appear in both halves cancel out (reach 0 or negative). Only characters that appear MORE
	 * in the first half than the second will have positive counts - these are the ones that need
	 * to be changed.
	 * 
	 * <p><b>Character Mapping:</b></p>
	 * The expression {@code s.charAt(i) - 'a'} maps characters to array indices:
	 * <ul>
	 *   <li>'a' → 0</li>
	 *   <li>'b' → 1</li>
	 *   <li>...</li>
	 *   <li>'z' → 25</li>
	 * </ul>
	 * 
	 * <p><b>Example:</b></p>
	 * <pre>
	 * Input: "aaabbb"
	 * freq array after first half "aaa": [3, 0, 0, ..., 0]
	 * freq array after second half "bbb": [3, -3, 0, ..., 0]
	 * Sum of positive values: 3 (need to change all 3 'a's to 'b's)
	 * </pre>
	 * 
	 * <p><b>Advantages over HashMap approach:</b></p>
	 * <ul>
	 *   <li>Faster: No hashing overhead, direct O(1) array access</li>
	 *   <li>Better cache locality: Contiguous memory vs scattered HashMap entries</li>
	 *   <li>No object allocation: Primitive array vs Integer objects</li>
	 *   <li>No autoboxing/unboxing costs</li>
	 * </ul>
	 * 
	 * <p><b>Disadvantages:</b></p>
	 * <ul>
	 *   <li>Only works for lowercase English letters (not Unicode-safe)</li>
	 *   <li>Less generalizable to other character sets</li>
	 *   <li>Slightly less readable than HashMap approach</li>
	 * </ul>
	 * 
	 * <p><b>Time Complexity:</b> O(n) where n is the length of the string</p>
	 * <p><b>Space Complexity:</b> O(1) - fixed array of size 26</p>
	 * 
	 * @param s the input string to analyze (must contain only lowercase a-z)
	 * @return minimum number of character changes needed, or -1 if string has odd length
	 */
	public static int anagramMorePerformant(String s) {
		// Edge cases: null, empty, or odd-length strings cannot be split into equal halves
		if (Objects.isNull(s) || (s.length() == 0) || (s.length() % 2 != 0)) return -1;
	    
		// Calculate midpoint for splitting
	    int mid = s.length() / 2;
	    
	    // Frequency array for 26 lowercase letters (a-z)
	    // Each index represents a letter: freq[0] = 'a', freq[1] = 'b', etc.
	    int[] freq = new int[26];
	    
	    // First half: increment frequency for each character
	    // This builds a "signature" of the first half
	    for (int i = 0; i < mid; i++) {
	        freq[s.charAt(i) - 'a']++;
	    }
	    
	    // Second half: decrement frequency for each character
	    // Characters present in both halves will cancel out (approach 0)
	    // Characters only in second half go negative (we ignore these)
	    // Characters more frequent in first half remain positive (need to change these)
	    for (int i = mid; i < s.length(); i++) {
	        freq[s.charAt(i) - 'a']--;
	    }
	    
	    // Sum only positive values - these represent excess characters in first half
	    // that have no counterpart in the second half and must be changed
	    int changes = 0;
	    for (int count : freq) {
	        if (count > 0) changes += count;
	    }
	    
	    return changes;
	}
}