import java.util.ArrayList;
import java.util.Random;

public class IntMaxHeap {
	
	private ArrayList<Integer> heap;
	
	public IntMaxHeap() {
		heap = new ArrayList<>();
		
	}
	
	private int parentIndex(int ci) {
		return (ci - 1) / 2;
	}
	
	private int LeftIndex(int pi) {
		return 2 * pi + 1;
	}
	
	private int RightIndex(int pi) {
		return 2 * pi + 2;
	}
	
	public void add(int d) {
		heap.add(d);
		int ci = heap.size() - 1;
		int pi = parentIndex(ci);
		
		// upheap
		while (heap.get(ci) > heap.get(pi)) {
			int temp = heap.get(pi);
			heap.set(pi, heap.get(ci));
			heap.set(ci, temp);
			
			ci = pi;
			pi = parentIndex(ci);
		}
	}
	
	
/*
	
	public void add(int d) {
		heap.add(d);
		
		int ci = heap.size() - 1;
		int pi;
		
		while (ci > 0 ) {
			pi = parentIndex(ci);
			
			int cv = heap.get(ci);
			int pv = heap.get(pi);
			
			if (pv < cv) {
			heap.set(ci,pv
		}
		
	}


 */
	
	
	public int peek() {
		if (!heap.isEmpty()) {
			return (int) Double.NaN;
		}
		else {
			return heap.get(0);
		}
	}
	
//	public int poll() {
//		if (heap.isEmpty()) {
//			return (int) Double.NaN;
//		}
//
//
//		int oldVal = heap.get(0);
//		heap.set(0, heap.get(heap.size() - 1));
//		heap.remove(heap.size()-1);
//		int pi = 0;
//
//
//		int li = LeftIndex(pi);
//		int ri = RightIndex(pi);
//
//		while (!(li < heap.size() && ri < heap.size())) {
//			int curr = heap.get(pi);
//			
//		    if (ri > heap.size() && li < heap.size()) {
//				int left = heap.get(li);
//				if (heap.get(li) > curr) {
//					heap.set(pi, left);
//					heap.set(li, curr);
//					pi = li;
//				}
//			} else {
//				int right = heap.get(ri);
//				int left = heap.get(li);
//				if (right > left) {
//					heap.set(pi, right);
//					heap.set(ri, curr);
//					pi = ri;
//				} else {
//					heap.set(pi, left);
//					heap.set(li, curr);
//					pi = li;
//				}
//			}
//			
//			li = LeftIndex(pi);
//			ri = RightIndex(pi);
//		}
//
//
//		return oldVal;
//	}
	
	public boolean isLeaf(int index) {
		return LeftIndex(index) >= heap.size();
	}
	
	public int poll() {
		if (heap.isEmpty()) {
			return (int) Double.NaN;
		}


		int largest = heap.get(0);
		
		int lastElement = heap.remove(heap.size() - 1);
		
		if (heap.isEmpty()) {
			return (int) Double.NaN;
		}
		
		heap.set(0, lastElement);

		int pi = 0;
		int pv = heap.get(pi);
		
		boolean swapped = true;
		while (!isLeaf(pi) && swapped) {
			
			int largerI = LeftIndex(pi);
			int largerV = heap.get(largerI);
			
			
			int ri = RightIndex(pi);
			
			
			if (ri < heap.size()) {
				int rv = heap.get(ri);
				
				if (largerV < rv) {
					largerI = ri;
					largerV = rv;
				}
			}
			
			swapped = false;
			
			if (pv < largerV) {
				heap.set(pi,  largerV);
				heap.set(largerI,  pv);
				
				pi = largerI;
				
				swapped = true;
			}
			
		}


		return largest;
	}
	
	public boolean isEmpty() {
		return heap.isEmpty();
	}
	
	public String toString() {
//		int nLevel = 1;
//		
//		int i = 0;
//		while (true);
		return heap.toString();
	}
	
	public static void main(String[] args) {
		
		Random rand = new Random();
		IntMaxHeap myHeap = new IntMaxHeap();
		
		int n = 10;
		
		for (int i = 0; i < n; ++i) {
			myHeap.add(rand.nextInt(50));
			
			System.out.println(myHeap);
			System.out.println();
		}
		
		System.out.println("Largest: " + myHeap.poll());
		System.out.println("---");
		System.out.println(myHeap);
		System.out.println("---");
		
		System.out.println("Largest: " + myHeap.poll());
		System.out.println("---");
		System.out.println(myHeap);
		System.out.println("---");
		
		
//		while (!myHeap.isEmpty()) {
//			
//			System.out.println("Largest: " + myHeap.poll());
//			System.out.println("---");
//			System.out.println(myHeap);
//			System.out.println("---");
//		}
	}
	
}
