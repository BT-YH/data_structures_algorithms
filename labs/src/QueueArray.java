import java.util.Arrays;

/**
 * FIFO queue
 * 
 * @author tangyi02
 *
 */

// Exercise add iterator


public class QueueArray<E> {
	
	public static final int INIT_CAPACITY = 4;
	
	private E[] queue;
	private int front, back;
	private int length;
	
	public QueueArray() {
		queue = (E[]) new Object[INIT_CAPACITY];
		
		front = back = -1;
	}
	
	public void add(E d) {
		if (isEmpty()) {
			front = 0;
		}
		
		++back;
		
		// must wrap-around
		if (back == queue.length && front > 0) {
			back = 0;
		}
		// must resize and copy if full
		else if (length == queue.length) {
			// first case: front and back are both at the end
			// 1st loop: copy everything from front index to end of array
			E[] old = queue;
			queue = (E[])new Object[queue.length*2];
			
			System.arraycopy(old, front, queue, 0, old.length - front);

			// not using array copy
//			for (int i = front, j = 0; i < queue.length; ++i, ++j) {
//				//queue[i - front] = old[i];
//				queue[j] = old[i];
//			}
			
			// second case: front and back are adjacent
			// straighten out front (to 0), back ()
			if (back <= front) {
				System.arraycopy(old, 0, queue, old.length - front, front);
				front = 0;
				back = old.length;
			}
			
			// 2nd loop: copy from 0 up to back
		}
		
		queue[back] = d;
		++length;
		
	}
	
	public E peek() {
		if (isEmpty()) {
			return null;
		}
		
		return queue[front];
	}
	
	public E poll() {
		if (isEmpty()) {
			return null;
		}
		
		E item = queue[front];
		
		// update front, length
		--length;
		
//		++front;
//		if (front == queue.length) {
//			
//		}
		
		front = (front + 1) % queue.length;
	
		return item;
	}
	
	public int size() {
		return length;
	}
	
	public boolean isEmpty() {
		return (length == 0);
	}
	
	public String toString() {
		return String.format("Queue front=%d back=%d q=%s", front, back, Arrays.toString(queue));
	}
	
	public static void main(String[] args) {
		String str = "ABCDEFGHIJK";
		QueueArray<Character> q1 = new QueueArray<>();
		for (int i = 0; i < str.length(); ++i) {
			q1.add(str.charAt(i));
			System.out.println(q1);
		}
		
		System.out.println();
		System.out.println();
		
		
		QueueArray<Character> q2 = new QueueArray<>();
		q2.add('X');
		q2.add('Y');
		q2.add('Z');
		System.out.println(q2);
		System.out.println(q2.poll() + " " + q2);
		for (int i = 0; i < str.length(); ++i) {
			q2.add(str.charAt(i));
			System.out.println(q2);
		}
	}
}





