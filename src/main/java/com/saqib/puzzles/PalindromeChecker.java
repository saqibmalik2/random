package com.saqib.puzzles;

import static java.lang.System.*;

import java.util.stream.IntStream;


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
    	out.println("Is " + testString + " a palindrome? " + ((new PalindromeChecker().isAPalindromeUsingStreams(testString)) == true ? "yes" : "no"));
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
    
    // alternative much more concise method that utilises Java streams
    public boolean isAPalindromeUsingStreams(String text) {
    	String temp  = text.replaceAll("\\s+", "").toLowerCase();
    	
    	// using java streams we check that at no point does our mismatch predicate hold true
    	// if that's the case then noneMatch will return true
    	
        return IntStream.range(0, temp.length() / 2)
          .noneMatch(i -> temp.charAt(i) != temp.charAt(temp.length() - i - 1));
    }
    
    private boolean isStringEvenLength(String s) {
    	return (s.length() % 2 == 0);
    }
}