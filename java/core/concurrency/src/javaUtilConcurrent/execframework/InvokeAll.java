package javaUtilConcurrent.execframework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class InvokeAll {
	static ExecutorService exec = Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) {
		List<Task> tasks = new ArrayList<Task>();
		for(int i=1; i<11; i++){
			tasks.add(new Task(i));
		}
		List<Future<Result>> futurelist = null;
		try {
//			futurelist = exec.invokeAll(tasks);
			futurelist = exec.invokeAll(tasks,2000L,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		exec.shutdown();
		Iterator<Task> taskitr = tasks.iterator();
		for (Future<Result> future : futurelist) {
			Task t=null;
			try {
				t = taskitr.next();
				Result rs = future.get();
				System.out.println(rs);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch(CancellationException e){
				System.out.println("Task timedout:" + t);
			}
		}
	}
}

class Task implements Callable<Result>{
	private Result rs;
	private int j;
	Task(){j=0; rs = new Result(j);	}
	Task(int i){j=i;rs = new Result(i);}
	public Result call() throws Exception {
		System.out.println("Started:[" + j+ "]" + Thread.currentThread().getName());
		Thread.sleep(1000L*j);
		System.out.println("Finished:[" + j+ "]" + Thread.currentThread().getName());
		return rs;
	}
	
	@Override public String toString(){return "Task:" + j + rs;}
}
