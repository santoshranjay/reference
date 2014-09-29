package ds;

/*
 * priority queue (heap) 
 * properties
 * ----------
 * 1. element in a node is always max/min (in order)  from all of the elements in children nodes.
 * 2. no ordering in sibling nodes.
 * 
 * The priority queue is maintained in a heap-ordered complete binary tree in the array pq[] with pq[0] unused 
 * and the N keys in the priority queue in pq[1] through pq[N]. To implement insert(), we increment N, 
 * add the new element at the end, then use swim() to restore the heap order. 
 * For delMax(), we take the value to be returned from pq[1], then move pq[N] to pq[1], decrement the size of the heap,
 *  and use sink() to restore the heap condition. 
 *  We also set the now-unused position pq[N+1] to null to allow the system to reclaim the memory associated with it.
 *  Algo:
 *  insert(E e){
 *  	array[++N]=e;
 *  	shiftUp(N); //restore heap order (swim / heapify)
 *  }
 *  getMax(){
 *  	max = array[1];
 *  	exch(1,N);
 *  	nullify/release(N);
 *  	shiftDown(1); // restore heap order (sink / heapify)
 *  }
 *  
 */
public class PriorityQueue<E extends Comparable<E>> {
	private E[] queue;
	private int size;
	private static final int INITIAL_SIZE = 10;
	
	@SuppressWarnings("unchecked")
	public PriorityQueue() {
		size = 0;
		queue =(E[])new Object[INITIAL_SIZE];
	}
	
	public synchronized void insert(E e){
		ensureCapcity(queue);
		queue[++size] = e;
		shiftUp(size);
	}
	
	//TODO
	private void ensureCapcity(E[] queue2) {
		// TODO Auto-generated method stub
		
	}

	public synchronized E deletePriority(){
		if(size==0)return null;
		E e = queue[1];
		swap(1,size);
		shiftDown(1);
		ensureCapcity(queue);
		return e;
	}
	
	
	void shiftUp(int n){
		while(n >1 && less(n/2,n)){
			swap(n/2,n);
			n = n/2;
		}
	}
	
	private void swap(int i, int n) {
		E tmp = queue[i];
		queue[i] = queue[n];
		queue[n] = tmp;
	}

	private boolean less(int i, int n) {
		return queue[i].compareTo(queue[n]) < 0 ;
	}

	void shiftDown(int n){
		while(2*n <= queue.length){
			
		}
		
	}
	
}
