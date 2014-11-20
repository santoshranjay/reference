package com.san.poc.osgi.hello.clientfactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

import com.san.poc.osgi.hello.api.Hello;

@Component
public class HelloClientFactory {
	
	private Map<String, Hello> helloServiceMap = new ConcurrentHashMap<String, Hello>();
	
	@Reference(dynamic=true,multiple=true)
	public void setHello(Hello hello, Map<?, ?> map) {
			helloServiceMap.put((String)map.get("component.name"), hello);
	}
	
	public void unsetHello(Hello hello, Map<?, ?> map) {
		helloServiceMap.remove((String)map.get("component.name"));
	}
	
	@Activate
	public void activate(){
		for (String key : helloServiceMap.keySet()) {
			System.out.println((helloServiceMap.get(key)).hello("world! - from "+key));
		}
	}
	

}
