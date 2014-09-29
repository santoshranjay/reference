package algo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Prac {
	public static void replaceWithNextBig(int[] a){
		//boundary condn 
		if(a==null || a.length==1)return;
		Set<Integer> indSet = new HashSet<Integer>();
		for(int i=0; i< a.length-1; i++){
			if(a[i] < a[i+1]){
				replace(a,i,i+1);
				Integer[] indSetA = indSet.toArray(new Integer[0]);
				for(int j=0; j<indSetA.length; j++){
					if(a[j] < a[i+1]){replace(a,j,i+1); indSet.remove(indSetA[j]);}
				}
			}else{indSet.add(i);}
		}
	}
	/**
	 * 7,5,6,2,4,1,2,9,11
	 * 1. compare with the next (till the 2nd last element), 
	 * 1.a. if success replace and compare the previous ones too
	 * 1.b. else add into the nextBugNotFound set (no need to compare the previous ones here, since the compare with previous element was already failed for the elements in this set and the previous element compare failed here (a < b, b < c => a <c)
	 * @param a
	 */
	public static void replaceWithNextBigImp(int[] a){
		//boundary condn 
		if(a==null || a.length==1)return;
		Set<Integer> indSet = new HashSet<Integer>();
		for(int i=0; i< a.length-1; i++){
			if(a[i] < a[i+1]){
				replace(a,i,i+1);
				Iterator<Integer> it = indSet.iterator();
				while(it.hasNext()){
					Integer j = it.next();
					if(a[j] < a[i+1]){replace(a, j, i+1);it.remove();}
				}
			}else{indSet.add(i);}
		}
	}

	private static void replace(int[] a, int i, int j) {
		a[i]=a[j];
	}
	
	
	public static void main(String[] args) throws CloneNotSupportedException {
		int a[] = {7,5,6,2,4,1,2,9,11};//{5,4,3,2,6};//
		replaceWithNextBigImp(a);
		System.out.println(Arrays.toString(a));
		System.out.println(new Prac().clone());
	}
}
