package thread;

public class PrintConsecutiveEvenAndOdd{
//	private static Integer N = 0;
	public static void main(String[] args) {
		new Thread(new Runnable(){
			public void run(){
				NumberPrinter n = new NumberPrinter();
				while(true)
				n.printEven();
			}
		},"EvenGen").start();
		new Thread(new Runnable(){
			public void run(){
				NumberPrinter n = new NumberPrinter();
				while(true)
				n.printOdd();
			}
		},"OddGen").start();
		
		/*final IntPrinter iprint = new IntPrinter();
		new Thread(new Runnable(){
		public void run(){
			int i=0;
			while(i++<10)
			iprint.printEven();
		}
	},"EvenGen").start();
	new Thread(new Runnable(){
		public void run(){
			int i=0;
			while(i++<10)
			iprint.printOdd();
		}
	},"OddGen").start();
		*/
		
	/*	new Thread(new Runnable(){
			public void run(){
				while(true){
				synchronized (N) {
					while(N%2!=0){
						try {
							N.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println(Thread.currentThread().getName() + ":" + N++);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//					N.notify();
				}
			}
			}
		}
		,"EvenNumberGen").start();
		new Thread(new Runnable(){
			public void run(){
				while(true){
					synchronized (N) {
						while(N%2==0){
							try {
								N.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						System.out.println(Thread.currentThread().getName() + ":" + N++);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						N.notify();
						}
					}
				}
			
		},"OddNumberGen").start();*/
	}

}
class SN{
	private volatile int i=0;
	public SN(int i){
		this.i=i;
	}
	public int getN(){return i;}
	public void incN(){i++;}
	public String toString(){
		return ""+i;
	}
	
}
class NumberPrinter {
	private static final SN N = new SN(0);
	
	public void printEven(){
		synchronized (N) {
			while(N.getN()%2!=0){
				try {
					N.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + N);
			N.incN();
			N.notify();
			
		}
			/*while(N%2!=0){
				synchronized (N) {
				try {
					N.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
			}
				System.out.println(Thread.currentThread().getName() + N);
//				synchronized (N) {
//					N++;
//					System.out.println(Thread.currentThread().getName() + N);
//					N.notify();
//				}
				incN();*/
	}
	/**/
	
	public void printOdd(){
		synchronized (N) {
			while(N.getN() % 2==0){
				try {
					N.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + N);
			N.incN();
			N.notify();
//				N++;
//				N.notify();
		}
	}
}

class IntPrinter{
	private Integer i = new Integer(0);
	
	public synchronized void printOdd(){
		while(i%2==0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			System.out.println(Thread.currentThread().getName() + i++);
			notify();
	}
	
	public synchronized void printEven(){
		while(i%2!=0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			System.out.println(Thread.currentThread().getName() + i++);
			notify();
	}
}
	
/*
 class NumberPrinter {
//	private static volatile SN N = new SN(0);
	private static volatile Integer N = new Integer(0);
	
	public void printEven(){
		synchronized (N) {
			while(N%2!=0){
				try {
					N.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + N);
			N++;
			N.notify();
			
		}
			/*while(N%2!=0){
				synchronized (N) {
				try {
					N.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
			}
				System.out.println(Thread.currentThread().getName() + N);
//				synchronized (N) {
//					N++;
//					System.out.println(Thread.currentThread().getName() + N);
//					N.notify();
//				}
				incN();
	}
	*/

/*

Of type Integer or similar? -- replaces the immutable Integer object with another one. Therefore you are calling notify on a different object than the 
synchronized.
Your code is the equivalent of:
Integer syncConunt = Integer.valueOf(5); [...] synchronized (syncCount) {     syncCount = Integer.valueOf(syncCount.intValue() + 1);     syncCount.notify(); }
You are not alone. Even before J2SE 5.0, I have seen example code published in a book that assigned a reference within a synchronized block. In general it is
 a good idea to mark lock fields final.
Another significant point is that the code synchronising on an object that it does not "own". Integer objects are shared (Integer.valueOf(int) will return
 exactly the same instance if called with values between -128 and 127, and perhaps further). If this were done by two pieces of unrelated code, 
 then there would hidden interactions. This applies to any type where instances are shared between unrelated code. Common examples are Integer, String, 
 Class (used by static synchronised methods) and Thread (in Sun's implementation, Thread happens to be used as a lock for join).

*/