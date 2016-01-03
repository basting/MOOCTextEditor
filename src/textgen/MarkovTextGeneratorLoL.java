package textgen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	private HashMap<String, ListNode> wordListNodeMap;
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
		wordListNodeMap = new HashMap<>();
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
	    String [] wordArr = sourceText.split("[\\s]");
        
	    List<String> words = Arrays.asList(wordArr);
	     
	    if(words.size() == 0) {
	        return;
	    }
	    
	    String prevWord = words.get(0);
        
	    if(wordList.isEmpty()) {
	        starter = prevWord;
	    } 
	    
	    for(int i = 1; i<words.size(); i++) {
	        String nextWord = words.get(i);
	        addToList(prevWord, nextWord);
	        prevWord = nextWord;
	    }	    
	    String lastWord = words.get(words.size()-1);
	    addToList(lastWord, starter);
	}
	
	private void addToList(String prevWord, String nextWord) {
	    if(wordListNodeMap.containsKey(prevWord)) {
            wordListNodeMap.get(prevWord).addNextWord(nextWord);
        } else {
            ListNode newNode = new ListNode(prevWord);
            newNode.addNextWord(nextWord);
            wordListNodeMap.put(prevWord, newNode);
            wordList.add(newNode);
        }
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    if(wordList.isEmpty() || numWords == 0) {
	        return "";
	    }
	    
	    String currWord = starter;
	    StringBuilder output = new StringBuilder();
	    output.append(currWord);
	    int counter = 1;
	    while(counter < numWords) {
	        ListNode currWordNode = wordListNodeMap.get(currWord);
	        String w = currWordNode.getRandomNextWord(rnGenerator);
	        output.append(" ");
	        output.append(w);
	        currWord = w;
	        counter++;            
	    }
	    
	    return output.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText){
	    wordList = new LinkedList<ListNode>();
        starter = "";
        wordListNodeMap = new HashMap<>();
        train(sourceText);
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		int size = nextWords.size();
	    int randomIdx = generator.nextInt(size);
	    return nextWords.get(randomIdx);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
	@Override
	public boolean equals(Object obj) {
	    return word.equals(((ListNode)obj).word);
	}
	
	@Override
	public int hashCode() {
	    return word.hashCode();
	}
}


