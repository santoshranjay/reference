package sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Algo:
 * Take one element at a time and insert it to the proper place in the left side of already sorted element list.
 * [3 4 7 1 6 8]
 * [3 4 7 1 6 8] - start from 2nd element (since 1st element, 1 array element is always sorted)
 * [3 4 7 1 6 8] - 7 not lesser than 4, break here, no need to compare further since (3,4 is already sorted and if 7 is not lesser than 4, it means it isn't lesser than 3 too)
 * [3 4 7 1 6 8] - 1 , lesser than 7 swap , next - lesser than 4 swap, next lesser than 3 swap,
 * [1 3 4 7 6 8] - 6, lesser than 7 swap, next - not lesser than 4, break here.
 * [1 3 4 6 7 8]
 * 
 * Book:
 * consider the elements one at a time, inserting each into its proper place among those already considered (keeping them sorted).
 * Unlike that of selection sort, the running time of insertion sort depends on the initial order of the items in the input.
 * For example, if the array is large and its entries are already in order (or nearly in order), then insertion sort is much, much faster than if the entries 
 * are randomly ordered or in reverse order.
 * Insertion sort uses ~N2/4 compares and ~N2/4 exchanges to sort a randomly ordered array of length N with distinct keys, on the average.
 * The worst case is ~N2/2 compares and ~N2/2 exchanges and 
 * The best case is N − 1 compares and 0 exchanges.

 *
 */
public class InsertionSort {

	public static <E extends Comparable<E>> void sort(E[] list){
		if(list.length==1)return;
		for (int i = 1; i < list.length; i++) {
			compareAndExch(list,i);
		}
	}
	
	private static <E extends Comparable<E>> void compareAndExch(E[] list, int ind){
		for (int l = ind-1, r= ind; l >= 0; l--,r--) {
			if(list[l].compareTo(list[r]) > 0){
				E tmp = list[l];
				list[l] = list[r]; 
				list[r] = tmp;
			}else return;
		}
	}
	//test
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>(Arrays.asList(1, 5, 3, 0, 7, 2,1,8,2));
		System.out.println(list);
		Integer[] a = list.toArray(new Integer[1]);
		InsertionSort.sort(a);
		System.out.println(Arrays.asList(a));

	}
	
}