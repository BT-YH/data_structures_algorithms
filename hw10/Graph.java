import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

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
			Vertex currV = verts.get(i);
			// initialize edges
			for (int j = 0; j < nVals; j++) {
				double currWeight = in.nextDouble();
				adjmat[i][j] = currWeight;
				if (currWeight != 0) {
					Edge newEd = new Edge(currV, verts.get(j), currWeight);
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
			currVert.reset(mark, newCost, newPred);
		}
	}
	
	public void resetVerts() {
		for (int i = 0; i < verts.size(); i++) {
			Vertex currVert = verts.get(i);
			currVert.reset();
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
		if (vsrc != null && vdst != null) {
			return vsrc.getAdj(vdst.getLabel());
		}
		return null;
	}
	
	/**
	 *  Returns the edge from src to dst. Returns null 
	 *  if no edge exists from src to dst. 
	 * @param src	the source vertex
	 * @param dst	the source vertex
	 * @return	the target edge, if not found returns null
	 */
	public Edge getEdge(String src, String dst) {
		Vertex vsrc = vertMap.get(src);
		Vertex vdst = vertMap.get(dst);
		
		return getEdge(vsrc, vdst);
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
		sb.append(String.format("\n[ Graph w/ %d vertices ]\n", nVerts()));
		for (int i = 0; i < nVerts(); i++) {
			Vertex currV = verts.get(i);
			sb.append(currV.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * EdgeComp.java
	 * comparator for edge costs first then edge labels
	 * compare first the costs of the edges
	 *         then source vertex labels        (if same cost)
	 *         then destination vertex labels   (if same cost and same source vertex)
	 */

	private class EdgeComp implements Comparator<Edge> {
		@Override
		/**
		 * returns a positive integer if e1 > e2, 0 if equal, or negative integer if e1 < e2
		 * 
		 * @param e1    first Edge
		 * @param e2    second Edge
		 * @return comparison value of two given Edges
		 */
	    public int compare(Edge e1, Edge e2) {
	        int diff = (int) (Math.round((e1.getWeight() - e2.getWeight()) * 100)) / 100;

	        // if costs of the two edges are the same, compare the edge labels
	        if (diff == 0) {
	            Vertex vsrc1 = e1.getSrc();
	            Vertex vdst1 = e1.getDst();
	            Vertex vsrc2 = e2.getSrc();
	            Vertex vdst2 = e2.getDst();

	            String eLabel1 = vsrc1.getLabel() + vdst1.getLabel();
	            String eLabel2 = vsrc2.getLabel() + vdst2.getLabel();

	            return eLabel1.compareTo(eLabel2);
	        }

	        return diff;
	    }
	}

	/**
	 * (to be used for Kruskal's algorithm)
	 *
	 * internally convert directed graph to undirected graph 
	 * and return the edges sorted by cost and label in increasing order
	 *
	 * @return list of edges sorted by cost first then by edge label in increasing order
	 */
	public ArrayList<Edge> getEdgesUndirected() {
	    ArrayList<Edge> und = new ArrayList<Edge>();
	    EdgeComp edgeComp = new EdgeComp();

	    for (Edge e : edges) {
	        Vertex src = e.getSrc();
	        Vertex dst = e.getDst();
	        double wgt = e.getWeight();

	        // make each edge's src vertex to be < dst vertex in 
	        // undirected graph for easier comparison
	        String srcLabel = src.getLabel();
	        String dstLabel = dst.getLabel();

	        if (srcLabel.compareTo(dstLabel) > 0) {
	            Vertex tmp = src;
	            src = dst;
	            dst = tmp;
	        }

	        Edge ne = new Edge(src, dst, wgt);

	        // similar to one iteration of the insertion sort algorithm
	        // add the newly created edge to a correct location according 
	        // to the sort order
	        int i = 0, eCount = und.size();
	        while (i < eCount && edgeComp.compare(ne, und.get(i)) > 0) {
	            ++i;
	        }
	        
	        // Barry's modification 
	        if (binarySearch(und, ne) == -1) {
	        	und.add(i, ne);
	        }
	    }
	    
	    return und;
	}

	/**
	 * (to be used before applying the MST algorithms)
	 *
	 * for each directed edge (v to u), add edge (u to v) if it doesn't exist
	 * if it does, update edge (u to v)'s weight to the weight of edge (v to u)
	 * update adjacency matrix accordingly
	 */
	public void toUndirected() {

	    for (Vertex v : verts) {

	        int vID = v.getID();

	        TreeMap<String, Edge> v_adj = v.getAdj();

	        // for each edge (v->u)
	        for (Edge e : v_adj.values()) {
	            Vertex u = e.getDst();
	            double w = e.getWeight();
	            int uID  = u.getID();

	            // add v as neighbor to dst if it isn't already a neighbor
	            // edge (u->v)
	            if (u.getAdj(v.getLabel()) == null) {
	                Edge newE = new Edge(u, v, w);
	                u.addAdj(newE);
	                edges.add(newE);
	            }

	            // if edge (u->v) already exists, need to update it with the same cost
	            else {
	                Edge oldE = u.getAdj(v.getLabel());
	                oldE.setWeight(w);
	            }

	            // update the adjacency matrix accordingly
	            adjmat[uID][vID] = w;
	        }
	    }
	}
	
	/**
	 * A standard binary search, used in place of ArrayList's contains
	 * method
	 * @param list
	 * @param target
	 * @return
	 */
	private int binarySearch(ArrayList<Edge> list, Edge target) {
		int left = 0;
		int right = list.size() - 1;
		int mid = -1;
		EdgeComp cmp = new EdgeComp();
		
		while  (left <= right) {
			mid = (left + right) / 2;
			
			if (cmp.compare(list.get(mid), target) < 0) {
				left = mid + 1;
			} else if (cmp.compare(list.get(mid), target) > 0) {
				right = mid - 1;
			} else {
				return mid;
			}
		}
		
		return -1; // not found
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
		System.out.println("Edges: " + myGraph.edges);
		System.out.println(myGraph.vertMap);
		System.out.println(myGraph);
		System.out.println(Arrays.deepToString(myGraph.adjmat));
		
		myGraph.toUndirected();
		myGraph.edges = myGraph.getEdgesUndirected();
		System.out.println(myGraph);
		System.out.println("Edges: " + myGraph.edges);
	}
}
