package wordNet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

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

public class SAP {
	private Digraph digraph;										// Sets a global digraph
	private static final int INFINITY = Integer.MAX_VALUE;			// Sets a final INFINITY for clarity purposes
    int minLength;													// Sets these values as global so they can be reached by more than just the helper methods
    int ancestor;
	
	/**
	 * Constructor takes a Digraph (not necessarily a DAG)
	 * @param G - the Digraph sent to search through for the shortest ancestral path
	 */
	public SAP(Digraph G) {
		if (G == null) 
			throw new java.lang.NullPointerException ();			//Throws exception if any argument is null
		digraph = G;												// Sets the local digraph to the global digraph
	}
	
	/**
	 * Is the Digraph a Directed Acyclic Graph?
	 * @return True if it is a directed acyclic graph, false if otherwise
	 */
	public boolean isDAG() {
		return new Topological(digraph).hasOrder();					// If the digraph has a topological order, it is a DAG
	
	}
	
	/**
	 * Is the Digraph a rooted DAG?
	 * @return True if it is a rooted DAG, false if otherwise
	 */
	public boolean isRootedDAG() {
		if(!isDAG()) return false;									// First it makes sure that the graph is a DAG
	    DepthFirstOrder dfo = new DepthFirstOrder(digraph);			// Find the potential 'root'
	    Integer rootVertex = dfo.post().iterator().next();			// (last vertex in topological order (reversePost), or 1st vertex in Post Order)
	   
	    // Send DepthFirstDirectedPaths a reverse graph, that way, the root will point to all children
	    DepthFirstDirectedPaths dfdp = new DepthFirstDirectedPaths(digraph.reverse(), rootVertex);
	    for(int i=0; i < digraph.V(); i++){							// Check if all vertices have a path to the root
		    if(!dfdp.hasPathTo(i)) return false;					// return false if one of the vertices does NOT have a path to the root
	    }															// End of for loop
	    return true;												// All vertices have a path to the rootVertex
	}
	
	/**
	 * length of shortest ancestral path between v and w; -1 if no such path
	 * @param v - The first vertex
	 * @param w - The second vertex
	 * @return the length of the shortest ancestral path between v and w; -1 if no such path
	 */
	public int length(int v, int w) {
		checkIfOutOfBound(v);										// Uses helper method to see if the int is out of bound
		checkIfOutOfBound(w);										// Uses helper method to see if the int is out of bound
		findLengthAndAncestor(v, w);								// Uses helper method to find the minimum length
		if (minLength == INFINITY)	return -1;						// returns -1 if the SAP was not found
		return minLength;											// returns the global minLength
	}
	
	/**
	 * a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	 * @param v
	 * @param w
	 * @return
	 */
	public int ancestor(int v, int w) {
		checkIfOutOfBound(v);										// Uses helper method to see if the int is out of bound
		checkIfOutOfBound(w);										// Uses helper method to see if the int is out of bound
		findLengthAndAncestor(v, w);								// Uses helper method to find the ancestor
		return ancestor;											// returns the global ancestor
	}
	
	/**
	 * // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	 * @param v - The first vertex
	 * @param w - The second vertex
	 * @return the length of the shortest ancestral path between v and w; -1 if no such path
	 */
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null)	
			throw new java.lang.NullPointerException();				//Throws exception if any argument is null
		checkIfOutOfBound(v);										// Uses helper method to see if the int is out of bound
		checkIfOutOfBound(w);										// Uses helper method to see if the int is out of bound
		findLengthAndAncestor(v, w);								// Uses helper method to find the minimum length
		if (minLength == INFINITY)	return -1;						// returns -1 if the SAP was not found
		return minLength;											// returns the global minLength
	}

	/**
	 * A common ancestor that participates in shortest ancestral path; -1 if no such path
	 * @param v - the first iterable sent to check
	 * @param w - the second iterable sent to check
	 * @return the common ancestor that participates in the shortest ancestral path
	 */
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
		if (v == null || w == null) throw new java.lang.NullPointerException();
		checkIfOutOfBound(v);										// Uses helper method to see if the int is out of bound
		checkIfOutOfBound(w);										// Uses helper method to see if the int is out of bound
		findLengthAndAncestor(v, w);								// Uses helper method to find the ancestor
		return ancestor;											// returns the global ancestor
	}
	
	/**
	 * do unit testing of this class
	 * @param args
	 */
	public static void main(String[] args) {
		String filename = "src/files/digraph1.txt";
	    In in = new In(filename);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (StdIn.hasNextLine()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }
	}
	
	/*************************HELPER CLASSES*****************************/
    private final void checkIfOutOfBound(int v) {				// Checks if out of bounds for integers
        if (v < 0 || v > digraph.V()) {							// If it is not between 0 and digraph.V() - 1
            throw new ArrayIndexOutOfBoundsException();			// Then it throws an out of bounds exception
        }
    }

    private void checkIfOutOfBound(Iterable<Integer> v) { 		// Checks if its out of bounds of the iteratable
        for (Integer i : v) {									// It will go through and check each iteration
            checkIfOutOfBound(i);								// Using the other helper overridden class
        }
    }
    
    private void findLengthAndAncestor(int v, int w) {
        minLength = INFINITY;									// Sets minLength to default
        ancestor = -1;											// Sets ancestor to default
		BreadthFirstDirectedPaths bfv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfw = new BreadthFirstDirectedPaths(digraph, w);

        for (int i = 0; i < digraph.V(); ++i) {					// Goes through each vertices in the digraph
            if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {			// If both v and w have a path to 'i'
            	int dist = bfv.distTo(i) + bfw.distTo(i);		// The distance is the sum of both distances
            	if (dist < minLength) {							// If the distance is less than the minimum length
            		minLength = dist;							// It is the minimum distance so far
            		ancestor = i;}								// the ancestor is set to i
            }													// End of if loop
        }														// End of for loop
    }
    
    private void findLengthAndAncestor(Iterable<Integer> v, Iterable<Integer> w) {
        minLength = INFINITY;											// Sets minLength to default
        ancestor = -1;													// Sets ancestor to default
		BreadthFirstDirectedPaths bfv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfw = new BreadthFirstDirectedPaths(digraph, w);
        
        for (int i = 0; i < digraph.V(); ++i) {					// Goes through each vertices in the digraph
            if (bfv.hasPathTo(i) && bfw.hasPathTo(i)) {			// If both v and w have a path to 'i'
            	int dist = bfv.distTo(i) + bfw.distTo(i);		// The distance is the sum of both distances
            	if (dist < minLength) {							// If the distance is less than the minimum length
            		minLength = dist;							// It is the minimum distance so far
            		ancestor = i;}								// the ancestor is set to i
            }													// End of if loop
        }														// End of for loop
    }
    
}
