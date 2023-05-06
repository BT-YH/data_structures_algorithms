/**
 * SList.java
 * @author tangyi02
 *
 * @param <E>
 * 
 * description: represents a non-headed generic singly linked list
 */

public class SList <E> {
	
	private SNode<E> head;
	private int 	 length;
	
	public SList() {
		head = null;
		length = 0;
	}
	
	/**
	 * adds given data to the beginning of the list
	 * 
	 * @param	d	data to add
	 * 
	 */
	public void addFront(E d) {
		SNode<E> nn = new SNode<E>(d, head);
		head = nn;
		++length;
	}
	
	/**
	 * adds given data to the end of the list
	 * @param d   data to add
	 */
	public void addBack(E d) {
		SNode<E> nn = new SNode<>(d); // new last node

		if (head == null) {
			head = nn;
		} else {
			// find the current last node and make its next point
			// to nn
			SNode<E> ptr = head;
//			for (int i = 0; i < length - 1; ++i) {
//				ptr = ptr.getNext();
//			}
			while (ptr.getNext() != null) {
				ptr = ptr.getNext();
			}

			ptr.setNext(nn);

		}
		++length;
	}
	
	/**
	 * adds given data into given index
	 * 
	 * @param index		position for new data
	 * @param d			data to add
	 */
	public void add(int index, E d) {
		if (index == 0) {
			addFront(d);
		} else if (index == length) {
			addBack(d);
		} else {
			// loop until you find node at index-1
			SNode<E> ptr = head;
			for (int i = 0; i < index - 1; ++i) {
				ptr = ptr.getNext();
			}

			SNode<E> old = ptr.getNext();
			ptr.setNext(new SNode<E>(d));
			ptr.getNext().setNext(old);
			// assign pointers
		}
		++length;
	}
	
	/**
	 * delete the data at given index
	 * @param index
	 */
	public void remove(int index) {
		// implement
	}
	
	/**
	 * removes and returns the first data item
	 * 
	 * 
	 */
	public E removeFront() {
		if (head == null)
			return null;
		
		E old = head.getData();
		head = head.getNext();
		--length;
		
		return old;
	}
	
	
	public E removeBack() {
		SNode<E> ptr = head;
		
		if (length == 1) {
			E old = head.getData();
			head = null;
			--length;
			return old;
		} else if (head == null) {
			return null;
		}
		
		while(ptr.getNext().getNext() != null) {
			ptr = ptr.getNext();
		}
		
		E old = ptr.getNext().getData();
		ptr.setNext(null);
		--length;
		return old;
	}
	
	public E removeBackFor() {
		SNode<E> ptr = head;
		
		if (head == null) {
			return null;
		}
		
		for (int i = 0; i < length - 2; i++) {
			ptr = ptr.getNext();
		}
		
		E old = null;
		
		if (ptr.getNext() == null) {
			old = ptr.getData();
			head = null;
		} else {
			old = ptr.getNext().getData();
			ptr.setNext(null);
		}
		
		--length;
		return old;
	}
	
	public int size() {
		return length;
	}
	
	@Override
	public String toString() {
		String str = "[ ";
//		SNode<E> ptr = head;
//		while (ptr != null) {
//			
//			str += ptr.getData() + " ";
//			
//			// advance ptr to next
//			ptr = ptr.getNext();
//		}
		
		for (SNode<E> ptr = head; ptr != null; ptr = ptr.getNext()) {
			str += ptr.getData() + " ";
		}
		
		str += "]";
		return str;
	}
}




