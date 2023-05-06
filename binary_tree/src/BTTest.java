import java.util.ArrayList;

public class BTTest {
	
	public static void main(String[] args) {
		
		// 9 nodes
		
		// level 4
		BTNode<String> nodeI = new BTNode<>("I");
		
		// level 3
		BTNode<String> nodeG = new BTNode<>("G");
		BTNode<String> nodeH = new BTNode<>("H", null, nodeI);
		
		// level 2
		BTNode<String> nodeD = new BTNode<>("D");
		BTNode<String> nodeE = new BTNode<>("E", nodeG, null);
		BTNode<String> nodeF = new BTNode<>("F", nodeH, null);
		
		// level 1
		BTNode<String> nodeB = new BTNode<>("B", nodeD, nodeE);
		BTNode<String> nodeC = new BTNode<>("C", null, nodeF);
		
		// level 0
		BTNode<String> nodeA = new BTNode<>("A", nodeB, nodeC);
		
		
		ArrayList<BTNode<String>> tree = new ArrayList<>();
		tree.add(nodeA);
		tree.add(nodeB);
		tree.add(nodeC);
		tree.add(nodeD);
		tree.add(nodeE);
		tree.add(nodeF);
		tree.add(nodeG);
		tree.add(nodeH);
		tree.add(nodeI);
		
		// countNodes, countHeight
		for (BTNode<String> node : tree) {
			System.out.printf("data=%s nNodes1=%d nNodes2=%d height1=%d height2=%d\n", 
							  node, node.countNodes(), BTNode.countNodes(node),
							  node.countHeight(), BTNode.countHeight(node));
		}
		
		System.out.printf("preorder(A): %s\n", BTUtil.toStringPre(nodeA));
		System.out.printf("postorder(A): %s\n", BTUtil.toStringPost(nodeA));
		System.out.printf("levelorder(A): %s\n", BTUtil.toStringLevel(nodeA));
		
	}
	
	
}
