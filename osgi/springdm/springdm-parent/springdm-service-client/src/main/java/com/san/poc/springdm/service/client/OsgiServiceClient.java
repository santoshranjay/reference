package com.san.poc.springdm.service.client;

import com.san.poc.springdm.service.HelloOsgiService;

public class OsgiServiceClient {

	public OsgiServiceClient(HelloOsgiService service){
		System.out.println(service.greet("osgi service."));
	}
	
	/*private  HelloOsgiService helloService;

	public HelloOsgiService getHelloService() {
		return helloService;
	}

	public void setHelloService(HelloOsgiService helloService) {
		this.helloService = helloService;
	}*/
}
