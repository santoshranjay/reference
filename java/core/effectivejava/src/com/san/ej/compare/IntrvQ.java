package com.san.ej.compare;

import java.util.Set;
import java.util.TreeSet;

/*sorted collections (TreeSet, TreeMap) & utility classes (Collections, Arrays) depends
*on comparision (compareTo)
*recommended - x.comareTo(y ==0) == x.equals(y)
*
*Q 1 - using TreeSet to store objects which are not of type comparable.
*Answer - add operation throw ClassCastException at runtime.
*
*Q 2 - using TreeSet to store object whose compareTo method is inconsistent with equals.
*Answer - store using the compareTo method (cann't store the unequal objects whose compareTo returns 0(same))
* e.g., BigDecimal - HashSet store both of these two - "1.0", "1.00"
* 					 TreeSet store only one "1.0" and not "1.00"
*/
public class IntrvQ {
	public static void main(String[] args) {
		//1.
//		Set<Emp> sortedSet = new TreeSet<Emp>();
//		sortedSet.add(new Emp(1,"ab"));sortedSet.add(new Emp(3,"bc"));sortedSet.add(new Emp(2,"ac"));
//		System.out.println(sortedSet);
		//2.
		Set<CompEmp> sortedSet = new TreeSet<CompEmp>();
		sortedSet.add(new CompEmp(1,"ab"));sortedSet.add(new CompEmp(3,"ab"));sortedSet.add(new CompEmp(2,"ac"));
		System.out.println(sortedSet);
	}
}

class Emp{
	int id;
	String name;
	
	public Emp(int id, String name) {
		this.id=id; this.name=name;
	}
}

class CompEmp implements Comparable<CompEmp>{
	int id;
	String name;
	
	public CompEmp(int id, String name) {
		this.id=id; this.name=name;
	}

	@Override
	public int compareTo(CompEmp o) {
		return name.compareTo(o.name);
	}
	
	@Override
	public String toString() {
		return id + ":"+name;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj==this)return true;
		if(getClass() == obj.getClass()){
			CompEmp an = (CompEmp)obj;
			return id==an.id && name.equals(an.name);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode()+id;
	}
}