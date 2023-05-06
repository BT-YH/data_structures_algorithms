
public class SNode<E> {
	private E data;
	
	private SNode<E> next;
	
	public SNode() {
	}
	
	public SNode(E data) {
		this.data = data;
	}
	
	public SNode(E data, SNode<E> next) {
		this.data = data;
		this.next = next;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public SNode<E> getNext() {
		return next;
	}

	public void setNext(SNode<E> next) {
		this.next = next;
	}
	
}
