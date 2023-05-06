/**
 * BTNode.java 
 * 
 * represents a generic Binary Tree node
 * 
 * @author tangyi02
 *
 */
public class BTNode<E> {
	
	E data;
	BTNode<E> left, right;
	
	public BTNode() {}
	
	public BTNode(E d) {
		this(d, null, null);
	}
	
	public BTNode(E d, BTNode<E> l, BTNode<E> r) {
		data = d;
		left = l;
		right = r;
	}
	
	public int countNodes() {
		
		int lCount = 0;
		if (left != null) {
			lCount = left.countNodes();
		}
		
		int rCount = 0;
		if (right != null) {
			rCount = right.countNodes();
		}
		
		return (lCount + rCount + 1);
		
	}
	
	public int countHeight() {
		
		if (left == null && right == null) {
			return 1;
		}
		
		if (left == null) {
			return right.countHeight() + 1;
		} else if (right == null) {
			return left.countHeight() + 1;
		}
		
		return left.countHeight() + 1;
	}
	
	public static <E> int countHeight(BTNode<E> node) {
		if (node == null) {
			return 0;
		}
		
		int lHeight = countHeight(node.left);
		int rHeight = countHeight(node.right);
		
		if (lHeight > rHeight) {
			return (lHeight + 1);
		} else {
			return (rHeight + 1);
		}
	}
	
	public static <E> int countNodes(BTNode<E> node) {
		if (node == null) {
			return 0;
		}
		
		return (countNodes(node.left) + 
				countNodes(node.right) + 1);
	}
	
	// predicates:
	public boolean hasLeft() {
		return (left != null);
	}
	
	public boolean hasRight() {
		return (right != null);
	}
	
	public boolean isLeaf() {
		return (left == null && right == null);
	}
	
	@Override
	public String toString() {
		return (data == null) ? "null" : data.toString();
	}
	
	public static void main(String[] args) {
//		BTNode<String> root = new BTNode<String>("root");
//		for (int i = 0; i < 10; i++) {
//			root = new BTNode<String>("root", new BTNode<String>(i + "l"), new BTNode<String>(i + "r"));
//			
//		}
	}
}
