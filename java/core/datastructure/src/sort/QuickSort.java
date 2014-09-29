package sort;

public class QuickSort {

	public static <E extends Comparable<E>> E[] sort(E[] list){
		if(list.length==1)return list;
		E pivot = list[0];
		E[] part1 = less(list[0],list);
		E[] part2 = greater(list[0],list);
		return concat(sort(part1), pivot, sort(part2));
	}
	
	//TODO
	private static <E extends Comparable<E>> E[] less(E e, E[] list){
		return null;
	}
	//TODO
	private static <E extends Comparable<E>> E[] greater(E e, E[] list){
		return null;
	}
	//TODO
	private static <E extends Comparable<E>> E[] concat(E[] llist, E e, E[] rlist){
		return null;
	}
}

