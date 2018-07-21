package com.saqib.justforfun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GenericMethod {
	
	public static void main (String...args) {
		Collection<Object> collectionOfStrings = new ArrayList<>();
		collectionOfStrings.add(new String());
		
		String[] stringArray = {"one", "two", "three"};
		System.out.println(Arrays.asList(stringArray).stream().reduce("", (a,b) -> a + b));
	}
	
	void foo(List<?> i) {
		fooHelper(i);
    }
	
	private <T> void fooHelper(List<T> l) {
        l.set(0, l.get(0));
    }

}
