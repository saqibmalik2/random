package com.saqib.puzzles;

import java.util.StringJoiner;


public class ReverseWordsInString {
	
	
	public static void main(String[] args) {
		String reversedString = new ReverseWordsInString().reverseWords("  This is the month  of my birthday ");
		System.out.println(reversedString);
	}
	
	public String reverseWords(String s) {
		String trimmedString = s.strip();
		
		//split on word boundary
		String[] words = trimmedString.split("\\b");
		
		//blank string
		StringJoiner joiner = new StringJoiner("");
		
		//add the words in reverse order
		for (int i=(words.length-1);i>-1;i--) {
			joiner.add(words[i]);
		}
		
		//strip out multiple blank spaces replacing them with a single space
		//e.g. "moon the  over   jumped cow the" will become "moon the over jumped cow the"
		return joiner.toString().replaceAll(" +", " ");
	}
	
}
