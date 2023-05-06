/**
 * StackNode.java
 * 
 * @author tangyi02
 * @param <E>
 *
 */

// Exercise: add iterator
public class StackNode<E> {
	
	private int length;
	private SNode<E> tos;
	
	public StackNode() {
		
	}
	
	// add to the beginning (insert to beginning)
	public void push(E d) {
		tos = new SNode<E>(d, tos);
	}
	
	public E peek() {
		if (tos == null) {
			return null;
		}
		
		return tos.getData();
	}
	
	public E pop() {
		if (tos == null) {
			return null;
		}
		
		E old = tos.getData();
		
		tos = tos.getNext();
		--length;
		
		return old;
	}
}
