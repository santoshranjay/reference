package executor;
/**
 * Executor framework for asynchronous task execution that supports a wide variety of task
 * execution policy.
 * 
 * Executor is based on the producer-consumer pattern, where activities that submit tasks
 * are the producers and the threads that execute tasks are the consumers.
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.san.jmx.helper.JmxAgent;


public class ExecService {
	/* Get an executor service that will run a maximum of 5 threads at a time: */
	private static ExecutorService exec = Executors.newFixedThreadPool(5);
	
	
	/* expose the exectuor service to jmx */
	public static interface MExecServiceMBean{
		public void shutdown();
		public void shutdownNow();
	}
	private static class MExecService implements MExecServiceMBean{
		public void shutdown() {
			exec.shutdown();
		}

		public void shutdownNow() {
			exec.shutdownNow();
		}
	
		
		static{
//			JmxAgent.deployMBean(new MExecService(), "Server:name=ExecutorService");
		}
		
	}
	
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
					}
//		            System.out.println("Running in: " + Thread.currentThread());
		        }
		    });
		}
		JmxAgent.deployMBean(new MExecService(), "Server:name=ExecutorService");
		JmxAgent.startMx4jAdapter(9191);

		/* Tell the executor that after these 100 steps above, we will be done: */
//		exec.shutdown();
		
		//after shutdown it'll not accept any new task.
		/*exec.execute(new Runnable(){
			public void run(){
				System.out.println("Started special:" + Thread.currentThread().getName());
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Finished special:" + Thread.currentThread().getName());
			}
		});*/
		//
		try {
		    /* The tasks are now running concurrently. We wait until all work is done, 
		     * with a timeout of 50 seconds: */
		    boolean b = exec.awaitTermination(5, TimeUnit.SECONDS);
		    if(!b){
		    	System.out.println("Timeout occur...shutting it down.");
//		    	exec.shutdownNow();
		    }
		    /* If the execution timed out, false is returned: */
		    System.out.println("All done: " + b);
		} catch (InterruptedException e) { e.printStackTrace(); }
	}

}
