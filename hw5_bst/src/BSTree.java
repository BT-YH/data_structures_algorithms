import java.util.Comparator;

/**
 * @Author: Barry Tang
 * @Date: Mar 13, 2023
 * @Description:
 * 	represents a Binary Search Tree
 * 
 */

public class BSTree<E> {
	
	BTNode<E> root;
	private Comparator<E> cmp;
	private int size;
	
	public BSTree(Comparator<E> cmp) {
		this.cmp = cmp;
		root = null;
	}
	
	/**
	 * Adds data to the tree
	 * @param data 	 the data added
	 */
	public void add(E data) {
		
		BTNode<E> newNode = new BTNode<>(data);
		if (root == null) {
			root = newNode;
			++size;
			return;
		}
		
		BTNode<E> ptr = root;
		
		// get the node where the new node is to be added
		while (cmp.compare(ptr.data, data) <= 0 && ptr.hasLeft() ||
			   cmp.compare(ptr.data, data) > 0 && ptr.hasRight()) {
			
			if (cmp.compare(ptr.data, data) <= 0)
				ptr = ptr.left;
			else 
				ptr = ptr.right;
			
		}
		
		// add the new node
		if (cmp.compare(ptr.data, data) <= 0 ) {
			ptr.left = newNode;
		} else {
			ptr.right = newNode;
		}
		++size;
	}
	
	/*
	 BTNode<E> ptr = root;
	 		parent = null;
	 
	 int c = -1;
	 
	 while (ptr != null) {
	 	c = cmp.compare(d, ptr.data);
	 	parent = ptr;
	 	if (c <= 0) {
	 		ptr = ptr.left;
	 	} else {
	 		ptr = ptr.right;
	 	}
	 }
	 
	 ++size;
	 
	 if (parent == null) {
	 	root = newNode;
	 	return;
	 }
	 
	 if (c <= 0) {
	 	parent.left = newNode;
	 } else {
	 	parent.right = newNode;
	 }
	 */
	
	/**
	 * returns true if there is at least one data matching target in 
	 * the tree rooted at the given node
	 * @param target 	 the target data
	 * @param node 	 the root node of the current subtree
	 * @return returns true if there is at least one data matching target in 
	 * the tree rooted at the given node
	 */
	private boolean contains(E target, BTNode<E> node) {
		if (node == null) {
			return false;
		}
		
		E currData = node.data;
		
		if (currData.equals(target)) 
			return true;
		else if (cmp.compare(currData, target) < 0) 
			return contains(target, node.left);
		else 
			return contains(target, node.right);
	}
	
	/**
	 * returns true if there is at least one data matching target
	 * in the tree
	 * @param target	 the target data
	 * @return returns true if there is at least one data matching target
	 * in the tree
	 */
	public boolean contains(E target) {
		return contains(target, root);
	}
	
	/**
	 * removes the successor (largest data) node for the given node
	 * from its leftChild and returns the largest data
	 * @param <E>
	 * @param node 	 the root node of the subtree
	 * @param leftChild 	 the left child 
	 * @return the largest data
	 */
	private static <E> E removeSuccessor(BTNode<E> node, BTNode<E> leftChild) {
		
		BTNode<E> ptr = leftChild;
		BTNode<E> trail = node;
		while(ptr.hasRight()) {
			trail = ptr;
			ptr = ptr.right;
		}
		
		E data = ptr.data;
		
		if (trail==node)
			trail.left = ptr.left;
		else
			trail.right = ptr.left;
		return data;
	}
	
	/**
	 * removes the first occurrence of target from this tree and returns the 
	 * removed value or null if target does not exist in this tree
	 * @param target 	 the target node
	 * @return returns the removed data
	 */
	public E remove (E target) {
		E currData = null;
		
		if (size == 0) {
			return currData;
		}
		
		BTNode<E> ptr = root;
		BTNode<E> trail = null;
		boolean found = false;
		
		
		while(ptr != null && !found) {
			currData = ptr.data;
			int c = cmp.compare(currData, target);
			if (c == 0) {
				found = true;
				
				if (!ptr.hasLeft()) { //the ptr does not have left child
					
					if (ptr.hasRight()) { // the ptr has right child
						
						if (ptr==root) // the ptr is the root
							root = ptr.right;
						else if (trail.left==ptr) // ptr is left child
							trail.left = ptr.right;
						else // ptr is right child
							trail.right = ptr.right;
						
					} else { // ptr has no child
						
						if (ptr==trail.left)
							trail.left = null;
						else
							trail.right = null;
					}
				} else {
					
					E maxData = removeSuccessor(ptr, ptr.left);
					ptr.data = maxData;
				}
				
			} else {
				trail = ptr;
				if (cmp.compare(currData, target) < 0) {
					ptr = ptr.left;
				} else {
					ptr = ptr.right;
				}
			}
			
		}
		
		if (!found)
			return null;
		
		--size;
		return currData;
	}
	
	/**
	 * returns the largest data element in this tree.
	 * @return returns the largest data
	 */
	public E getMax() {
		if (root == null) {
			return null;
		}
		
		
		BTNode<E> ptr = root;
		while(ptr.hasRight()) {
			ptr = ptr.right;
		}
			
		return ptr.data;
	}
	
	/**
	 * returns the smallest data element in the tree starting
	 * from node
	 * @param <E>
	 * @param node	 root node of the current subtree
	 * @return the smallest data
	 */
	private static <E> E getMin(BTNode<E> node) {
		
		if (!node.hasLeft())
			return node.data;
		
		return getMin(node.left);
	}
	
	/**
	 * returns the smallest data element in this tree.
	 * 
	 * @return returns the smallest data element in this tree
	 */
	public E getMin() {
		return getMin(root);
	}
	
	/**
	 * returns the number of data items in this tree.
	 * @return returns the size of the tree
	 */
	public int size() {
		return size;
	}
	
	/**
	 * return true if the tree is empty
	 * @return true if the tree is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * clears the tree
	 */
	public void clear() {
		root = null;
		size = 0;
	}
	
	/**
	 *  returns a print-friendly String with entire content of 
	 *  this tree in level-order
	 */
	public String toString() {
		String str = BTUtil.toStringLevel(root);
		str = "[ " + str.trim() + " ]";
		return str;
	}
	
	/**
	 *  returns a print-friendly String with entire content of 
	 *  this tree in in-order
	 */
	public String toStringIn() {
		String str = BTUtil.toStringIn(root);
		str = "[ " + str.trim() + " ]";
		return str;
	}
	
	/**
	 *  returns a print-friendly String with entire content of
	 *   this tree in pre-order
	 */
	public String toStringPre() {
		String str = BTUtil.toStringPre(root);
		str = "[ " + str.trim() + " ]";
		return str;
	}
	
	/**
	 *  returns a print-friendly String with entire content of
	 *   this tree in post-order
	 */
	public String toStringPost() {
		String str = BTUtil.toStringPost(root);
		str = "[ " + str.trim() + " ]";
		return str;
	}
}
