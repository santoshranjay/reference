package thread;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;


public class ConnectionPoolTest {

	final ConnectionPool connectionPool = new ConnectionPool();
	
	
	@Before
	public void setUp() throws Exception {
		
		
	}

	@Test
	public final void testPool() {
		final Random rand = new Random();
		final int nThreads = 500;
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch endLatch = new CountDownLatch(nThreads);
		
		for (int i = 0; i <nThreads; i++) {
			
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						startLatch.await();
						//get connection
						Connection con = connectionPool.getConnection();
						// do job. return connection once the job done.
						connectionPool.returnConnection(con);
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						endLatch.countDown();
					}
				}
			});
			t.start();
		}
			try {
				System.out.println("Starting all Connection user threads at once.");
				startLatch.countDown();
				endLatch.await();
				System.out.println("Finished all connection user threads.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}

}
