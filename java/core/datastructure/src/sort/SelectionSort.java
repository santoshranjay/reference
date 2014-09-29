package sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * the sorting algorithm based on selecting the lowest value card from a pack of
 * cards in hand and placing it to left in order.
 * 
 * Algo - select the min in the list and place it in the leftmost position.
 * 
 * cost- min(list) - n + (n-1) + (n-2) ...+1 traverse the entire list - n worst
 * case - n + (1 + 2 + ...+n) = ~ sq(n) best case (sorted list) - same
 * 
 * book: 
 * Algo - find the smallest item in the array and exchange it with the
 * first entry Selection sort uses ~N2/2 compares and N exchanges to sort an
 * array of length N.
 * 
 * 
 * @author santosh
 * 
 */
public class SelectionSort {

	static <E extends Comparable<E>> List<E> sort(E[] list) {
		// @SuppressWarnings("unchecked")
		// E[] list = (E[])l.toArray();
		for (int i = 0; i < list.length; i++) {
			int minIndex = minIndex(list, i, list.length);
//			E minE = list[minIndex];
//			shift(list, i, minIndex);
			swap(list,i,minIndex);
//			list[i] = minE;
			System.out.println(Arrays.asList(list));
		}
		return Arrays.asList(list);
	}

	private static <E extends Comparable<E>> int minIndex(E[] list, int i,
			int len) {
		int minInd = i;
		E minE = list[i];
		for (int j = i; j < len; j++) {
			if (minE.compareTo(list[j]) > 0) {
				minE = list[j];
				minInd = j;
			}
		}
		return minInd;
	}

	private static <E extends Comparable<E>> void shift(E[] list, int i,
			int minIndex) {
		if (i == minIndex)
			return;
		for (int j = minIndex; j > i; j--) {
			list[j] = list[j - 1];
		}
	}
	
	private static <E extends Comparable<E>> void swap(E[] list, int i,
			int minIndex) {
		if (i == minIndex)
			return;
		E temp = list[i];
		list[i]=list[minIndex];list[minIndex]=temp;
	}

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>(Arrays.asList(1, 5, 3, 0, 7, 2,1,8,2));
		System.out.println(list);
		list = SelectionSort.sort(list.toArray(new Integer[1]));
		System.out.println(list);

	}
}
