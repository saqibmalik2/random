package com.saqib.puzzles;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
/**
 * 
 * A coding exercise for a job interview I did at Tesco. 
 * Unfortunately I'm never too good at these 1 hour pair programming exercises
 * so didn't quite get the final solution (it partially worked) in the allotted time.
 * 
 * For fun I finished it off straight after the interview.
 * 
 * It's a very simple email sanitiser. The example test case input string they give you is:
 * 
 * "James was a crap builder and generally a cretin. Send him your complaints on james@jollycleverbuilders.com or (07965123456)";
 * 
 * which should be sanitised to this:
 * 
 * "James was a **** builder and generally a ******. Send him your complaints on <email address> or (<phone number>)"
 * 
 * The two rude words, "crap" and "cretin" have been replaced with same number of asterixes as they have characters.
 * Also the email address and phone number have been replaced with "<email address>" and "<phone number>" respectively.
 * 
 */
public class CommentSanitiser {
	
	private List<String> dictionary = new ArrayList<>();
	private Pattern validEmailRegEx = Pattern.compile("([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)");
	// IMPROVED: More restrictive phone pattern - requires 10-11 digits, optionally in parentheses
	// This prevents matching random numbers like "5" or "2024" in normal text
	private Pattern validPhoneNumberRegEx = Pattern.compile("\\([0-9]{10,11}\\)|[0-9]{10,11}");
	private String asterix = "*";
	private String emailAddress = "<email address>";
	private String phoneNumberReplacement = "<phone number>";
	
	public CommentSanitiser() {
		initialiseDictionary();
	}
	private void initialiseDictionary() {
		dictionary.add("crap");
		dictionary.add("cretin");
		dictionary.add("idler");
		dictionary.add("idiot");
	}
	public static void main(String[] args) {
		CommentSanitiser commentSanitiser = new CommentSanitiser();
		System.out.println(commentSanitiser.sanitiseComment("James was a crap builder...and generally a cretin. Send him your complaints on james@jollycleverbuilders.com,boyo@jollycleverbuilders.com or (07965123456),07445598796"));
		
		// Test case-insensitivity
		System.out.println(commentSanitiser.sanitiseComment("What a CRAP job, Cretin!"));
		
		// Test word boundaries
		System.out.println(commentSanitiser.sanitiseComment("He's not scrappy, just crap at building"));
	}
	
	public String sanitiseComment(String comment) {
		
		// IMPROVED: Use word boundaries and case-insensitive matching for profanity
		// Pattern.quote() prevents the word from being treated as regex if it contained special chars
		for (String forbiddenWord : dictionary) {
			String replacement = asterix.repeat(forbiddenWord.length());
			// \b ensures we match whole words only (so "crap" doesn't match in "scrappy")
			// CASE_INSENSITIVE flag catches "crap", "Crap", "CRAP", etc.
			Pattern pattern = Pattern.compile("\\b" + Pattern.quote(forbiddenWord) + "\\b", 
			                                  Pattern.CASE_INSENSITIVE);
			comment = pattern.matcher(comment).replaceAll(replacement);
		}
		
		// IMPROVED: Direct replacement using Matcher.replaceAll() - simpler and more efficient
		// No need to collect matches in a list first, just replace them directly
		comment = validEmailRegEx.matcher(comment).replaceAll(emailAddress);
		
		comment = validPhoneNumberRegEx.matcher(comment).replaceAll(phoneNumberReplacement);
		
		return comment;
	}
}