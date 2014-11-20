package graph;

/**
 * Graph - set of vertices connected via the edges.
 * Graph representation
 * 
 * 1. Adjacency-matrix graph
 * 2. Adjacency-list graph 
 * 
 * Path - sequence of vertices connected by edges.
 * Cycle - Path having 1st and last vertices same.
 * Degree(v) - number of vertices connected to the vertex v.
 * 
 * Euler tour - exactly one edge.
 * Hamilton tour - exactly one vertex.
 * 
 * DFS (depth first search algo)
 * Maze exploration [ using ball & string to trace the return(visited) path ]
 * depth-first search [systematically search through a graph, mimic maze exploration]
 * 1. mark v as visited
 * 2. recursively visit all unmarked vertices w adjacent to v.
 */
