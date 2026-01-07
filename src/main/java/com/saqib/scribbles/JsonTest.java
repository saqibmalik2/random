package com.saqib.scribbles;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;



class Result {

    /*
     * Complete the 'validateRequests' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. STRING_ARRAY blacklisted_ips
     *  2. STRING_ARRAY requests
     */

    public static List<Integer> validateRequests(List<String> blacklisted_ips, List<String> requests) {
    	List<Integer> requestResults = new ArrayList<>();
    	
    	for (String request:requests) {
    		
//    		Boolean matched = 
		//Pattern p = Pattern.compile(regex);
		//Matcher m = p.matcher(line);
    
    	}
		return requestResults;
    }

}

public class Solution {
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 * 
	 * if two unblocked requests have been receieved from the same ip address within the last 5 seconds
	 * then block the current request
	 */
    public static void main(String[] args) throws IOException {
    	
    	List<String> blacklisted_ips = new ArrayList<>();
    	List<String> requests = new ArrayList<>();
    	
    	blacklisted_ips.add("111.*.255");
    	blacklisted_ips.add("12.");
    	
    	requests.add("121.3.5.255");
    	requests.add("12.13.5.255");
    	requests.add("111.3.5.255");
    	requests.add("121.3.5.255");

        List<Integer> result = Result.validateRequests(blacklisted_ips, requests);

        System.out.println(result);
        
        
        
    }
}