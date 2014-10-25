package com.san.ej;

/**
 * Item 4- enforce noninstantiability with a private constructor
 * 
 *  Noninstantiable utility class
 * @author santosh
 *
 */

public class UtilityClass {
    // Suppress default constructor for noninstantiability
    private UtilityClass() {
        throw new AssertionError();
    }
}
