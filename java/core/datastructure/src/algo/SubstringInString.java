package algo;
/*
 * Boyer–Moore string search algorithm
 */
public class SubstringInString {

	/**
	 * O(n*m) - brute-force algorithm - Refers to a programming style that does
	 * not include any shortcuts to improve performance, but instead relies on
	 * sheer computing power to try all possibilities until the solution to a
	 * problem is found. A classic example is the traveling salesman problem
	 * (TSP). Suppose a salesman needs to visit 10 cities across the country.
	 * How does one determine the order in which cities should be visited such
	 * that the total distance traveled is minimized? The brute force solution
	 * is simply to calculate the total distance for every possible route and
	 * then select the shortest one. This is not particularly efficient because
	 * it is possible to eliminate many possible routes through clever
	 * algorithms. Although brute force programming is not particularly elegant,
	 * it does have a legitimate place in software engineering. Since brute
	 * force methods always return the correct result -- albeit slowly -- they
	 * are useful for testing the accuracy of faster algorithms. In addition,
	 * sometimes a particular problem can be solved so quickly with a brute
	 * force method that it doesn't make sense to waste time devising a more
	 * elegant solution.
	 * 
	 * @param str
	 * @param substr
	 * @return
	 */
	public static int subString(String str, String substr){
		int n = str.length();
		int m = substr.length();
		for (int i = 0; i <= n-m ; i++) {
			//find the first char
			while(i <= n-m){
				if(str.charAt(i)==substr.charAt(0))break;
				i++;
			}
			//found the first char - yes/no
			if(i != n-m){
				//find the rest char
				int j=i+1;
				int k=1;
				for (; k <m ; j++,k++) {
					if(str.charAt(j) != substr.charAt(k))break;
				}
				if(k==m) return i;
			}
		}
		return -1;
	}
	
	/*
	 * The search() method below operates in this way to find the first
	 * occurrence of a pattern string pat in a text string txt. The program
	 * keeps one pointer (i) into the text and another pointer (j) into the
	 * pattern. For each i, it resets j to 0 and increments it until finding a
	 * mismatch or the end of the pattern (j == M). If we reach the end of the
	 * text (i == N-M+1) before the end of the pattern, then there is no match:
	 * the pattern does not occur in the text. Our convention is to return the
	 * value N to indicate a mismatch.
	 */
//	bruteforceSubstring()
	
	/*
	 * Boyer–Moore string search algorithm
	 */
	
	public static void main(String[] args) {
		System.out.println("" + subString("aaaaabcaaabcda", "bcd") + "");
	}
	
	/*
	 * my algo
	 * two cursor in string array. for first match for last match (gap m)
	 */
	public static int mysubStr(String str, String substr){
		int n = str.length(); int m = substr.length();
		char f = substr.charAt(0); char l = substr.charAt(m-1);
		
		for (int i = 0; i <= n-m ; i++) {
			//find the first char
			while(i <= n-m){
				if(str.charAt(i)==f)break;
				i++;
			}
			//found the first char - yes/no
			if(i != n-m){
//				check the last char
				if(str.charAt(i+m) == l){
					//find the rest char
					int j=i+1;
					int k=1;
					for (; k <m ; j++,k++) {
						if(str.charAt(j) != substr.charAt(k))break;
					}
					if(k==m) return i;
				}
				//last char do not match - move the last cursor to right until it match
				
				//last char match, forward the first cursor with the substr length and do a match
			}
		}
		
		return -1;
	}
}
