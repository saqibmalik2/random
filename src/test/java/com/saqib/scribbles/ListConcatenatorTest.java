package com.saqib.scribbles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

public class ListConcatenatorTest {
	
	
	ListConcatenator listConcatenator = new ListConcatenator();
	
	// required for the string list test
	static List<String> stringList1 = new ArrayList<>();
	static List<String> stringList2 = new ArrayList<>();
	static List<String> stringList3 = new ArrayList<>();
	static List<List<String>> listOfListOfStrings = new ArrayList<>();
	static List<String> expectedStringResult = new ArrayList<>();
	
	// required for the object list test
	static List<Object> objectList1 = new ArrayList<>();
	static List<Object> objectList2 = new ArrayList<>();
	static List<Object> objectList3 = new ArrayList<>();
	static List<List<Object>> listOfListOfObject = new ArrayList<>();
	static List<Object> expectedObjectResult = new ArrayList<>();
	

	@BeforeAll
	static void initAll() {
		
		//test concatenation of multiple lists of Strings
		stringList1.add("one");
		stringList1.add("two");
		stringList1.add("three");
		stringList1.add("four");
		
		stringList2.add("five");
		stringList2.add("six");
		stringList2.add("seven");
		stringList2.add("eight");
		
		stringList3.add("nine");
		stringList3.add("ten");

		
		listOfListOfStrings.add(stringList1);
		listOfListOfStrings.add(stringList2);
		listOfListOfStrings.add(stringList3);
		
		expectedStringResult.add("one");
		expectedStringResult.add("two");
		expectedStringResult.add("three");
		expectedStringResult.add("four");
		expectedStringResult.add("five");
		expectedStringResult.add("six");
		expectedStringResult.add("seven");
		expectedStringResult.add("eight");
		expectedStringResult.add("nine");
		expectedStringResult.add("ten");
		
		
		//for the object test
		objectList1.add(Integer.valueOf(3));
		objectList1.add(Integer.valueOf(2));
		objectList1.add(Integer.valueOf(1));
		
		objectList2.add(Integer.valueOf(7));
		objectList2.add(Integer.valueOf(7));
		objectList2.add(Integer.valueOf(7));
		
		objectList3.add(Integer.valueOf(9));
		objectList3.add("end");
		
		listOfListOfObject.add(objectList1);
		listOfListOfObject.add(objectList2);
		listOfListOfObject.add(objectList3);
		
		expectedObjectResult.add(Integer.valueOf(3));
		expectedObjectResult.add(Integer.valueOf(2));
		expectedObjectResult.add(Integer.valueOf(1));
		expectedObjectResult.add(Integer.valueOf(7));
		expectedObjectResult.add(Integer.valueOf(7));
		expectedObjectResult.add(Integer.valueOf(7));
		expectedObjectResult.add(Integer.valueOf(9));
		expectedObjectResult.add("end");
		
	}
	
	@Test
	@DisplayName("String List Concatenator")
	void stringListConcatenatorTest() {
		List<String> actualResult = listConcatenator.concatenateLists(listOfListOfStrings);
		assertEquals(expectedStringResult, actualResult);
	}
	
	@Test
	@DisplayName("Object List Concatenator")
	void stringObjectConcatenatorTest() {
		List<Object> actualResult = listConcatenator.concatenateLists(listOfListOfObject);
		assertEquals(expectedObjectResult, actualResult);
	}
	
}
