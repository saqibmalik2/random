package com.saqib.puzzles;

public class GreatestCommonDenominator {

	public static void main(String[] args) {
		System.out.println(new GreatestCommonDenominator().gcd(14, 6));
	}

	public int gcd(int a, int b) {
		   if (b==0) return a;
		   return gcd(b,a%b);
	}
}
