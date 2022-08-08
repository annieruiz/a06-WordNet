package wordNet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * WordNet is a semantic lexicon for the English language that is used 
 * extensively by computational linguists and cognitive scientists; for 
 * example, it was a key component in IBM's Watson. WordNet groups words 
 * into sets of synonyms called synsets and describes semantic 
 * relationships between them. One such relationship is the is-a 
 * relationship, which connects a hyponym (more specific synset) to a
 *  hypernym (more general synset). For example, a plant organ is a 
 *  hypernym of carrot and plant organ is a hypernym of plant root.
 *  This assignment builds a WordNEt digraph and finds the SAP of each
 *  of the words.
 * @author Annie Ruiz
 *
 */
public class Outcast {

	private WordNet wordnet;												// Makes a global wordnet
	
	/**
	 * Constructor takes a WordNet object
	 * @param wordnet the wordnet to be searching through
	 */
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;												// Assigns the sent wordnet to the global wordnet
	}
	
	/**
	 * Given an array of WordNet nouns, return an outcast
	 * @param nouns - the array of nouns to be searching through
	 * @return
	 */
	public String outcast(String[] nouns) {
		if (nouns.length == 0) throw new java.lang.IllegalArgumentException();
		int maxDist = 0;													// Sets the max distance found to 0
		String outcast = nouns[0];											// Sets the outcast String to the first noun in the array
		for (String noun : nouns) {											// Loops through every noun in the array
			int tempDist = 0;												// Sets a temporary distance to 0, used to find the max dist of the elements
			for (String otherNoun : nouns) {								// Loops through every noun in the array
				if (noun != otherNoun) {									// If the first noun is not equal to the second noun
					tempDist += wordnet.distance(noun, otherNoun);			// Add the distance of the two nouns to the tempDist
				}
			}
			if (tempDist > maxDist) {										// If the temp distance is greater than the max distance
				maxDist = tempDist;											// The temp distance is assigned to the max distance
				outcast = noun;												// And the noun with the largest distance is assigned as the outcast
			}
		}
		return outcast;														// Returns the outcast in the array
	}
	
	
	/**
	 * // for unit testing of this class (such as the one below)
	 * @param args
	 */
	public static void main(String[] args) {
		String file1 = "src/files/synsets.txt";
		String file2 = "src/files/hypernyms.txt";
	    WordNet wordnet = new WordNet(file1, file2);
	    Outcast outcast = new Outcast(wordnet);
	    String[] files = {"src/files/outcast5.txt", 
	                      "src/files/outcast8.txt",
	                      "src/files/outcast11.txt"};
	    for (int t = 0; t < files.length; t++) {
	        In in = new In(files[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(files[t] + ": " + outcast.outcast(nouns));
	    }
	}
}
