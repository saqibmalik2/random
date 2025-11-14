package com.saqib.puzzles;

import static java.lang.System.*;


public class BuySellStock {
	
	public static void main(String[] args) {
		int[] prices = {7,1,3,5,6,4,8};
		BuySellStock bss = new BuySellStock();
		out.println(bss.maxProfit(prices));
	}
	
	public int maxProfit(int[] prices) {
		int maxProfit=0;
		
        for (int i=1;i<prices.length;i++) {
        	int difference = (prices[i] - prices[i-1]);
        	if (difference > 0) {
        		maxProfit+=difference;
        	}
        }
        return maxProfit;
    }

}
