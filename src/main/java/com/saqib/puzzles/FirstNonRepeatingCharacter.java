package com.saqib.puzzles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Find the first non-repeating character in a string. E.g. given "racecars" it would be 'e'.
 * @author Saqib Malik
 *
 */

public class FirstNonRepeatingCharacter {

	public static void main(String[] args) {
		System.out.println(new FirstNonRepeatingCharacter().firstNonRepeatingCharacter("racecars"));
		System.out.println(new FirstNonRepeatingCharacter().firstNonRepeatingCharacterOptimised("racecars"));
	}
	
	// O(n^2) - not an optimal solution
	public Character firstNonRepeatingCharacter(String inputString) {
		Set<Character> repeatedCharacters = new HashSet<>();
		char[] characters = inputString.toCharArray();
		
		for (int i=0;i<characters.length;i++) {
			boolean foundCharacter = true;
			if (repeatedCharacters.contains(new Character(characters[i]))) continue;
			for (int j=0;j<characters.length;j++) {
				if (j==i) continue;
				if (characters[i] == characters[j]) {
					repeatedCharacters.add(characters[j]);
					foundCharacter = false;
					break;
				}
			}
			if (foundCharacter) return characters[i];
		}
		return null;
	}
	
	// O(n) 
	public Character firstNonRepeatingCharacterOptimised(String inputString) {
		HashMap<Character,Integer> scoreboard = new HashMap<>();
		
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (scoreboard.containsKey(c)) {
                scoreboard.put(c, scoreboard.get(c) + 1);
            } else {
                scoreboard.put(c, 1);
            }
        }
        // since HashMap doesn't maintain order, going through string again
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (scoreboard.get(c) == 1) {
                return c;
            }
        }
        return null;
    }

}
