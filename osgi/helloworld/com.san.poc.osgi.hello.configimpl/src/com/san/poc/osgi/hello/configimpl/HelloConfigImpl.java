package com.san.poc.osgi.hello.configimpl;

import java.util.Map;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.metatype.Configurable;

import com.san.poc.osgi.hello.api.Hello;

@Component(immediate=true,designate=Config.class)
public class HelloConfigImpl implements Hello{
	Config config;

	@Activate
	public void activate(Map<String, String> properties){
		System.out.println("Activating...");
		config = Configurable.createConfigurable(Config.class, properties);
		System.out.println("Activated config " + config.helloMsg());
	}
	
	@Override
	public String hello(String hellomsg) {
		return "Hello " + config.helloMsg() + "!";
	}

}
