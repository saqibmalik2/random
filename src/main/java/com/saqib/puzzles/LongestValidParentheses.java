package com.saqib.puzzles;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class LongestValidParentheses {

	public static void main(String[] args) {
		LongestValidParentheses lvp = new LongestValidParentheses();
		String inputString = "((()))())";
		System.out.println(lvp.longestValidParentheses(inputString)); //expect 6
	}
	
	public int longestValidParentheses(String s) {
		if (s.length() < 2) return 0;
		
		char[] characters = s.toCharArray();
		ArrayList<Integer> values = new ArrayList<>();
		ArrayDeque<Integer> onePositions = new ArrayDeque<>();
		
		for (int i=0; i<s.length(); i++) {
			if (characters[i] == '(') {
				if (i < s.length()-1) {
					if (characters[i+1] == ')') {
						values.add(2);
						i++;
						continue;
					}
				}
				values.add(0);
			}
			else {
				values.add(1);
			}
		}
		
		System.out.println(values);
		
		for (int i=values.size()-1; i >= 0; i--) {
			var value = values.get(i);
			if (value == 1) {
				onePositions.push(i);
			}
			if (value == 0) {
				if (!onePositions.isEmpty()) {
					values.set(i, 2);
					int removePosition = onePositions.pop();
					values.set(removePosition, 3);
				}
			}
		}
		System.out.println(values);
		
		int count = 0;
		int maxCount = 0;
		for (int i=0;i<values.size();i++) {
			if (values.get(i) == 2) {
				count = count + 2;
				continue;
			}
			if (values.get(i) == 3) {
				continue;
			}
				maxCount = Math.max(maxCount, count);
				count = 0;
		}
		return Math.max(maxCount, count);
	}

}
