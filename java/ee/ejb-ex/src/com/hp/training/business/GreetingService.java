package com.hp.training.business;
import javax.ejb.Remote;

@Remote
public interface GreetingService {
	public String sayHello(String s);
}
