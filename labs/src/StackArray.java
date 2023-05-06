import java.util.Arrays;
import java.util.Iterator;
/**
 * StackArray
 * 
 * generic stack using Array as internal storage
 * @author tangyi02
 *
 */
public class StackArray<E> implements Iterable<E> {
	
	public static final int INIT_CAPACITY = 4;
	
	private int tos;
	private E[] stack;
	
	
	public StackArray() {
		tos = -1;
		stack = (E[])new Object[INIT_CAPACITY];
	}
	
	/**
	 * pushes given data on to the top of the stack
	 * @param d		data to add
	 */
	public void push(E d) {
		if (tos == stack.length - 1) {
			// create a new array of double the current capacity
			E[] temp = (E[])new Object[stack.length*2];
			
			// copy all data to new array
//			for (int i = 0; i < stack.length; ++i) {
//				temp[i] = stack[i];
//			}
			System.arraycopy(stack, 0, temp, 0, stack.length);
			
			stack = temp;
		}
		//stack[tos++] = d; wouldn't work
		stack[++tos] = d;
	}
	
	public E peek() {
		return (tos == -1) ? null : stack[tos];
	}
	
	public E pop() {
		// handle stack underflow
		if (tos == -1) {
			return null;
		}
		
		//E oldData = stack[tos];
		//--tos;
		
		E oldData = stack[tos--];
		return oldData;
	}
	
	public int size() {
		return (tos + 1);
	}
	
	public boolean isEmpty() {
		return (tos == -1);
	}
	
	public String toString() {
		return Arrays.toString(stack);
	}
	
	@Override
	public Iterator<E> iterator() {
		return new StackArrayIterator();
	}
	
	private class StackArrayIterator implements Iterator<E> {
		
		// member variables
		private int currIndex;  // index of next avaiable data item
		
		public StackArrayIterator() {
			currIndex = tos;
		}
		
		@Override
		public boolean hasNext() {
			return (currIndex >= 0);
		}

		@Override
		public E next() {
			if (!hasNext()) {
				return null;
			}
			E item = stack[currIndex];
			--currIndex;
			return item;
		}
		
	}
	
	public static void main(String[] args) {
		StackArray<String> s = new StackArray<>();
		
		String[] data = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
		for (String d : data) {
			s.push(d);
			System.out.println(s);
		}
		
		// implicit iterator
		for (String d : s) {
			System.out.println(d);
		}
		
		System.out.println();
		
		// explicit iterator
		Iterator<String> itr = s.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
}
