package annot.jmx;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import annot.Test;

public class Agent {
	public void deploy(Object obj) throws NotABeanException{
		Class beanClz = obj.getClass();
		if(!beanClz.isAnnotationPresent(MBean.class))throw new NotABeanException();
		createDynBean(obj);
		for (Method m : beanClz.getDeclaredMethods()) {
			if (m.isAnnotationPresent(ExpMd.class)) {
				createMethod(m);
			}
		}
		for (Field f : beanClz.getDeclaredFields()) {
			if (f.isAnnotationPresent(ExpAttr.class)) {
				createAttribute(f);
			}
		}
	}
	void createDynBean(Object obj){
		
	}
	void createMethod(Method m){
		
	}
	void createAttribute(Field f){
		
	}
}

class NotABeanException extends Exception{

	private static final long serialVersionUID = 8976218614077653697L;
	
	public String getMessage(){
		return "not a bean";
	}
}