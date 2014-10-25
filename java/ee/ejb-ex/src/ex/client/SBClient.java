package ex.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.hp.training.business.GreetingService;


public class SBClient {
	private static InitialContext ctx;
	
	static{
		Properties p = new Properties();
		p.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		p.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
		try {
			ctx = new InitialContext(p);
			System.out.println("got init ctx "  + ctx);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public void CustomerTest(){
		GreetingService cm = null;
		try {
			cm = (GreetingService)ctx.lookup("myEAR/GreetingServiceBean/remote");
			cm.sayHello("lll");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new SBClient().CustomerTest();
	}
}
