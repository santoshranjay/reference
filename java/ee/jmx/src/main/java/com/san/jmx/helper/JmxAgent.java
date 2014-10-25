package com.san.jmx.helper;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import mx4j.tools.adaptor.http.HttpAdaptor;
import mx4j.tools.adaptor.http.XSLTProcessor;

public class JmxAgent {
	private static MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
	
	
	/**
	 * register the given MBean to the MBeanServer
	 * @param obj
	 * @param ObjectName
	 */
	public static void deployMBean(Object obj, String objectName){
		ObjectName name;
		try {
			name = new ObjectName(objectName);
			mBeanServer.registerMBean(obj, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void startMx4jAdapter(int port){
		HttpAdaptor adapter = new HttpAdaptor();
		XSLTProcessor proc = new XSLTProcessor();
		proc.setDefaultPage("serverbydomain");
		proc.setLocaleString("en");
		proc.setPathInJar("mx4j/tools/adaptor/http/xsl");
		proc.setFile("mx4j-tools.jar");
		adapter.addAuthorization("admin", "admin");
		adapter.setProcessor(proc);
		ObjectName httpAdaptor = null;
		try {
			httpAdaptor = new ObjectName("Server:name=HttpAdaptor");
			mBeanServer.registerMBean(adapter, httpAdaptor);
			adapter.setPort(port);
			adapter.setHost("0.0.0.0");
			adapter.start();
			System.out.println("started at " + port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}
