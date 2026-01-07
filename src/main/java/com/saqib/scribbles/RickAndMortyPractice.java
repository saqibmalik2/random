package com.saqib.scribbles;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.*;
import static java.util.stream.Collectors.*;

/**
 * REALISTIC PAGINATION PRACTICE - Rick and Morty API
 * 
 * TASK:
 * Using the Rick and Morty API (https://rickandmortyapi.com/api/character),
 * find the character who appears in the MOST episodes.
 * In case of a tie, choose the character whose name comes first alphabetically.
 * 
 * API Details:
 * - Base URL: https://rickandmortyapi.com/api/character?page=N
 * - First response includes pagination metadata
 * - You must discover the total number of pages from the API response
 * 
 * Response structure:
 * {
 *   "info": {
 *     "count": 826,
 *     "pages": 42,
 *     "next": "https://rickandmortyapi.com/api/character?page=2",
 *     "prev": null
 *   },
 *   "results": [
 *     {
 *       "id": 1,
 *       "name": "Rick Sanchez",
 *       "status": "Alive",
 *       "species": "Human",
 *       "episode": [
 *         "https://rickandmortyapi.com/api/episode/1",
 *         "https://rickandmortyapi.com/api/episode/2",
 *         ...
 *       ]
 *     },
 *     ...
 *   ]
 * }
 * 
 * EXPECTED OUTPUT:
 * Print the name of the character who appears in the most episodes.
 * 
 * KEY LEARNING:
 * - Extract total pages from API response (not hardcoded!)
 * - Navigate nested JSON structure (info vs results)
 * - Count array length (episode array size)
 * - Two-level comparator with counting logic
 */
public class RickAndMortyPractice {
    
    public static void main(String[] args) {
        try {
            String result = findMostFrequentCharacter();
            System.out.println("Character appearing in most episodes: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * TODO: Implement this method
     * 
     * Return the name of the character who appears in the MOST episodes.
     * In case of a tie, return the character whose name comes first alphabetically.
     * 
     */
    public static String findMostFrequentCharacter() throws Exception {
    	// create our HttpClient for sending requests
    	HttpClient httpClient = HttpClient.newHttpClient();
    	
    	//build our request
    	HttpRequest httpRequest = HttpRequest.newBuilder()
    										 .uri(URI.create("https://rickandmortyapi.com/api/character"))
    										 .GET()
    										 .build();
    	//HttpResponse Body Handler
    	HttpResponse.BodyHandler<String> responseBodyHandler = BodyHandlers.ofString();
    	
    	//send our request and receive the response
    	String response = httpClient.send(httpRequest, responseBodyHandler).body();
    	System.out.println(response);
    	
    	JsonElement root = JsonParser.parseString(response);
    	JsonObject rootObject = root.getAsJsonObject();
    	
    	// retrieve the info section of the initial response which holds the metadata
    	// this metadata tells us how many pages in total there are and we retrieve that
    	// so we know many pages we need to loop through
    	JsonObject infoObject = rootObject.get("info").getAsJsonObject();
    	int totalPages = Integer.parseInt(infoObject.get("pages").getAsString());
    	
    	String responsePage;

    	// we are using a TreeMap as it stores its keys (i.e. the actor names) in sorted order according to their natural ordering
    	TreeMap<String, Integer> actorsEpisodes = new TreeMap<>();
  
    	// variables to hold the actor name and the number of episodes they've featured in
    	String actorName;
    	Integer totalEpisodes = Integer.valueOf(0);
    	
    	
    	//loop from 1 to the total number of pages we previously extracted
    	for (int i=1;i <= totalPages; i++) {
    		// create a request for this specific page number
    		HttpRequest paginatedRequest = HttpRequest.newBuilder()
					 .uri(URI.create("https://rickandmortyapi.com/api/character?page=" + i))
					 .GET()
					 .build();
    		
    		//send our request and receive the response
    		responsePage = httpClient.send(paginatedRequest, responseBodyHandler).body();
    		
    		//parse the returned JSon
    		JsonElement resultsPage = JsonParser.parseString(responsePage);
    		
    		//the results is the holder object for all the individual actor objects and their associate
    		JsonArray arrayOfElements = resultsPage.getAsJsonObject().get("results").getAsJsonArray();
    		
    		for (JsonElement element:arrayOfElements) {
    			totalEpisodes = element.getAsJsonObject().get("episode").getAsJsonArray().size();
    			actorName = element.getAsJsonObject().get("name").getAsString();
    			actorsEpisodes.merge(actorName, totalEpisodes, Integer::sum);
    		}
    	}
    	
    	Comparator<Map.Entry<String, Integer>> compare = (e1, e2) -> { 
    																   return e1.getValue().compareTo(e2.getValue());
    																  };
    	Map.Entry<String, Integer> topEntry = actorsEpisodes.entrySet().stream().max(compare).get();
    	
    	// this is just to check that the tiebreak condition is being met
    	// we find the highest number of episodes any actor has starred in
    	// then we find all actors who match that criteria and gather them into a Set
    	// we print out the Set so we can see whether the final answer does indeed pick the lowest one
    	// by alphabetical order.
    	int highestNoOfEpisodes = actorsEpisodes.values().stream().mapToInt(i -> i).max().getAsInt();
    	Set<String> topActors = actorsEpisodes.entrySet().stream().sorted(compare).filter(e -> e.getValue() == highestNoOfEpisodes)
    			.map(e -> e.getKey() + " : " + e.getValue()).collect(toSet());
    	System.out.println(topActors);
    	return topEntry.getKey();
    }
}
