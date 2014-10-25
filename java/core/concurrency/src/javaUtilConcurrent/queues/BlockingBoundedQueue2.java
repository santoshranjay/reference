package javaUtilConcurrent.queues;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Blocking queue of concurrent library.
 * @author santosh
 *
 */
public class BlockingBoundedQueue2 {
	
	
	public static void main(String[] args) {
		final BlockingQueue<String> q = new LinkedBlockingQueue<String>(2);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						System.out.println(q.poll(10,TimeUnit.SECONDS));
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						q.offer("st" + i, 10, TimeUnit.SECONDS);
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
