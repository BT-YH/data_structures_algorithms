import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;


public class BTree <E> {
	
	int numKeys;
	int degree;
	Comparator<E> cmp;
	BTreeNode<E> root;
	
	/**
	 * Set up and initializes the instance variables
	 * @param cmp	A Comparator
	 * @param deg	the degree of the tree and the node
	 */
	public BTree(Comparator<E> cmp, int deg) {
		this.cmp = cmp;
		this.degree = deg;
		root = new BTreeNode<E>(null, cmp, deg);
	}
	
	
	/**
	 * Looks through the keys in ptr and returns the child to which target might belong.
	 * if ptr is a leaf node, return null
	 * @param <E>
	 * @param ptr
	 * @param target
	 * @param cmp
	 * @return returns the child to which target might belong.
	 * if ptr is a leaf node, return null
	 */
	private static <E> BTreeNode<E> stepDown(BTreeNode<E> ptr, E target, Comparator<E> cmp) {
		if (ptr.children.isEmpty()) {
			return ptr;
		}
		
		for (int i = 0; i < ptr.keys.size(); i++) {
			E currKey = ptr.getKey(i);
			int compVal = cmp.compare(currKey, target);
			if (compVal > 0) {
				return ptr.getChild(i);
			}
		}
		
		return ptr.getLastChild();
	}
	
	/**
	 * Starting from the root node, finds and returns the leaf node to which target
	 * should be added OR belong by repeatedly calling the stepDown method form above
	 * until a leaf node is reached
	 * @param target
	 * @return returns the leaf node to which target
	 * should be added
	 */
	private BTreeNode<E> findLeaf(E target) {
		
		BTreeNode<E> ptr = root;
		while (!ptr.isLeaf()) {
			ptr = stepDown(ptr, target, cmp);
		}
		
		return ptr;
	}
	
	/**
	 * add newKey to the tree
	 * 
	 * @param newKey
	 */
	public void add(E newKey) {
		BTreeNode<E> leaf = findLeaf(newKey);
		BTreeNode<E> parent = leaf.add(newKey);
		
		if (parent != null) {
			root = parent;
		}
		
		numKeys++;
	}
	
