package com.saqib.puzzles;


public class ReverseCharactersInStringRecursively {

	public static void main(String[] args) {
		ReverseCharactersInStringRecursively reverseCharacters = new ReverseCharactersInStringRecursively();
		System.out.println(reverseCharacters.reverseCharacters("abcdefghijklmnopqrstuvwxyz"));
	}
	
	
	// assume input string isn't empty
	public String reverseCharacters(String input) {
		
		StringBuilder sb = new StringBuilder("");
		
		int lengthOfString = input.length();
		
		if (input.length() == 1) return input;
		
		sb.append(input.charAt(lengthOfString -1));
		sb.append(reverseCharacters(input.substring(0, lengthOfString - 1)));
		
		return sb.toString();
	}

}
