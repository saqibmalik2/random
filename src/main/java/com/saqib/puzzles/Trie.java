package com.saqib.puzzles;

import java.util.HashMap;

/**
 * A simple trie data structure.
 * This represents a basic implementation. It can be hugely optimised in various ways.
 */

public class Trie {
	
	TrieNode rootNode;
	
	//create our trie with a solitary empty root node
	public Trie() {
		rootNode = new TrieNode();	
	}
	
	private class TrieNode {
		HashMap<Character, TrieNode> childNodes = new HashMap<>();
		boolean terminal; //the JVM initialises this to false
	}
	
	public void insert(String word) {
		// start at the root node
		TrieNode currentNode = rootNode;
		
		//start traversing the word one letter at a time
		for (int i=0; i < word.length(); i++) {
			//this letter is already present in the trie so move to the next node
			if (currentNode.childNodes.containsKey(word.charAt(i))) {
				currentNode = currentNode.childNodes.get(word.charAt(i));
			}
			//this letter isn't present so add it as a child node of the current node
			//and then move the currentNode pointer to this new node
			else {
				currentNode.childNodes.put(word.charAt(i), new TrieNode());
				currentNode = currentNode.childNodes.get(word.charAt(i));
			}
		}
		//the currentNode is now pointing at the final node of the word - regardless of...
		//...whether it was inserted or already existed. We set the terminal flag to true.
		currentNode.terminal = true;
	}
	
	public boolean search(String word) {
		// start at the root node
		TrieNode currentNode = rootNode;
		
		//start traversing the word one letter at a time
		for (int i=0; i < word.length(); i++) {
			//if the letter is in one of the child nodes then move on to the next node
			if (currentNode.childNodes.containsKey(word.charAt(i))) {
				currentNode = currentNode.childNodes.get(word.charAt(i));
			}
			else {
				return false;
			}
		}
		//every letter is present but are we at a terminal node (i.e is the word itself
		// there or are these letters part of a longer word?)
		return (currentNode.terminal == Boolean.TRUE) ?  true: false;
	}
	
	public boolean startsWith(String prefix) {
		// start at the root node
		TrieNode currentNode = rootNode;
		
		//start traversing the word one letter at a time
		for (int i=0; i < prefix.length(); i++) {
			//if the letter is in one of the child nodes then move on to the next node
			if (currentNode.childNodes.containsKey(prefix.charAt(i))) {
				currentNode = currentNode.childNodes.get(prefix.charAt(i));
			}
			else {
				return false;
			}
		}
		//if we got here then every letter of the prefix is present
		return true;
	}
	
	    
	public static void main(String[] args) {
		Trie trie = new Trie();
		//false as nothing yet inserted
		System.out.println(trie.search("amazon"));
		trie.insert("amazon");
		trie.insert("amazing");
		trie.insert("forest");
		trie.insert("forage");
		//true as we've inserted 'amazon'
		System.out.println(trie.search("amazon"));
		//false as no word in the trie starts with 'amoz'
		System.out.println(trie.startsWith("amoz"));
		//true as 'amazon' starts with 'amaz'
		System.out.println(trie.startsWith("amaz"));
		//true
		System.out.println(trie.search("forest"));
		//true
		System.out.println(trie.search("forage"));
		//true
		System.out.println(trie.startsWith("for"));
	}
	
   
}
