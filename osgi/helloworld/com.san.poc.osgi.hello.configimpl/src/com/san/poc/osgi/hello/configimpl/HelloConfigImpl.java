package com.san.poc.osgi.hello.configimpl;

import java.util.Map;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.ConfigurationPolicy;
import aQute.bnd.annotation.metatype.Configurable;

import com.san.poc.osgi.hello.api.Hello;

@Component(designate=HelloConfigImplConfig.class, configurationPolicy=ConfigurationPolicy.optional)
public class HelloConfigImpl implements Hello{
	HelloConfigImplConfig config;

	@Activate
	public void activate(Map<String, Object> properties){
		System.out.println("Activating...");
		config = Configurable.createConfigurable(HelloConfigImplConfig.class, properties);
		System.out.println("Activated config " + config.helloMsg());
	}
	
	@Override
	public String hello(String hellomsg) {
		return "Hello " + config.helloMsg() + "!";
	}

}

