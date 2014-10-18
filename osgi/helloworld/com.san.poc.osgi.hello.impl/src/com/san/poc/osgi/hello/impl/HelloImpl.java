package com.san.poc.osgi.hello.impl;

import aQute.bnd.annotation.component.Component;

import com.san.poc.osgi.hello.api.Hello;

@Component(immediate=true)
public class HelloImpl implements Hello {

	@Override
	public String hello(String hellomsg) {
		return "Hello " + hellomsg + "!";
	}

}
