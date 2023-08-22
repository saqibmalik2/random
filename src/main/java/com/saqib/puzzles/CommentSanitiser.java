package com.saqib.puzzles;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * A coding exercise for a job interview I did at Tesco. Unfortunately I'm never too good at these 1 hour pair programming exercises
 * so didn't quite get the final solution (it partially worked) in the allotted time. For fun I finished it off straight after the interview.
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
	
	private Pattern validEmailRegEx = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	private Pattern validPhoneNumberRegEx = Pattern.compile("^\\([0-9]+\\)|[0-9]+");
	
	private String emailAddress = "<email address>";
	private String phoneNumber = "<phone number>";
	
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
		System.out.println(commentSanitiser.sanitiseComment("James was a crap builder...and generally a cretin. Send him your complaints on james@jollycleverbuilders.com,boyo@jollycleverbuilders.com or (07965123456)"));
	}
	
	public String sanitiseComment(String comment) {
		String[] words = comment.split("(\s+)|,");
		String asterix = "*";
		
		for (int i=0;i<words.length;i++) {
			String currentWord = words[i].replaceAll("\\p{Punct}", "").toLowerCase();
			if (dictionary.contains(currentWord)) {
				String replacement;
				if (words[i].matches(".*\\p{Punct}")) {
					//there might be more than one punctuation mark after the word
					// e.g. "James was a crap builder and generally a cretin.."
					// in this case word[i] will be "cretin.." but we only need 6 asterixes 
					// for the replacement not 7 or 8 i.e. don't create asterixes for punctuation marks
					replacement = asterix.repeat(words[i].length() - (words[i].length() - currentWord.length()));
				}
				else {
					replacement = asterix.repeat(words[i].length());
				}
				comment = comment.replaceFirst(currentWord, replacement);
			}
			if (validateEmail(words[i])) {
				comment = comment.replaceFirst(words[i], emailAddress);
			}
			if (validatePhoneNumber(words[i])) {
				comment = comment.replaceFirst("[0-9]+", phoneNumber);
			}
		}
		
		return comment;
	}

	private boolean validateEmail(String emailStr) {
        Matcher matcher = validEmailRegEx.matcher(emailStr);
        return matcher.matches();
	}
	
	private boolean validatePhoneNumber(String phoneNumber) {
        Matcher matcher = validPhoneNumberRegEx.matcher(phoneNumber);
        return matcher.matches();
	}


}
