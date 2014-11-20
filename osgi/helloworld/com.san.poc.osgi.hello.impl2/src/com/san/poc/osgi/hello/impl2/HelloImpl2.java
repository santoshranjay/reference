package com.san.poc.osgi.hello.impl2;

import aQute.bnd.annotation.component.Component;

import com.san.poc.osgi.hello.api.Hello;

@Component(immediate=true, name=HelloImpl2.HELLO_IMPL2,properties="p1=propt1,p2=propt2|propt21")
public class HelloImpl2 implements Hello {

	static final String HELLO_IMPL2 = "hello:impl2";

	@Override
	public String hello(String hellomsg) {
		return "Hello " + hellomsg + "!";
	}

}
