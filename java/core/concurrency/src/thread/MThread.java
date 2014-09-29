package thread;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

class Msg{
	private static double counter=0;
	public String getMessage(){
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return ""+ counter + (Math.PI + Math.E)/++counter  ;
	}
}

public class MThread implements Runnable{
	private static Vector<String> vector = new Vector<String>();
	private static PrintWriter pw;
	
	static{
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter("C:" + File.separator + "tlog.log")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void run(){
		Msg m = new Msg();
		vector.add(m.getMessage());
	}
	
	public static void main(String args[])throws Exception{
		Thread t = new Thread(new Runnable(){
			public void run(){
				int i=0;
				for(;;){
					if(vector.size()>0){
						Object[] objarray = vector.toArray();
						vector.clear();
						System.out.print(i + ":" + objarray.length + "-");
						pw.print(i + ":" + objarray.length + "-");
						for (Object obj : objarray) {
							String str = (String)obj;
							System.out.print( str+"-");
							pw.print( str+"-");
						}
						System.out.println();
						pw.println();
						pw.flush();
					}
					else System.out.println("Nothing to print.");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.setDaemon(true);
		t.start();
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				for(int i=0;i<50;i++)
					new Thread(new MThread()).start();
			}
		}, 1000, 1000);
			
	}

}
