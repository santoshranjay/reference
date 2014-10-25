package com.hp.training.business;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class GreetingServiceBean
 */
@Stateless
public class GreetingServiceBean implements GreetingService {

    /**
     * Default constructor. 
     */
    public GreetingServiceBean() {
        // TODO Auto-generated constructor stub
    }

	public String sayHello(String s) {
		return "Hello " + s + " !";
	}

}
