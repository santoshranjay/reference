package javaUtilConcurrent;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CacheImpl {
	
	public static void main(String[] args) {
		CacheImpl c = new CacheImpl();
		Random rand = new Random(10L);
		for(int i=0; i<30; i++ ){
			int j = rand.nextInt(5); 
			try {
				System.out.print("val of " + j + ": ");
				System.out.println(c.getResOfExpComp(j+""));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public interface Computable<T,V>{
		public V compute(T t);
	}
	public class ExpFunc implements Computable<String,String>{
		public String compute(String s){
			try {
				Thread.sleep(1000L*10L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return s;
		}
	}
	private ConcurrentMap<String, Future<String>> cache = new ConcurrentHashMap<String, Future<String>>();
	
	public String getResOfExpComp(final String s) throws InterruptedException{
		Future<String> future = cache.get(s);
		if(future != null)
			try {
				return future.get();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		FutureTask<String> ft = new FutureTask<String>(
				new Callable<String>(){
					public String call() throws Exception {
						return new ExpFunc().compute(s);
					}
				});
		future = cache.putIfAbsent(s, ft);
		try {
			if(future==null){
				ft.run();
//				ft.get();
			}
			return cache.get(s).get();
		}  catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}


