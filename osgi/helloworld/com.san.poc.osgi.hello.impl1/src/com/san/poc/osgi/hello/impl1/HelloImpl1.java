package com.san.poc.osgi.hello.impl1;

import aQute.bnd.annotation.component.Component;

import com.san.poc.osgi.hello.api.Hello;

@Component(immediate=true, name=HelloImpl1.HELLO_IMPL1,properties="p1=prop1,p2=prop2|prop21")
public class HelloImpl1 implements Hello {

	static final String HELLO_IMPL1 = "hello:impl1";

	@Override
	public String hello(String hellomsg) {
		return "Hello " + hellomsg + "!";
	}

}
