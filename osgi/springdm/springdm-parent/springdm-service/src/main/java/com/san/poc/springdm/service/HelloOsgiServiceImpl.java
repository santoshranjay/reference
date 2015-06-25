package com.san.poc.springdm.service;

public class HelloOsgiServiceImpl implements HelloOsgiService {

	public String greet(String msg) {
		return "Hello  " + msg;
	}

}
