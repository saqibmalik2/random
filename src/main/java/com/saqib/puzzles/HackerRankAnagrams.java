package com.saqib.puzzles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;


/**
 * Given an array of strings, create a multidimensional array grouping them into anagrams.
 * For example for input: {"eat", "tea", "tan", "ate", "nat", "bat"} we should output:
 * 
 * [eat, tea, ate], 
 * [bat], 
 * [tan, nat]
 * 
 */

public class HackerRankAnagrams {

	public static void main(String[] args) {
		
		// example input
		String[] strs = new String[] {"eat", "tea", "tan", "ate", "nat", "bat"};
		
		//a hashmap to store the anagram groups
		HashMap<String, ArrayList<String>> anagramGroups = new HashMap<>();
		
		//iterate through the input array
		for (String str:strs) {
			//convert the current string into a character array and sort it
			char[] chars = str.toCharArray();
		    Arrays.sort(chars);
		    String sorted = new String(chars);
		    //add the original string to an ArrayList with the string made up of the sorted characters as the key
		    //this words because all anagrams will have the same key as once their characters are sorted they resolve
		    //to the same string. So, for example, "eat", "tea" and "ate" all resolve to "aet" once you sort their characters.
		    // therefore they can all be added to the same ArrayList in the HashMap referenced by the key, "aet"
		    anagramGroups.computeIfAbsent(sorted, k -> new ArrayList<>()).add(str);
		}
		
		// how many ArrayLists do we have in the HashMap. Each one represents a group of anagrams
		int size = anagramGroups.values().size();
		// create our 2D array with the correct number of rows for the number of anagram groups we found
		// note that each row length (i.e. the number of columns in each row) can vary
		String[][] output = new String[size][];
		
		int rowIndex=0;
		// populate the output array one row at a time
		for (ArrayList<String> list: anagramGroups.values()) {
			output[rowIndex] = list.toArray(new String[0]);
			rowIndex++;
		}
		// convert the 2D array to a String representation with the Arrays convenience method deepToString
		System.out.println(Arrays.deepToString(output));
		//alternatively if you want each group on a separate line as per the original output specification
		Stream.of(output).forEach(row -> System.out.println(Arrays.toString(row)));
	}
	
}
