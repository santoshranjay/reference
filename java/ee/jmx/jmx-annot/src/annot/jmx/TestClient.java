package annot.jmx;

import java.util.concurrent.TimeUnit;

public class TestClient {
	public static void main(String[] args) throws NotABeanException, InterruptedException {
		Agent agent = new Agent();
		agent.deploy(new TestAnnotBean());
		TimeUnit.MINUTES.sleep(5);
	}
	
	
}
