import java.util.ArrayList;
import java.util.Comparator;

public class BTreeNode <E> {
	
	int degree;
	// int depth;
	ArrayList<E> keys;
	ArrayList<BTreeNode<E>> children;
	Comparator<E> cmp;
	BTreeNode<E> parent;
	
	
	public BTreeNode(BTreeNode<E> p, Comparator<E> cmp, int deg) {
		this.degree = deg;
		this.cmp = cmp;
		parent = p;
		//depth = parent.depth + 1;
		
		keys = new ArrayList<E>(deg - 1);
		children = new ArrayList<BTreeNode<E>>(deg);
	}
	
	private <T> int getLastIndex(ArrayList<T> list) {
		if (list.isEmpty()) {
			return 0;
		}
		return list.size()-1;
	}
	
	/**
	 * returns the data(keys) of this node as a String
	 * @return returns the data(keys) of this node as a String
	 */
	public String toString() {
		return keys.toString().replace(",", "  ");
	}
	
	/**
	 * returns true if this node is a leaf node
	 * @return returns true if this node is a leaf node
	 */
	public boolean isLeaf() {
		return children.isEmpty();
	}
	
	/**
	 * returns true if there is no key in this node
	 * @return returns true if there is no key in this node
	 */
	public boolean isEmpty() {
		return keys.isEmpty();
	}
	
	/**
	 * returns the child of this node at index,
	 * if no child at index, return null
	 * @param index
	 * @return returns the child of this node at index,
	 * if no child at index, return null
	 */
	public BTreeNode<E> getChild(int index) {
		if (index < 0 || index >= children.size())
			return null;
		return children.get(index);
	}
	
	/**
	 * returns the first child of this node
	 * null otherwise
	 * @return returns the first child of this node
	 * null otherwise
	 */
	public BTreeNode<E> getFirstChild() {
		return children.get(0);
	}
	
	/**
	 * returns the last child of this node
	 * null otherwise
	 * @return returns the last child of this node
	 * null otherwise
	 */
	public BTreeNode<E> getLastChild() {
		
		int index = getLastIndex(children);
		
		return children.get(index);
	}
	
	/**
	 * returns the number of keys
	 * @return returns the number of keys
	 */
	public int nKeys() {
		return keys.size();
	}
	
	/**
	 * returns the number of children
	 * @return returns the number of children
	 */
	public int nChildren() {
		return children.size();
	}
	
	/**
	 * returns the key at index
	 * @param index
	 * @return returns the key at index
	 */
	public E getKey(int index) {
		if (index < 0 || index >= keys.size())
			return null;
		return keys.get(index);
	}
	
	/**
	 * returns the key at index
	 * @param index
	 * @return returns the key at index
	 */
	public E getFirstKey() {
		return keys.get(0);
	}
	
	/**
	 * returns the last key of this node
	 * null otherwise
	 * @return  returns the last key of this node
	 * null otherwise
	 */
	public E getLastKey() {
		
		int index = getLastIndex(keys);
		
		return keys.get(index);
	}
	
	/**
	 * inserts new child at index if index is valid for insertion
	 * if index invalid, do nothing
	 * @param index
	 * @param newChild
	 */
	public void addChild(int index, BTreeNode<E> newChild) {
		if (0 <= index && index <= children.size()) {
			children.add(index, newChild);
			newChild.parent = this;
		}
	}
	
	/**
	 * add child to the begininng of the list
	 * @param newChild
	 */
	public void addFirstChild(BTreeNode<E> newChild) {
		children.add(0, newChild);
		newChild.parent = this;
	}
	
	/**
	 * add child to the last of the list
	 * @param newChild
	 */
	public void addLastChild(BTreeNode<E> newChild) {
		children.add(newChild);
		newChild.parent = this;
	}
	
	/**
	 * replaces the key at index with value if index is valid
	 * else do nothing
	 * @param index
	 * @param value
	 */
	public void setKey(int index, E value) {
		if (0 <= index && index <= keys.size()) {
			keys.set(index, value);
		}
	}
	
	/**
	 * inserts value at index if index is valid
	 * else do nothing
	 * @param index
	 * @param value
	 */
	public void addKey(int index, E value) {
		if (0 <= index && index <= keys.size()) {
			keys.add(index, value);
		}
	}
	
	/**
	 * inserts value to start of keys list
	 * @param value
	 */
	public void addFirstKey(E value) {
		keys.add(0, value);
	}
	
	/**
	 * inserts value to end of keys list
	 * @param value
	 */
	public void addLastKey( E value) {
		keys.add(value);
	}
	
	/**
	 * removes and returns key at index
	 * @param index
	 * @return removes and returns key at index
	 */
	public E removeKey(int index) {
		if (0 <= index && index < keys.size()) {
			E val = keys.remove(index);
			return val;
		}
		return null;
	}
	
	/**
	 * removes and returns first key
	 * @param index
	 * @return removes and returns first key
	 */
	public E removeFirstKey() {
		return keys.remove(0);
	}
	
	/**
	 * removes and returns the last key
	 * @return removes and returns the last key
	 */
	public E removeLastKey() {
		int index = getLastIndex(keys);
		return keys.remove(index);
	}
	
	/**
	 * removes and returns child at index
	 * @param index
	 * @return removes and returns child at index
	 */
	public BTreeNode<E> removeChild(int index) {
		if (0 <= index && index < children.size()) {
			BTreeNode<E> node = children.remove(index);
			return node;
		}
		return null;
	}
	
