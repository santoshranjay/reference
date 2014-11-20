package graph;

/**
 * impl e.g, Paths 
 * Paths(G, s)
 * 
 * implementing all the graph processing into one - FAT interface
 * always decouple into (design pattern) 
 * @author Santoshkumar
 *
 */
public interface Path {

	/**
	 * check whether there is a path from source s to v.
	 * @param v
	 * @return
	 */
	boolean hasPathTo(int v);
	
	/**
	 * find and return a path from s to v.
	 * @param v
	 * @return
	 */
	Iterable<Integer> pathTo(int v);
}
