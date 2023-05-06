import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class HuffmanTreeAL {
	
	private static HuffmanNodeComparator cmp = new HuffmanNodeComparator();
	
	private HuffmanNode root;
	
	public HuffmanTreeAL(char[] chars, int[] weights) {
		ArrayList<HuffmanNode> nodes = new ArrayList<>();
		for (int i = 0; i < chars.length; i++) {
			TreeSet<Character> trset = new TreeSet<>();
			trset.add(chars[i]);
			nodes.add(new HuffmanNode(trset, weights[i], null, null));
		}
		
		Collections.sort(nodes, cmp);
		
		for (int i = 0; i < nodes.size() - 1; i += 2) {
			@SuppressWarnings("unchecked")
			TreeSet<Character> newData = (TreeSet<Character>) 
					nodes.get(i).getData().clone();
			newData.addAll(nodes.get(i + 1).getData()); 
			int newWeight = nodes.get(i).getWeight() + nodes.get(i + 1).getWeight();
			HuffmanNode newNode = new HuffmanNode(newData, newWeight, nodes.get(i), nodes.get(i+1));
			
			int index = findInsertPos(nodes, newNode);
			nodes.add(index, newNode);
		}
		
		root = nodes.get(nodes.size() - 1);
	}
	
	private static int findInsertPos(ArrayList<HuffmanNode> list, HuffmanNode node) {
		if (list.isEmpty()) { // when the list is empty
			return 0;
		}
		
		for (int i = 0; i < list.size(); i++) {
			if (cmp.compare(list.get(i), node) > 0) {
				return i; // insert at the first encounter of a larger node
			} 
			
		}
		
		return list.size(); // when node is the biggest node
	}
	
	public boolean empty() {
		return root == null;
	}
	
	public HuffmanNode getRoot() {
		return root;
	}
	
	public void levelorder() {
		BTNode.levelorder(root);
	}
}
