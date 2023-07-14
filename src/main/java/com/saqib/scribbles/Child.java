package com.saqib.scribbles;

public class Child extends Parent {
		
	public void doSomething(Integer i) {
		System.out.println("Child");
	}
	
	public static void main(String[] args) {
		Parent p = new Child();
		p.doSomething(6);
	}
	
}
