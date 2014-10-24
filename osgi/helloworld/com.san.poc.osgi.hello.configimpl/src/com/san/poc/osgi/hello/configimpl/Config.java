package com.san.poc.osgi.hello.configimpl;

import aQute.bnd.annotation.metatype.Meta.AD;
import aQute.bnd.annotation.metatype.Meta.OCD;

@OCD
public interface Config {
	
	@AD(deflt="osgi world")
	String helloMsg();

}
