import java.util.ArrayList;
import java.util.HashMap;

public class DisjointSet<E> {
	
	private HashMap<E, DSNode> set;
	
	/**
	 * initializes a new DisjointSet by
	 *
     * - creating a hash map of E (type for key) and DSNode (type for value).
     * - for each item in items, create a new DSNode object as a wrapper for 
     *   it and add (item, DSNode object) to the hash map

	 * @param items		the items to be converted to disjoint sets
	 */
	public DisjointSet(Iterable<E> items) {
		set = new HashMap<E, DSNode>();
		for (E item : items) {
			DSNode node = new DSNode(item);
			set.put(item, node);
		}	
	}
	
	/**
	 * An iterative approach that finds the DSNode object 
	 * associated with item in the hash map and 
	 * returns the representative of the set
	 * item belongs to.
	 * @param item  	the target item
	 * @return the representative of the target item
	 */
	public E findRepIter(E item) {
		DSNode node = set.get(item);
		DSNode ptr = node;
		E data = ptr.data;
		E rep = ptr.rep;
		E root = null;
		
		while (!data.equals(rep)) {
			ptr = set.get(rep);
			data = ptr.data;
			rep = ptr.rep;
		}
		
		root = rep;
		
		// path-compress
		ptr = node;
		data = ptr.data;
		while (!data.equals(root)) {
			rep = ptr.rep; 		  // store the old rep
			ptr.rep = root; 	  // assign root as new rep
			ptr.rank = 0;
			ptr = set.get(rep);   // set pointer to the node of the old rep
			data = ptr.data;      // update data
		}
		return root;
	}
	
	/**
	 * A recursive approach that finds the DSNode object 
	 * associated with item in the hash map and returns 
	 * the representative of the set item belongs to.
	 * @param item		the target item
	 * @return the representative of the target item
	 */
	public E findRep(E item) {
		DSNode node = set.get(item);
		E rep = node.rep;
		if (item.equals(rep)) {
			return rep;
		}
		
		rep = findRep(rep);
		node.rep = rep;
		return rep;
	}
	
	/**
	 * performs a union operation on the two sets
	 *  d1 and d2 belong to if necessary:
	 * @param d1	the first set
	 * @param d2	the second set
	 */
	public void union(E d1, E d2) {
		E rep1 = findRep(d1);
		E rep2 = findRep(d2);
		
		if (rep1.equals(rep2)) {
			return;
		}
		
		DSNode node1 = set.get(rep1);
		DSNode node2 = set.get(rep2);
		
		if (node1.rank > node2.rank) {
			node2.rep = rep1;
		} else if (node1.rank < node2.rank) {
			node1.rep = rep2;
		} else {
			node2.rep = rep1;
			node1.rank++;
		}
	}
	
	/**
	 * Represent the set of disjointed sets in String format
	 * @return  the string represetation of the disjointed sets
	 */
	@Override
	public String toString() {
		return set.toString();
	}
	
	/**
	 * represents a disjoint set node
	 * @author tangyi02
	 *
	 */
	private class DSNode {
		E data;
		E rep;
		int rank;
		
		public DSNode(E data) {
			this.data = data;
			rep = data;
			rank = 0;
		}
		
		@Override
		public String toString() {
			return String.format("( %s, %s, %d )", data.toString(), rep.toString(), rank);
		}
	}
	
	public static void main(String[] args) {
		ArrayList<String> letters = new ArrayList<String>();
		letters.add("A");
		letters.add("B");
		letters.add("C");
		letters.add("D");
		letters.add("E");
		letters.add("F");
		letters.add("G");
		letters.add("H");

		DisjointSet<String> dset = new DisjointSet<String>(letters);

		System.out.println("DisjointSet with: " + letters);
		System.out.println(dset);

		System.out.println("\nunion('A', 'B')");
		dset.union("A", "B");
		System.out.println(dset);

		System.out.println("\nunion('B', 'C')");
		dset.union("B", "C");
		System.out.println(dset);

		System.out.println("\nunion('E', 'F')");
		dset.union("E", "F");
		System.out.println(dset);

		System.out.println("\nunion('G', 'H')");
		dset.union("G", "H");
		System.out.println(dset);

		System.out.println("\nunion('C', 'H')");
		dset.union("C", "H");
		System.out.println(dset);

		System.out.println("\nunion('G', 'F')");
		dset.union("G", "F");
		System.out.println(dset);

		System.out.println("\nunion('D', 'H')");
		dset.union("D", "H");
		System.out.println(dset);
	}
}
