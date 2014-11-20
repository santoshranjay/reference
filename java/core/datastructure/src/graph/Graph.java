package graph;

public interface Graph {

	/**
	 * add edge to vertex v and w.
	 * 
	 * @param v
	 * @param w
	 */
	void addEdge(int v, int w);
	
	/**
	 * total number of vertices in graph
	 * @return
	 */
	int numVertices();
	
	/**
	 * number of edges in graph
	 * @return
	 */
	int numEdges();
	
	/**
	 * get the list of adjacent vertices for a given vertex v.(vertex adjacency list)
	 * @param v
	 * @return
	 */
	Iterable<Integer> adj(int v);
}
