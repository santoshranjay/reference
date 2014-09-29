package javaUtilConcurrent.synchronizer;
/**
 * Semaphore are useful for implementing resource pool such as database connection pool.
 * 
 * Counting semaphore are used to control the number of activities that can access a 
 * certain resource or perform a given action at the same time.
 * 
 * Binary semaphore can be used as mutex with nonreentrant locking semantics.
 */
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class SemaphoreSync {
	public static void main(String[] args) {
		final BoundedHashSet<String> set = new BoundedHashSet<String>(5);
		Thread t1 = new Thread(new Runnable(){
			int i=0;
			public void run(){
				while(true){
				try {
					set.add("T-" + i++);
					Thread.sleep(1500L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
			}
		});
		t1.start();
		Thread t2 = new Thread(new Runnable(){
			int i=0;
			public void run(){
				while(true){
				set.remove("T-"+i++);
				try {
					Thread.sleep(2600L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
			}
		});
		t2.start();
		while(true){
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(set);
		}
		
	}
}
/**
 * Use of semaphore to turn any collection into a blocking bounded collection for example 
 * BoundedHashSet
 */
class BoundedHashSet<T>{
	private Set<T> set;
	private Semaphore semaphore;
	
	public BoundedHashSet(int size){
		set = Collections.synchronizedSet(new HashSet<T>(size));
		semaphore = new Semaphore(size);
	}

	public boolean add(T e) throws InterruptedException{
		semaphore.acquire();
		boolean success = false;
		try{
			success = set.add(e);
			return success;
		}finally{
			if(!success)semaphore.release();
		}
	}

	public boolean remove(Object o) {
		boolean success=false;
		try{
			success = set.remove(o);
			return success;
		}finally{
			if(success)semaphore.release();	
		}
	}
	
	@Override public String toString(){
		return set.toString();
	}
}