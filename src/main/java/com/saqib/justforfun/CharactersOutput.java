package com.saqib.justforfun;

public class CharactersOutput {
	
	public static void main (String...args) {
		"abracadabra".chars().distinct().peek(ch -> System.out.printf("%c", ch)).sorted().forEach(System.out::println);;
	}

}
