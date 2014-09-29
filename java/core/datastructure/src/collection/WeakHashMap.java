package collection;

/**
 * WeakHashMap is an implementation of the Map interface that stores only weak
 * references to its keys. Storing only weak references allows a key-value pair
 * to be garbage collected when its key is no longer referenced outside of the
 * WeakHashMap. This class is intended primarily for use with key objects whose
 * equals methods test for object identity using the == operator. Once such a
 * key is discarded it can never be recreated, so it is impossible to do a
 * look-up of that key in a WeakHashMap at some later time and be surprised that
 * its entry has been removed.
 * 
 * @author santosh
 * 
 */
public class WeakHashMap {

}
