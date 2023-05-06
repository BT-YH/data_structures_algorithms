//import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class DList<E> implements Iterable<E> {
	
	private DNode<E> head;
	private DNode<E> tail;
	private int 	 length;
	
	/**
	 * Creates an empty doubly-linked list
	 */
	public DList() {
		head = new DNode<E>();
		tail = head;
	}
	
	/**
	 * Get the first item in the list
	 * @return	the first item of the list
	 */
	public DNode<E> getHead() {
		return head.getNext();
	}
	
	/**
	 * Set the value of the first item in the list
	 * @param head	  a DNode
	 */
	public void setHead(DNode<E> head) {
		head.setNext(head);
	}
	
	/**
	 * Get the last item in the list
	 * @return the last item of the list
	 */
	public DNode<E> getTail() {
		return tail;
	}

	/**
	 * Set the value of the last item in the list
	 * @param tail		a DNode
	 */
	public void setTail(DNode<E> tail) {
		this.tail = tail;
	}
	
	/**
	 * Get the length of the list
	 * @return the length of the list
	 */
	public int getLength() {
		return length;
	}
	
	@Override
	/**
	 * Returns a print-friendly String representation of this list from 
	 * front to back.
	 * @return a String representation of the list
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		DNode<E> ptr = head;
		while (ptr.getNext() != null) {
			ptr = ptr.getNext();
			sb.append(ptr.getData());
			sb.append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Returns a print-friendly String representation of this
	 * list from back to front (reverse order)
	 * @return a String representation of the reverse of the list
	 */
	public String toStringBwd() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		DNode<E> ptr = tail;
		while(ptr.getPrev() != null) {
			sb.append(ptr.getData());
			sb.append(" ");
			ptr = ptr.getPrev();
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Add element to the end of the collection
	 * @param item	data to add
	 */
	public void add(E item) {
		DNode<E> newNode = new DNode<>(item);
		if (this.length == 0) {
			tail = newNode;
			tail.setPrev(head);
			head.setNext(tail);
		} else {
			newNode.setPrev(tail);
			tail.setNext(newNode);
			tail = newNode;
		}
		++length;
	}
	
	/**
	 * Inserts the item at index position/ This method should 
	 * behave the same way as the add(int index, E item) method
	 * in the ArrayList class including error handling.
	 * 
	 * 
	 * @param index 	position for new data
	 * @param item		data to add
	 */
	public void add(int index, E item) throws IndexOutOfBoundsException {
		if (index < 0 || index > length) {
			throw new IndexOutOfBoundsException();
		}
		
		DNode<E> newNode = new DNode<>(item);
		if(this.length == 0) {
			tail = newNode;
			tail.setPrev(head);
			head.setNext(tail);
		} else if (index == length) {
			newNode.setPrev(tail);
			tail.setNext(newNode);
			tail = newNode;
		} else {
			// loop until you find node at index-1
			DNode<E> ptr = head;
			for (int i = 0; i < index; ++i) {
				ptr = ptr.getNext();
			}

			DNode<E> old = ptr.getNext();
			ptr.setNext(newNode); // next pointer of prev node
			newNode.setPrev(ptr); // prev pointer of new node
			newNode.setNext(old); // next pointer of new node
			old.setPrev(newNode); // prev pointer of old node
			// assign pointers
		}
		++length;
	}
	
	/**
	 * Inserts all of the elements in the specified collection
	 * coll into this list at the specified index and returns 
	 * true if index is valid. This method runs in O(M+N)
	 * where M is the size of coll and N is the size of this 
	 * list.
	 * 
	 * if index is not valid, returns false. 
	 * 
	 * @param index 	the position of the collection of data
	 * @param coll	the collection of data to add
	 * @return whether the index is valid
	 */
	public boolean addAll(int index, Collection<E> coll) {
		if (index > length || index < 0) {
			return false;
		}
		
		// add the first item of the collection the list
		// then append the rest of the collection to the 
		// first element then reestablish the links
		Iterator<E> itr = coll.iterator();
		DNode<E> newFirstNode = null;
		DNode<E> old = null;
		DNode<E> ptr = null;
		
		if (itr.hasNext()) {
			newFirstNode = new DNode<>(itr.next());
			length++;
			// loop until you find node at index-1
			ptr = head;
			for (int i = 0; i < index; ++i) {
				ptr = ptr.getNext();
			}

			old = ptr.getNext();
			ptr.setNext(newFirstNode); // next pointer of prev node
			newFirstNode.setPrev(ptr); // prev pointer of new node
			// assign pointers
		} else { 
			return true;
		}
		
		ptr = newFirstNode;
		while(itr.hasNext()) {
			DNode<E> newNode = new DNode<>(itr.next());
			newNode.setPrev(ptr);
			ptr.setNext(newNode);
			ptr = newNode;
			++length;
		}
		
		if (old != null) {
			ptr.setNext(old);
			old.setPrev(ptr);
		} else {
			tail = ptr;
		}
		
		return true;
	}
	
	/**
	 * Removes all data (and associated nodes) from this list
	 */
	public void clear() {
		if (length == 0) 
			return;
		DNode<E> ptr1 = this.getHead();
 		head.setNext(null);
 		
 		DNode<E> temp = null;
		while(ptr1 != null) {
			temp = ptr1;
			ptr1 = ptr1.getNext();
			temp.setNext(null);;
		}
		
		temp = null;
		DNode<E> ptr2 = this.getTail();
		while(ptr2 != null) {
			temp = ptr2;
			ptr2 = ptr2.getPrev();
			temp.setPrev(null);
		}
		length = 0;
	}
	
	/**
	 * If index is valid, returns the data at index.
	 * Otherwise, returns null.
	 * 0-based indexing.
	 * @param index		the position of the desired data
	 * @return the data at the given index
	 */
	public E get(int index) {
		DNode<E> ptr = this.getHead();
		for (int i = 0; i < index; ++i) {
			ptr = ptr.getNext();
		}
		return ptr.getData();
	}
	
	/**
	 * Replaces the data at index with item and returns
	 * the old (replaced) data if index if valid.
	 * Otherwise, returns null. 
	 * 0-based indexing.
	 * @param index		the position of of the target data
	 * @param item		the new data
	 * @return the data being replaced
	 */
	public E set(int index, E item) {
		DNode<E> ptr = this.getHead();
		for (int i = 0; i < index; ++i) {
			ptr = ptr.getNext();
		}
		E old = ptr.getData();
		ptr.setData(item);
		return old;
	}
	
	/**
	 * Returns true if this list contains item - uses
	 * equals() for equality check
	 * @param target	the target data
	 * @return whether the list contains the item
	 */
	public boolean contains(E target) {
		DNode<E> ptr = this.getHead();
		E old = null;
		while(ptr != null) {
			old = ptr.getData();
			if (old.equals(target))
				return true;
			ptr = ptr.getNext();
		}
		return false;
	}
	
	/**
	 * Returns the index of the first occurrence of item.
	 * Returns -1 if this list odes not contain item.
	 * DO Not use contains method
	 * @param target	the target data
	 * @return index 	the index of the target data
	 */
	public int indexOf(E target) {
		DNode<E> ptr = this.getHead();
		E old = null;
		int index = 0;
		while(ptr != null) {
			old = ptr.getData();
			if (old.equals(target))
				return index;
			ptr = ptr.getNext();
			++index;
		}

		return index;
	}
	
	/**
	 * Returns the index of the last occurrence of item.
	 * Returns -1 if this list does not contain item.
	 * @param target	the target data
	 * @return index	the last index of the target data
	 */
	public int lastIndexOf(E target) {
		DNode<E> ptr = this.getTail();
		E old = null;
		int index = this.length;
		while(ptr != null) {
			--index;
			old = ptr.getData();
			if (old.equals(target))
				return index;
			ptr = ptr.getPrev();
		}

		return index;
	}
	
	/**
	 * Returns the number of data elements in this list
	 * @return the length of the list
	 */
	public int size() {
		return length;
	}
	
	/**
	 * Returns true if this list is empty,
	 * false otherwise
	 * @return  whether the list is empty
	 */
	public boolean isEmpty() {
		return length == 0;
	}
	
	@Override
	public Iterator<E> iterator() {
		DListIterator<E> iter = new DListIterator<>(this);
		return iter;
	}
	
	private class DListIterator<E> implements Iterator<E> {
		
		private DNode<E> currentNode; 
		
		public DListIterator(DList<E> list) {
			currentNode = list.getHead();
		}
		
		@Override
		public boolean hasNext() {
			return currentNode != null;
		}

		@Override
		public E next() {
			E data = currentNode.getData();
			currentNode = currentNode.getNext();
			return data;
		}
		
	}
}
