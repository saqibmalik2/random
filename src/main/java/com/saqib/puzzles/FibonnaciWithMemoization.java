package com.saqib.puzzles;

/**
 * @author Saqib Malik
 * 
 */

public class FibonnaciWithMemoization {
	
	public static long fibArray[]=new long[31];
	 
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
		 fibArray[0]=1;
		 fibArray[1]=1;
		 long preTime=System.currentTimeMillis();
		 System.out.println("Value of 30th number in Fibonacci series->"+fibonacci(30));
		 long postTime=System.currentTimeMillis();
		 System.out.println("Time taken to compute in milliseconds->"+(postTime-preTime));
	 }

}
