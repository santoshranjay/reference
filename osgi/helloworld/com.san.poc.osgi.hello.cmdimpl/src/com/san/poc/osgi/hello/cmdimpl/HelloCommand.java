package com.san.poc.osgi.hello.cmdimpl;

import org.apache.felix.service.command.CommandProcessor;

import com.san.poc.osgi.hello.api.Hello;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component(properties={
		CommandProcessor.COMMAND_SCOPE+":String=san",
		CommandProcessor.COMMAND_FUNCTION+":String=hello"
}, provide=Object.class)
public class HelloCommand {
	private Hello hello;
	
	@Reference
	public void setHello(Hello hello) {
		this.hello = hello;
	}
	
	public void hello(String msg){
		System.out.println(hello.hello(msg));
	}

}
