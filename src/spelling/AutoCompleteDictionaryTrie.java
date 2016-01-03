package spelling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)	{
	    TrieNode current = root;
	    char [] letters = word.toCharArray();
	    for(int i=0; i< letters.length; i++) {
	        char cLower = Character.toLowerCase(letters[i]);
	        TrieNode newNode = current.insert(cLower);
	        if (newNode == null) {
	            current = current.getChild(cLower);
	        } else {
	            current = newNode;
	        }	        
	    }
	    if(!current.endsWord()) {
	        current.setEndsWord(true);    
	        size++;
	        return true;
	    }
        return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size() {
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) {
	    TrieNode current = root;
        char [] letters = s.toCharArray();
        for(int i=0; i< letters.length; i++) {
            char cLower = Character.toLowerCase(letters[i]);
            TrieNode childNode = current.getChild(cLower);
            if (childNode == null) {
                return false;
            } 
            current = childNode;           
        }
        return current.endsWord();
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */
	@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	
	    List<String> completions = new ArrayList<>();
	    if(numCompletions <= 0) {
	        return completions;
	    }
	    TrieNode current = root;
        char [] letters = prefix.toCharArray();
        for(int i=0; i< letters.length; i++) {
            char cLower = Character.toLowerCase(letters[i]);
            TrieNode childNode = current.getChild(cLower);
            if (childNode == null) {
                return completions;
            } 
            current = childNode;           
        }
        
        LinkedList<TrieNode> queue = new LinkedList<TrieNode>();
        queue.add(current);
        int count = 0;
        while(!queue.isEmpty()) {
            TrieNode currNode = queue.remove();
            if(currNode.endsWord()) {
                completions.add(currNode.getText());
                if(++count >= numCompletions) {
                    return completions;
                }
            }
            Set<Character> childChars = currNode.getValidNextCharacters();
            for(Character c: childChars) {
                queue.add(currNode.getChild(c));
            }
        }
        
        return completions;
     }

 	// For debugging
 	public void printTree()	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr) {
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}