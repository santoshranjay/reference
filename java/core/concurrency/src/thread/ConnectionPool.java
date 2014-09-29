package thread;
/**
 * Key notes
 * ---------
 * dameon thread terminates one the user (main) thread terminates.to ensure the completeness of all these worker thread before the jvm terminates
 * i. set dameon as false or
 * ii. sleep the main thread for long time or
 * iii. add shutdown hook & join all the worker threads to it (this will ensure finishing the worker threads in case of vm shutdown).
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Thread sync. typical use of wait/notify in a resource/connection pool
 * @author Guest
 *
 */
public class ConnectionPool {
	private List<Connection> connections = createConnection();
	
	private List<Connection> createConnection(){
		List<Connection> conns =  new ArrayList<Connection>(5);
		//create initial conn.
		for (int i=0; i<5; i++) {
			conns.add(new Connection());
		}
		return conns;
	}
	
	public Connection getConnection(){
		String tname = Thread.currentThread().getName() ;
		synchronized(connections){
			while(connections.isEmpty()){
				try {
					System.out.println(tname + ":no connection available now.waiting to get one...");
					connections.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(tname + ":got connection." + connections.get(0));
			return connections.remove(0);
		}
	}
	
	public void returnConnection(Connection con){
		String tname = Thread.currentThread().getName() ;
		synchronized(connections){
			System.out.println(tname+ ":return connection." + con);
			connections.add(con);
			connections.notifyAll();
			
		}
//		connections.notifyAll();
	}
	
	//test connection pool
	public static void main(String[] args) throws InterruptedException{
		final Random rand = new Random();
		final ConnectionPool cpool = new ConnectionPool();
		//some threads (10) getting connection, doing job and returning connection asynch..
		for(int i=0; i<10; i++){
			final Thread t = new Thread(new Runnable(){
				public void run(){
					//get a connection.
					Connection con = cpool.getConnection();
					//do job.
					long stime = rand.nextInt(6000);
					System.out.println("Estimated time for job completion:" + stime);;
					try {
						Thread.sleep(stime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//return connection once the job done.
					cpool.returnConnection(con);
				}
			});
			/* dameon thread terminates one the user (main) thread terminates.to ensure
			 * the completeness of all these worker thread before the jvm terminates
			 * i. set dameon as false or
			 * ii. sleep the main thread for long time or
			 * iii. add shutdown hook & join all the worker threads to it (this will ensure finishing the worker threads in case of vm shutdown).
			 */
			/*Runtime.getRuntime().addShutdownHook(new Thread(){
				public void run(){
					try {
						t.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});*/
			t.setDaemon(false);
			
			t.start();
		}
//		System.exit(0);
//		Thread.sleep(Long.MAX_VALUE);
	}
}

class Connection{
	private static int counter=0;
	private String conn = "Connection" + ++counter; 
	public String toString(){
		return conn;
	}
}
