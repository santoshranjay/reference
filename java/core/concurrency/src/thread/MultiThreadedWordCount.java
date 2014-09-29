package thread;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Design -
 * 1. each file will be read in different thread. (IOTask, which creates a wordcount task (cpu intesive task) with the 
 * file contents and submit the task into the consumer queue.
 * 
 * 2. the wordcount task will count the words and save the count in the map (filename, wordocount).
 * 
 * @author Santoshkumar
 *
 */
public class MultiThreadedWordCount {
	public static final int DEFAULT_IOTHREAD_COUNT = 1;
	public static final int DEFAULT_CPUTHREAD_COUNT = Runtime.getRuntime().availableProcessors();
	public static final int IOTHREAD_COUNT;
	public static final int CPUTHREAD_COUNT;
	
	private final Map<String, AtomicInteger> countMap = new ConcurrentHashMap<String, AtomicInteger>();
	private final ExecutorService ioService = Executors.newFixedThreadPool(IOTHREAD_COUNT);
	private final ExecutorService cpuService = Executors.newFixedThreadPool(CPUTHREAD_COUNT);

	static{
		IOTHREAD_COUNT = Integer.getInteger("iothreadcount", DEFAULT_IOTHREAD_COUNT);
		CPUTHREAD_COUNT = Integer.getInteger("cputhreadcount", DEFAULT_CPUTHREAD_COUNT);
	}
	
	public static void main(String[] args) throws InterruptedException {
		String path = args[0];
		MultiThreadedWordCount mtwc = new MultiThreadedWordCount();
		long st = System.currentTimeMillis();
		mtwc.doWordCount(new File(path));
		System.out.printf("Total Time - %d sec, iothreads - %d, cputhreads - %d ",(System.currentTimeMillis()-st  )/1000 ,IOTHREAD_COUNT,CPUTHREAD_COUNT);
	}
	
	private void doWordCount(File file) throws InterruptedException {
		createAndSubmitIOTask(file);
		ioService.shutdown();
		ioService.awaitTermination(1, TimeUnit.HOURS);
		cpuService.shutdown();
		cpuService.awaitTermination(1, TimeUnit.HOURS);
		int tw=0;
		for (Entry<String, AtomicInteger> entry : countMap.entrySet()) {
			System.out.println("file:wordcount - " + entry.getKey() + ":" + entry.getValue());
		}
		for (AtomicInteger ai : countMap.values()) {
			tw = tw + ai.get();
		}
		System.out.println("Total Wrods: " + tw);
	}
	
	private void createAndSubmitIOTask(File file){
		if (!file.isDirectory()) {
			ioService.submit(new IOTask(file, cpuService, countMap));
			return;
		}
		File[] files = file.listFiles();
		for (File f : files) {
			createAndSubmitIOTask(f);
		}
	}

	private static class IOTask implements Callable<Integer>{
		File file;
		ExecutorService execs;
		Map<String, AtomicInteger> map;
		
		public IOTask(File file, ExecutorService execs, Map<String, AtomicInteger> map) {
			this.file = file;this.execs =execs; this.map = map;
		}
		
		@Override
		public Integer call() throws Exception {
//			System.out.println(Thread.currentThread().getName() + " :reading file" + file.getAbsolutePath());
			int nl=0;
			if(!map.containsKey(file.getAbsolutePath())) map.put(file.getAbsolutePath(), new AtomicInteger());
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				WordCountTask task = new WordCountTask(scanner.nextLine(), file.getAbsolutePath(), map);
				execs.submit(task);nl++;
			}
			scanner.close();
//			System.out.println(Thread.currentThread().getName() + " :done");
			return nl;
			
		}
	}
	
	private static class WordCountTask implements Callable<Integer>{
		private String str;
		private String fileName;
		private Map<String, AtomicInteger> countMap;
		
		public WordCountTask(String str, String fileName, Map<String, AtomicInteger>  countMap) {
			this.str = str;this.fileName = fileName; this.countMap= countMap;
		}
		
		@Override
		public Integer call() throws Exception {
//			System.out.println(Thread.currentThread().getName() + " : wordcount started");
			int wcount=0;
			Scanner scanner = new Scanner(str);
			while(scanner.hasNext()){scanner.next(); wcount++;}
			int oldCount = countMap.get(fileName).get();
			while(!countMap.get(fileName).compareAndSet(oldCount, oldCount+wcount))oldCount = countMap.get(fileName).get();
//			System.out.println(Thread.currentThread().getName() + " : wordcount done");
			return wcount;
		}
		
	}
	
	
	
}
