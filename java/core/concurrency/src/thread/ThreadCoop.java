package thread;

public class ThreadCoop {
	private static final CoopObj obj = new CoopObj();
	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			new Thread(new Consumer(obj)).start();
		}
		new Thread(new Producer(obj)).start();
		
		
	}
}

class CoopObj{
//	private volatile boolean update;
	private volatile int count;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
class Producer implements Runnable{
	private final CoopObj obj;
	public Producer(CoopObj obj) {
		this.obj = obj;
	}
	public void run() {
		for(int i=0; i<10; i++){
			synchronized (obj) {
				obj.setCount(i);	
			}
			System.out.println(Thread.currentThread().getName()+ "Changing i:" + i);
			synchronized (obj) {
				obj.notifyAll();
			}
			try {
				Thread.sleep(1000L*1L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer implements Runnable{
	private final CoopObj obj;
	
	public Consumer(CoopObj obj){this.obj = obj;}
	
	public void run(){
		for(int i=0;i<10;i++){
			try {
				synchronized (obj) {
					obj.wait(2000L);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+ "Reading i:" + obj.getCount());
		}
	}
}