package com.san.ex.jms;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MsgComm {

	InitialContext getInitialContext() throws NamingException{
		Properties env = new Properties();
		env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		env.setProperty(Context.PROVIDER_URL, "http://localhost:1099");
		env.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming");
		return new InitialContext(env);
	}
	
	void sendMsg(String msg) throws NamingException, JMSException{
		InitialContext ctx = getInitialContext();
		//1. lookup the connection factory and destination (queue/topic)
		ConnectionFactory cf = (ConnectionFactory)ctx.lookup("ConnectionFactory");
		Queue q = (Queue)ctx.lookup("queue/A");
		//2. create a connection and get a session
		Connection conn = cf.createConnection();
		Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//3. create a message producer
		MessageProducer producer = sess.createProducer(q);
		//4. create a message and send via producer
		Message txtmsg = sess.createTextMessage(msg);
		producer.send(txtmsg);
	}
	
	void recieveMsg() throws NamingException, JMSException {
		InitialContext ctx = getInitialContext();
		//1. lookup the connection factory and destination (queue/topic)
		ConnectionFactory cf = (ConnectionFactory)ctx.lookup("ConnectionFactory");
		Queue q = (Queue)ctx.lookup("queue/A");
		//2. create a connection and get a sessioin
		Connection conn = cf.createConnection();
		Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//3. create a consumer and register the listener to recieve the message
		MessageConsumer consumer = sess.createConsumer(q);
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg) {
				System.out.println(msg);
			}
		});
	}
}
