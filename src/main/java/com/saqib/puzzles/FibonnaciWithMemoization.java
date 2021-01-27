package com.saqib.puzzles;

/**
 * How to do Fibonnaci using Memoization.
 * 
 * @author Saqib Malik
 * @since 12/02/2020
 * 
 */

public class FibonnaciWithMemoization {
	
	public static long fibArray[]=new long[31];
	
	static {
		 fibArray[0]=1;
		 fibArray[1]=1;
	}
	 
	public static long fibonacci(long n){
		 long fibValue=0;
		 if(n==0 ){
			 return 0;
		 }
		 else if(n==1){
			 return 1;
		 }
		 else if(fibArray[(int)n]!=0){
		   return fibArray[(int)n];
		 }
		 else{
		     fibValue=fibonacci(n-1)+fibonacci(n-2);
		     fibArray[(int) n]=fibValue;
		     return fibValue;
		 }
	 }
	 
	 public static void main(String args[]){
		 long preTime=System.currentTimeMillis();
		 System.out.println("Value of 6th number in Fibonacci series->"+fibonacci(6));
		 long postTime=System.currentTimeMillis();
		 System.out.println("Time taken to compute in milliseconds->"+(postTime-preTime));
	 }

}
