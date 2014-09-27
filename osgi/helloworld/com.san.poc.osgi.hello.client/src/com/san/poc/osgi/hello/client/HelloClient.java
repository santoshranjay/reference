package com.san.poc.osgi.hello.client;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

import com.san.poc.osgi.hello.api.Hello;

@Component
public class HelloClient {
	private Hello hello;
	
	@Reference
	public void setHello(Hello hello) {
		this.hello = hello;
	}
	
	@Activate
	public void activate(){
		System.out.println(hello.hello("World"));
	}
	

}
