package algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Algo {

	/**
	 * generate first n prime nos.
	 * largest prime no known today has around 17million decimal digit
	 * @param n
	 * @return
	 */
	// n (1000) first prime nos.
	static int[] primes(int n){
		int[] primes = new int[n];
		int size=0;
		int i=1;
		while(size < n){
			if(isPrime(i)){
				primes[size++]=i;
			}i++;
		}
		return primes;
	}

	static boolean isPrime(int num){
		if(num==1)return true;
		for(int i=2; i<=Math.sqrt(num); i++){
			if(num % i ==0)return false;
		}
		return true;
	}
	
	//sum of two large integers
	static int[] sum(int[] num1, int[] num2){
		if(num1.length < num2.length){
			int[] tmp = num1;
			num1 = num2;
			num2=tmp;
		}
		int[] result = new int[num1.length + 1];
		int j = num2.length-1;
		int sum=0, carry=0;
		int k = result.length-1;
		for(int i=num1.length-1; i>=0; i--){
			
			if(j >=0){
				sum = (num1[i] + num2[j] + carry)%10;
				carry = (num1[i] + num2[j] + carry)/10;
				j--;
			}else{
				sum = (num1[i] + carry)%10;
				carry = (num1[i] + carry)/10;
			}
//			System.out.println(sum);
			result[k--] = sum;
		}
		result[k]=carry;
		return result;
	}
	
	static void testSum(){
		String s1 = "838383838889850435454";String s2 = "939393939354787878899";
		int[] n1 = new int[s1.length()];
		int[] n2 = new int[s2.length()];
		for(int i=0; i<s1.length(); i++)n1[i]=(Character.getNumericValue(s1.charAt(i)));
		for(int i=0; i<s2.length(); i++)n2[i]=Character.getNumericValue(s2.charAt(i));
		for (int i : n2) {
		 System.out.print(i);	
		}
		System.out.println();
		int [] r = Algo.sum(n1,n2);
		StringBuffer sb = new StringBuffer();
		for (int i : r) {
			System.out.print(i);
			sb.append(i);
		}
		
		BigInteger b = new BigInteger(s1).add(new BigInteger(s2));
		String s = new String(b.toString());
		System.out.println();
		System.out.println(s);
		System.out.println(sb.toString().equals(s));
	}
	
	//remove duplicate from string using java api
	static String removeDup(String str){
		Set<Character> cset = new LinkedHashSet<Character>();
		for(int i=0; i<str.length(); i++)cset.add(str.charAt(i));
		StringBuilder sb = new StringBuilder();
		for(Character c: cset)sb.append(c);
		return sb.toString();
	}
	
	//find unique words in the list
	static String[] uniqueWords(String[] words){
		Set<String> ustr = new HashSet<String>();
		Set<String> dstr = new HashSet<String>();

		for(String word: words){
			if(!ustr.add(word))dstr.add(word);
		}
		ustr.removeAll(dstr);
		return ustr.toArray(new String[ustr.size()]);
	}
	
	//non-repeat char
	static String nonRepeatChar(String str){
			char[] ia = str.toCharArray();
			char[] oa = new char[255];
			char[] ra = new char[255];
			StringBuilder sb = new StringBuilder();
			for(char c: ia){
				if(oa[c] == '\u0000'){
					oa[c]=c;sb.append(c);
				}else{ra[c]=c;}
			}
			ia = sb.toString().toCharArray();
			sb = new StringBuilder();
			for(char c: ia){ 
				if(ra[c] == '\u0000')sb.append(c);
			}
			return sb.toString();
		}
	
	//most popular word in a file
	static String maxFreqWord(File f) throws FileNotFoundException{
		Scanner in = new Scanner(f);
		Map<String,AtomicInteger> map= new HashMap<String,AtomicInteger>();
		while(in.hasNext()){
			String word = in.next();
			if(map.get(word)==null){map.put(word,new AtomicInteger(1));}
			else{map.get(word).incrementAndGet();}
		}
		in.close();
		System.out.println(map);
		int max=0;
		String pword = null;
		
		for(Entry<String, AtomicInteger> e: map.entrySet()){
			if(e.getValue().intValue() > max){
				max = e.getValue().intValue();
				pword = e.getKey();
			}
		}

		return pword;
	}
	
	//decimal to binary conversion
	static String toBinary(int n){
		StringBuffer sb = new StringBuffer();
		while(n>1){
			sb.append(n%2);
			n = n / 2;
		}
		sb.append(n);
			
		return sb.reverse().toString();
	}
	//distinct char in a string
		static String dist(String str){
			Character[] ch = new Character[255];
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<str.length(); i++){
				 int ci = str.charAt(i);
				 if(ch[ci] == null ){
					 ch[ci]=str.charAt(i);
					 sb.append(str.charAt(i));
				 }
			}
			return sb.toString();
		}
		
		//find top n most frequent words in a file (big file)
		static String[] mostFrequentWords(File f, int topK) throws FileNotFoundException{
			Map<String, AtomicInteger> wordTable = new HashMap<>();
			Map<Integer, Set<String>> countTable = new LinkedHashMap<>();
			Scanner scan = new Scanner(f);
			while(scan.hasNext()){
				String word = scan.next();
				if(wordTable.get(word)==null){
					wordTable.put(word, new AtomicInteger(1));
					if(countTable.get(new Integer(1))==null){
						Set<String> words = new HashSet<>();words.add(word);
						countTable.put(new Integer(1), words);
					}else{
						countTable.get(new Integer(1)).add(word);
					}
				}else{
					int i = wordTable.get(word).incrementAndGet();
					countTable.get(new Integer(i-1)).remove(word);
					if(countTable.get(new Integer(i))==null){
						Set<String> words = new HashSet<>();words.add(word);
						countTable.put(new Integer(i), words);
					}else{
						countTable.get(new Integer(i)).add(word);
					}
				}
			}
			int n = 0;
//			List<String> topkwords = new ArrayList<>();
			Object[]  keys= countTable.keySet().toArray();
//			while(n < topK){
//				
//			}
			System.out.println(countTable);
			
			return (String[]) countTable.get(keys[0]).toArray();
		}

	public static void main(String[] args) throws FileNotFoundException {
//		int[] p = Algo.primes(1000);
//		for (int i : p) {
//			System.out.print(i + " ");
//		}
		
		System.out.println(Algo.mostFrequentWords(new File("C:\\temp\\test.txt"),5));
	}
}
