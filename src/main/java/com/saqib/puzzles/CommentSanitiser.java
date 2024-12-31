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
	private Pattern validEmailRegEx = Pattern.compile("([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)");
	private Pattern validPhoneNumberRegEx = Pattern.compile("\\([0-9]+\\)|[0-9]+");
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
	}
	
	public String sanitiseComment(String comment) {
		
		for (String forbiddenWord:dictionary) {
			String replacement = asterix.repeat(forbiddenWord.length());
			comment = comment.replaceAll(forbiddenWord, replacement);
			
		}
		
		List<String> emailMatches = new ArrayList<String>();
		Matcher emailMatcher = validEmailRegEx.matcher(comment);
        while(emailMatcher.find()) {
        	emailMatches.add(emailMatcher.group(0));
        }
        
    	List<String> phoneNumberMatches = new ArrayList<String>();
    	Matcher phoneNumberMatcher = validPhoneNumberRegEx.matcher(comment);
        while(phoneNumberMatcher.find()) {
        	phoneNumberMatches.add(phoneNumberMatcher.group(0));
        }
		
        for (String email:emailMatches) {
        	comment = comment.replaceAll(email, emailAddress);
        }
        
        for (String phoneNumber:phoneNumberMatches) {
        	comment = comment.replaceAll(phoneNumber, phoneNumberReplacement);
        }
		
		return comment;
	}


}
