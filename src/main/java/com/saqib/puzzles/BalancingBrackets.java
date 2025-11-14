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
		
	    // Optimization: odd length can never be balanced
	    if (input.length() % 2 != 0) return false;

	    Stack<Character> inputStack = new Stack<>();
	    
	    // Map closing brackets to their corresponding opening brackets
	    HashMap<Character, Character> characterPairs = new HashMap<>();
	    characterPairs.put(')', '(');
	    characterPairs.put(']', '[');
	    characterPairs.put('}', '{');

	    for (char c : input.toCharArray()) {
	        // If opening bracket, push to stack
	        if (c == '{' || c == '[' || c == '(') {
	            inputStack.push(c);
	        }
	        // If closing bracket, check if it matches the most recent opening bracket
	        else {
	            // No opening bracket to match with
	            if (inputStack.isEmpty()) return false;
	            
	            // Check if top of stack matches the expected opening bracket
	            if (characterPairs.get(c) != inputStack.pop()) {
	                return false;
	            }
	        }
	    }
	    
	    // All brackets matched if stack is empty
	    return inputStack.isEmpty();
	}
	
}
