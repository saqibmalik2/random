package com.saqib.puzzles;

public class GasStation {

	public static void main(String[] args) {
		int[] gas = {1,2,3,3,1,7,4,1};
		int[] cost = {3,4,1,1,6,2,1,1};
		GasStation gs = new GasStation();
		System.out.println(gs.canCompleteCircuit(gas, cost));
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @param gas
	 * @param cost
	 * @return startingIndex
	 */
	public int canCompleteCircuit(int[] gas, int[] cost) {
        int sumCost = 0;
        for (int c : cost) {
            sumCost += c;
        }
        int sumGas = 0;
        for (int g : gas) {
            sumGas += g;
        }
        // Check if it is possible to complete the journey
        if (sumCost > sumGas) {
            return -1;
        }

        int currentGas = 0;
        int startingIndex = 0;

        for (int i = 0; i < gas.length; i++) {
            currentGas += gas[i] - cost[i];
            if (currentGas < 0) {
                currentGas = 0;
                startingIndex = i + 1;
            }
        }
        return startingIndex;
    }

}
