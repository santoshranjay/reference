package search;


public class BinarySearch {
	
	static <E extends Comparable<E>> int search(E[] list, E e, int st, int end){
		if(st >= end ) {
			return -1;
		}
		int mid = (end-st)/2 + st;
		if(e.compareTo(list[mid]) > 0)return search(list,e,mid+1,end);
		else if(e.compareTo(list[mid]) < 0)return search(list,e,st,mid-1);
		else return mid;
	}

	public static void main(String[] args) {
		System.out.println(search(new Integer[]{1,2,3,4,5,6,8,9},10,0,8));
	}
}
