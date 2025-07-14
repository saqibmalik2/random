package com.saqib.puzzles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class HackerRankAnagrams {

	public static void main(String[] args) {
		String[] strs = new String[] {"eat", "tea", "tan", "ate", "nat", "bat"};
		
		HashMap<String, ArrayList<String>> temp = new HashMap<>();
		
		for (String str:strs) {
			char[] chars = str.toCharArray();
		    Arrays.sort(chars);
		    String sorted = new String(chars);
		    ArrayList<String> insertion = temp.getOrDefault(sorted, new ArrayList<String>());
		    insertion.add(str);
		    temp.put(sorted, insertion); // Put the list back into the map
		}
		int size = temp.values().size();
		String[][] output = new String[size][];
		int i=0;
		for (ArrayList<String> list: temp.values()) {
			output[i] = list.toArray(new String[0]);
			i++;
		}
		
		System.out.println(Arrays.deepToString(output));

	}
	
}
