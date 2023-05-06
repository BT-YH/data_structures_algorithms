import java.util.TreeMap;


public class Vertex {
	
	public static final double INF = Double.POSITIVE_INFINITY;
	
	private int id;
	private String label;
	private Vertex pred;
	private double cost;
	private boolean marked;
	
	//use natural ordering of Strings to sort the 
	//neighbors (edges to neighbors) on destination vertex labels
	TreeMap<String, Edge> adj;	
	
	/**
	 * Instantiates a new Vertex 
	 * @param id  	the ID of the Vertex
	 * @param label		the label of the Vertex
	 */
	public Vertex(int id, String label) {
		this.id = id;
		this.label = label;
		
		cost = INF;
		pred = null;
		marked = false;
		this.adj = new TreeMap<String, Edge>();
	}
	
	/**
	 * Returns the id of this vertex 
	 * @return	Returns the id of this vertex 
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the label of this vertex
	 * @return   Returns the label of this vertex
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Returns the predecessor vertex of this vertex 
	 * @return	Returns the predecessor vertex of this vertex 
	 */
	public Vertex getPred() {
		return pred;
	}
	
	/**
	 * Returns the cost of this vertex 
	 * @return	 	Returns the cost of this vertex 
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Returns whether this Vertex is processed 
	 * (marked, or visited) or not. 
	 * @return 	Returns whether this Vertex is processed 
	 */
	public boolean isMarked() {
		return marked;
	}
	
	/**
	 * Returns the adjacency list (neighbors) as a TreeMap 
	 * of outgoing edges sorted by dst vertex label. 
	 * @return 	Returns the adjacency list
	 */
	public TreeMap<String, Edge> getAdj() {
		return adj;
	}
	
	/**
	 * Resets this Vertex for graph algorithms using the parameters.  
	 * @param mark	 the mark value
	 * @param newCost	the new cost
	 * @param newPred	the new predecessor
	 */
	public void reset(boolean mark, double newCost, Vertex newPred) {
		marked = mark;
		cost = newCost;
		pred = newPred;
	}
	
	/**
	 *  Resets this Vertex for graph algorithms (marked to false, 
	 *  cost to +infinity, pred to null) 
	 */
	public void reset() {
		marked = false;
		cost = INF;
		pred = null;
	}
	
	/**
	 *  Marks this Vertex as processed (or visited; no need to check) 
	 */
	public void mark() {
		marked = true;
	}
	
	/**
	 *  Marks this Vertex as _not_ processed; no need to check 
	 */
	public void unmark() {
		marked = false;
	}
	
	/**
	 *  Adds c to the current cost of this Vertex ('s path) 
	 * @param c 	added cost
	 */
	public void addCost(double c) {
		cost += c;
	}
	
	/**
	 *  Sets the cost of this Vertex ('s path) to c
	 * @param c		new cost
	 */
	public void setCost(double c) {
		cost = c;
	}
	
	/**
	 *  Sets the predecessor vertex of this vertex to p
	 * @param p  the new predecessor
	 */
	public void setPred(Vertex p) {
		pred = p;
	}
	
	/**
	 *  Returns the number of neighbors this vertex 
	 *  has (size of adjacency list) 
	 * @return	 number of neighnors
	 */
	public int nAdj() {
		return adj.size();
	}
	
	/**
	 * Returns the edge connecting this vertex to the Vertex 
	 * labeled dst, or returns null if no such edge exists. 
	 * @param dst	the label of the dst vertex
	 * @return		the target edge, null if not found
	 */
	public Edge getAdj(String dst) {
		for (String key : adj.keySet()) {
			if (key.equals(dst)) {
				return adj.get(key);
			}
		}
		return null;
	}
	
	/**
	 * Returns the information of this vertex in
	 * String format
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s (%d, %d): ", label, id, nAdj()));
		
		(adj.entrySet()).forEach(entry -> {
			Edge e = entry.getValue();
			String lab = entry.getKey();
			sb.append(String.format("%s (%.1f) ", lab, e.getWeight()));
		});
		return sb.toString();
	}
	
	/**
	 * Creates and adds a new Edge with vdst and w 
	 * to this Vertex's neighbor list.
	 * @param vdst	the destination vertex
	 * @param w		the cost from source to destination
	 */
	public void addAdj(Vertex vdst, double w) {
		Edge e = new Edge(this, vdst, w);
		adj.put(vdst.label, e);
	}
	
	/**
	 * Adds the given Edge to this Vertex's neighbor list.
	 * @param e		the edge being added
	 */
	public void addAdj(Edge e) {
		Vertex dst = e.getDst();
		adj.put(dst.label, e);
	}
}








