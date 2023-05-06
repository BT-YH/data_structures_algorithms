import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
			TreeMap<String, Edge> adj = currV.adj;
			
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
		
		TreeMap<String, Edge> adj = vsrc.adj;
		
		for (Edge e : adj.values()) {
			Vertex dst = e.getDst();
			if (!dst.isMarked()) {
				dst.mark();
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
		vsrc.mark();
		vsrc.setCost(0);
		pq.offer(vsrc);
		
		while(!pq.isEmpty()) {
			Vertex currV = pq.poll();
			TreeMap<String, Edge> adj = currV.adj;
			
			for (Edge e : adj.values()) {
				Vertex dst = e.getDst();
				double nw = currV.getCost() + e.getWeight();
				
				if (!dst.isMarked()) {
					dst.mark();
					dst.setPred(currV);
					dst.setCost(nw);
					pq.offer(dst);
				} else if (nw < dst.getCost()) {
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
			if (src.equals(currV.getLabel())) {
				continue;
			}
			printDijkstraPaths(g, vsrc.getLabel(), currV.getLabel());
			System.out.println();
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
		
		
		while (pred != null && !dstV.equals(srcV)) {
			dstV = pred;
			pred = dstV.getPred();
			path = dstV.getLabel() + "-" + path;
		}
		
		String output = String.format("%s-->%s: ", src, dst);
		
		if (path.contains(src)) {
			output += "(" + g.getVertex(dst).getCost() + ") ";
			output += path;
			output += "(done)";
			System.out.print(output);
		} else {
			output += "no path";
			System.out.print(output);
		}
		
//		if (pred == null) {
//			System.out.print(dst + "-");
//			return ;
//		}
//		
//		if (dst.equals(src)) {
//			System.out.print(dst + "-");
//			return;
//		}
//		
//		printDijkstraPaths(g, src, pred.getLabel());
//		System.out.print(dst + "-");
		
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
		
		Graph myGraph = new Graph(gfn);
		System.out.printf("[ Graph w/ %d vertices ]\n", myGraph.nVerts());
		System.out.println(myGraph);
		System.out.println();
		
		// 
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
