package sort;

public class Sort {

	static void bubbleSort(Comparable[] elements){
		//one less every iteration
		int len = elements.length;
		boolean swapped=true;
		while(len>1 && swapped){
			swapped = false;
			for(int i=0,j=1; i<len-1; i++,j++){
			 if(elements[i].compareTo(elements[j]) > 0){swapped=true; Comparable tmp = elements[i]; elements[i]=elements[j];elements[j]=tmp;}
			}
			len--;
		}
	}
	
	public static void main(String[] args) {
		Integer[] i = new Integer[]{5,0};
		bubbleSort(i);
		for (Integer integer : i) {
			System.out.print(integer);
		}
	}
}
