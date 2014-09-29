package javaUtilConcurrent.execframework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFutureTask {
	static final ExecutorService executor = Executors.newFixedThreadPool(10);
	static Future<Result> future;
	
	public static void main(String[] args) {
			final int j=1;
			future = executor.submit(new Callable<Result>(){
				public Result call() throws Exception {
					System.out.println("Started:[" + j+ "]" + Thread.currentThread().getName());
					Thread.sleep(5000);
					return new Result(j);
				}
			});
			
			executor.shutdown();
			
			try {
				Result rs = future.get();
				System.out.println(rs);
			}  catch (InterruptedException e) {
	            // Re-assert the thread's interrupted status
	            Thread.currentThread().interrupt();
	            // We don't need the result, so cancel the task too
	            future.cancel(true);
	        } catch (ExecutionException e) {
	        	e.printStackTrace();
	        }
	}
}

class Result{
	public String result;
	public Result(){}
	public Result(int i){result = ""+i;}
	@Override public String toString(){return "Result:" + result;}
}
