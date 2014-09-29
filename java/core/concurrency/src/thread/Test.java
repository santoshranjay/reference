package thread;

public class Test {

	//
	
	public static void main(String[] args) {
//		immutablitiy - object state can't be change & all its fields are final. e.g., String
		final String s = new String("hello");
		System.out.println(s.replace('h', 'j'));
		System.out.println(s);
	}
}
