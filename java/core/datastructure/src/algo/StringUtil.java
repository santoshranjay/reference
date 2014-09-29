package algo;

public class StringUtil {

	//reverse a string
	public static String reverse(String str){
		char[] sarr = str.toCharArray();
		char[] rarr = new char[sarr.length];
		int j=0;
		for(int i=sarr.length-1; i>=0; i--){
			rarr[j++]=sarr[i];
		}
		return new String(rarr);
	}
	
	//reverse a string recursively
	static String reverseRec(String str){

		if(str.length()==1)return str;
		return str.charAt(str.length()-1) + reverseRec(str.substring(0,str.length()-1));
		
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtil.reverseRec("hello world"));
	}
}
