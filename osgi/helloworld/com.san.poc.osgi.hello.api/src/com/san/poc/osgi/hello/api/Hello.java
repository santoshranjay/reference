package com.san.poc.osgi.hello.api;

import aQute.bnd.annotation.ProviderType;

@ProviderType
public interface Hello{

	String hello(String hellomsg);
}
