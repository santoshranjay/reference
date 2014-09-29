package algo;

public class RecursiveAlgo {
	static StringBuffer sb = new StringBuffer("01");
	
	//fibonacci
	static int f(int n){
		if(n==0 || n==1)return n;
		else return f(n-1) + f(n-2);
	}


	//factorial
	static int fact(int n){
		if(n==1) return n;
		return n*fact(n-1);
	}
	
	//reverse a string recursively
	static String reverseRec(String str){

		if(str.length()==1)return str;
		return str.charAt(str.length()-1) + reverseRec(str.substring(0,str.length()-1));
		
	}
	
	public static void main(String[] args) {
//		for(int i=0; i<5; i++)
//		System.out.println(RecursiveAlgo.f(i));
		
//		for(int i=0; i<100; i++)
//		System.out.println(toBinary(i) + " " + Integer.toBinaryString(i));
		
//		System.out.println(dist("abcbcadeffdgh"));
//		for(int i=0; i<255; i++)System.out.print((char)i + " ");
		
		
	}
}
