package thread;
/**
 * Question - why can't we just invoke wait()/notify() rather calling list.wait(()/list.notify()
 * Answer - java.lang.IllegalMonitorStateException - Thrown to indicate that a thread has attempted to wait on an object's monitor or to notify other threads
 *  waiting on an object's monitor without owning the specified monitor. 
 * A Thread need to own the object's monitor first to invoke the wait/notify on that object.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SyncRW {
	private static final int MAX_SIZE = 10;
	private List<Item> list = new ArrayList<Item>(MAX_SIZE);
	
	public Item read(){
		Item it;
		synchronized (list) {
			while(list.size() <=0 ){
				try {
					list.wait();
//					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			it = list.remove(list.size()-1);
			list.notify();
		}
		return it;
	}
	
	public int write(){
		int s;
		synchronized (list) {
			while(list.size()>= MAX_SIZE){
				try {
					list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			s = list.size();
			list.add(new Item(s));
			list.notify();
		}
		
		return s;
	}
	
	public List<Item> getList(){
		return list;
	}
	
	public static void main(String[] args) {
		final SyncRW srw = new SyncRW();
		
			
/*	new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName() + "-" + srw.write());
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		},"WriteTh").start();*/
	
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			while(true){
				System.out.println(Thread.currentThread().getName() + "-" + srw.getList().add(new Item(srw.getList().size())));
				synchronized(srw.getList()){
				srw.getList().notify();}
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	},"WriteTh").start();
	
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			while(true){
				System.out.println(Thread.currentThread().getName() + "-" +srw.read());
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	},"ReadTh").start();

	}
}

class Item{
	int ls;
	public Item(int ls) {
		this.ls = ls;
	}
	@Override
	public String toString() {
		return "" + ls;
	}
}