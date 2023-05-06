import java.util.TreeSet;

public class HuffmanTree {
	
	private static HuffmanNodeComparator cmp = new HuffmanNodeComparator();
	private HuffmanNode root;
	
	public HuffmanTree(char[] chars, int[] weights) {
		// make a TreeSet of HuffmanNodes, pass in HuffmanNodeComparator
		TreeSet<HuffmanNode> nodes = new TreeSet<>(cmp);
		
		// initialize the leaf nodes
		for (int i = 0; i < chars.length; i++) {
			TreeSet<Character> trset = new TreeSet<>();
			trset.add(chars[i]);
			nodes.add(new HuffmanNode(trset, weights[i], null, null));
		}
		
		// traverse through the nodes and add new parent nodes
		// based on the huffman algorithm
		while(nodes.size() > 1) {
			HuffmanNode left = nodes.pollFirst();
			HuffmanNode right = nodes.pollFirst();
			
			TreeSet<Character> newData = new TreeSet<>();
			newData.addAll(left.data);
			newData.addAll(right.data);
			int newWeight = left.getWeight() + right.getWeight();
			
			nodes.add(new HuffmanNode(newData, newWeight, left, right));
		}
		
		root = nodes.pollLast();
	}
	
	/**
	 * checks if the tree is empty
	 * @return true if the tree is empty
	 */
	public boolean empty() {
		return root == null;
	}
	
	/**
	 * returns the root of the tree
	 * @return returns the root of the tree
	 */
	public HuffmanNode getRoot() {
		return root;
	}
	
	/**
	 * print the level order of the tree
	 */
	public void levelorder() {
		BTNode.levelorder(root);
	}
}
