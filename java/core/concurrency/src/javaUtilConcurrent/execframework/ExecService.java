package javaUtilConcurrent.execframework;
/**
 * Executor framework for asynchronous task execution that supports a wide variety of task
 * execution policy.
 * (decouple task submission from the task execution)
 * 
 * Executor is based on the producer-consumer pattern, where activities that submit tasks
 * are the producers and the threads that execute tasks are the consumers.
 * 
 * ?difference between submit and execute
 * Method submit extends base method Executor.execute(java.lang.Runnable) by creating and returning a Future that 
 * can be used to cancel execution and/or wait for completion.
 * ? awaitTermination - how to simulate the block for current thread interruption.
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecService {
	/* Get an executor service that will run a maximum of 5 threads at a time: */
//	private static ExecutorService exec = Executors.newFixedThreadPool(5);
	private static ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	public static void main(String[] args) {
		/* For all the 100 tasks to be done altogether... */
		for (int i = 0; i < 100; i++) {
		    /* ...execute the task to run concurrently as a runnable: */
			final int j = i;
			
		    exec.execute(new Runnable() {
		        public void run() {
		            /* do the work to be done in its own thread */
		        	try {
		        		System.out.println("Started:[" + j+ "]" + Thread.currentThread().getName());
						Thread.sleep(5000);
						System.out.println("Finished:[" + j+ "]" + Thread.currentThread().getName());
					} catch (InterruptedException e) {
						System.out.println("Interrupted:" + e.getLocalizedMessage());
						Thread.currentThread().interrupt();
					}
//		            System.out.println("Running in: " + Thread.currentThread());
		        }
		    });
		}
		
		/* Tell the executor that after these 100 steps above, we will be done: */
//		exec.shutdown();
		
		//after shutdown it'll not accept any new task.
		exec.execute(new Runnable(){
			public void run(){
				System.out.println("Started special:" + Thread.currentThread().getName());
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Finished special:" + Thread.currentThread().getName());
			}
		});
		/* The tasks are now running concurrently. We wait until all work is done, with a timeout of 6 seconds: */
		try {
			//Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs, or the current thread is interrupted, whichever happens first.
		    boolean b = exec.awaitTermination(4, TimeUnit.SECONDS);
		    if(!b){
		    	System.out.println("Timeout occur...shutting it down.");
		    	exec.shutdownNow();
		    }
		    
		    /* If the execution timed out, false is returned: */
		    System.out.println("All done: " + b);
		} catch (InterruptedException e) { e.printStackTrace(); }
	}

}
