/**
 * 
 In Goetz's "Java Concurrency in Practice", in a footnote on page 101, he writes "For computational problems like this
  that do not I/O and access no shared data, Ncpu or Ncpu+1 threads yield optimal throughput; more threads do not help
  , and may in fact degrade performance..."

My question is, when performing I/O operations such as file writing, file reading, file deleting, etc, are there guidelines for the number of threads to use 
to achieve maximum performance? I understand this will be just a guide number, since disk speeds and a host of other factors play into this.

Still, I'm wondering: can 20 threads write 1000 separate files to disk faster than 4 threads can on a 4-cpu machine?
 */
package thread;

import java.util.concurrent.Callable;

//word count in the directory.
public class ThreadDesign {
	static final int nDefault = Runtime.getRuntime().availableProcessors();
	static final int nFiles;
	static final int nIOThreads;
	static final int nCpuThreads;
	
	static{
		nFiles = Integer.getInteger("nFiles", nDefault);
		nIOThreads = Integer.getInteger("nIOThreads", nDefault);
		nCpuThreads = Integer.getInteger("nCpuThreads", nDefault);
	}
	
}

class WordCount implements Callable<Integer>{

	private String string;
	
	public WordCount(String string) {
		this.string = string;
	}
	@Override
	public Integer call() throws Exception {
			   string = string.trim();
			   if (string.isEmpty()) return 0;
			   return string.split("\\s+").length; //separate string around spaces
	}
	
}
