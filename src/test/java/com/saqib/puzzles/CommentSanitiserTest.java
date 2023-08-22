package com.saqib.puzzles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommentSanitiserTest {
	
	String originalComment = "James was a crap builder and generally a cretin. Send him your complaints on james@jollycleverbuilders.com or (07965123456)";
	String originalComment1 = "James was: an idiot,idler and generally a cretin. Send him your complaints on james@jollycleverbuilders.com or (07965123456)";
	
	
	@Test
	void sanitiseCommentTest() {
		CommentSanitiser commentSanitiser = new CommentSanitiser();
		String sanitisedComment = commentSanitiser.sanitiseComment(originalComment);
		
		System.out.println(sanitisedComment);
		System.out.println("James was a **** builder and generally a ******. Send him your complaints on <email address> or (<phone number>)");
		
		assertEquals("James was a **** builder and generally a ******. Send him your complaints on <email address> or (<phone number>)", sanitisedComment);
	}
	
	@Test
	void sanitiseCommentTest1() {
		CommentSanitiser commentSanitiser = new CommentSanitiser();
		String sanitisedComment = commentSanitiser.sanitiseComment(originalComment1);
		
		System.out.println(sanitisedComment);
		System.out.println("James was: an *****,***** and generally a ******. Send him your complaints on <email address> or (<phone number>)");
		
		assertEquals("James was: an *****,***** and generally a ******. Send him your complaints on <email address> or (<phone number>)", sanitisedComment);
	}
	
	
}
