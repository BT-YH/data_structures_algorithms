import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;


public class GraphUtil {
	
	/**
	 * Lists (prints) the vertices (labels) visited by the 
	 * Breadth-First-Search traversal algorithm on the graph 
	 * g from the vsrc vertex. 
	 * @param g		the graph
	 * @param vsrc	the source vertex
	 */
	public static void bfs(Graph g, Vertex vsrc) {
		g.resetVerts();
		Queue<Vertex> q = new LinkedList<>();
		q.offer(vsrc);
		vsrc.mark();
		System.out.printf("bfs(%s): ", vsrc.getLabel());
		
		while (!q.isEmpty()) {
			Vertex currV = q.poll();
			System.out.print(currV.getLabel() + "-");
			TreeMap<String, Edge> adj = currV.getAdj();
			
			for (Edge e : adj.values()) {
				Vertex dst = e.getDst();
				if (!dst.isMarked()) {
					dst.mark();
					q.offer(dst);
				}
			}
		}
		System.out.print("(done)\n");
	}
	
	private static void dfsHelper(Graph g, Vertex vsrc) {
		System.out.print(vsrc.getLabel() + "-");
		vsrc.mark();
		
		TreeMap<String, Edge> adj = vsrc.getAdj();
		
		for (Edge e : adj.values()) {
			Vertex dst = e.getDst();
			if (!dst.isMarked()) {
//				dst.mark();   // not needed because of line 47
				dfsHelper(g, dst);
			}
		}
	}
	
	/**
	 * Lists (prints) the vertices (labels) visited by the 
	 * Depth-First-Search traversal algorithm on the graph 
	 * g from the vsrc vertex. 
	 * @param g		the graph
	 * @param vsrc	the source vertex
	 */
	public static void dfs(Graph g, Vertex vsrc) {
		g.resetVerts();
		System.out.printf("dfs(%s): ", vsrc.getLabel());
		dfsHelper(g, vsrc);
		System.out.print("(done)\n");
	}
	
	/**
	 * 1. Applies the Dijkstra's shortest path algorithm to 
	 * the graph g from the vsrc vertex.
	 * 2. Prints the paths from src to each vertex in the 
	 * graph g by calling printDijkstraPaths. 
	 * @param g		the graph
	 * @param vsrc	the source vertex
	 */
	public static void dijkstra(Graph g, Vertex vsrc) {
		g.resetVerts();
		PriorityQueue<Vertex> pq = new PriorityQueue<>(new VertexComparator());

		vsrc.setCost(0);
		pq.addAll(g.getVerts());
		
		while(!pq.isEmpty()) {
			Vertex currV = pq.poll();
			currV.mark();
			TreeMap<String, Edge> adj = currV.getAdj();
			
			for (Edge e : adj.values()) {
				Vertex dst = e.getDst();
				double nw = currV.getCost() + e.getWeight();
				
				if (!dst.isMarked() && nw < dst.getCost()) { // if dst is new
					pq.remove(dst);
					dst.setCost(nw);
					dst.setPred(currV);
					pq.offer(dst);
				} 
			}
		}
		
		String src = vsrc.getLabel();
		System.out.println("dijkstra's single-source shortest path from " + src);
		for (int j = 0; j < g.nVerts() ; j++) {
			Vertex currV = g.getVertex(j);
			if (!src.equals(currV.getLabel())) {
				printDijkstraPaths(g, vsrc.getLabel(), currV.getLabel());
				System.out.println();
			}
		}

	}
	
	/**
	 * Prints the path from src to dst assuming Dijkstra's shortest 
	 * path algorithm had been applied and vertices have proper 
	 * information (cost and predecessor of the path, if exists). 
	 * @param g		the graph
	 * @param src	the source vertex
	 * @param dst	the destination vertex
	 */
	public static void printDijkstraPaths(Graph g, String src, String dst) {
		Vertex srcV = g.getVertex(src);
		Vertex dstV = g.getVertex(dst);
		Vertex pred = dstV.getPred();
		
		String path = dstV.getLabel() + "-";
		
		
		while (pred != null && dstV != srcV) {
			dstV = pred;
			pred = dstV.getPred();
			path = dstV.getLabel() + "-" + path;
		}
		
		String output = String.format("%s-->%s: ", src, dst);
		
		if (dstV == srcV) {
			output += "(" + g.getVertex(dst).getCost() + ") ";
			output += path;
			output += "(done)";
			System.out.print(output);
		} else {
			output += "no path";
			System.out.print(output);
		}
	}
	
