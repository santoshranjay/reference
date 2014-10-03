package com.san.ex.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

public class AmqMsgComm {
	public static ActiveMQConnectionFactory cf;

	static{
		  	cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		    RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
		    redeliveryPolicy.setInitialRedeliveryDelay(10000);
		    redeliveryPolicy.setBackOffMultiplier(2);
		    redeliveryPolicy.setUseExponentialBackOff(true);
		    redeliveryPolicy.setMaximumRedeliveries(-1);

		    cf.setRedeliveryPolicy(redeliveryPolicy);
		    cf.setUseRetroactiveConsumer(true);
		    cf.setClientIDPrefix("ID");
	}
	
	Connection getConnection() throws JMSException{
		return cf.createConnection();
	}
	
	void sendMsg(String msg) throws NamingException, JMSException{
		Connection conn = getConnection();
		conn.start();
		Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = sess.createQueue("testq");
		MessageProducer producer = sess.createProducer(destination);
		Message txtmsg = sess.createTextMessage(msg);
		producer.send(txtmsg);
		conn.close();
	}
	
	// Transaction
	void recieveMsg(final MessageHandler mh) throws NamingException, JMSException {
		Connection conn = cf.createConnection();
		
		conn.start();
		final Session sess = conn.createSession(true, Session.SESSION_TRANSACTED);
		Destination destination = sess.createQueue("testq");
		MessageConsumer consumer = sess.createConsumer(destination);
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				System.out.println("Recv msg " + message);
				if(message instanceof TextMessage){
					try {
						mh.handle(((TextMessage)message).getText());
						try {
							sess.commit();
						} catch (JMSException e) {
							e.printStackTrace();
						}
					} catch (HandlerException | JMSException e) {
						try {
							e.printStackTrace();
							sess.rollback();
						} catch (JMSException e1) {
							e1.printStackTrace();
						}
					}
				}
				
			}
		});
	}
	
	String recieveMsg() throws NamingException, JMSException {
		Connection conn = cf.createConnection();
		conn.start();
		Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = sess.createQueue("testq");
		MessageConsumer consumer = sess.createConsumer(destination);
		Message msg = consumer.receive(5);
		String text = msg == null ? null : ((TextMessage)msg).getText();
		conn.close();
		return text;
	}
	
	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		AmqMsgComm msgcomm = new AmqMsgComm();
//		System.out.println("Msg Recvd " + msgcomm.recieveMsg());
		msgcomm.sendMsg("Msg 1: Test msg");
//		System.out.println("Msg Recvd " + msgcomm.recieveMsg());
		msgcomm.sendMsg("Msg 2: Test msg");
		msgcomm.sendMsg("Msg 3: Test msg");
//		System.out.println("Msg Recvd " + msgcomm.recieveMsg());
//		System.out.println("Msg Recvd " + msgcomm.recieveMsg());
		msgcomm.recieveMsg(new MessageHandler() {
			@Override
			public void handle(String msg) throws HandlerException {
				if(msg.contains("Msg 2")) throw new HandlerException();
			}
		} );
	}
	
	public static interface MessageHandler{
		public void handle(String msg) throws HandlerException;
	}
	
	public static class HandlerException extends Exception{

		private static final long serialVersionUID = 6816958395330301206L;
		
	}
}
