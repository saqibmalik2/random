package com.saqib.puzzles;

import static java.lang.System.*;


/**
 * @author Saqib Malik (2018)
 * 
 * Checks to see if the supplied string is a palindrome.
 *
 */
public class PalindromeChecker 
{
    public static void main( String[] args )
    {
    	if (args.length == 0) throw new IllegalArgumentException("Cannot supply a null for testing!");
    	String testString = args[0];
    	out.println("Is " + testString + " a palindrome? " + ((new PalindromeChecker().isAPalindrome(testString)) == true ? "yes" : "no"));
    }
    
    public boolean isAPalindrome(String s) {
    	int length = s.length();
    	int endpoint = isStringEvenLength(s) ? ((length/2) -1) : (length/2);
    	int backwardsCounter = length - 1;
    	int forwardsCounter = 0;
    	
    	if (length < 2) throw new IllegalArgumentException("Length of test string must be two or greater!");
    	
    	while (forwardsCounter <= endpoint) {
    		if (s.charAt(forwardsCounter) != s.charAt(backwardsCounter)) return false;
        	backwardsCounter--;
        	forwardsCounter++;
    	}
    	
    	return true;
    }
    
    private boolean isStringEvenLength(String s) {
    	return (s.length() % 2 == 0);
    }
}