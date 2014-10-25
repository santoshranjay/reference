package com.san.jmx.service;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

public class EmailNotificationListener
               implements NotificationListener, NotificationFilter {
	private static final long serialVersionUID = -7356655490623609721L;
	private MailingService mailingService;
	
	public EmailNotificationListener(MailingService mailingService) {
		this.mailingService = mailingService;
	}
	
	public void handleNotification(Notification notification, Object handback) {
        System.out.println(notification);
        System.out.println(handback);
        System.out.println("sending mail...");
        mailingService.sendMail("santoshranjay@gmail.com", notification.toString());
        System.out.println("sent mail");
        
    }

    public boolean isNotificationEnabled(Notification notification) {
        return AttributeChangeNotification.class.isAssignableFrom(notification.getClass());
//    	return true;
    }
}