	/**
	 * return the content of the tree as String in 
	 * level-order
	 * @return returns a string representation of the tree
	 */
	public String toString() {
		if (root == null) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		Queue<BTreeNode<E>> q = new LinkedList<>();
		q.offer(root);
		
		while (!q.isEmpty()) {
			BTreeNode<E> currNode = q.poll();
			sb.append(currNode.toString() + "   ");
			
			for (BTreeNode<E> child : currNode.children) {
				q.offer(child);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * returns the total number of keys in this tree
	 * @return the total number of keys in this tree
	 */
	public int size() {
		return numKeys;
	}
	
	/**
	 * returns the degree of this tree
	 * @return returns the degree of this tree
	 */
	public int getDegree() {
		return degree;
	}
	
	/**
	 * returns the pointer to the root of this tree
	 * @return returns the pointer to the root of this tree
	 */
	public BTreeNode<E> getRoot() {
		return root;
	}
	
	public static void main(String[] args) {
		
		class IntegerComp implements Comparator<Integer> {

		    public int compare(Integer s1, Integer s2) {
		        return (int) Math.signum(s1-s2);
		    }
		}
		
		class StringComp implements Comparator<String> {

		    public int compare(String s1, String s2) {
		        return s1.compareTo(s2);
		    }
		}
		
		//// Note the following test cases have been
		//// comfirmed:
		//// for int trees: by visualization
		//// for str trees: by hand
		
		/// Integers
		// Test adding Integers, deg 4
		System.out.println("---Testing Integer Trees---");
		BTree<Integer> tree = new BTree<Integer>(new IntegerComp(), 4);
		tree.add(4);
		tree.add(9);
		tree.add(14);
		tree.add(19);
		tree.add(20);
		tree.add(100);
		tree.add(29);
		tree.add(1);
		tree.add(3);
		tree.add(12);
		System.out.println("Tree 1:" + tree);
		// output: [4, 14, 29]  [1, 3]  [9, 12]  [19, 20]  [100]
		
		// Test adding Integers, deg 5
		tree = new BTree<Integer>(new IntegerComp(), 5);
		tree.add(4);
		tree.add(9);
		tree.add(14);
		tree.add(19);
		tree.add(20);
		tree.add(100);
		tree.add(29);
		tree.add(1);
		tree.add(3);
		tree.add(12);
		System.out.println("Tree 2:" + tree);
		// output: [4, 14]  [1, 3]  [9, 12]  [19, 20, 29, 100]  
		
		// Test adding Integers, deg 6
		tree = new BTree<Integer>(new IntegerComp(), 6);
		tree.add(4);
		tree.add(9);
		tree.add(14);
		tree.add(19);
		tree.add(20);
		tree.add(100);
		tree.add(29);
		tree.add(1);
		tree.add(3);
		tree.add(12);
		System.out.println("Tree 3:" + tree);
		// output: [9, 19]  [1, 3, 4]  [12, 14]  [20, 29, 100]   
		
		// Test adding Integers, deg 7
		tree = new BTree<Integer>(new IntegerComp(), 7);
		tree.add(4);
		tree.add(9);
		tree.add(14);
		tree.add(19);
		tree.add(20);
		tree.add(100);
		tree.add(29);
		tree.add(1);
		tree.add(3);
		tree.add(12);
		System.out.println("Tree 4:" + tree);
		// output: [19]  [1, 3, 4, 9, 12, 14]  [20, 29, 100]    
		
		
		/// Strings
		// Test adding Integers, deg 4
		System.out.println("\n---Testing String Trees---");
		BTree<String> stree = new BTree<String>(new StringComp(), 4);
		stree.add("four");
		stree.add("nine");
		stree.add("fourteen");
		stree.add("nineteen");
		stree.add("twenty");
		stree.add("one hundred");
		stree.add("twenty nine");
		stree.add("one");
		stree.add("three");
		stree.add("twelve");
		System.out.println("Tree 1:" + stree);
		// output: [4, 14, 29]  [1, 3]  [9, 12]  [19, 20]  [100]

		// Test adding Integers, deg 5
		stree = new BTree<String>(new StringComp(), 5);
		stree.add("four");
		stree.add("nine");
		stree.add("fourteen");
		stree.add("nineteen");
		stree.add("twenty");
		stree.add("one hundred");
		stree.add("twenty nine");
		stree.add("one");
		stree.add("three");
		stree.add("twelve");
		System.out.println("Tree 2:" + stree);
		// output: [4, 14]  [1, 3]  [9, 12]  [19, 20, 29, 100]  

		// Test adding Integers, deg 6
		stree = new BTree<String>(new StringComp(), 6);
		stree.add("four");
		stree.add("nine");
		stree.add("fourteen");
		stree.add("nineteen");
		stree.add("twenty");
		stree.add("one hundred");
		stree.add("twenty nine");
		stree.add("one");
		stree.add("three");
		stree.add("twelve");
		System.out.println("Tree 3:" + stree);
		// output: [9, 19]  [1, 3, 4]  [12, 14]  [20, 29, 100]   

		// Test adding Integers, deg 7
		stree = new BTree<String>(new StringComp(), 7);
		stree.add("four");
		stree.add("nine");
		stree.add("fourteen");
		stree.add("nineteen");
		stree.add("twenty");
		stree.add("one hundred");
		stree.add("twenty nine");
		stree.add("one");
		stree.add("three");
		stree.add("twelve");
		System.out.println("Tree 4:" + stree);
		// output: [19]  [1, 3, 4, 9, 12, 14]  [20, 29, 100]    
	}
}
