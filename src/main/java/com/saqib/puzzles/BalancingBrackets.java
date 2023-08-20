package com.saqib.puzzles;

import java.util.HashMap;
import java.util.Stack;

/**
 * 
 * @author Saqib Malik
 * @since  July 2022
 * 
 * Check if a string has balanced brackets.
 * 
 * [](){} is valid
 * ({[]}) is valid
 * []{}()(()) is valid
 * (( is invalid
 * (] is invalid
 * [(]) is invalid
 *
 */
public class BalancingBrackets {

	public static void main(String args[]) {
		System.out.println(new BalancingBrackets().isValid("([]{}(){}{{}}[[{}]](())")); // should be false
		System.out.println(new BalancingBrackets().isValid("([]{}(){}{{}}[[{}]](()))")); // should be true
	}
	
	public boolean isValid(String input) {
		
		//number of characters can't be odd
		if (input.length() % 2 != 0) return false;
		
		Stack<Character> inputStack = new Stack<>();
		HashMap<Character, Character> characterPairs = new HashMap<>();
		characterPairs.put(')', '(');
		characterPairs.put(']', '[');
		characterPairs.put('}', '{');
		
		for (Character c: input.toCharArray()) {
			//if we encounter an open bracket add it to the stack
			if (c.equals('{') || c.equals('[') || c.equals('(')) {
				inputStack.add(c);
			}
			// if a closing bracket then don't add but check what previous character was
			else {
				//we've popped all previous open brackets off so we shouldn't be encountering a closing bracket
				if (inputStack.isEmpty()) return false;
				// is the top of the stack the corresponding opening bracket?
				if (!characterPairs.get(c).equals(inputStack.pop())) {
					return false;
				}
			}
		}
		// if this returns true then all the brackets matched up correctly
		return inputStack.isEmpty();
	}
	
}
