package com.saqib.scribbles;

import java.util.Arrays;

public class GenericArrayPositionExchange {

	public static void main(String[] args) {
		String[] stringArray = {"What", "a", "lovely", "day!"};
		Integer[] integerArray = {1,2,3,4,5};
		System.out.println(Arrays.toString(new GenericArrayPositionExchange().exchangePosition(stringArray, 0,3)));
		System.out.println(Arrays.toString(new GenericArrayPositionExchange().exchangePosition(integerArray, 0,3)));
	}
	
	public <T> T[] exchangePosition(T[] inputArray, int positionA, int positionB) {
		T tempHolder = inputArray[positionB];
		inputArray[positionB] = inputArray[positionA];
		inputArray[positionA] = tempHolder;
		
		return inputArray;
	}

}
