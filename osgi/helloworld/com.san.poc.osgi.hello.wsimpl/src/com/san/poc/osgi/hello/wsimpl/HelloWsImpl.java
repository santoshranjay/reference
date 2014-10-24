package com.san.poc.osgi.hello.wsimpl;

import aQute.bnd.annotation.component.Component;

import com.san.poc.osgi.hello.api.Hello;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Component
@Path("/hello")
public class HelloWsImpl implements Hello {

	@GET
	@Path("greet")
	@Produces("text/plain")
	@Override
	public String hello(@QueryParam("to")String hellomsg) {
		return "Hello " + hellomsg + "!";
	}

}
