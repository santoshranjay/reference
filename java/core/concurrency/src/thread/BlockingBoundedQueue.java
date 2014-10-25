package thread;
import java.util.concurrent.TimeUnit;
/**
 * Array based blocking queue implementation which is thread safe.
 * @author santosh
 *
 * @param <E>
 */
public class BlockingBoundedQueue<E> {
	private E[] queue;
	private volatile int fcur;
	private volatile int lcur;

	@SuppressWarnings("unchecked")
	public BlockingBoundedQueue(int size) {
		queue = (E[]) new Object[size];
		fcur = lcur = 0;
	}

	public synchronized E pop() {
		E popE = null;
		while (isEmpty()) {
			try {
				System.out.println("pop waiting");
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		popE = queue[fcur++];
		if (fcur == queue.length)fcur=0;
		notifyAll();
		return popE;
	}

	public synchronized void push(E e) {
		while (isFull()) {
			try {
				System.out.println("push waiting");
				wait();
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
			queue[lcur++] = e;
			if (lcur == queue.length)lcur = 0;
			notifyAll();
	}
	
	public synchronized boolean  isFull(){
		return (lcur==queue.length-1 && fcur ==0) || (lcur +1 == fcur);
	}
	
	public synchronized boolean  isEmpty(){
		return (fcur == lcur);
	}

	public static void main(String[] args) throws InterruptedException {
		final BlockingBoundedQueue<String> q = new BlockingBoundedQueue<String>(
				2);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println(q.pop());
					try {
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
					q.push("st" + i);
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	
}
