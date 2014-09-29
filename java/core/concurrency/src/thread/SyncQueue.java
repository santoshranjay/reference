package thread;
/*
 *  IllegarMonitorException-So the object you call notifyAll() on and the one you synchronize on are not the 
 *  same object; hence, the exception.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class SyncQueue {
	private String[] bucket;
	private int size;
	private volatile int cursor;
	
	public SyncQueue(){
		bucket = new String[20];
		size = 20;
		cursor=0;
	}
	public SyncQueue(int size){
		bucket = new String[size];
		this.size = size;
		cursor=0;
	}
	
	public void add(String str){
		//System.out.println("Adding " + str);
		synchronized(this){
			while(cursor>=20)
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			bucket[cursor++]=str + "\n";
			//System.out.println("Added " + str);
		}
		
	}
	
	public void clear(){
		synchronized(this){
			while(cursor<20)
				try {
					wait(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			clearBucket();
			cursor=0;
			notifyAll();
		}
	}
	
	public void clear(OutputStream out) throws IOException{
		//System.out.println("Cleaning ");
		synchronized(this){
			while(cursor<20)
				try {
					wait(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			for(int i=0; i<size; i++)
				out.write(bucket[i].getBytes());
			out.flush();
			clearBucket();
			cursor=0;
			//System.out.println("Cleaned up.");
			notifyAll();
		}
	}
	
	private void clearBucket(){
		for(int i=0 ; i<size; i++)
			bucket[i] = null;
	}
	
	public static void main(String[] args)throws IOException{
		//a sync bucket.
		final SyncQueue bucket = new SyncQueue();
		final OutputStream out = new FileOutputStream(new File("c:\\temp\\pout"));
		
		//some threads parallely writing into the SyncQueue.
		for(int i=0; i<5; i++){
			Thread t = new Thread(new Runnable(){
				public void run(){
					String tname = Thread.currentThread().getName();
					while(true){
						bucket.add(tname);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			t.setDaemon(true);
			t.start();
		}
		
		//one thread to clear the SyncQueue.
		Thread t = new Thread(new Runnable(){
			public void run(){
				while(true)
					try {
						bucket.clear(System.out);
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		});
		t.setDaemon(true);
		t.start();
		
		//wait all thread to complete.
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
