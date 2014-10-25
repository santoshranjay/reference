package com.san.ej;

/**
 * Item 3- enforce the singleton property with a private constructor or an enum
 * type.
 * 
 * singleton with public static final field & private constructor (eager
 * initialization) 
 * singleton with static factory (private static final field
 * (eager init) & private constructor) 
 * serializable singleton
 * 
 * @author santosh
 * 
 */

public class Singleton {
	public static void main(String[] args) {
		EnumSingleton enumInstance = EnumSingleton.INSTANCE;
		System.out.println(enumInstance);
		enumInstance.testMd();
	}
}

// Enum singleton - the preferred approach - page 18
enum EnumSingleton {
	INSTANCE;
	public String toString() {
		return "EnumSingleton";
	};
	public void testMd(){System.out.println("test md");}

}