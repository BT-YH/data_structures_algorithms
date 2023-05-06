import java.util.Iterator;

public class DNode<E>  {

	private E data;
	private DNode<E> next;
	private DNode<E> prev;
	
	public DNode() {
	}
	
	public DNode(E data) {
		this.data = data;
	}
	
	public DNode(E data, DNode<E> prev, DNode<E> next) {
		this.data = data;
		this.prev = prev;
		this.next = next;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public DNode<E> getNext() {
		return next;
	}

	public void setNext(DNode<E> next) {
		this.next = next;
	}
	
	public DNode<E> getPrev() {
		return prev;
	}

	public void setPrev(DNode<E> prev) {
		this.prev = prev;
	}
	
	public String toString() {
		String str = "" + this.getData();
		return str;
		
	}
	
}