	/**
	 * removes and return first child
	 * @return removes and return first child
	 */
	public BTreeNode<E> removeFirstChild() {
		if (isLeaf()) {
			return null;
		}
		return children.remove(0);
	}
	
	/**
	 * removes and return last child
	 * @return removes and return last child
	 */
	public BTreeNode<E> removeLastChild() {
		if (isLeaf()) {
			return null;
		}
		int index = getLastIndex(children);
		return children.remove(index);
	}
	
	/**
	 * returns the index at which new_key should be inserted 
	 * in keys arraylist
	 * @param new_key
	 * @return returns the index at which new_key should be inserted 
	 * in keys arraylist
	 */
	public int findInsertPos(E new_key) {
		
		for (int i = 0; i < keys.size(); i++) {
			if (cmp.compare(keys.get(i), new_key) > 0) {
				return i;
			} 
			
			if (i == keys.size() - 1) { // when new_key is the biggest element
				return i + 1;
			}
		}
		
		return 0; // when the keys is empty
	}
	
	/**
	 * returns the index in which target is found in this node's key arraylist
	 * or -1 if not found
	 * @param target
	 * @return returns the index in which target is found in this node's key arraylist
	 * or -1 if not found
	 */
	public int indexOf(E target) {
		return binarySearch(keys, target);
	}
	
	/**
	 * returns the index in which child is found in this node's children
	 * arraylist or -1 if not found
	 * @param child
	 * @return returns the index in which child is found in this node's children
	 * arraylist or -1 if not found
	 */
	public int indexOf(BTreeNode<E> child) {
		return children.indexOf(child);
	}
	
	/**
	 * returns true if this node has target in 
	 * its keys
	 * @return returns true if this node has target in 
	 * its keys
	 */
	public boolean contains(E target) {
		
		return binarySearch(keys, target) != -1;
	}
	
	/**
	 * returns true if there is an overflow in 
	 * this node
	 * @return returns true if there is an overflow in 
	 * this node
	 */
	public boolean isOverflow() {
		return keys.size() > degree-1;
	}
	
	/**
	 * should only be called in add(E)
	 * 
	 */
	private BTreeNode<E> split() {
		
		BTreeNode<E> right = new BTreeNode<E>(parent, cmp, degree);
		
		int midIndex = keys.size()/2;
		
		//re-arranging extra keys
		while (midIndex + 1 < keys.size()) {
			right.addLastKey(removeKey(midIndex + 1));
		}
		
		
		while (midIndex + 1 < children.size()) {
			right.addLastChild(removeChild(midIndex + 1));
		}
		
		if (parent == null) {
			BTreeNode<E> newParent = new BTreeNode<E>(null, cmp, degree);
			newParent.addLastKey(removeKey(midIndex));
			newParent.addLastChild(this);
			newParent.addLastChild(right);
			parent = newParent;
			right.parent = newParent;
			return newParent;
		} 
		else { 
			E newKey = removeKey(midIndex);
			parent.addKey(parent.findInsertPos(newKey), newKey);
			int index = parent.indexOf(this) + 1;
			parent.addChild(index, right);
			
			if (parent.isOverflow()) {
				return parent.split();
			}
		}
		
		return null;
	}
	
	/**
	 * adds k to this node's key list in the right place
	 * returns null if there is no overflow after adding
	 * other wise:
	 * @param k		value to add
	 * @return adds k to this node's key list in the right place
	 * returns null if there is no overflow after adding
	 * other wise:
	 */
	public BTreeNode<E> add(E k) {
		int index = findInsertPos(k);
		addKey(index, k);
		if (isOverflow()) {
			return split();
		}
		
		return null;
	}
	
	private int binarySearch(ArrayList<E> list, E target) {
		int left = 0;
		int right = list.size() - 1;
		int ptr = -1;
		
		while  (left <= right) {
			ptr = (left + right) / 2;
			
			if (cmp.compare(list.get(ptr), target) < 0) {
				left = ptr + 1;
			} else if (cmp.compare(list.get(ptr), target) > 0) {
				right = ptr - 1;
			} else {
				return ptr;
			}
		}
		
		return -1; // not found
	}
	

	public static void main(String[] args) {
		
		class StringComp implements Comparator<String> {

			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
			
		}
		
		BTreeNode<String> node = new BTreeNode<String>(null, new StringComp(), 3);
		node.addLastKey("love");
		int index = node.findInsertPos("adore");
		node.addKey(index, "adore");
		index = node.findInsertPos("rejoice");
		node.addKey(index, "rejoice");
		
		
		System.out.println(node.toString());
		System.out.println(node.contains("love"));
		System.out.println(node.contains("adore"));
		System.out.println(node.contains("rejoice"));
		System.out.println(node.contains("rejoic"));
		System.out.println(node.indexOf("love"));
		System.out.println(node.indexOf("adore"));
		System.out.println(node.indexOf("rejoice"));
		System.out.println(node.indexOf("rejoic"));
		
		System.out.println(node.isOverflow());
		BTreeNode<String> parent = node.split();
		System.out.println(parent);
		System.out.println(parent.children.toString());
		System.out.println(parent.getFirstChild());
		System.out.println(parent.getLastChild());
		
		parent.add("good");
		parent.add("grace");
		System.out.println(parent);
		System.out.println(parent.children.toString());
		System.out.println(parent.getFirstChild());
		System.out.println(parent.getLastChild());
	}
}










