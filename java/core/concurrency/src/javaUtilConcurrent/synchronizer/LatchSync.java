package javaUtilConcurrent.synchronizer;
/**
 * latches can facilitate starting a group of related activities or waiting for a group of
 * related activities to complete.
 * 
 * latch.await - calling thread block for one or more event to be occur.
 * latch.countdown - raise an event.
 */
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class LatchSync {
	private int nThreads;
	CountDownLatch startLatch;
	CountDownLatch endLatch;
	
	LatchSync(int nThreads){
		this.nThreads = nThreads;
		startLatch = new CountDownLatch(1);
		endLatch = new CountDownLatch(nThreads);
	}
	
	public static void main(String[] args) {
		final LatchSync ls = new LatchSync(5);
		for(int i=0; i<ls.nThreads; i++){
//			final int j = i;
			Thread t = new Thread(new Runnable(){
				public void run(){
					try {
						ls.startLatch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}try{
						new Task().run();
					}finally{
						ls.endLatch.countDown();
					}
				}
			});
			t.start();
		}
			long startTime = System.currentTimeMillis();
			System.out.println("Starting Threads.");
			ls.startLatch.countDown();
			try {
				ls.endLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Finished Threads.");
			long endTime = System.currentTimeMillis();
			System.out.println("Toatl Time=" + (endTime-startTime)/1000L);
	}
}

class Task implements Runnable{
	private static AtomicInteger i = new AtomicInteger(0);
	public void run() {
		System.out.println("Running:" + Thread.currentThread().getName());
		try {
			Thread.sleep(5000L + i.getAndSet(i.get()+2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		System.out.println("Finished:" + Thread.currentThread().getName());
	}
	
}
