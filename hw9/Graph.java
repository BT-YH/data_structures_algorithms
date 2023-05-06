import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Graph {
	
	private HashMap<String, Vertex> vertMap;
	private ArrayList<Vertex> verts;
	private ArrayList<Edge> edges;
	private double[][] adjmat;
	
	/**
	 * Instantiate a graph
	 * @param gfn	text file containing the information
	 * of the graph
	 */
	public Graph(String gfn) {
		verts = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		vertMap = new HashMap<String, Vertex>();
		
		Scanner in = new Scanner(gfn);
		String labels = in.nextLine();
		String[] labels_array = labels.split(" ");
		int nVals = labels_array.length;
		
		adjmat = new double[nVals][nVals];
		
		// initialize verts and vertMap
		for (int i = 0; i < nVals; i++) {
			String strLab = labels_array[i];
			Vertex newV = new Vertex(i, strLab);
			verts.add(newV);
			vertMap.put(strLab, newV);
		}
		
		for (int i = 0; i < nVals; i++) {
			String currLine = in.nextLine();
			String[] weights = currLine.split(" ");
			//System.out.println("weights: " + Arrays.toString(weights));
			// initialize edges
			for (int j = 0; j < nVals; j++) {
				double currWeight = Double.parseDouble(weights[j]);
				adjmat[i][j] = currWeight;
				if (currWeight != 0) {
					Edge newEd = new Edge(verts.get(i), verts.get(j), currWeight);
					Vertex currV = verts.get(i);
					currV.addAdj(newEd);
					edges.add(newEd);
				}
			}
		}
		in.close();
	}
	
	/**
	 * Resets predecessor, cost, and marked of all vertices 
	 * in this graph to the given values.
	 * @param mark		the mark value
	 * @param newCost	the new cost
	 * @param newPred	the new predecessor
	 */
	public void resetVerts(boolean mark, double newCost, Vertex newPred) {
		for (int i = 0; i < verts.size(); i++) {
			Vertex currVert = verts.get(i);
			if (mark) {
				currVert.mark();
			} else {
				currVert.unmark();
			}
			currVert.setCost(newCost);
			currVert.setPred(newPred);
		}
	}
	
	public void resetVerts() {
		for (int i = 0; i < verts.size(); i++) {
			Vertex currVert = verts.get(i);
			currVert.unmark();
			currVert.setCost(Vertex.INF);
			currVert.setPred(null);
		}
	}
	
	/**
	 * returns the vertex list of this graph
	 * @return   the vertex list of this graph
	 */
	public ArrayList<Vertex> getVerts() {
		return verts;
	}
	
	/**
	 * returns the number of vertices in this graph 
	 * @return	number of vertices in the graph
	 */
	public int nVerts() {
		return verts.size();
	}
	
	/**
	 *  returns the number of edges in this graph 
	 * @return		the number of edges in this graph 
	 */
	public int nEdges() {
		return edges.size();
	}
	
	/**
	 *  Returns the vertex associated with label.
	 *  Returns null if no vertex in this graph matches 
	 *  the label. 
	 * @param label		the label of the target edge
	 * @return	returns the target vertex, null if not found
	 */
	public Vertex getVertex(String label) {
		return vertMap.get(label);
	}
	
	/**
	 * Returns the index-th vertex (0-based). 
	 * Returns null if index is invalid.
	 * @param index		the ID of the vertex
	 * @return	the target vertex, null if not found
	 */
	public Vertex getVertex(int index) {
		if (index < 0 || index >= verts.size()) {
			return null;
		}
		return verts.get(index);
	}
	
	/**
	 * Returns the edge from vsrc to vdst. 
	 * Returns null if no edge exists from vsrc to vdst. 
	 * @param vsrc	 the source vertex
	 * @param vdst	 the destination vertex
	 * @return	the target edge, null if not found
	 */
	public Edge getEdge(Vertex vsrc, Vertex vdst) {
		for (Edge edge : edges) {
			Vertex dst = edge.getDst();
			Vertex src = edge.getSrc();
			
			if (dst.equals(vdst) && src.equals(vsrc)) {
				return edge;
			}
		}
		return null;
	}
	
	/**
	 *  Returns the edge from src to dst. Returns null 
	 *  if no edge exists from src to dst. 
	 * @param src	the source vertex
	 * @param dst	the source vertex
	 * @return
	 */
	public Edge getEdge(String src, String dst) {
		for (Edge edge : edges) {
			Vertex vdst = edge.getDst();
			String strDst = vdst.getLabel();
			Vertex vsrc = edge.getSrc();
			String strSrc = vsrc.getLabel();
			
			if (dst.equals(strDst) && src.equals(strSrc)) {
				return edge;
			}
		}
		return null;
	}
	
	/**
	 * Returns the adjacency matrix of this graph 
	 * @return	the adjacency matrix of this graph 
	 */
	public double[][] getMat() {
		return adjmat;
	}
	
	/**
	 * returns the information of this graph in String format
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nVerts(); i++) {
			Vertex currV = verts.get(i);
			if (i == nVerts() - 1) {
				sb.append(currV.toString());
			} else {
				sb.append(currV.toString() + "\n");
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		File graph = new File("src/graphs/g1");
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
		
		System.out.println(myGraph.verts);
		System.out.println(myGraph.edges);
		System.out.println(myGraph.vertMap);
		System.out.println(myGraph);
		System.out.println(Arrays.deepToString(myGraph.adjmat));
	}
}
