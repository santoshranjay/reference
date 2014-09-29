package sort;

import java.util.Arrays;

public class MergeSort {
	
	public static <E extends Comparable<E>> E[] sort(E[] list, int lo, int hi){
		if(lo==hi)return list;
		int l=list.length-1; int m = (0+l)/2;
		E[] list1 = sort(Arrays.copyOfRange(list, 0, m),0,m);
		E[] list2 = sort(Arrays.copyOfRange(list, m, l),0,l-m);
		return mergeSorted(list1, list2);
	}
	
	static <E extends Comparable<E>> E[] mergeSorted(E[] list1, E[] list2){
		@SuppressWarnings("unchecked")
		E[] list = (E[])new Object[list1.length+list2.length];
		for(int i=0,j=0,k=0; i<(list1.length) || j<list2.length; k++){
			if(list1[i].compareTo(list2[j]) > 0){list[k]=list1[i];i++;}
			else {list[k]=list2[j];j++;}
		}
		return list;
	}
	
	public static void main(String[] args) {
		Integer[] a = sort(new Integer[]{5,4,3,1,2}, 0, 4);
		for (Integer integer : a) {
			System.out.println(integer);
		}
	}

}
