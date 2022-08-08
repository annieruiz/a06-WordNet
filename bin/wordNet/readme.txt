/******************************************************************************
 *  Name:     Annie Ruiz
 *  NetID:    NA
 *  Precept:  NA
 *
 *  Partner Name: NA    
 *  Partner NetID:  NA  
 *  Partner Precept:  NA
 *
 *  Hours to complete assignment (optional): ~8 hours
 *
 ******************************************************************************/

Programming Assignment 6: WordNet


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in synsets.txt. Why did you make this choice?
 *****************************************************************************/
 For the synset.txt, I decided to use an Bag Iterable to store the ids, since
 it is the easiest and best running way to store multiple ids for one word.


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in hypernyms.txt. Why did you make this choice?
 *****************************************************************************/
The hypernyms were stored using an Integer and a String, the nouns were placed
into one and the ids into the other.

/******************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithms as a function of the number of vertices V and the
 *  number of edges E in the digraph?
 *****************************************************************************/

Description:
	The shortest common ancestor uses two BreadthFirstDirectedPaths. A loop
	Is created to search for an ancestor, if both the vertices have a path
	to the current iteration, then the vertex is the ancestor.
	The length to that ancestor is computer and the loop runs
	until it finds another ancestor, and then checks whether that one is a shorter
	distance to see if it is a closer ancestor or not.



Order of growth of running time: worst case, all vertices are iterated (V + E)


/******************************************************************************
 *  Describe concisely your algorithm to compute the shortest common
 *  ancestor in ShortestCommonAncestor. For each method, what is the order of
 *  growth of the worst-case running time as a function of the number of
 *  vertices V and the number of edges E in the digraph? For each method,
 *  what is the order of growth of the best-case running time?
 *
 *  If you use hashing, you should assume the uniform hashing assumption
 *  so that put() and get() take constant time.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't
 *  forget to count the time needed to initialize the marked[],
 *  edgeTo[], and distTo[] arrays.
 *****************************************************************************/

Description:

                                              running time
method                               best case            worst case
------------------------------------------------------------------------
length(int v, int w)					V + E		V + E

ancestor(int v, int w)					V + E		V + E

length(Iterable<Integer> v,				V + E		V + E
       Iterable<Integer> w)

ancestor(Iterable<Integer> v,			2V + 2E		2V + 2E
         Iterable<Integer> w)




/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
None that I know of 

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
 I got help by a few of the school tutors

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
 I had difficulty setting up the constructor but then it was okay

/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/
 I did not work with a partner



/**********************************************************************
 *  Have you completed the mid-semester survey? If you haven't yet,
 *  please complete the brief mid-course survey at https://goo.gl/gB3Gzw
 * 
 ***********************************************************************/
 No

/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/
