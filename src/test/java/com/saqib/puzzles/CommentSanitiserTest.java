package com.saqib.puzzles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommentSanitiserTest {
	
	String originalComment = "James was a crap builder and generally a cretin. Send him your complaints on james@jollycleverbuilders.com or (07965123456)";
	String commaSeparatedAbusiveWords = "James was: an idiot,idler and generally a cretin. Send him your complaints on james@jollycleverbuilders.com or (07965123456)";
	String moreThanTwoPhoneNumbers = "James was: an idiot,idler and generally a cretin. Send him your complaints on james@jollycleverbuilders.com or (07965123456) or (07958178325)";
	String moreThanTwoEmailAddresses = "James was: an idiot,idler and generally a cretin. Send him your complaints on james@jollycleverbuilders.com or jimmy@cowboybuilders.com or (07965123456)";
	
	@Test
	void sanitiseCommentTest() {
		CommentSanitiser commentSanitiser = new CommentSanitiser();
		String sanitisedComment = commentSanitiser.sanitiseComment(originalComment);
		
		assertEquals("James was a **** builder and generally a ******. Send him your complaints on <email address> or (<phone number>)", sanitisedComment);
	}
	
	@Test
	void sanitiseCommentCommaSeparatedAbusiveTest() {
		CommentSanitiser commentSanitiser = new CommentSanitiser();
		String sanitisedComment = commentSanitiser.sanitiseComment(commaSeparatedAbusiveWords);
		
		assertEquals("James was: an *****,***** and generally a ******. Send him your complaints on <email address> or (<phone number>)", sanitisedComment);
	}
	
	@Test
	void sanitiseCommentPhoneNumbersTest() {
		CommentSanitiser commentSanitiser = new CommentSanitiser();
		String sanitisedComment = commentSanitiser.sanitiseComment(moreThanTwoPhoneNumbers);
		
		assertEquals("James was: an *****,***** and generally a ******. Send him your complaints on <email address> or (<phone number>) or (<phone number>)", sanitisedComment);
	}
	
	@Test
	void sanitiseCommentTwoEmailAddressesTest() {
		CommentSanitiser commentSanitiser = new CommentSanitiser();
		String sanitisedComment = commentSanitiser.sanitiseComment(moreThanTwoEmailAddresses);
		
		assertEquals("James was: an *****,***** and generally a ******. Send him your complaints on <email address> or <email address> or (<phone number>)", sanitisedComment);
	}
	
}
