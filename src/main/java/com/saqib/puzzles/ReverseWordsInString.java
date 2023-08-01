package com.saqib.puzzles;

import java.util.StringJoiner;


public class ReverseWordsInString {
	
	
	public static void main(String[] args) {
		String reversedString = new ReverseWordsInString().reverseWords("  This is the month of my birthday ");
		System.out.println(reversedString);
	}
	
	public String reverseWords(String s) {
		String trimmedString = s.strip();
		String[] words = trimmedString.split("\\b");
		StringJoiner joiner = new StringJoiner("");
		for (int i=(words.length-1);i>-1;i--) {
			joiner.add(words[i]);
		}
		return joiner.toString().replaceAll(" +", " ");
	}
	
}
