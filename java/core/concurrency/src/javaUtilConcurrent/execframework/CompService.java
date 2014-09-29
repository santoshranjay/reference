/**
 * A service that decouples the production of new asynchronous tasks from the consumption of the results of 
 * completed tasks. 
 * With ExecutorService , once you have submitted the tasks to run , you need to manually code for efficiently 
 * getting the results of the tasks completed. With CompletionService , this is pretty much automated.
 * method in completion service
 * poll, take, submit
 */
package javaUtilConcurrent.execframework;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CompService {
	static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	static Future<Result> future;
	
	public static void main(String[] args) {
			CompletionService<Result> compservice = new ExecutorCompletionService<Result>(executor);
			for (int i = 0; i < 10; i++) {
			    /* ...execute the task to run concurrently as a callable: */
				final int j = i;
			    compservice.submit(new Callable<Result>() {
					public Result call() throws Exception {
						try {
			        		System.out.println("Started:[" + j+ "]" + Thread.currentThread().getName());
							Thread.sleep(1000L*j);
							System.out.println("Finished:[" + j+ "]" + Thread.currentThread().getName());
							return new Result(j);
						} catch (InterruptedException e) {
							System.out.println("Interrupted:" + e.getLocalizedMessage());
						}
						return null;
					}
			    });
			}
			/*future = executor.submit(new Callable<Result>(){
				public Result call() throws Exception {
					System.out.println("Started:[" + j+ "]" + Thread.currentThread().getName());
					Thread.sleep(5000);
					return new Result(j);
				}
			});*/
			
			executor.shutdown();
			Future<Result> f;
			try {
				while( (f = compservice.poll(5, TimeUnit.SECONDS)) != null){
					try {
						System.out.println("completed task " + f.toString() + " result " + f.get().result);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*try {
				for(int i=10; i>0; i--){
					Future<Result> rs = compservice.take();
					System.out.println(rs.get());
				}
			}  catch (InterruptedException e) {
	            // Re-assert the thread's interrupted status
	            Thread.currentThread().interrupt();
	            // We don't need the result, so cancel the task too
	            future.cancel(true);
	        } catch (ExecutionException e) {
	        	e.printStackTrace();
	        }*/
	}
}

/*class Result{
	public String result;
	public Result(){}
	public Result(int i){result = ""+i;}
	@Override public String toString(){return "Result:" + result;}
}*/