	/**
	 * applies Floyd-Warshall algorithm to compute the all-pairs
	 * shortest paths algorithm to graph g;
	 * also prints the paths from every vertex to every other 
	 * vertex by calling printAllPairsShortestPaths
	 * @param g	 	the target graph
	 */
	public static void floyedWarshall(Graph g) {
		int size = g.nVerts();
		double[][] dMatrix = new double[g.nVerts()][g.nVerts()];
		Vertex[][] pMatrix = new Vertex[g.nVerts()][g.nVerts()];
		double[][] adjmat = g.getMat();
		
		// initialize matrices to default values
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i != j) {
					dMatrix[i][j] = Double.POSITIVE_INFINITY;
					pMatrix[i][j] = null;
				} else {
					dMatrix[i][j] = 0;
					pMatrix[i][j] = g.getVertex(i);
				}
			}
		}
		
		// initialize matrices to the correct initial values
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (adjmat[i][j] != 0) {
					dMatrix[i][j] = adjmat[i][j];
					pMatrix[i][j] = g.getVertex(i);
				}
			}
		}
		
		// the floyed warshall algorithm
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					double newVal = dMatrix[i][k] + dMatrix[k][j];
					if (dMatrix[i][j] > newVal) {
						dMatrix[i][j] = newVal;
						pMatrix[i][j] = pMatrix[k][j];
					}
				}
			}
		}
		
		printAllPairsShortestPaths(g, dMatrix, pMatrix);
	}
	
	/**
	 * Using the given matrices, reconstructs and prints the 
	 * shortest path from every vertex to every other vertex in g. 
	 * @param g		the target graph
	 * @param dist		the distance matrix
	 * @param pred		the predecessor matrix
	 */
	public static void printAllPairsShortestPaths(Graph g, double[][] dist, Vertex[][] pred) {
		int size = g.nVerts();
		
		for (int i = 0; i < size; i++) {
			Vertex src = g.getVertex(i);
			System.out.print("\nStarting from " + src.getLabel());
			for (int j = 0; j < size; j++) {
				if (i==j) continue;
				
				Vertex dst = g.getVertex(j);
				System.out.printf("\n%s --> %s: ", src.getLabel(), dst.getLabel());
				String path = "";
				
				if (dist[i][j] == Double.POSITIVE_INFINITY) {
					System.out.print("no path");
				} else {
					while (!src.equals(dst)) {
						path = dst.getLabel() + "-" + path;
						int nj = dst.getID();
						dst = pred[i][nj];
					}
					path = src.getLabel() + "-" + path;
					path = String.format("(%.1f) ", dist[i][j]) + path;
					System.out.print(path.substring(0, path.length() - 1));
				}
			}
			
			System.out.println();
		}
	}
	
	/**
	 * 
	 * applies Kruskal's algorithm to find the minim spanning 
	 * tree in graph g and prints the total cost and the edges 
	 * in the mst in the order they are added to it. 
	 * @param g		the target graph
	 */
	public static void kruskal(Graph g) {
		g.toUndirected();
		ArrayList<Edge> undEdges = g.getEdgesUndirected();
		DisjointSet<Vertex> set = new DisjointSet<>(g.getVerts());
		ArrayList<Edge> mst = new ArrayList<>();
		
		double sum = 0;
		for (Edge e : undEdges) {
			Vertex src = e.getSrc();
			Vertex dst = e.getDst();
			
			Vertex sRep = set.findRep(src);
			Vertex dRep = set.findRep(dst);
			
			if (!sRep.equals(dRep)) {
				set.union(src, dst);
				mst.add(e);
				sum += e.getWeight();
			}
			
		}
		
		System.out.println("MST: " + mst.toString());
		System.out.println("Total Cost: " + sum);
	}
	
	/**
	 * Test the given graph by applying BFS, DFS and Dijkstra's algorithm
	 * @param gName		the name of the graph
	 */
	public static void testGraph(String gName) {
		File graph = new File("src/graphs/" + gName);
		String gfn = "";
		try {
			Scanner in = new Scanner(graph);
			while (in.hasNextLine()) {
				gfn += in.nextLine() + "\n";
			}
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(gfn);
		Graph myGraph = new Graph(gfn);
		System.out.println(myGraph);
		System.out.println();

		System.out.println("Breadth First Search: ");
		for (int i = 0; i < myGraph.nVerts(); i++) {
			Vertex src = myGraph.getVertex(i);;
			bfs(myGraph, src);
		}
		
		System.out.println("\nDepth First Search: ");
		for (int i = 0; i < myGraph.nVerts(); i++) {
			Vertex src = myGraph.getVertex(i);;
			dfs(myGraph, src);
		}
		
		System.out.println("\nDijkstra's: ");
		
		for (int i = 0; i < myGraph.nVerts(); i++) {
			Vertex src = myGraph.getVertex(i);;
			dijkstra(myGraph, src);
			System.out.println();
		}

		
		System.out.println("\nKruskal's: ");
		kruskal(myGraph);
		
		
		myGraph = new Graph(gfn);
		System.out.println("\nFloyd-Warshall: ");
		floyedWarshall(myGraph);
	}
	
	
	/**
	 * Test the given graphs by applying BFS, DFS and Dijkstra's algorithm
	 * @param min	the min index
	 * @param max	the max index
	 */
	public static void testGraphs(int min, int max) {
		for (int i = min; i <= max; i++) {
			String name = "g" + i;
			
			System.out.printf("\n\n------------------Testing %s-----------------\n\n", name);
			testGraph(name);
		}
	}
	
	private static class VertexComparator implements Comparator<Vertex> {

		@Override
		public int compare(Vertex o1, Vertex o2) {
			double cost1 = o1.getCost();
			double cost2 = o2.getCost();
			
			if (cost1 == cost2) {
				String label1 = o1.getLabel();
				String label2 = o2.getLabel();
				return label1.compareTo(label2);
			}
			
			return (int) (cost1 - cost2 + 1);
		}
	}
	
	
}
