package serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * uses default serialization
 * @author santosh
 *
 */
public class StringList implements Serializable{
	private int size;
	private Entry head;

	// the reference object must be serializable 
//	public static class Entry {
	public static class Entry implements Serializable{
		private String name;
		private Entry next;
	}
	
	public void add(String str){
		Entry entry = new Entry();
		entry.name = str;
		Entry next = head;
		head = entry;
		head.next = next;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Entry e=head; e!=null; e=e.next){
			sb.append(e.name + "," + e.next+", ");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		StringList list = new StringList();
		list.add("a");
		list.add("b");
		System.out.println(list);
		
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File("C:\\temp\\serial_sl.txt")));
		os.writeObject(list);
		os.close();
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("C:\\temp\\serial_sl.txt")));
		StringList ll = (StringList)is.readObject();
		
		System.out.println(ll);
	}

}
