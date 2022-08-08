package wordNet;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

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
public class WordNet {
	private Digraph digraph;
	private SAP sap;													// Shortest Ancestor Path class
	private ST<String, Bag <Integer>> nounIndex;						// Keeps track of the noun and the lookup from the String
	private ST<Integer, String> synsetIDs;								// Keeps track of the synset from the ID
	
	/**
	 * 	Constructor takes the name of the two input files
	 * @param synsets -
	 * @param hypernyms -
	 */
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null) 						// No argument can be null
			throw new java.lang.NullPointerException();					// Throws NullPointerException
		synsetIDs = new ST<Integer, String>();							// Assigns local synsetIndex to global synsetIndex
		nounIndex = new ST<String, Bag <Integer>>();					// Assigns local nounIndex to global nounIndex 
		readSynsets(synsets);											// Builds the synsetIDs and nounIndex by adding the synsets and hypernyms sent
		digraph = new Digraph(synsetIDs.size());						// Assigns the digraph after, using the size of the synsetID as the size of the digraph
		readHypernyms(hypernyms);										// Reads in the hypernyms and sets each edge for the digraph
		sap = new SAP(digraph);											// Instantiates the class SAP with the digraph created
	}

	/**
	 * Returns all WordNet nouns
	 * @return - all WordNet nouns
	 */
	public Iterable<String> nouns() {
		   return nounIndex.keys();										// Uses build in 'keys' class to get all nouns in index
	}

	/**
	 * Is the word a WordNet noun?
	 * @param word - the word to check whether it's a noun or not
	 * @return true if it is a noun, false if otherwise
	 */
	public boolean isNoun(String word) {
		if (word == null) 												// No argument can be null
			throw new java.lang.NullPointerException();					// Throws NullPointerException
		return nounIndex.contains(word);								// Returns true if the nounIndex contains word sent, false if otherwise
	}

	/**
	 * Distance between nounA and nounB
	 * @param nounA - The first noun sent
	 * @param nounB - The second noun sent
	 * @return The distance between the two nouns sent
	 */
	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null) 								// No argument can be null
			throw new java.lang.NullPointerException();						// Throws NullPointerException
		if (nounA.equalsIgnoreCase(nounB)) return 0;						// Returns zero if they're the same word, there's no distance
		return sap.length(nounIndex.get(nounA), nounIndex.get(nounB));		// Uses the SAP class to find the length of the two fields
	}

	/**
	 * Synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	 * the common ancestor of nounA and nounB in a shortest ancestral path
	 * @param nounA - The first noun
	 * @param nounB - The second noun
	 * @return The common ancestor of the two nouns in a shortest ancestral path
	 */
	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null)										// No argument can be null
			throw new java.lang.NullPointerException();							// Throws NullPointerException
        int sapID = sap.ancestor(nounIndex.get(nounA), nounIndex.get(nounB));	// Gets the ancestor ID uses the SAP class
        return synsetIDs.get(sapID);											// Returns the synset index of the ancestor ID found
	}

	// do unit testing of this class
	public static void main(String[] args) {
	}
	
	
	/*****************************HELPER METHODS******************************************/
	public void readSynsets(String syn) {
		In inSyn = new In(syn);											// Input file of the synsets
		String delimiter = ",";											// Sets delimiter for practicality purposes
		
		while (inSyn.hasNextLine()) {									// Runs through each line in the file
			String[] allArgs = inSyn.readLine().split(delimiter);		// Gets the upcoming line of the CSV
			Integer id = Integer.parseInt(allArgs[0]);					// Gets the id from the first CSV argument
			String synset = allArgs[1];									// Grabs the synsets
			synsetIDs.put(id, synset);									// Adds these synsets to the synsetID ST
			for (String noun : synset.split(" ")) {						// Iterates through each of the nouns in the synset
				if (nounIndex.contains(noun)) 							// If the nounIndex already has the noun in the iteration
					nounIndex.get(noun).add(id);						// Just add the id connected to it to the ST
				else {													// Otherwise...
					Bag<Integer> list = new Bag<Integer>();				// Creates a new Bag
					list.add(id);										// Adds the sent id to the the Bag
					nounIndex.put(noun, list);}							// Adds the Bag and the noun to the NounIndex
			}															// End of for loop
		}																// End of while loop
	}
	
	public void readHypernyms(String hyp) {
		In inHyp = new In(hyp);											// Reads in the input file of the hypernyms
	
		while(inHyp.hasNextLine()) {									// Runs through each line in the file
			String[] splitHyps = inHyp.readLine().split(",");			// Sets the each individual object of the line
			Integer id = Integer.parseInt(splitHyps[0]);				// The id is the first object in each line
			for (int i = 1; i < splitHyps.length; i++) {				// Goes through each object in the line and
				digraph.addEdge(id, Integer.parseInt(splitHyps[i]));	// Adds an edge to the digraph for each object
			}
		}
	}
}
