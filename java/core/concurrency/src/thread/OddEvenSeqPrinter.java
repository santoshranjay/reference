package thread;

import java.util.concurrent.TimeUnit;

public class OddEvenSeqPrinter {
	private int number = 1;
	
	public synchronized void printOdd(){
		while(!isOdd(number)){
			try {
				wait();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println(number++);
		notify();
	}
	
	public synchronized void printEven(){
		while(!isEven(number)){
			try{
				wait();
			}catch(InterruptedException e){
				
			}
		}
		System.out.println(number++);
		notify();
	}

	private static boolean isEven(int i) {
		return i%2==0;
	}

	private static boolean isOdd(int i) {
		return i%2 !=0;
	}

	public static void main(String[] args) {
		final OddEvenSeqPrinter printer = new OddEvenSeqPrinter();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					printer.printEven();
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		},"EvenPrinter").start();
		
	new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					printer.printOdd();
				}
			}
		},"OddPrinter").start();
	}
}
