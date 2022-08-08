# Programming Assignment 6: WordNet
 - *Name: Annie Ruiz*
 - *Hours to complete assignment: ~8 hours*

### Describe concisely the data structure(s) you used to store the information in synsets.txt. Why did you make this choice?
For the synset.txt, I decided to use an Bag Iterable to store the ids, since it is the easiest and best running time when storing multiple ids for one word. 

### Describe concisely the algorithm you use in the constructor of ShortestCommonAncestor to check if the digraph is a rooted DAG. What is the order of growth of the worst-case running times of your algorithms as a function of the number of vertices V and the number of edges E in the digraph?
 
The shortest common ancestor uses two BreadthFirstDirectedPaths. A loop is created to search for an ancestor, if both the vertices have a path to the current iteration, then the vertex is the ancestor. The length to that ancestor is computer and the loop runs until it finds another ancestor, and then checks whether that one is a shorter distance to see if it is a closer ancestor or not.
