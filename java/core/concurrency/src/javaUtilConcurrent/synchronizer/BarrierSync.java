package javaUtilConcurrent.synchronizer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * A synchronization aid that allows a set of threads to all wait for each other to reach a 
 * common barrier point. 
 * Sample usage: Here is an example of using a barrier in a parallel decomposition design: 
 * @author santku
 *
 */
public class BarrierSync {
	static final float[][] data = {{5,5,5},{5,5,5},{5,5,5}};
	
	public static void main(String[] args) throws Exception{
		Solver solver = new Solver(data);
//		Thread.sleep(1L);
		print();
	}
	
	static void print(){
		for(int i=0; i<data.length; i++){
			System.out.println();
			for(int j=0; j<data[i].length; j++){
				System.out.print(data[i][j] + ",\t");
			}
			System.out.println();
		}
	}
}

class Solver {
	final int N;
	final float[][] data;
	final CyclicBarrier barrier;

	class Worker implements Runnable {
		int myRow;
		Worker(int row) {
			myRow = row;
		}
		public void run() {
//			while (!done()) {
				System.out.println(Thread.currentThread().getName() + " processing myRow:" + myRow);
				processRow(myRow);
				try {
					barrier.await();
				} catch (InterruptedException ex) {
					return;
				} catch (BrokenBarrierException ex) {
					return;
				}
				System.out.println(Thread.currentThread().getName() + " done processing myRow:" + myRow);
//			}
		}
		
		public void processRow(int row){
			for(int i=0; i<data[row].length; i++){
				data[row][i] = data[row][i]+row;
			}
		}
		public boolean done(){return false;}
	}

	public Solver(float[][] matrix) {
	     data = matrix;
	     N = matrix.length;
	     barrier = new CyclicBarrier(N, 
	                                 new Runnable() {
	                                   public void run() { 
	                                	  System.out.println( Thread.currentThread().getName() + " processing merge");
	                                     mergeRows(); 
	                                   }
	                                 });
	     for (int i = 0; i < N; ++i) 
	       new Thread(new Worker(i)).start();
//	     waitUntilDone();
	   }
	
	public void mergeRows(){
		for(int i=0; i<data.length; i++){
			for(int j=0; j<data[i].length; j++){
				data[i][j]= data[i][j]-1;
			}
		}
	}
	
}
