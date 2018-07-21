package com.saqib.justforfun;

public class TestInheritance {
	public String returnIdentity() {
		return "TestInheritance";
	}
	
	public static void main(String args[]) {
		TestInheritanceChild tic = new TestInheritanceChild();
		TestInheritance ti = new TestInheritanceChild();
		tic = (TestInheritanceChild) ti;
		System.out.println(ti.returnIdentity());
		
		System.out.println(10d/3d);
	}
}

class TestInheritanceChild extends TestInheritance {
	
	public String returnIdentity() {
		return "ChildInheritance";
	}
	
	public String specialChildInheritanceMethod() {
		return "Special Child Inheritance Method";
	}
}
