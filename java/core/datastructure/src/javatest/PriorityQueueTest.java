package javatest;

import java.util.Arrays;
import java.util.PriorityQueue;

public class PriorityQueueTest {
	public static void main(String[] args) {
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		heap.addAll(Arrays.asList(1,5,6,4,2,7,9,0));
		System.out.println(heap);
		Object[] a = heap.toArray();
		while(!heap.isEmpty()){
			System.out.println(heap.poll());
			System.out.println(heap);
		}
	}
}
