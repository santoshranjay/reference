package thread;

public class Deadlock {

	static void syncAccess(Object a, Object b) throws InterruptedException {
		System.out.println(a + "," +b);
		synchronized (a) {
			Thread.sleep(100);
			System.out.println("done with " + a);
			synchronized (b) {
				System.out.println("done with" + b);
			}

		}
	}

	public static void main(String[] args) {
		final Object a = new Object();
		final Object b = new Object();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Deadlock.syncAccess(a, b);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Deadlock.syncAccess(b, a);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
