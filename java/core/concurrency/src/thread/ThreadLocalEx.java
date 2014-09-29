package thread;
import java.text.SimpleDateFormat;
import java.util.Date;


/*public class ThreadLocalEx {
	void test(){
		Foo f = new Foo();
		for(int i=0; i<5; i++){
		String s = f.formatIt(new Date());
		try {
			Thread.sleep(1000*5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(s);
		}
	}
	public static void main(String[] args) {
		ThreadLocalEx t = new ThreadLocalEx();
		t.test();
//		
		for(int i=0; i<10; i++){
			new Thread(new Runnable(){
				public void run(){
					
					try {
						Thread.sleep(5*1000);
						System.out.println(Thread.currentThread().getName() + ":" + new Foo().formatIt(new Date(new Date().getTime()+10000)));
						Thread.sleep(5*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}*/

 class Foo {    
	//  SimpleDateFormat is not thread-safe, so give one to each thread 
		private static final ThreadLocal<SimpleDateFormat> formatter = 
			new ThreadLocal<SimpleDateFormat>() {
			
				@Override
				protected SimpleDateFormat initialValue() {
					return new SimpleDateFormat("yyyy/MM/dd HH:mm");
				}
			};

	public String formatIt(Date date) {
		System.out.println(formatter.get() + " : " + formatter.hashCode());
		return formatter.get().format(date);
	} 
}
 
 //A thread is a unit of execution and so multiple thread can execute the same code at the same time. 
 //If multiple threads execute on an object/instance at the same time they will share the instance variables. 
 //Each thread will have its own local variables but it is difficult to share these across objects without passing parameters.
 class LO{
	 String name = "default";
	 public LO(){}
	 public LO(String n){name=n;}
	 @Override public String toString(){return name+":";}
	 @Override public int hashCode(){return name.hashCode();}
	 public void setName(String str){name +=str;}
 }
 /*class TL{
	 private static ThreadLocal<LO> los = new ThreadLocal<LO>();
	 @Override public String toString(){return los.get().toString();}
 }*/

public class ThreadLocalEx{
	 private static ThreadLocal<LO> los = new ThreadLocal<LO>(){
		 @Override protected LO initialValue(){return new LO();}
	 };
	 private static volatile int in = 0;
	public static void main(String[] args) {
		for(int i=0; i<5; i++){
			new Thread(new Runnable(){
//				private LO lo = new LO();
				public void run(){
					double x = 102589 * 3432432 * 4325325;
					x = x * x *2 - 234/2;
//					lo = new LO(Thread.currentThread().getName());
					x = 102589 * 3432432 * 4325325;
					x = x * x *2 - 234/2;
//					los.set(new LO(Thread.currentThread().getName()));
					LO lo = los.get();
					lo.setName(""+(in++));
					try {
						Thread.sleep(5*1000);
						System.out.println(Thread.currentThread().getName() + lo);
						Thread.sleep(5*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}).start();
		}
	}
}

