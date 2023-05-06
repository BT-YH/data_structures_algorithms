
public class Edge {
	
	private Vertex src;
	private Vertex dst;
	private double weight;
	
	/**
	 * Instantiate a new edge on a weighted graph
	 * @param vsrc	source vertex
	 * @param vdst	destination vertex
	 * @param w		weigth
	 */
	public Edge(Vertex vsrc, Vertex vdst, double w) {
		src = vsrc;
		dst = vdst;
		weight = w;
	}
	
	/**
	 * Instantiate a new edge on an unweighted graph
	 * @param vsrc	source vertex
	 * @param vdst	destination vertex
	 */
	public Edge(Vertex vsrc, Vertex vdst) {
		this(vsrc, vdst, 1);
	}
	
	/**
	 *  Gets the source vertex of this edge
	 * @return	the source vertex of this edge
	 */
	public Vertex getSrc() {
		return src;
	}
	
	/**
	 * Gets the destination vertex of this edge. 
	 * @return	the destination of this edge
	 */
	public Vertex getDst() {
		return dst;
	}
	
	/**
	 *  Gets the weight of this edge. 
	 * @return	the weight of this edge
	 */
	public double getWeight() {
		return weight;
	}
	
	public void setSrc(Vertex src) {
		this.src = src;
	}

	public void setDst(Vertex dst) {
		this.dst = dst;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
	/**
	 * Compares if two edges are equal
	 * 
	 * @return 	true if equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Edge) {
			Edge target = (Edge) obj;
			Vertex tarSrc = target.getSrc();
			Vertex tarDst = target.getDst();
			
			String curSrcLabel = src.getLabel();
			String curDstLabel = dst.getLabel();
			
			return (curSrcLabel.equals(tarSrc.getLabel())) &&
				   (curDstLabel.equals(tarDst.getLabel())) &&
				   (target.weight == weight);
		}
		return false;
	}
	
	/**
	 * Returns the information of this Edge in a String format.
	 * 
	 * @return the string representation of the edge
	 */
	public String toString() {
		return String.format("%s-%s (%.1f)", src.getLabel(),
				dst.getLabel(), weight);
	}
}
