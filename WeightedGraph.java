import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class WeightedGraph<T> implements IWeightedGraph<T>{
	public static final int NULL_EDGE = 0;
	private static final int DEFCAP = 50; // default
	private int numVertices;
	private int maxVertices;
	private T[] vertices;
	private int[][] edges;
	private boolean[] marks; 
	
	/**
	 * No-args constructor sets maximum vertices to default capacity
	 */
	public WeightedGraph() {
		numVertices = 0;
		maxVertices = DEFCAP;
		vertices = (T[]) new Object[DEFCAP];
		marks = new boolean[DEFCAP];
		edges = new int[DEFCAP][DEFCAP];
	}
	
	/**
	 * Constructor initializes maxVertices to specified capacity
	 * @param maxVertices specified graph capacity
	 */
	public WeightedGraph(int maxVertices) {
		numVertices = 0;
		this.maxVertices = maxVertices;
		vertices = (T[]) new Object[maxVertices];
		marks = new boolean[maxVertices];
		edges = new int[maxVertices][maxVertices];
	}

	/**
	 * returns whether or not graph is empty
	 */
	@Override
	public boolean isEmpty() {
		return numVertices == 0;
	}

	/**
	 * returns weather or not the graph is full
	 * (full if number of vertices equal to maximum
	 * capacity of vertices)
	 */
	@Override
	public boolean isFull() {
		return numVertices == maxVertices;
	}

	/**
	 * Method to add vertex to grapy
	 */
	@Override
	public void addVertex(T vertex) {
		/** 
		 * PRECONDITIONS HERE?????????????????????????????????????????????????????????????????????????????????
		 */
		if (vertex == null) {
			throw new IllegalArgumentException("No null vertices allowed");
		}
		
		if (isFull()) {
			
		}
		
		// add vertex to end of (non-null part of) vertices array
		vertices[numVertices] = vertex;
		
		for (int i = 0; i < numVertices; i++) {
			// set row corresponding to this vertex's array to null-value (0), since
			// this vertex does not yet have any edges
			edges[numVertices][i] = NULL_EDGE;
			// set column corresponding to this vertex to null-value, since
			// there no other vertexes have an edge to this vertex
			edges[i][numVertices] = NULL_EDGE;
		}
		
		numVertices++; // increment num vertices
	}

	
	/**
	 * method that checks if specified vertex exists in graph
	 * @param vertex vertex to look for
	 */
	@Override
	public boolean hasVertex(T vertex) {
		for (T v : vertices) {
			if (v.equals(vertex)) {
				return true;
			}
		}
		
		return false;
	}

	
	/**
	 * Method to add edge to graph
	 * @param fromVertex start vertex of edge to add
	 * @param toVertex endpoint of edge to add
	 * @param int weight weight of edge
	 */
	@Override
	public void addEdge(T fromVertex, T toVertex, int weight) {
		// set edge[v1][v2] to weight
		edges[indexOf(fromVertex)][indexOf(toVertex)] = weight;	
	}
	
	
	/**
	 * Method to add two edges from one vertex to another, each pointing the opposite way
	 * @param vertexA start vertex
	 * @param vertexB end vertex
	 * @param weightA weight of edge going from vertexA to vertexB
	 * @param weightB weight of edge going from vertexB to vertexA
	 */
	public void addTwoWayEdge(T vertexA, T vertexB, int weightA, int weightB) {
		// set edge[v1][v2] to weight
		edges[indexOf(vertexA)][indexOf(vertexB)] = weightA;	
		edges[indexOf(vertexB)][indexOf(vertexA)] = weightB;	
	}
	
	/**
	 * method that returns index of specified vertex in the vertices array
	 * @param vertex specified vertex
	 * @return index of specified vertex
	 */
	private int indexOf(T vertex) {
		int index = 0;
		while(!vertex.equals(vertices[index])) {
			index++;
		}
		return index;
	}

	
	@Override
	/**
	 * method that returns weight of edge
	 * @param fromVertex start of edge
	 * @param toVertex end of edge
	 */
	public int weightIs(T fromVertex, T toVertex) {
		return edges[indexOf(fromVertex)][indexOf(toVertex)];
	}

	
	@Override
	/**
	 * method that returns edges that point to specified vertex
	 */
	public Queue<T> getToVertices(T vertex) {
		Queue<T> adjacentVertices = new LinkedList<T>();
		for (int i = 0; i < edges.length; i++) {
			if (edges[i][indexOf(vertex)] != NULL_EDGE) {
				adjacentVertices.add(vertices[i]);
			}
		}
		
		return adjacentVertices;
	}
	
	
	/**
	 * method that returns edges that start from specified vertex
	 * @param vertex start vertex of returned edges
	 * @return edges that start from specified vertex
	 */
	public Queue<T> goFromVertex(T vertex) {
		Queue<T> adjacentVertices = new LinkedList<T>();
		
		for (int i = 0; i < edges[indexOf(vertex)].length; i++) {
			if (edges[indexOf(vertex)][i] != NULL_EDGE) {
				adjacentVertices.add(vertices[i]);
			}
		}
		return adjacentVertices;
	}

	@Override
	/**
	 * method that clears marks from all vertices
	 */
	public void clearMarks() {
		for (int i = 0; i < marks.length; i++) {
			marks[i] = false;
		}
	}

	@Override
	/**
	 * mark a specific vertex
	 * @param vertex to mark
	 */
	public void markVertex(T vertex) {
		marks[indexOf(vertex)] = true;
		
	}

	@Override
	/**
	 * @return if specified vertex is marked 
	 */
	public boolean isMarked(T vertex) {
		return marks[indexOf(vertex)];
	}

	@Override
	/**
	 * @return first unmarked vertex
	 */
	public T getUnmarked() {
		for (T vertex : vertices) {
			if (!isMarked(vertex)) {
				return vertex;
			}
		}
		
		return null;
	}
	
	/**
	 * breadth first search
	 * @param startVertex start search
	 * @param endVertex end destination 
	 * @return path from start to end vertex
	 */
	public Queue<T> breadthFirstSearch(T startVertex, T endVertex) {
		Queue<T> q = new LinkedList<T>();
		HashMap<T, T> ht = new HashMap<T, T>(numVertices);
		boolean found = false;
		T current;
		
		// clear all marks, mark end vertex, and enqueue
		clearMarks();
		markVertex(endVertex);
		q.add(endVertex);

		// while the queue is not empty and the destination is not yet found...
		do {
			// set current vertex to first on queue
			current = q.poll();

			// if current vertex is starting point, set found to true
			if (current.equals(startVertex)) {
				found = true;
			
			// otherwise...
			} else {
				// create queue of vertices adjacent to current vertex
				Queue<T> adjacentVertices = getToVertices(current);
				
				// while the queue of adjacent vertices is not empty...
				while(!adjacentVertices.isEmpty()) {
					// remove first adjacency
					T adjacent = adjacentVertices.poll();

					// if it's not marked yet, mark it, add it to search queue, and put into hashmap
					if (!isMarked(adjacent)) {
						markVertex(adjacent);
						q.add(adjacent);
						ht.put(adjacent, current);
					} 
				}
			}
		} while (!q.isEmpty() && !found);
		
		// if a path has been found...
		if (found) {
			// create a stack
			Stack<T> path = new Stack<T>();
			// push destination onto stack
			path.push(endVertex);
						
			// honesty note: I wrote this code myself based on a diagram in the slides.
			// as I'm coming back to write comments, I am sure the algorithm is wrong, yet
			// when I change it to what I think it should be it all crashes and this seems
			// to be giving the right output. But I am so confused why the next line doesn't
			// say endVertex instead of startVertex
			T traversal = ht.get(startVertex);

			while (traversal != endVertex) {
				path.push(traversal);
				traversal = ht.get(traversal);
			}
			
			// pop stack onto linked list
			LinkedList<T> ll = new LinkedList<T>();
			ll.add(startVertex);
			while (!path.isEmpty()) {
				ll.add(path.pop());
			}
			return ll;
		}
		
		return null;
	}
	
	// display adjacency table
	public void printAdjacencyTable() {
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i] != null) {
				System.out.print((i+1) + ": " + vertices[i]);
				
				for (T adjacency : goFromVertex(vertices[i])) {
					System.out.print(" ----------> "+ adjacency + " | " + weightIs(vertices[i], adjacency));
				}
				System.out.println();
			}
		}
	}
}
