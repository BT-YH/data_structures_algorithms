import java.util.LinkedList;
import java.util.Queue;

/**
 * BTUtil
 * 
 * collection of Binary Tree utility methods
 * @author tangyi02
 *
 */

public class BTUtil {
	
	public static String toStringPre(BTNode node) {
		if (node == null) {
			return "";
		}
		
		String left = toStringPre(node.left);
		String right = toStringPre(node.right);
		
//		return (node + " " +  left + " " + right);
		// string replace multiple spaces wit a single space
		// regular expression
		return (node + (left.equals("") ? "" : " " +  left) + 
				(right.equals("") ? "" : " " + right));
	}
	
	public static String toStringPost(BTNode node) {
		if (node == null) {
			return "";
		}
		
		String left = toStringPost(node.left);
		String right = toStringPost(node.right);
		
		return ((left.equals("") ? "" : left + " ") + 
				(right.equals("") ? "" : right + " ") + node);
	}
	
	public static String toStringIn(BTNode node) {
		if (node == null) {
			return "";
		}
		
		String left = toStringIn(node.left);
		String right = toStringIn(node.right);
		
		return ((left.equals("") ? "" : left + " ") + node +
				(right.equals("") ? "" : " " + right));
	}
	
	public static String toStringLevel(BTNode node) {
		if (node == null) {
			return "";
		}
		
		//Breadth-First Search
		Queue<BTNode> q = new LinkedList<>(); // queue in java is an interface
		q.add(node);
		
		String str = "";
		
		while (!q.isEmpty()) {
			BTNode curr = q.poll();
			
			str += curr + " ";
			
			if (curr.hasLeft()) {
				q.add(curr.left);
			}
			
			if (curr.hasRight()) {
				q.add(curr.right);
			}
		}
		
		return str;
	}
}
