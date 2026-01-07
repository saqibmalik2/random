package com.saqib.scribbles;

import com.google.gson.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

/**
 * WARMUP EXERCISE - HttpClient + GSon basics (no pagination)
 * 
 * TASK:
 * Fetch Studio Ghibli films from: https://ghibliapi.vercel.app/films
 * Find the film with the HIGHEST rt_score (Rotten Tomatoes score).
 * In case of a tie, choose the one that comes first alphabetically by title.
 * 
 * Response format: JSON array of film objects
 * Each film has:
 * {
 *   "id": "...",
 *   "title": "Castle in the Sky",
 *   "rt_score": "95",  // NOTE: This is a STRING, not a number!
 *   "release_date": "1986",
 *   ...
 * }
 * 
 * EXPECTED OUTPUT:
 * Print the title of the highest-rated film.
 * 
 * GOTCHA: rt_score is a STRING like "95", not an integer.
 * You'll need to convert it: Integer.parseInt(obj.get("rt_score").getAsString())
 */
public class SimpleGsonPractice {
    
    public static void main(String[] args) {
        try {
            String result = findHighestRatedFilm();
            System.out.println("Highest rated film: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * TODO: Implement this method
     * 
     * Return the title which has the highest rt_score.
     * In the event of a tie-break us alphabetical ordering to resolve.
	 *
     * 
     */
    public static String findHighestRatedFilm() throws Exception {
    	//create the HttpClient
    	HttpClient httpClient = HttpClient.newHttpClient();
    	
    	//create the HttpRequest
    	HttpRequest request = HttpRequest.newBuilder()
    									  .uri(URI.create("https://ghibliapi.vercel.app/films"))
    									  .GET()
    									  .build();
    	
    	HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
    	
    	String responseBody = httpClient.send(request, bodyHandler).body();
    	
    	JsonElement root = JsonParser.parseString(responseBody);
    	
    	List<JsonElement> listOfJsonElements = new ArrayList<JsonElement>();
    	
    	if (root.isJsonArray()) {
    		for (JsonElement element:root.getAsJsonArray()) {
    			listOfJsonElements.add(element);
        	}
    	}
    	
    	listOfJsonElements.sort((je1, je2) -> {		
    												JsonObject jo1 = je1.getAsJsonObject();
    												JsonObject jo2 = je2.getAsJsonObject();
    												Integer rtScore1 = Integer.parseInt(jo1.get("rt_score").getAsString());
    												Integer rtScore2 = Integer.parseInt(jo2.get("rt_score").getAsString());
    												//note we want the higher one to come first and the lower to come second
    												if (rtScore1 > rtScore2) return -1;
    												if (rtScore1 < rtScore2) return 1;
    												//if we reach here then we're obviously in a tiebreak situation where rtScores are equal
    												return jo1.get("title").getAsString().compareTo(jo2.get("title").getAsString());
    												/**
    												 * ALTERNATIVE WAY OF PERFORMING THE COMPARISON
    												 * int scoreComp = Integer.compare(rtScore2, rtScore1); // descending
													 * if (scoreComp != 0) return scoreComp;
												     * return jo1.get("title").getAsString().compareTo(jo2.get("title").getAsString());
    												 */
    										   }
    							);
    
        return listOfJsonElements.get(0).getAsJsonObject().get("title").getAsString();
    }
}

