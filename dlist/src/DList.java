/**
 * @author Barry Tang
 * 
 * Description: 
 *     Simulate a doubly linked list
 * 
 * Date: Feb 7 2023
 */

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class DList<E> implements Iterable<E> {
    
    private DNode<E> head;
    private DNode<E> tail;
    private int      length;
    private Comparator<E> comp;
    
    /**
     * Creates an empty doubly-linked list
     */
    public DList() {
        head = new DNode<E>();
        tail = head;
    }
    
    public DList(Comparator comp) {
        this();
        this.comp = comp;
    }
    
    /**
     * Get the first item in the list
     * @return    the first item of the list
     */
    private DNode<E> getHead() {
        return head.getNext();
    }
    
    /**
     * Set the value of the first item in the list
     * @param head      a DNode
     */
    private void setHead(DNode<E> head) {
        head.setNext(head);
    }
    
    /**
     * Get the last item in the list
     * @return the last item of the list
     */
    private DNode<E> getTail() {
        return tail;
    }

    /**
     * Set the value of the last item in the list
     * @param tail        a DNode
     */
    private void setTail(DNode<E> tail) {
        this.tail = tail;
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
     * @param item    data to add
     */
    public void add(E item) {
        DNode<E> newNode = new DNode<>(item, tail, null);
        tail.setNext(newNode);
        tail = newNode;
        ++length;
    }
    
    /**
     * Inserts the item at index position/ This method should 
     * behave the same way as the add(int index, E item) method
     * in the ArrayList class including error handling.
     * 
     * 
     * @param index     position for new data
     * @param item        data to add
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
     * @param index     the position of the collection of data
     * @param coll    the collection of data to add
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
        DNode<E> ptr1 = getHead();
         head.setNext(null);
         
         DNode<E> temp = null;
        while(ptr1 != null) {
            temp = ptr1;
            ptr1 = ptr1.getNext();
            temp.setNext(null);;
        }
        
        temp = null;
        DNode<E> ptr2 = getTail();
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
     * @param index        the position of the desired data
     * @return the data at the given index
     */
    public E get(int index) {
        if (index < 0 || index >= length) {
            return null;
        }
        
        DNode<E> ptr = getHead();
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
     * @param index        the position of of the target data
     * @param item        the new data
     * @return the data being replaced
     */
    public E set(int index, E item) {
        DNode<E> ptr = getHead();
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
     * @param target    the target data
     * @return whether the list contains the item
     */
    public boolean contains(E target) {
        DNode<E> ptr = getHead();
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
     * @param target    the target data
     * @return index     the index of the target data
     */
    public int indexOf(E target) {
        DNode<E> ptr = getHead();
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
     * @param target    the target data
     * @return index    the last index of the target data
     */
    public int lastIndexOf(E target) {
        DNode<E> ptr = this.getTail();
        E old = null;
        int index = size();
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

    
    /*
     * Homework 2 starts here! :)
     */

    /**
     * If index is valid remove the element at index position
     * and return the removed data
     * Otherwise, throw IndexOutOfBoundsException
     * @param index  position of the data removing
     * @return the value of the removed data
     */
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > length - 1) {
            throw new IndexOutOfBoundsException();
        }
        
        DNode<E> ptr = this.getHead();
        for (int i = 0; i < index; ++i) {
            ptr = ptr.getNext();
        }
        
        E data = ptr.getData();
        
        DNode<E> prev = ptr.getPrev();
        DNode<E> next = ptr.getNext();
        
        prev.setNext(next);
        if (next != null) {
            next.setPrev(prev);
        } else {
            tail = prev;
        }
        
        --length;
        return data;
    }
    
    /**
     * Remove the first occurrence of the target in this list
     * and returns true
     * Otherwise return false
     * 
     * @param target the value of the item removing
     * @return  true or false
     */
    public boolean remove(E target) {
        DNode<E> ptr = this.getHead();
        
        for (int i = 0; i < length; ++i) {
            E data = ptr.getData();
            
            if (data.equals(target)) {
                DNode<E> prev = ptr.getPrev();
                DNode<E> next = ptr.getNext();
                prev.setNext(next);
                
                if (next != null) {
                    next.setPrev(prev);
                } else {
                    tail = prev;
                }
                
                --length;
                return true;
            }
            
            ptr = ptr.getNext();
        }
        
        return false;
    }
    
    @Override
    /**
     * Compare current list with the list passed in the parameter,
     * if the lists have the same length and contains the same 
     * content in each index position, return true
     * 
     * Otherwise, return false
     * 
     * @param obj   the list to compare
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof DList)) {
            return false;
        }
        
        DList<E> other = (DList<E>) obj;
        
        if (other.size() != size()) {
            return false;
        }
        
        DNode<E> ptr1 = getHead();
        DNode<E> ptr2 = other.getHead();
        for (int i = 0; i < length; ++i) {
            if (ptr1.getData() != ptr2.getData()) {
                return false;
            }
            ptr1 = ptr1.getNext();
            ptr2 = ptr2.getNext();
        }
        
        return true;
    }
    
    /**
     * this method should only be called when current list is sorted
     * insert d in the proper location.
     * 
     * @param d  the element to add
     */
    public void addInOrder(E d) {
        DNode<E> newNode = new DNode<>(d);
        DNode<E> ptr = getHead();

        //System.out.println("orderIndicator: " + orderIndicator);

        ptr = getHead();
        int comVal = 0;
        boolean found = false;
        while (!found && ptr != null) {
            comVal = comp.compare(ptr.getData(), d);
            if (comVal >= 0) {
                DNode<E> old = ptr.getPrev();
                ptr.setPrev(newNode);
                newNode.setNext(ptr);
                newNode.setPrev(old);
                old.setNext(newNode);
                found = true;
            } else {
                ptr = ptr.getNext();
            } 
        }
        
        if (ptr == null) {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
    }

    
    @Override
    /**
     * Instantiates and returns a new iterator
     */
    public Iterator<E> iterator() {
        return new DListIterator<>(this);
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
            if (!hasNext()) {
                return null;
            }
            E data = currentNode.getData();
            currentNode = currentNode.getNext();
            return data;
        }
    }
}